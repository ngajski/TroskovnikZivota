package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOProvider;
import model.User;


/**
 * This servlet is used to validate data for registration of new user. For data
 * to be valid <code>email</code> and <code>nick</code> must be unique in whole
 * subsystem for data persistence and <code>email</code> must be in valid form
 * (contains <code>@</code>, is larger then 3 character, <code>@</code> is not
 * at the end of string, etc).
 * <p>
 * If something is invalid servlet will set valid parameters and for those that
 * are invalid it will send error message and forward request back to
 * <code>register.jsp</code> so user can try to register again without filling
 * valid data again.
 * <p>
 * If every thing is valid it will send redirect to servlet mapped to
 * <code>/blog/servleti/main</code> so user can see home with list of registerd authors.
 * 
 * @author Filip
 * @version 1.0
 */
@WebServlet("/servlets/register")
public class RegisterServlet extends HttpServlet {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String fn = req.getParameter("firstName");
		String ln = req.getParameter("lastName");
		String telefon = req.getParameter("telefon");
		String email = req.getParameter("email");
		String oib = req.getParameter("oib");
		String dateOfBirth = req.getParameter("dateOfBirth");
		String town = req.getParameter("town");
		String postCode = req.getParameter("postCode");
		String street = req.getParameter("street");
		String houseNumber = req.getParameter("houseNumber");
		User user = new User(username, password, fn, ln, null, null, null, null, email);
		
		DAOProvider.getDAO().addUser(user);
		System.out.println(fn + "/n" + ln);
			
		resp.getWriter().write("ok");
		resp.getWriter().flush();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}
}
