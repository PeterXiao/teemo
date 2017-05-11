package tasty.mushrooms.data.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import tasty.mushrooms.data.IRowMapper;
import tasty.mushrooms.data.internal.resolvers.MappedArgumentResolver;

/**
 * 声明参数所对应的{@link IRowMapper}
 * 
 * @since 1.0.0
 * @see IRowMapper
 * @see MappedArgumentResolver
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapper {
	Class<? extends IRowMapper> value();
}
