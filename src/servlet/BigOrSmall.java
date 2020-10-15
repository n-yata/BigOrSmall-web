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

import bean.UserInfo;
import model.BigOrSmallLogic;
import util.JsonConvertUtil;

/**
 * Servlet implementation class BigOrSmall
 */
@WebServlet("/BigOrSmall")
public class BigOrSmall extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
        int bet = Integer.parseInt(reqMap.get("betChip"));
        String bigSmall = reqMap.get("bigOrSmall");

        BigOrSmallLogic logic = new BigOrSmallLogic();

        // Bet数の処理
        int resultBet = logic.executeResultBet(userInfo, bet, bigSmall);

        // Big or Small判定の処理
        boolean compareResult = logic.exeucteCompare(userInfo, resultBet);

        // ユーザー情報をセッションスコープに保存
        session.setAttribute("userInfo", userInfo);

        // 戻り値用のオブジェクト作成
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("compareResult", compareResult);
        resMap.put("bet", resultBet);
        resMap.put("getBet", userInfo.getBet());
        resMap.put("bigSmall", userInfo.getBigSmall());
        resMap.put("prev", userInfo.getPrev().toString());
        resMap.put("follow", userInfo.getFollow().toString());

        // JSONを戻す
        JsonConvertUtil.convertToJson(resMap, response);

    }
}
