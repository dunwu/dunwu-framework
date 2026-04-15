package io.github.dunwu.common.dal.elasticsearch;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RegexpQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import io.github.dunwu.common.dal.constant.NamingStrategy;
import io.github.dunwu.common.dal.constant.QueryJudgeType;
import io.github.dunwu.common.dal.elasticsearch.annotation.QueryDocument;
import io.github.dunwu.common.dal.elasticsearch.annotation.QueryField;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Elasticsearch 查询构建工具类
 * <p>
 * 提供将带有 {@link QueryDocument} 和 {@link QueryField} 注解的 Java 对象
 * 转换为 Elasticsearch Java API Client 所需的 {@link Query} 和 {@link SortOptions} 列表的功能。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-18
 */
public class ElasticSearchUtil {

    /**
     * 模糊查询正则表达式模板
     */
    private static final String LIKE_REGEX_TEMPLATE = ".*%s.*";

    /**
     * 私有构造方法，防止实例化
     */
    private ElasticSearchUtil() { }

    /**
     * 将查询对象转换为 Elasticsearch 查询构建器列表
     * <p>
     * 解析对象中标记了 {@link QueryField} 的字段，并根据其配置生成对应的 {@link Query}。
     *
     * @param queryBean 包含查询条件的 Java 对象，必须标注 {@link QueryDocument}
     * @return 查询构建器列表
     * @throws IllegalAccessException 如果反射访问字段失败
     * @throws IllegalArgumentException 如果对象未标注 {@link QueryDocument}
     */
    public static List<Query> transToQueryBuilders(final Object queryBean) throws IllegalAccessException {

        QueryDocument document = queryBean.getClass().getAnnotation(QueryDocument.class);
        if (null == document) {
            throw new IllegalArgumentException("查询条件类定义必须使用 @QueryDocument 注解");
        }

        // 处理查询字段和字段值
        Field[] fields = queryBean.getClass().getDeclaredFields();
        NamingStrategy namingStrategy = document.namingStrategy();
        List<Query> queryBuilders = new ArrayList<>();
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

                Query query = getQuery(queryField.judgeType(), fieldName, value);
                if (query != null) {
                    queryBuilders.add(query);
                }
            }
        }

        return queryBuilders;
    }

    /**
     * 获取排序构建器列表
     * <p>
     * 根据 {@link QueryDocument} 注解中配置的排序项生成 {@link SortOptions}。
     *
     * @param queryBean 包含查询条件的 Java 对象
     * @return 排序构建器列表
     * @throws IllegalArgumentException 如果对象未标注 {@link QueryDocument}
     */
    public static List<SortOptions> getSortBuilders(Object queryBean) {
        QueryDocument document = queryBean.getClass().getAnnotation(QueryDocument.class);
        if (null == document) {
            throw new IllegalArgumentException("查询条件类定义必须使用 @QueryDocument 注解");
        }

        List<SortOptions> sortOptions = new ArrayList<>();
        if (ArrayUtil.isNotEmpty(document.orderItem())) {
            SortOrder order = "asc".equalsIgnoreCase(document.orderType().name())
                ? SortOrder.Asc : SortOrder.Desc;
            for (String orderItem : document.orderItem()) {
                SortOptions sort = SortOptions.of(s -> s
                    .field(FieldSort.of(f -> f.field(orderItem).order(order)))
                );
                sortOptions.add(sort);
            }
        }
        return sortOptions;
    }

    /**
     * 根据判断类型生成查询条件
     *
     * @param judgeType 查询判断类型
     * @param fieldName 字段名
     * @param value     字段值
     * @return 生成的 {@link Query} 对象，不支持的类型返回 null
     */
    public static Query getQuery(QueryJudgeType judgeType, String fieldName, Object value) {
        switch (judgeType) {
            case EQUALS:
                return Query.of(q -> q
                    .term(TermQuery.of(t -> t.field(fieldName).value(value.toString())))
                );
            case LIKE:
                String regexp = String.format(LIKE_REGEX_TEMPLATE, value);
                return Query.of(q -> q
                    .regexp(RegexpQuery.of(r -> r.field(fieldName).value(regexp)))
                );
            default:
                return null;
        }
    }

    /**
     * 获取字段名
     * <p>
     * 优先使用 {@link QueryField} 中指定的值，否则根据命名策略转换 Java 字段名。
     *
     * @param namingStrategy 命名策略
     * @param field          Java 字段
     * @param queryField     查询字段注解
     * @return 转换后的字段名
     */
    private static String getFieldName(NamingStrategy namingStrategy, Field field, QueryField queryField) {
        if (StrUtil.isNotBlank(queryField.value())) {
            return queryField.value();
        } else {
            return getFieldName(namingStrategy, field);
        }
    }

    /**
     * 根据命名策略获取字段名
     *
     * @param namingStrategy 命名策略
     * @param field          Java 字段
     * @return 转换后的字段名
     */
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
