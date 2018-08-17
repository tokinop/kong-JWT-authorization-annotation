package pub.toki.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;
@Component
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface KongJwtAuthAnnotation {
    String userName() default "";
    String groupName() default "";
}
