package io.github.dunwu.common.dal.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.common.dal.domain.TableColumnInfo;
import io.github.dunwu.common.dal.domain.TableInfo;
import io.github.dunwu.common.dal.domain.PageImpl;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.data.domain.PageRequest;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据库元数据查询工具类
 * <p>
 * 基于 jOOQ 封装了对 {@code information_schema} 的常用查询操作，
 * 用于获取数据库表结构、列信息以及分页查询表列表。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-11-28
 */
public class DatabaseUtil {

    private DatabaseUtil() {
    }

    /**
     * 获取指定表的列信息列表
     * <p>
     * 查询 {@code information_schema.columns} 视图，返回指定 schema 下表的所有列定义。
     *
     * @param connection 数据库连接
     * @param schemaName 数据库模式名（Schema Name）
     * @param tableName  表名，如果为空则查询该 schema 下所有表的列
     * @return 列信息列表 {@link TableColumnInfo}
     */
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

    /**
     * 获取指定表的列信息映射
     * <p>
     * 调用 {@link #getColumnList(Connection, String, String)} 并将结果转换为以列名为 Key 的 Map。
     *
     * @param connection 数据库连接
     * @param schemaName 数据库模式名
     * @param tableName  表名
     * @return 列名到列信息的映射 Map
     */
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

    /**
     * 获取指定表的元数据信息
     * <p>
     * 查询 {@code information_schema.tables} 视图，获取单个表的详细信息。
     *
     * @param connection 数据库连接
     * @param schemaName 数据库模式名
     * @param tableName  表名
     * @return 表信息对象 {@link TableInfo}，如果未找到则返回 null
     */
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

    /**
     * 分页查询指定 Schema 下的表列表
     * <p>
     * 查询 {@code information_schema.tables} 视图，支持按表名模糊匹配，并返回分页结果。
     *
     * @param connection    数据库连接
     * @param schemaNameLike 数据库模式名
     * @param tableName     表名过滤条件（支持模糊匹配）
     * @param page          当前页码（从 0 开始）
     * @param size          每页大小
     * @return 分页结果对象 {@link PageImpl} 包含 {@link TableInfo} 列表
     */
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
        return PageImpl.of(list, PageRequest.of(page, size), total);
    }

}
