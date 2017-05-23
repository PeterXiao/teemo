package tasty.mushrooms.finder;

/**
 * 原始Driver封装
 * 
 * @param <D> 原始Driver的类型
 * @since 1.0.0
 */
public interface IDriverWrapper<D> {

	/**
	 * 获取原始驱动
	 * 
	 * @return
	 */
	D getRealDriver();

}
