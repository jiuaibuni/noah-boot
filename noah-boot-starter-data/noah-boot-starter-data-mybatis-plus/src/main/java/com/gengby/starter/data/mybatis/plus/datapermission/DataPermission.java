package com.gengby.starter.data.mybatis.plus.datapermission;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 数据权限注解
 *
 * @author Noah
 * @since 1.1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {

    /**
     * Alias for the {@link #tableAlias()} attribute.
     */
    @AliasFor("tableAlias")
    String value() default "";

    /**
     * 表别名
     */
    @AliasFor("value")
    String tableAlias() default "";

    /**
     * ID
     */
    String id() default "id";

    /**
     * 部门 ID
     */
    String deptId() default "dept_id";

    /**
     * 用户 ID
     */
    String userId() default "create_user";

    /**
     * 角色 ID（角色和部门关联表）
     */
    String roleId() default "role_id";

    /**
     * 部门表别名
     */
    String deptTableAlias() default "sys_dept";

    /**
     * 角色和部门关联表别名
     */
    String roleDeptTableAlias() default "sys_role_dept";
}
