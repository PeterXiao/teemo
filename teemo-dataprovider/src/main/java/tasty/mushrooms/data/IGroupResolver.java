package tasty.mushrooms.data;

import java.lang.reflect.Method;

/**
 * 解析数据分组名
 * 
 * @since 1.0.0
 */
public interface IGroupResolver {

	/**
	 * 根据测试方法解析数据分组名
	 * 
	 * @param method 测试方法
	 * @return 数据分组名
	 */
	String resolve(Method method);

}
