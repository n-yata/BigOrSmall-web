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
import util.JsonConvertUtil;

/**
 * Servlet implementation class Continue
 */
@WebServlet("/Continue")
public class Continue extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Continue() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // セッションスコープに保存されたユーザー情報を取得
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

        // 送信されたJSONの取得
        Map<String, String> reqMap = JsonConvertUtil.convertToObject(request);

        String continueStr = reqMap.get("continue");

        // ゲーム終了フラグ
        boolean exitFlg = false;

        if(continueStr.equals("yes")) {
            exitFlg = false;
            // 新たなデッキを作成しトランプを引き直す
            userInfo.getTrump().createDeck();
            userInfo.setPrev(userInfo.getTrump().draw());

            // ユーザー情報をセッションスコープに保存
            session.setAttribute("userInfo", userInfo);
        }else {
            exitFlg = true;
            // セッション情報の破棄
            session.invalidate();
        }

        // 戻り値用のオブジェクト作成
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("exitFlg", exitFlg);

        // JSONを戻す
        JsonConvertUtil.convertToJson(resMap, response);
	}

}
