package io.github.dunwu.common.dal.generator.config.querys;

import io.github.dunwu.common.dal.generator.config.IDbQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库表查询抽象基类。
 * <p>
 * 实现了 {@link IDbQuery} 接口的部分默认方法，为不同数据库的元数据查询实现提供基础模板。
 * 子类可根据具体数据库方言重写相关方法。
 *
 * @author hubin
 * @since 2018-01-16
 */
public abstract class AbstractDbQuery implements IDbQuery {

    /**
     * 判断主键是否为自增（Identity）。
     * <p>
     * 默认返回 false，子类可根据具体数据库特性重写此方法。
     *
     * @param results 数据库查询结果集
     * @return 如果是自增主键返回 true，否则返回 false
     * @throws SQLException 如果发生数据库访问异常
     */
    @Override
    public boolean isKeyIdentity(ResultSet results) throws SQLException {
        return false;
    }

    /**
     * 获取自定义字段名称列表。
     * <p>
     * 默认返回 null，子类可根据需要扩展返回额外的字段名。
     *
     * @return 自定义字段名称数组
     */
    @Override
    public String[] fieldCustom() {
        return null;
    }

    /**
     * 获取结果集中表示字段是否允许为空的列名。
     * <p>
     * 默认返回 "NULL"。
     *
     * @return 列名
     */
    @Override
    public String nullAble() {
        return "NULL";
    }

}
