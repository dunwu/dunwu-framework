/**
 * 数据库元数据查询实现包
 * <p>
 * 包含针对不同数据库（如 MySQL, Oracle, PostgreSQL, SQL Server, SQLite 等）的元数据查询实现。
 * 每个实现类继承自 {@link io.github.dunwu.common.dal.generator.config.querys.AbstractDbQuery}，
 * 提供特定数据库的表查询 SQL、字段查询 SQL 以及结果集列名映射，供代码生成器提取表结构信息。
 *
 * @since 2018-01-16
 */
package io.github.dunwu.common.dal.generator.config.querys;
