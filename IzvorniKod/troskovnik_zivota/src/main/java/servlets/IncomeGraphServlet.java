package servlets;

import java.io.IOException;
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

@WebServlet (name="incomeGraph", urlPatterns="/graph/incomes")
public class IncomeGraphServlet extends HttpServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2715653871919641337L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("servlet");
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
		
		Map<String, Double> salaryIncomes = expenseList.findIncomeAmount(startDate,
				endDate, true);
		Map<String, Double> otherIncomes = expenseList.findIncomeAmount(startDate,
				endDate, false);
		Map<String, Double> allIncomes = expenseList.findIncomeAmount(startDate,
				endDate, null);
		
		System.out.println(salaryIncomes);
		System.out.println(otherIncomes);
		System.out.println(allIncomes);
		
		DefaultCategoryDataset graph1DataSet = new DefaultCategoryDataset();
		
		for (Map.Entry<String, Double> entry : salaryIncomes.entrySet()) {
			String date = entry.getKey();
			Double value = entry.getValue();
			String xAxis =	date;
			graph1DataSet.addValue(value, "salary", xAxis);			
		}
		
		for (Map.Entry<String, Double> entry : otherIncomes.entrySet()) {
			String date = entry.getKey();
			Double value = entry.getValue();
			String xAxis =	date;
			graph1DataSet.addValue(value, "oher incomes", xAxis);			
		}
		
		for (Map.Entry<String, Double> entry : allIncomes.entrySet()) {
			String date = entry.getKey();
			Double value = entry.getValue();
			String xAxis =	date;
			graph1DataSet.addValue(value, "all incomes", xAxis);			
		}

		
		JFreeChart graph1 = ChartFactory.createLineChart("Prihodi", "Vrijeme", "Prihod",
				graph1DataSet, PlotOrientation.VERTICAL, true, true, false);

		byte[] imageAsBytes = ChartUtilities.encodeAsPNG(graph1.createBufferedImage(1200, 600));

		resp.setContentType("image/png");
		resp.getOutputStream().write(imageAsBytes);
	}
}
