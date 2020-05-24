package com.bzb.atjob.common.util;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

/**
 * 针对 Mybatis 的工具方法
 */
public class MybatisUtil {
    /**
     * 生成排序语句
     * 
     * @param sort                       排序字符串，格式类似：userName.ascend-userAgd.decend
     * @param additionSortPropName       额外排序属性名。因为如果用户指定的排序属性数据全部一样，在物理分页的时候每页数据将不稳定，需要附加一个值唯一的排序属性，一般使用主键即可
     * @param getFieldNameByPropNameFunc 从属性名获取数据库字段名的方法
     * @return
     */
    static public String getOrderByClause(String sort, String additionSortPropName,
            Function<String, String> getFieldNameByPropNameFunc) {
        if (StringUtils.isNotBlank(sort)) {
            String orderByClause = Stream.of(sort.split("-")).map(t -> {
                var sortSpec = t.split("[.]");
                var fieldName = sortSpec[0];
                var direction = sortSpec[1];
                var orderBy = String.format("%s %s", getFieldNameByPropNameFunc.apply(fieldName),
                        StringUtils.equals(direction, "ascend") ? "ASC" : "DESC");
                return orderBy;
            }).collect(Collectors.joining(", ", "", ", " + getFieldNameByPropNameFunc.apply(additionSortPropName)));
            return orderByClause;
        } else {
            return "";
        }
    }
}