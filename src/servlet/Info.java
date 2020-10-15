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
import model.InfoLogic;
import util.JsonConvertUtil;

/**
 * Servlet implementation class Info
 * ユーザー情報の表示サーブレット
 */
@WebServlet("/Info")
public class Info extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // セッションスコープに保存されたユーザー情報を取得
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

        // セッションにユーザー情報なければ新規作成
        if(userInfo == null) {
            userInfo = new UserInfo();
        }

        InfoLogic logic = new InfoLogic();

        // ゲームオーバーの判定
        boolean gameOver = logic.executeGameOver(userInfo);

        if(!gameOver) {
            // ユーザー情報をセッションスコープに保存
            session.setAttribute("userInfo", userInfo);
        }else {
            // セッション情報の破棄
            session.invalidate();
        }

        // ゲームクリアの判定
        boolean gameClear = logic.executeGameClear(userInfo);

        if(gameClear) {
            // セッション情報の破棄
            session.invalidate();
        }

        // 戻り値用のオブジェクト作成
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("chip", userInfo.getChip().toString());
        resMap.put("sumChip", userInfo.getChip().getSum());
        resMap.put("card", userInfo.getPrev().toString());
        resMap.put("gameOver", gameOver);
        resMap.put("gameClear", gameClear);

        // JSONを戻す
        JsonConvertUtil.convertToJson(resMap, response);

	}
}
