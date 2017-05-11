package tasty.mushrooms.data.impl;

import java.lang.reflect.Method;

import tasty.mushrooms.data.IGroupResolver;
import tasty.mushrooms.data.annotations.DataGroup;

/**
 * 默认分组解析器，优先使用测试方法的{@link DataGroup}注解，若没有则使用"类名_方法名"作为数据分组名
 * 
 * @since 1.0.0
 */
public class DefaultGroupResolver implements IGroupResolver {

	@Override
	public String resolve(Method method) {
		String vRet = getGroupFromAnno(method);
		if (vRet == null) {
			vRet = method.getDeclaringClass().getSimpleName() + "_" + method.getName();
		}
		return vRet;
	}

	private String getGroupFromAnno(Method method) {
		DataGroup vAnno = method.getAnnotation(DataGroup.class);
		if (vAnno != null) {
			return vAnno.value();
		}
		return null;
	}

}
