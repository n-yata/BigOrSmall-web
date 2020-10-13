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
 * Servlet implementation class DoubleUp
 */
@WebServlet("/DoubleUp")
public class DoubleUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoubleUp() {
        super();
        // TODO Auto-generated constructor stub
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

        String doubleUp = reqMap.get("doubleUp");

        if(doubleUp.equals("no")) {
            // ダブルアップしない場合、獲得したチップを手元に加える
            userInfo.getChip().addChip(userInfo.getBet());
            userInfo.setBet(0);
        }

        // 現在のカードをセット
        userInfo.setPrev(userInfo.getFollow());

        // ユーザー情報をセッションスコープに保存
        session.setAttribute("userInfo", userInfo);

        // 戻り値用のオブジェクト作成
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("doubleUp", doubleUp);

        // JSONを戻す
        JsonConvertUtil.convertToJson(resMap, response);
	}
}
