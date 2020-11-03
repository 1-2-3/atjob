package com.bzb.atjob.architecture.lib;

import static com.tngtech.archunit.PublicAPI.Usage.ACCESS;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.PublicAPI;
import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

@PublicAPI(usage = ACCESS)
public final class MyDependencyRules {
  @PublicAPI(usage = ACCESS)
  public static final ArchRule NO_CLASSES_SHOULD_DEPEND_UPPER_PACKAGES =
      noClasses()
          .should(dependOnUpperPackages())
          .because(
              "that might prevent packages on that level from being split into separate artifacts"
                  + " in a clean way");

  @PublicAPI(usage = ACCESS)
  public static ArchCondition<JavaClass> dependOnUpperPackages() {
    return new DependOnUpperPackagesCondition();
  }

  private static class DependOnUpperPackagesCondition extends ArchCondition<JavaClass> {
    DependOnUpperPackagesCondition() {
      super("depend on upper packages");
    }

    @Override
    public void check(final JavaClass clazz, final ConditionEvents events) {
      for (Dependency dependency : clazz.getDirectDependenciesFromSelf()) {
        boolean dependencyOnUpperPackage =
            isDependencyOnUpperPackage(dependency.getOriginClass(), dependency.getTargetClass());
        events.add(
            new SimpleConditionEvent(
                dependency, dependencyOnUpperPackage, dependency.getDescription()));
      }
    }

    private boolean isDependencyOnUpperPackage(JavaClass origin, JavaClass target) {
      String originPackageName = origin.getPackageName();
      String targetSubPackagePrefix = target.getPackageName() + ".";
      return originPackageName.startsWith(targetSubPackagePrefix);
    }
  }
}
