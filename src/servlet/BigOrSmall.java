package servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Card;
import bean.UserInfo;
import util.JsonConvertUtil;

/**
 * Servlet implementation class BigOrSmall
 */
@WebServlet("/BigOrSmall")
public class BigOrSmall extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BigOrSmall() {
        super();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッションスコープに保存されたユーザー情報を取得
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

        // 送信されたJSONの取得
        Map<String, String> reqMap = JsonConvertUtil.convertToObject(request);


        int bet = 0;
        if(userInfo.getBet() == 0) {
            // ユーザーのベット枚数が0であれば入力されたベット枚数をセット
            bet = Integer.parseInt(reqMap.get("betChip"));
            // チップからベットした枚数を引く
            userInfo.getChip().subChip(bet);
        }else {
            // 0でなければ（ダブルアップ時）ユーザーのベット枚数を継続してセット
            bet = userInfo.getBet();
        }
        userInfo.setBigSmall(reqMap.get("bigOrSmall"));

        // 次のカードを引く
        userInfo.setFollow(userInfo.getTrump().draw());

        boolean compareResult;

        if (result(userInfo)) {
            userInfo.setBet(bet * 2);
            compareResult = true;

        } else {
            userInfo.setBet(0);
            compareResult = false;
        }

        // ユーザー情報をセッションスコープに保存
        session.setAttribute("userInfo", userInfo);

        // 戻り値用のオブジェクト作成
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("compareResult", compareResult);
        resMap.put("bet", bet);
        resMap.put("getBet", userInfo.getBet());
        resMap.put("bigSmall", userInfo.getBigSmall());
        resMap.put("prev", userInfo.getPrev().toString());
        resMap.put("follow", userInfo.getFollow().toString());

        // JSONを戻す
        JsonConvertUtil.convertToJson(resMap, response);

    }

    public boolean result(UserInfo userInfo) {
        if (compare(userInfo.getPrev(), userInfo.getFollow())) {
            switch (userInfo.getBigSmall()) { // true = win, false = lose;
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

    public boolean compare(Card prev, Card follow) { // result()の中で呼び出し
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
