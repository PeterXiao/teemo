package tasty.mushrooms.data.internal.resolvers;

import java.lang.reflect.Parameter;
import java.util.Date;

import tasty.mushrooms.data.IArgumentResolver;
import tasty.mushrooms.data.IRow;
import tasty.mushrooms.data.annotations.Key;

public class NamedArgumentResolver implements IArgumentResolver {

	@Override
	public boolean support(Parameter param) {
		Class<?> vClazz = param.getType();
		if (vClazz.isPrimitive() || vClazz == String.class || vClazz == Date.class) {
			return param.isAnnotationPresent(Key.class);
		}
		return false;
	}

	@Override
	public Object resolve(Parameter param, IRow row) {
		Key vKey = param.getAnnotation(Key.class);
		String vColumnName = vKey.value();
		Class<?> vClazz = param.getType();
		if (vClazz == boolean.class) {
			return row.getBoolean(vColumnName);
		}
		if (vClazz == char.class) {
			return row.getChar(vColumnName);
		}
		if (vClazz == byte.class) {
			return row.getByte(vColumnName);
		}
		if (vClazz == short.class) {
			return row.getShort(vColumnName);
		}
		if (vClazz == int.class) {
			return row.getInt(vColumnName);
		}
		if (vClazz == long.class) {
			return row.getLong(vColumnName);
		}
		if (vClazz == float.class) {
			return row.getFloat(vColumnName);
		}
		if (vClazz == double.class) {
			return row.getDouble(vColumnName);
		}
		if (vClazz == String.class) {
			return row.getString(vColumnName);
		}
		if (vClazz == Date.class) {
			return row.getDate(vColumnName);
		}
		throw new IllegalArgumentException("Should never be here");
	}

}
