package tasty.mushrooms.data.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import tasty.mushrooms.data.internal.resolvers.NamedArgumentResolver;

/**
 * 声明参数所对应的列
 * 
 * @since 1.0.0
 * @see NamedArgumentResolver
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Key {
	/**
	 * 列名
	 * 
	 * @return
	 */
	String value();
}
