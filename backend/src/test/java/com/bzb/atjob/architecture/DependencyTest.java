package com.bzb.atjob.architecture;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.bzb.atjob.common.annotation.AggregateRoot;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

@AnalyzeClasses(
    packages = "com.bzb.atjob.app.auth",
    importOptions = {
      ImportOption.DoNotIncludeTests.class,
      ImportOption.DoNotIncludeJars.class,
      ImportOption.DoNotIncludeArchives.class
    })
public class DependencyTest {
  @ArchTest
  public static final ArchRule layer_dependcy =
      layeredArchitecture()
          .layer("Controller")
          .definedBy("com.bzb.atjob.app..feed.web..")
          .layer("ApplicationService")
          .definedBy("com.bzb.atjob.app..application..")
          .layer("Repository")
          .definedBy("com.bzb.atjob.app..repository..")
          .layer("Model")
          .definedBy("com.bzb.atjob.app..model..")
          .layer("Mapper")
          .definedBy("com.bzb.atjob.app..mapper..")
          .whereLayer("Controller")
          .mayNotBeAccessedByAnyLayer()
          .whereLayer("ApplicationService")
          .mayOnlyBeAccessedByLayers("Controller")
          .whereLayer("Repository")
          .mayOnlyBeAccessedByLayers("ApplicationService")
          .whereLayer("Mapper")
          .mayOnlyBeAccessedByLayers("Repository")
          .as("层依赖关系");

  @ArchTest
  public static final ArchRule application_service_should_independent =
      noClasses()
          .that()
          .resideInAPackage("com.bzb.atjob.app..application")
          .should()
          .dependOnClassesThat()
          .resideInAPackage("com.bzb.atjob.app..application")
          .as("ApplicationService不应依赖其他ApplicationService");

  @ArchTest
  public static final ArchRule repository_should_independent =
      noClasses()
          .that()
          .resideInAPackage("com.bzb.atjob.app..repository")
          .should()
          .dependOnClassesThat()
          .resideInAPackage("com.bzb.atjob.app..repository")
          .as("Repository不应依赖其他Repository");

  @ArchTest
  public static final ArchRule application_service_should_depend_on_repository_inside_same_package =
      noClasses()
          .that()
          .resideInAPackage("com.bzb.atjob.app..application")
          .should(dependOnClassesThatResideInOtherParentPackageAndEndWith("Repository"))
          .as("Application Service 不应依赖其他模块的 Repository");

  @ArchTest
  public static final ArchRule aggregate_root_should_not_depend_on_other_aggregate_root =
      noClasses()
          .that()
          .areAnnotatedWith(AggregateRoot.class)
          .should()
          .dependOnClassesThat()
          .areAnnotatedWith(AggregateRoot.class)
          .as("聚合根不应依赖其他聚合根");

  private static ArchCondition<JavaClass> dependOnClassesThatResideInOtherParentPackageAndEndWith(
      String classEndWith) {
    return new ArchCondition<JavaClass>("依赖不同的上级包") {
      @Override
      public void check(JavaClass input, ConditionEvents events) {

        String inputParentPackageName = parentPackageName(input.getPackageName());

        List<String> diffParentPackageNames =
            input.getFieldAccessesFromSelf().stream()
                .filter(t -> t.getTarget().getRawType().getSimpleName().endsWith(classEndWith))
                .map(t -> parentPackageName(t.getTarget().getRawType().getPackageName()))
                .filter(t -> !StringUtils.equals(t, inputParentPackageName))
                .collect(Collectors.toList());

        boolean accessedDiffParentPackages = diffParentPackageNames.size() > 0;

        if (accessedDiffParentPackages) {
          events.add(
              SimpleConditionEvent.satisfied(
                  input,
                  input.getName() + " 依赖包: " + StringUtils.join(diffParentPackageNames, ", ")));
        }
      }

      private String parentPackageName(String packageName) {
        String[] packageNames = packageName.split("[.]");
        if (packageNames.length <= 1) {
          return "";
        }

        return StringUtils.join(Arrays.copyOfRange(packageNames, 0, packageNames.length - 1), ".");
      }
    };
  }
}
