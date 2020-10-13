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
 * Servlet implementation class Info
 */
@WebServlet("/Info")
public class Info extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Info() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // セッションスコープに保存されたユーザー情報を取得
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

        if(userInfo == null) {
            // ユーザー情報を生成
            userInfo = new UserInfo();
        }

        boolean gameOver = false;

        // チップ枚数が1以上またはベット枚数が1以上であればゲーム継続
        if(userInfo.getChip().getSum() > 0 || userInfo.getBet() > 0) {

            // ユーザー情報をセッションスコープに保存
            session.setAttribute("userInfo", userInfo);

            // ゲームオーバーフラグをオフ
            gameOver = false;

        // ゲームオーバー
        }else {
            // セッション情報の破棄
            session.invalidate();

            // ゲームオーバーフラグをオン
            gameOver = true;
        }

        String chip = userInfo.getChip().toString();
        int sumChip = userInfo.getChip().getSum();
        String card = userInfo.getPrev().toString();

        // 戻り値用のオブジェクト作成
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("chip", chip);
        resMap.put("sumChip", sumChip);
        resMap.put("card", card);
        resMap.put("gameOver", gameOver);

        // JSONを戻す
        JsonConvertUtil.convertToJson(resMap, response);

	}
}
