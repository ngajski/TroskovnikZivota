package servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Ovo je servlet koji sluzi kao posrednik. Losija varijanta od JSONP formata
 * ili tehnologije kaj god da to je. No ako možda treba radit dodatna
 * filtriranja i kaj sve ne može bit korisno.
 * 
 * @author Filip
 * @version 1.0
 */
@WebServlet({ "/troskovnik" })
public class ProxyGetExpensesListServlet extends HttpServlet {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userName = req.getParameter("userName");

		String URL = "http://localhost:8080/baza-podataka/troskovnik";
		URL = appendParamter(URL, "userName", userName, true);

		URL dbServer = new URL(URL);

		URLConnection con = dbServer.openConnection();

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		resp.setContentType("application/json;charset=UTF-8");
		Writer writer = resp.getWriter();

		char[] buffer = new char[1024];
		int len = 0;
		while ((len = in.read(buffer)) != -1) {
			writer.write(buffer, 0, len);
		}
		in.close();

		writer.flush();
	}

	private String appendParamter(String URL, String paramterName, String parameterValue, boolean first) {
		String newURL = "";
		if (first) {
			newURL += URL + "?";
		} else {
			newURL += URL + "&";
		}

		return newURL + paramterName + "=" + parameterValue;
	}
}
