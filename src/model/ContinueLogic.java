package model;

import bean.UserInfo;

/**
 * 継続確認の処理クラス
 * @author yata1
 *
 */
public class ContinueLogic {

    /**
     * ゲーム継続の確認
     * @param userInfo
     * @param continueStr
     * @return exitFlg
     */
    public boolean execute(UserInfo userInfo, String continueStr) {

        // ゲーム継続フラグ
        boolean continueFlg = false;

        // ゲーム継続の場合
        if(continueStr.equals("yes")) {
            continueFlg = true;

            // 新たなデッキを作成しトランプを引き直す
            userInfo.getTrump().createDeck();
            userInfo.setPrev(userInfo.getTrump().draw());
        }

        return continueFlg;
    }
}
