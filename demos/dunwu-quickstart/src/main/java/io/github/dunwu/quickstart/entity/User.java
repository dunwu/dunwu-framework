package io.github.dunwu.quickstart.entity;

import io.github.dunwu.data.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Zhang Peng
 * @since 2019-04-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别,0:MALE, 1:FEMALE
     */
    private Integer gender;

    /**
     * 年级
     */
    private Integer grade;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 角色ID
     */
    private Long roleId;


}
