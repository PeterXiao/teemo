package tasty.mushrooms.data.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 定义行过滤规则，仅对支持条件查询的IDataReader生效
 * </p>
 * 
 * 与{@link Filter}的区别：
 * <ul>
 * <li>Filter是先读取数据，之后根据过滤规则决定丢弃或使用该数据，适用于txt等不支持支持条件查询的数据源</li>
 * <li>Criteria则是直接根据条件读取数据，不需在读取后处理数据，适用于数据库等支持条件查询的数据源</li>
 * </ul>
 * 
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Criteria {
	/**
	 * 过滤规则，语法同SQL语句中的WHERE子句
	 * 
	 * @return
	 */
	String value();
}
