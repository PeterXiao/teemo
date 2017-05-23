package tasty.mushrooms.finder;

/**
 * 定位方式
 * 
 * @since 1.0.0
 */
public class LocateStrategy {
	public final String name;
	public final String value;

	public LocateStrategy(String name, String value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String toString() {
		return name + '(' + value + ')';
	};

}
