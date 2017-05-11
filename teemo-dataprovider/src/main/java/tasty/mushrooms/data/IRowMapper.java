package tasty.mushrooms.data;

/**
 * 将行数据映射成指定类型的对象
 * 
 * @since 1.0.0
 */
public interface IRowMapper {

	/**
	 * 根据行数据映射成指定类型的对象
	 * 
	 * @param row 行数据
	 * @param clazz 要生成对象的类型
	 * @return 映射的对象
	 */
	Object map(IRow row, Class<?> clazz);

}
