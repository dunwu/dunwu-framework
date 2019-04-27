package io.github.dunwu.data.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-27
 */
@Data
@Accessors(chain = true)
public class RecordEntity extends BaseEntity {

    private static final long serialVersionUID = 416443810370056461L;

    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 修改日期
     */
    private Date modifyDate;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 修改者
     */
    private String modifier;
}
