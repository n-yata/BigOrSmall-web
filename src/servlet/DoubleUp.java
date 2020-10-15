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
import model.DoubleUpLogic;
import util.JsonConvertUtil;

/**
 * Servlet implementation class DoubleUp
 * ダブルアップ判定のサーブレット
 */
@WebServlet("/DoubleUp")
public class DoubleUp extends HttpServlet {
	private static final long serialVersionUID = 1L;


    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // セッションスコープに保存されたユーザー情報を取得
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

        // 送信されたJSONの取得
        Map<String, String> reqMap = JsonConvertUtil.convertToObject(request);
        String doubleUp = reqMap.get("doubleUp");

        DoubleUpLogic logic = new DoubleUpLogic();

        // ダブルアップ判定の処理
        logic.execute(userInfo, doubleUp);

        // ユーザー情報をセッションスコープに保存
        session.setAttribute("userInfo", userInfo);

        // 戻り値用のオブジェクト作成
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("doubleUp", doubleUp);

        // JSONを戻す
        JsonConvertUtil.convertToJson(resMap, response);
	}
}
