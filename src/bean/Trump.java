package bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 山札のBeanクラス
 *
 * @author yata1
 */
public class Trump {
    /** 山札 */
    List<Card> trumpList = new ArrayList<>();
    /** 現在のカード */
    Card card;

    /**
     * 山札の初期化
     */
    public void createDeck() {
        trumpList.clear();
        for (CardMark cm : CardMark.values()) {
            for (int j = 1; j < 14; j++) {
                card = new Card(cm, j);
                trumpList.add(card);
            }
        }
    }

    /**
     * 山札からカードを１枚引く
     *
     * @return drawCard
     */
    public Card draw() {
        int drawNum = new java.util.Random().nextInt(trumpList.size());
        return trumpList.remove(drawNum);
    }
}