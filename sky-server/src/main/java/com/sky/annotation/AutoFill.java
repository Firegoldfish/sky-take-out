package com.sky.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

// 自动填充注解
@Target(ElementType.ANNOTATION_TYPE)
public @interface AutoFill {
}
