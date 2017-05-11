package tasty.mushrooms.data.internal.resolvers;

import java.lang.reflect.Parameter;

import tasty.mushrooms.data.IArgumentResolver;
import tasty.mushrooms.data.IRow;

public class RowResolver implements IArgumentResolver {

	@Override
	public boolean support(Parameter param) {
		return param.getType() == IRow.class;
	}

	@Override
	public Object resolve(Parameter param, IRow row) {
		return row;
	}

}
