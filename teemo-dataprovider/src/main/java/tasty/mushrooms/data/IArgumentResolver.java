package tasty.mushrooms.data;

import java.lang.reflect.Parameter;

import tasty.mushrooms.data.internal.resolvers.ArgumentResolverComposite;
import tasty.mushrooms.data.internal.resolvers.CustomRecordResolver;
import tasty.mushrooms.data.internal.resolvers.MappedArgumentResolver;
import tasty.mushrooms.data.internal.resolvers.NamedArgumentResolver;
import tasty.mushrooms.data.internal.resolvers.RowResolver;

/**
 * <p>
 * 参数解析器，根据参数信息从IRow中得到相应的值
 * </p>
 * 默认解析器如下：
 * <ul>
 * <li>{@link NamedArgumentResolver}</li>
 * <li>{@link MappedArgumentResolver}</li>
 * <li>{@link RowResolver}</li>
 * <li>{@link CustomRecordResolver}</li>
 * </ul>
 * 
 * @since 1.0.0
 * @see ArgumentResolverComposite
 */
public interface IArgumentResolver {

	/**
	 * 是否支持当前参数
	 * 
	 * @param param 待处理的参数
	 * @return 支持返回true，否则返回false
	 */
	boolean support(Parameter param);

	/**
	 * 根据参数信息从IRow中得到相应的值
	 * 
	 * @param param 待处理的参数
	 * @param row Reader读取的行数据
	 * @return 参数所对应的值
	 */
	Object resolve(Parameter param, IRow row);

}
