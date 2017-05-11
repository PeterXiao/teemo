package tasty.mushrooms.commons.util;

import tasty.mushrooms.commons.exception.TeemoInstantiationException;

/**
 * 实例化工具类
 * 
 * @since 1.0.0
 */
public abstract class InstantiateUtil {

	/**
	 * 根据类型实例化对象
	 * 
	 * @param clazz 要实例化的类型
	 * @return 对象实例
	 * @throws TeemoInstantiationException 实例化失败（如无默认构造方法，实例化类型为抽象类或接口等）时抛出此异常
	 */
	public static <T> T instantiate(Class<T> clazz) throws TeemoInstantiationException {
		Assert.notNull(clazz, "Class must not be null");
		if (clazz.isInterface()) {
			throw new TeemoInstantiationException("Specified class is an interface");
		}
		try {
			return clazz.newInstance();
		} catch (InstantiationException ex) {
			throw new TeemoInstantiationException("Is it an abstract class?", ex);
		} catch (IllegalAccessException ex) {
			throw new TeemoInstantiationException("Is the constructor accessible?", ex);
		}
	}

	/**
	 * 根据类型实例化对象
	 * 
	 * @param clazz 要实例化的类型
	 * @param assignableTo 检查类型，用来检查要实例化的类型是否与从此类型相同或为此类型的子类， 检查失败抛出
	 *            IllegalArgumentException 异常
	 * @return 对象实例
	 * @throws TeemoInstantiationException 实例化失败（如无默认构造方法，实例化类型为抽象类或接口等）时抛出此异常
	 */
	@SuppressWarnings("unchecked")
	public static <T> T instantiate(Class<?> clazz, Class<T> assignableTo) throws TeemoInstantiationException {
		Assert.isAssignable(assignableTo, clazz, null);
		return (T) instantiate(clazz);
	}
}
