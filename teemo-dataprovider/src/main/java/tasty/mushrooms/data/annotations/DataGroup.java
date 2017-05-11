package tasty.mushrooms.data.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import tasty.mushrooms.data.IGroupResolver;
import tasty.mushrooms.data.impl.DefaultGroupResolver;

/**
 * 定义数据分组名
 * 
 * @since 1.0.0
 * @see IGroupResolver
 * @see DefaultGroupResolver
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataGroup {
	String value();
}
