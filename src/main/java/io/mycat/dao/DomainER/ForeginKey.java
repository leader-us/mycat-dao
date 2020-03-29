package io.mycat.dao.DomainER;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于Domain对象ＥＲ关系说明的注解，表示此字段为外键，关联指定的某个父Domain 注解的参数为父类Domain对象的Class，如
 * io.mycat.myweb.domain.User.class Used for Domain(Entity) Object's ER relation
 * annotion to tell LeaderDao one Domain how to related with other
 * 
 * @author Leader us
 */
@Target(value = { ElementType.FIELD })
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ForeginKey {

    /**
     * 关联的父域对象
     */
    Class<?> value();
}