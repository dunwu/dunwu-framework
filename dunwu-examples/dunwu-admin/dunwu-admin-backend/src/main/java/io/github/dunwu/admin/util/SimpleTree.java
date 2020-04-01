package io.github.dunwu.admin.util;

import java.util.Collection;

/**
 * 简单树结构接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-26
 */
public interface SimpleTree {

    Integer getId();

    Integer getParentId();

    Collection<? extends SimpleTree> getChildren();

    boolean isLeaf();

}
