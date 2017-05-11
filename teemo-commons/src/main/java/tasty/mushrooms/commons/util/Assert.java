package tasty.mushrooms.commons.util;

/**
 * 验证工具类
 * 
 * @since 1.0.0
 */
public abstract class Assert {

	/**
	 * 验证当前状态是否正确，若表达式为假则抛出IllegalStateException异常
	 * 
	 * @param expression 待验证布尔表达式
	 */
	public static void state(boolean expression) {
		state(expression, "[Assertion failed] - this state invariant must be true");
	}

	/**
	 * 验证当前状态是否正确，若表达式为假则抛出IllegalStateException异常
	 * 
	 * @param expression 待验证布尔表达式
	 * @param message 抛出异常时所包含的信息
	 */
	public static void state(boolean expression, String message) {
		if (!expression) {
			throw new IllegalStateException(message);
		}
	}

	/**
	 * 验证是否为真，若不为true则抛出IllegalArgumentException异常
	 * 
	 * @param expression 待验证布尔表达式
	 * @param message 抛出异常时所包含的信息
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 验证对象是否为null，若不为null则抛出IllegalArgumentException异常
	 * 
	 * @param object 待验证对象
	 * @param message 抛出异常时所包含的信息
	 */
	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 验证对象是否不为null，若为null则抛出IllegalArgumentException异常
	 * 
	 * @param object 待验证对象
	 * @param message 抛出异常时所包含的信息
	 */
	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * 验证对象是否为指定类型的实例，若不是则抛出IllegalArgumentException异常
	 * 
	 * @param type 用来验证的类型
	 * @param obj 待验证对象
	 * @param message 抛出异常时所包含的信息
	 */
	public static void isInstanceOf(Class<?> type, Object obj, String message) {
		notNull(type, "Type to check against must not be null");
		if (!type.isInstance(obj)) {
			throw new IllegalArgumentException((message.length() > 0 ? message + " " : "") + "Object of class ["
					+ (obj != null ? obj.getClass().getName() : "null") + "] must be an instance of " + type);
		}
	}

	/**
	 * 验证类型是否为指定类型的子类，若不是则抛出IllegalArgumentException异常
	 * 
	 * @param superType 用来验证的类型
	 * @param subType 待验证的类型
	 * @param message 抛出异常时所包含的信息
	 */
	public static void isAssignable(Class<?> superType, Class<?> subType, String message) {
		notNull(superType, "Type to check against must not be null");
		if (subType == null || !superType.isAssignableFrom(subType)) {
			throw new IllegalArgumentException(
					(message.length() > 0 ? message + " " : "") + subType + " is not assignable to " + superType);
		}
	}

	/**
	 * 验证字符串是否长度大于0，若不大于0 则抛出IllegalArgumentException异常
	 * 
	 * @param text 待验证字符串
	 * @param message 抛出异常时所包含的信息
	 */
	public static void hasLength(String text, String message) {
		if (text.length() == 0) {
			throw new IllegalArgumentException(message);
		}
	}

}
