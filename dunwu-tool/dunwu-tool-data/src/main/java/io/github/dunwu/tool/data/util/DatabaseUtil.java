package io.github.dunwu.tool.data.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.data.PageImpl;
import io.github.dunwu.tool.data.entity.TableColumnInfo;
import io.github.dunwu.tool.data.entity.TableInfo;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-11-28
 */
public class DatabaseUtil {

    public static List<TableColumnInfo> getColumnList(Connection connection, String schemaName, String tableName) {

        DSLContext dslContext = DSL.using(connection);

        // 查询条件
        List<Condition> conditions = new ArrayList<>();
        conditions.add(DSL.field("TABLE_SCHEMA").eq(schemaName));
        if (StrUtil.isNotBlank(tableName)) {
            conditions.add(DSL.field("TABLE_NAME").equal(tableName));
        }

        // 分页查询匹配记录
        return new ArrayList<>(dslContext.select()
                                         .from("information_schema.columns")
                                         .where(conditions)
                                         .orderBy(DSL.field("ordinal_position").asc())
                                         .fetchInto(TableColumnInfo.class));
    }

    public static Map<String, TableColumnInfo> getColumnMap(Connection connection, String schemaName, String tableName) {

        List<TableColumnInfo> list = getColumnList(connection, schemaName, tableName);
        if (CollectionUtil.isEmpty(list)) {
            return new LinkedHashMap<>(0);
        }

        Map<String, TableColumnInfo> map = new LinkedHashMap<>(list.size());
        for (TableColumnInfo dto : list) {
            map.put(dto.getColumnName(), dto);
        }
        return map;
    }

    public static TableInfo getTable(Connection connection, String schemaName, String tableName) {

        DSLContext dslContext = DSL.using(connection);

        // 查询条件
        List<Condition> conditions = new ArrayList<>();
        conditions.add(DSL.field("TABLE_SCHEMA").eq(schemaName));
        if (StrUtil.isNotBlank(tableName)) {
            conditions.add(DSL.field("TABLE_NAME").equal(tableName));
        }

        // 分页查询匹配记录
        List<TableInfo> list = new ArrayList<>(dslContext.select()
                                                         .from("information_schema.tables")
                                                         .where(conditions)
                                                         .fetchInto(TableInfo.class));

        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    public static PageImpl<TableInfo> getTables(Connection connection, String schemaNameLike, String tableName,
        Integer page, Integer size) {

        DSLContext dslContext = DSL.using(connection);

        // 查询条件
        List<Condition> conditions = new ArrayList<>();
        conditions.add(DSL.field("TABLE_SCHEMA").eq(schemaNameLike));
        if (StrUtil.isNotBlank(tableName)) {
            conditions.add(DSL.field("TABLE_NAME").likeIgnoreCase(StrUtil.format("%{}%", tableName)));
        }

        // 查询所有匹配记录数
        int total = dslContext.selectCount()
                              .from("information_schema.tables")
                              .where(conditions)
                              .fetchOne(0, int.class);

        // 计算分页偏移量
        int offset = PageUtil.getStart(page, size);

        // 分页查询匹配记录
        List<TableInfo> list = new ArrayList<>(dslContext.select()
                                                         .from("information_schema.tables")
                                                         .where(conditions)
                                                         .limit(offset, size.intValue())
                                                         .fetchInto(TableInfo.class));

        // 组装分页展示数据
        return new PageImpl<>(list, page, size, total);
    }

}
