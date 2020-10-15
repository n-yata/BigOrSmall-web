package model;

import bean.Card;
import bean.UserInfo;

/**
 * Big or Small判定の処理クラス
 * @author yata1
 */
public class BigOrSmallLogic {
    /**
     *  ベット枚数の処理（ダブルアップか判定）
     * @param userInfo
     * @param bet
     * @param bigSmall
     * @return resultBet
     */
    public int executeResultBet(UserInfo userInfo, int bet, String bigSmall) {
        int resultBet = 0;

        if(userInfo.getBet() == 0) {
            // 入力したベットをセットする
            resultBet = bet;
            // チップからベットした枚数を引く
            userInfo.getChip().subChip(bet);
        }else {
            // 0でなければ（ダブルアップ時）ユーザーのベット枚数を継続してセット
            resultBet = userInfo.getBet();
        }
        userInfo.setBigSmall(bigSmall);

        // 次のカードを引く
        userInfo.setFollow(userInfo.getTrump().draw());

        return resultBet;
    }

    /**
     * 勝ち負けの判定
     * @param userInfo
     * @param resultBet
     * @return compareResult
     */
    public boolean exeucteCompare(UserInfo userInfo, int resultBet) {
        boolean compareResult = false;

        if (result(userInfo)) {
            userInfo.setBet(resultBet * 2);
            compareResult = true;

        } else {
            userInfo.setBet(0);
            compareResult = false;
        }

        return compareResult;
    }

    /**
     * 勝ち負け判定のロジック
     * @param userInfo
     * @return result
     */
    private boolean result(UserInfo userInfo) {
        if (compare(userInfo.getPrev(), userInfo.getFollow())) {
            switch (userInfo.getBigSmall()) {
            case "big":
                return true;
            case "small":
                return false;
            }
        } else {
            switch (userInfo.getBigSmall()) {
            case "big":
                return false;
            case "small":
                return true;
            }
        }
        return false;
    }

    /**
     *  大小関係の処理ロジック
     * @param prev
     * @param follow
     * @return bigSmall
     */
    private boolean compare(Card prev, Card follow) {
        if (prev.getNumber() == follow.getNumber()) {
            if (prev.getMark().getStrength() < follow.getMark().getStrength()) {
                return true;
            } else {
                return false;
            }
        }
        if (prev.getNumber() < follow.getNumber()) {
            return true;
        } else {
            return false;
        }
    }
}
