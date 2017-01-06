package servlets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import dao.DAOProvider;
import model.ExpenseList;

@WebServlet (name="sumGraph", urlPatterns="/graph/sum")
public class SummedGraphServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5580837957070089425L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String expenseListName = req.getParameter("expenseList");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");

		if (startDate.startsWith("0")) {
			startDate = startDate.substring(1);
		}
		
		if (endDate.startsWith("0")) {
			endDate = endDate.substring(1);
		}

		ExpenseList expenseList = DAOProvider.getDAO().getExpenseListByName(expenseListName);

		Map<String, Double> allIncomes = expenseList.findIncomeAmount(startDate, endDate, null);
		;
		Map<String, Double> allExpenses = expenseList.findExpenseAmount(startDate, endDate, new LinkedHashMap<>(),
				expenseList.getExpenseCategories(), null);

		DefaultCategoryDataset grap3DataSet = new DefaultCategoryDataset();

		for (Map.Entry<String, Double> entry : allIncomes.entrySet()) {
			String date = entry.getKey();
			Double value = entry.getValue();
			String xAxis = date;
			grap3DataSet.addValue(value, "income", xAxis);
		}

		for (Map.Entry<String, Double> entry : allExpenses.entrySet()) {
			String date = entry.getKey();
			Double value = entry.getValue();
			String xAxis = date;
			grap3DataSet.addValue(value, "expense", xAxis);
		}

		JFreeChart graph = ChartFactory.createLineChart("Ukupno", "Vrijeme", "Novac", grap3DataSet,
				PlotOrientation.VERTICAL, true, true, false);

		byte[] imageAsBytes = ChartUtilities.encodeAsPNG(graph.createBufferedImage(1200, 600));

		resp.setContentType("image/png");
		resp.getOutputStream().write(imageAsBytes);
	}
}
