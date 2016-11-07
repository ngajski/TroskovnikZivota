package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.Expens;
import model.ExpensesList;

/**
 * This servlet is used to get ExpsensList object for one client.
 * 
 * @author Filip
 * @version 1.0
 */
@WebServlet({ "/troskovnik" })
public class GetExpensesListServlet extends HttpServlet {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(req.getParameter("userName"));

		ExpensesList expensesList = new ExpensesList(new Expens("struja", 102, false));
		resp.setContentType("application/json;charset=UTF-8");

		Gson gson = new Gson();
		String jsonText = gson.toJson(expensesList);

		resp.getWriter().write(jsonText);
		resp.getWriter().flush();
	}
}
