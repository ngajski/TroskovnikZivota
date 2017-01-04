package servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Chart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import dao.DAOProvider;
import model.ExpenseList;

@WebServlet (name="graf", urlPatterns="/graf")
public class GenerateImageServlet extends HttpServlet {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2715653871919641337L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("servlet");
		String expenseListName = req.getParameter("expenseListname");
		ExpenseList expenseList = DAOProvider.getDAO().getExpenseListByName("Troškovnik_Rucno");
		
		Map<String, Double> salaryIncomes = expenseList.findIncomeAmount("3/2016",
				"3/2017", true);
		Map<String, Double> otherIncomes = expenseList.findIncomeAmount("3/2016",
				"3/2017", false);
		Map<String, Double> allIncomes = expenseList.findIncomeAmount("3/2016",
				"3/2017", null);
		
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

		int width = 1140; /* Width of the image */
		int height = 480; /* Height of the image */
		File lineChart = new File("Graf1_prihod.jpeg");
		System.out.println(width);
		byte[] imageAsBytes = ChartUtilities.encodeAsPNG(graph1.createBufferedImage(1200, 600));

		resp.setContentType("image/png");
		resp.getOutputStream().write(imageAsBytes);
//		Map<String, Integer> map = (Map<String, Integer>) req.getServletContext().getAttribute("map");
//		BufferedImage bi = new BufferedImage(200, 400, BufferedImage.TYPE_INT_RGB);
//		
//		
//		resp.setContentType("image/png");
//		ImageIO.write(bi, "png",resp.getOutputStream());
	}
}
