package tasty.mushrooms.data;

import java.util.Date;

/**
 * 行数据
 * 
 * @since 1.0.0
 */
public interface IRow {

	/**
	 * 获取当前行索引，从1开始
	 * 
	 * @return 当前行索引
	 */
	int getIndex();

	/**
	 * 获取所有列名
	 * 
	 * @return 所有列名
	 */
	String[] getColumnNames();

	/**
	 * 获取列数
	 * 
	 * @return 列数
	 */
	int getColumnNumbers();

	/**
	 * 获取Boolean值
	 * 
	 * @param key 列名
	 * @return Boolean值
	 */
	boolean getBoolean(String key);

	/**
	 * 获取Boolean值
	 * 
	 * @param index 列索引，从1开始
	 * @return Boolean值
	 */
	boolean getBoolean(int index);

	/**
	 * 获取Byte值
	 * 
	 * @param key 列名
	 * @return Byte值
	 */
	byte getByte(String key);

	/**
	 * 获取Byte值
	 * 
	 * @param index 列索引，从1开始
	 * @return Byte值
	 */
	byte getByte(int index);

	/**
	 * 获取Char值
	 * 
	 * @param key 列名
	 * @return Char值
	 */
	char getChar(String key);

	/**
	 * 获取Char值
	 * 
	 * @param index 列索引，从1开始
	 * @return Char值
	 */
	char getChar(int index);

	/**
	 * 获取Short值
	 * 
	 * @param key 列名
	 * @return Short值
	 */
	short getShort(String key);

	/**
	 * 获取Short值
	 * 
	 * @param index 列索引，从1开始
	 * @return Short值
	 */
	short getShort(int index);

	/**
	 * 获取Int值
	 * 
	 * @param key 列名
	 * @return Int值
	 */
	int getInt(String key);

	/**
	 * 获取Int值
	 * 
	 * @param index 列索引，从1开始
	 * @return Int值
	 */
	int getInt(int index);

	/**
	 * 获取Long值
	 * 
	 * @param key 列名
	 * @return Long值
	 */
	long getLong(String key);

	/**
	 * 获取Long值
	 * 
	 * @param index 列索引，从1开始
	 * @return Long值
	 */
	long getLong(int index);

	/**
	 * 获取Float值
	 * 
	 * @param key 列名
	 * @return Float值
	 */
	float getFloat(String key);

	/**
	 * 获取Float值
	 * 
	 * @param index 列索引，从1开始
	 * @return Float值
	 */
	float getFloat(int index);

	/**
	 * 获取v值
	 * 
	 * @param key 列名
	 * @return Double值
	 */
	double getDouble(String key);

	/**
	 * 获取Double值
	 * 
	 * @param index 列索引，从1开始
	 * @return Double值
	 */
	double getDouble(int index);

	/**
	 * 获取String值
	 * 
	 * @param key 列名
	 * @return String值
	 */
	String getString(String key);

	/**
	 * 获取String值
	 * 
	 * @param index 列索引，从1开始
	 * @return String值
	 */
	String getString(int index);

	/**
	 * 获取Date值
	 * 
	 * @param key 列名
	 * @return Date值
	 */
	Date getDate(String key);

	/**
	 * 获取Date值
	 * 
	 * @param index 列索引，从1开始
	 * @return Date值
	 */
	Date getDate(int index);

}
