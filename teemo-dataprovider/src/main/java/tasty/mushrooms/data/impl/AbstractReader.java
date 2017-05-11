package tasty.mushrooms.data.impl;

import tasty.mushrooms.data.IDataReader;

/**
 * Reader抽象类，支持并发，默认不支持条件查询
 * 
 * @param <T> 用于存储线程关联数据的类型
 * 
 * @since 1.0.0
 */
public abstract class AbstractReader<T> implements IDataReader {
	private ThreadLocal<T> mData = new ThreadLocal<>();

	@Override
	public void changeGroup(String group) {
		T vLocal = getLocalData();
		if (vLocal == null) {
			vLocal = newLocalData(group);
			mData.set(vLocal);
		} else {
			resetLocalData(vLocal, group);
		}
	}

	@Override
	public boolean supportQuery() {
		return false;
	}

	@Override
	public void setCriteria(String criteria) {
	}

	/**
	 * 获取当前线程关联的数据
	 * 
	 * @return
	 */
	protected T getLocalData() {
		return mData.get();
	}

	/**
	 * 移除当前线程关联的数据
	 */
	protected void removeLocalData() {
		mData.remove();
	}

	/**
	 * 重置当前线程关联的数据
	 * 
	 * @param data 之前的数据
	 * @param group 要切换的数据分组名
	 */
	protected abstract void resetLocalData(T data, String group);

	/**
	 * 新建当前线程关联的数据
	 * 
	 * @param group 要切换的数据分组名
	 * @return
	 */
	protected abstract T newLocalData(String group);

}
