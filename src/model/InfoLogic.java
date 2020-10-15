package model;

import bean.UserInfo;

/**
 * ユーザー情報の表示処理クラス
 * @author yata1
 *
 */
public class InfoLogic {
    /**
     * ゲームオーバーか判定
     * @param userInfo
     * @return gameOver
     */
    public boolean executeGameOver(UserInfo userInfo) {
        boolean gameOver = false;

        // チップ枚数が1以上またはベット枚数が1以上であればゲーム継続
        if(userInfo.getChip().getSum() > 0 || userInfo.getBet() > 0) {

            // ゲームオーバーフラグをオフ
            gameOver = false;

        // ゲームオーバー
        }else {

            // ゲームオーバーフラグをオン
            gameOver = true;
        }

        return gameOver;
    }

    /**
     * ゲームクリアか判定
     * @param userInfo
     * @return gameClear
     */
    public boolean executeGameClear(UserInfo userInfo) {
        boolean gameClear = false;

        // ゲームクリア（チップが500枚以上）
        if(userInfo.getChip().getSum() >= 500) {
            // ゲームクリアフラグをオン
            gameClear = true;
        }
        return gameClear;
    }
}
