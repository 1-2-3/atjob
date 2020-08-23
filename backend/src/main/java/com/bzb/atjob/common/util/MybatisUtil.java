package com.bzb.atjob.common.util;

import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;

/** 针对 Mybatis 的工具方法. */
public class MybatisUtil {
  /**
   * 生成排序语句.
   *
   * @param sort 排序字符串，格式类似：userName.ascend-userAgd.decend
   * @param additionSortPropName
   *     额外排序属性名。因为如果用户指定的排序属性数据全部一样，在物理分页的时候每页数据将不稳定，需要附加一个值唯一的排序属性，一般使用主键即可
   * @param getFieldNameByPropNameFunc 从属性名获取数据库字段名的方法
   * @return
   */
  public static String getOrderByClause(
      String sort,
      String additionSortPropName,
      Function<String, String> getFieldNameByPropNameFunc) {
    if (StringUtils.isNotBlank(sort)) {
      String orderByClause =
          Stream.of(sort.split("-"))
              .map(
                  t -> {
                    var sortSpec = t.split("[.]");
                    var fieldName = sortSpec[0];
                    var direction = sortSpec[1];
                    var orderBy =
                        String.format(
                            "%s %s",
                            getFieldNameByPropNameFunc.apply(fieldName),
                            StringUtils.equals(direction, "ascend") ? "ASC" : "DESC");
                    return orderBy;
                  })
              .collect(
                  Collectors.joining(
                      ", ", "", ", " + getFieldNameByPropNameFunc.apply(additionSortPropName)));
      return orderByClause;
    } else {
      return "";
    }
  }

  /**
   * 生成用于 Mybatis plus 排序的 OrderItem 列表.
   *
   * @param sort 排序字符串，格式类似：userName.ascend-userAgd.decend
   * @param clazz 要进行排序的实体类型
   * @param isAppendPkOrder 是否追加主键作为第二排序字段。当使用分页排序时，如果第一排序字段有大量相同值，不追加一个唯一字段作为第二排序字段将导致分页数据混乱。
   * @return
   */
  public static List<OrderItem> getOrderItemList(
      String sort, Class<?> clazz, Boolean isAppendPkOrder) {
    // 属性名 -> 字段名 映射
    TableInfo tableInfo = TableInfoHelper.getTableInfo(clazz);
    Map<String, String> fieldsMap =
        tableInfo.getFieldList().stream()
            .collect(
                Collectors.toMap(
                    fieldInfo -> fieldInfo.getProperty(), fieldInfo -> fieldInfo.getColumn()));

    List<OrderItem> result = new ArrayList<OrderItem>();

    if (StringUtils.isNotBlank(sort)) {
      result =
          Stream.of(sort.split("-"))
              .map(
                  t -> {
                    var sortSpec = t.split("[.]");
                    var propName = sortSpec[0];
                    var direction = sortSpec[1];
                    return StringUtils.equals(direction, "ascend")
                        ? OrderItem.asc(fieldsMap.get(propName))
                        : OrderItem.desc(fieldsMap.get(propName));
                  })
              .collect(Collectors.toList());
    }

    if (isAppendPkOrder) {
      result.add(OrderItem.asc(tableInfo.getKeyColumn()));
    }

    return result;
  }

  private static IdentifierGenerator identifierGenerator = new DefaultIdentifierGenerator();

  /** 生成新Id. */
  public static Number nextId() {
    return identifierGenerator.nextId(new Object());
  }

  /** 创建分页对象。当 pageNum 为 null 时返回所有数据. */
  public static <T> Page<T> createPage(Long pageNum, Long pageSize) {
    if (pageNum == null) {
      return new Page<T>(1, Long.MAX_VALUE);
    } else {
      return new Page<T>(pageNum, pageSize);
    }
  }
}
