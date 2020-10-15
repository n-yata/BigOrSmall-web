package model;

import bean.UserInfo;

/**
 * ダブルアップ判定の処理クラス
 * @author yata1
 *
 */
public class DoubleUpLogic {
    /**
     * ダブルアップか判定
     * @param userInfo
     * @param doubleUp
     */
    public void execute(UserInfo userInfo, String doubleUp) {
        if(doubleUp.equals("no")) {
            // ダブルアップしない場合、獲得したチップを手元に加える
            userInfo.getChip().addChip(userInfo.getBet());
            userInfo.setBet(0);
        }

        // 現在のカードをセット
        userInfo.setPrev(userInfo.getFollow());
    }
}
