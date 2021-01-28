package io.paradaux.bukkit.chiton.models.enums;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Sync {

    /**
     * @return The rate of refresh
     */
    Rate rate() default Rate.TICK;
}