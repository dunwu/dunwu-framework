package io.github.dunwu.data.elasticsearch;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.data.QueryJudgeType;
import io.github.dunwu.tool.bean.support.NamingStrategy;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.RegexpQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-18
 */
public class ElasticSearchUtil {

    private static final String LIKE_REGEX_TEMPLATE = ".*%s.*";

    private ElasticSearchUtil() {}

    public static List<QueryBuilder> transToQueryBuilders(final Object queryBean) throws IllegalAccessException {

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        QueryDocument document = queryBean.getClass().getAnnotation(QueryDocument.class);
        if (null == document) {
            throw new IllegalArgumentException("查询条件类定义必须使用 @QueryDocument 注解");
        }

        // 排序条件
        List<FieldSortBuilder> sortBuilders = getSortBuilders(queryBean);

        for (FieldSortBuilder sortBuilder : sortBuilders) {
            nativeSearchQueryBuilder.withSort(sortBuilder);
        }

        // 处理查询字段和字段值
        Field[] fields = queryBean.getClass().getDeclaredFields();
        NamingStrategy namingStrategy = document.namingStrategy();
        List<QueryBuilder> queryBuilders = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(queryBean);

            if (value != null) {
                // 如果字段没有被 QueryField 修饰，直接跳过
                QueryField queryField = field.getAnnotation(QueryField.class);
                if (null == queryField) {
                    continue;
                }

                // 获取查询字段实际 key
                String fieldName = getFieldName(namingStrategy, field, queryField);
                if (StrUtil.isBlank(fieldName)) {
                    continue;
                }

                QueryBuilder queryBuilder = getQueryBuilder(queryField.judgeType(), fieldName, value);
                queryBuilders.add(queryBuilder);
            }
        }

        return queryBuilders;
    }

    public static List<FieldSortBuilder> getSortBuilders(Object queryBean) {
        QueryDocument document = queryBean.getClass().getAnnotation(QueryDocument.class);
        if (null == document) {
            throw new IllegalArgumentException("查询条件类定义必须使用 @QueryDocument 注解");
        }

        List<FieldSortBuilder> sortBuilders = new ArrayList<>();
        if (ArrayUtil.isNotEmpty(document.orderItem())) {
            SortOrder order = SortOrder.fromString(document.orderType().name());
            for (String orderItem : document.orderItem()) {
                FieldSortBuilder sortBuilder = new FieldSortBuilder(orderItem).order(order);
                sortBuilders.add(sortBuilder);
            }
        }
        return sortBuilders;
    }

    public static QueryBuilder getQueryBuilder(QueryJudgeType judgeType, String fieldName, Object value) {
        QueryBuilder queryBuilder = null;

        switch (judgeType) {
            case Equals:
                queryBuilder = new TermQueryBuilder(fieldName, value);
                break;
            case Like:
                String regexp = String.format(LIKE_REGEX_TEMPLATE, value);
                queryBuilder = new RegexpQueryBuilder(fieldName, regexp);
                break;
            default:
                break;
        }
        return queryBuilder;
    }

    private static String getFieldName(NamingStrategy namingStrategy, Field field, QueryField queryField) {
        if (StrUtil.isNotBlank(queryField.value())) {
            return queryField.value();
        } else {
            return getFieldName(namingStrategy, field);
        }
    }

    private static String getFieldName(NamingStrategy namingStrategy, Field field) {
        String fieldName;
        switch (namingStrategy) {
            case CAMEL:
                fieldName = StrUtil.toCamelCase(field.getName());
                break;
            case LOWER_UNDERLINE:
                fieldName = StrUtil.toUnderlineCase(field.getName()).toLowerCase();
                break;
            case UPPER_UNDERLINE:
                fieldName = StrUtil.toUnderlineCase(field.getName()).toUpperCase();
                break;
            case LOWER_DASHED:
                fieldName = StrUtil.toSymbolCase(field.getName(), CharUtil.DASHED).toLowerCase();
                break;
            case UPPER_DASHED:
                fieldName = StrUtil.toSymbolCase(field.getName(), CharUtil.DASHED).toUpperCase();
                break;
            default:
                fieldName = field.getName();
                break;
        }
        return fieldName;
    }

}
