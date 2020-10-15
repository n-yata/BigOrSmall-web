package bean;

/**
 * トランプカードのBeanクラス
 * @author yata1
 */
public class Card {
    /** カードマーク */
	private CardMark mark;
	/** カードナンバー */
	private int number;

	/**
	 * コンストラクタメソッド
	 * @param mark
	 * @param number
	 */
	public Card(CardMark mark, int number) {
		this.mark = mark;
		this.number = number;
	}

	public CardMark getMark() {
		return mark;
	}

	public int getNumber() {
		return number;
	}

	public String toString() {
		return "" + mark.getName() + number;
	}
}