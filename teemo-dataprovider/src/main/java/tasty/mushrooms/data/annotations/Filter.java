package tasty.mushrooms.data.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import tasty.mushrooms.data.IRowFilter;

/**
 * 声明行过滤器
 * 
 * @since 1.0.0
 * @see IRowFilter
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Filter {
	Class<? extends IRowFilter> value();
}
