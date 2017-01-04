package pdf;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import model.ExpenseCategory;
import model.ExpenseItem;
import model.ExpenseList;
import model.IncomeItem;
import model.time.Period;

public abstract class PDFGenerator {

	private static final float TITLE_SIZE_1 = 35;
	private static final float TITLE_SIZE_2 = 17;
	private static final float TITLE_SIZE_3 = 10;
	private static final float TEXT_SIZE = 15;
	private static final float TEXT_TITLE_SIZE = 20;
	
	private static final float TITLE_LEADING = 14.5f;
	private static final float TEXT_LEADING = 18;
	
	private static final int TITLE_OFFSET_X = 120;
	private static final int TITLE_OFFSET_Y = 620;
	private static final int TEXT_OFFSET_X = 50;
	private static final int TEXT_OFFSET_Y = 740;
	
	private static final int TEXT_MARGIN = 50;
	
	private static final float TEXT_LINE_HEIGHT = 21;
	private static final float EMPTY_LINE_HEIGHT = 5;
	
	private static PDDocument document;
	private static PDPage page;
	private static PDPageContentStream stream;
	private static PDType0Font font;
	private static PDType0Font fontBold;
	private static PDType0Font currentFont;
	private static float pageWidth;
	private static float pageHeight;
	private static String pathToFonts;
	
	public static void generatePDF(PDDocument document, String owner, ExpenseList expenseList) throws IOException {
		PDFGenerator.document = document;
		String author = expenseList.getUserOwner().getUsername();
		String name = expenseList.getName();
		List<ExpenseCategory> expenseCategories = getAllCategories(expenseList.getExpenseCategories());
		List<IncomeItem> incomeItems = expenseList.getIncomeItems();
		
		loadFonts();
		setDocumentInfo(author, owner, name);
		addTitlePage(author, owner, name);
		newPage();
		addTitle("TROŠKOVI");
		addEmptyLines(2);
		for(ExpenseCategory expenseCategory : expenseCategories) {
			addExpenseCategory(expenseCategory);
			addEmptyLines(1);
		}
		closePage();
		newPage();
		addTitle("PRIHODI");
		addEmptyLines(2);
		for(IncomeItem incomeItem : incomeItems) {
			addIncomeItem(incomeItem);
			addEmptyLines(1);
		}
		closePage();
	
	}

	public static void setPathToFonts(String pathToFonts) {
		PDFGenerator.pathToFonts = pathToFonts;
	}
	
	
	private static void addTitle(String title) throws IOException {
		stream.setFont(fontBold, TEXT_TITLE_SIZE);
		addTitleDashes(title.length());
		stream.showText("                " + title);
		stream.newLine();
		addTitleDashes(title.length());
		pageHeight -=  EMPTY_LINE_HEIGHT;
		stream.setFont(font, TEXT_SIZE);
	}


	private static void addTitleDashes(int length) throws IOException {
		String dashes = "";
		for(int i = 0; i < length; i++) {
			dashes += "-";
		}
		stream.showText("                " + dashes);
		stream.newLine();
	}


	public static void loadFonts() throws IOException {
		File file = new File(pathToFonts + "/Courier Prime.ttf");
		font = PDType0Font.load(document, file);
		file = new File(pathToFonts + "/Courier Prime Bold.ttf");
		fontBold = PDType0Font.load(document, file);
		currentFont = font;
	}


	private static List<ExpenseCategory> getAllCategories(List<ExpenseCategory> expenseCategories) {
		List<ExpenseCategory> categories = new LinkedList<>();
		for(ExpenseCategory category : expenseCategories) {
			categories.add(category);
			if(!category.getSubCategories().isEmpty()) {
				categories.addAll(getAllCategories(category.getSubCategories()));
			}
		}
		return categories;
	}


	private static void addIncomeItem(IncomeItem incomeItem) throws IOException {
		addBoldedLine("Ime prihoda: " + incomeItem.getName(), "");
		addLine("Iznos: " + getAmounts(incomeItem.getAmounts()), "  "); 
		addLine("Period: " + getPeriod(incomeItem.getPeriod()), "  ");
		addLine("Komentar: " + incomeItem.getComment(), "  ");
		addLine("Fiksnost: " + getFixed(incomeItem.isFixed()), "  ");
		addLine("Opis: " + getDescription(incomeItem.isSallary()), "  ");
		addLine("Datum početka: " + incomeItem.getStartDate(), "  ");
		addLine("Datm završetka: " + incomeItem.getEndDate(), "  ");
	}


	private static void addBoldedLine(String line, String offset) throws IOException {
		currentFont = fontBold;
		stream.setFont(fontBold, TEXT_SIZE);
		addLine(line, offset);
		currentFont = font;
		stream.setFont(font, TEXT_SIZE);
	}


	private static String getAmounts(List<Double> amounts) {
		String text = "";
		for(Double amount : amounts) {
			text += amount + ", ";
		}
		text = text.substring(0, text.length()-2);
		return text;
	}


	private static String getDescription(boolean sallary) {
		if(sallary) {
			return "plaća";
		} else {
			return "ostalo";
		}
	}


	private static void addExpenseCategory(ExpenseCategory expenseCategory) throws IOException {
		addBoldedLine("IME KATEGORIJE: " + expenseCategory.getName(), "");
		addLine("FIKSNOST KATEGORIJE: " + getFixed(expenseCategory.isgetFixed()), "  ");
		addLine("NADKATEGORIJA: " + getSuperCategory(expenseCategory.getSuperCategory()), "  ");
		addLine("PODKATEGORIJE: " + getSubCategories(expenseCategory.getSubCategories()), "  ");
		addLine("STAVKE TROŠKOVA: " + getExpenseItems(expenseCategory.getExpenseItems()), "  ");
		addEmptyLines(1);
		
		List<ExpenseItem> expenseItems = expenseCategory.getExpenseItems();
		for(ExpenseItem expenseItem : expenseItems) {
			addExpenseItem(expenseItem);
			addEmptyLines(1);
		}

	}
	

	private static String getExpenseItems(List<ExpenseItem> expenseItems) {
		if(expenseItems.isEmpty()) {
			return "nema";
		} else {
			return "";
		}
	}


	private static void addExpenseItem(ExpenseItem expenseItem) throws IOException {
		addBoldedLine("Ime troška: " + expenseItem.getName(), "     ");
		addLine("Iznos: " + getAmounts(expenseItem.getAmounts()), "       ");
		addLine("Period: " + getPeriod(expenseItem.getPeriod()), "       ");
		addLine("Komentar: " + expenseItem.getComment(), "       ");
		addLine("Datum početka: " + expenseItem.getStartDate(), "       ");
		addLine("Datum završetka: " + expenseItem.getEndDate(), "       ");
	}


	private static String getPeriod(Period period) {
		if(period == Period.ANUALY) {
			return "godišnje";
		} else if(period == Period.MONTHLY) {
			return "mjesečno";
		} else if(period == Period.QUARTARLY) {
			return "kvartalno";
		} else {
			return "jednoročno";
		}
	}


	private static void newPage() throws IOException {
		page = new PDPage();
		stream = new PDPageContentStream(document, page);
		stream.beginText();
		stream.setFont(currentFont, TEXT_SIZE);
		stream.setLeading(TEXT_LEADING);
		stream.newLineAtOffset(TEXT_OFFSET_X, TEXT_OFFSET_Y);
		pageWidth = page.getMediaBox().getWidth() - TEXT_MARGIN * 2;
		pageHeight = page.getMediaBox().getHeight() - TEXT_MARGIN * 2;
	}


	private static void closePage() throws IOException {
		stream.endText();
		stream.close();
		document.addPage(page);
	}

	private static void addLine(String line, String offset) throws IOException {
		if(pageHeight < TEXT_MARGIN + TEXT_LINE_HEIGHT) {
			closePage();
			newPage();
		}
		
		if(isTooLong(line)) {
			addMultipleLines(line, offset);
		} else {
			stream.showText(offset + line);
			stream.newLine();
			pageHeight -= TEXT_LINE_HEIGHT;
		}
	}

	private static void addMultipleLines(String longLine, String offset) throws IOException {
		List<String> lines = getLines(longLine);
		for(String line : lines) {
			addLine(line, offset);
		}
	}


	private static List<String> getLines(String longLine) throws IOException {
		List<String> lines = new LinkedList<>();
		List<String> words = new LinkedList<>(Arrays.asList(longLine.split(" ")));
		String line = "";
		while(!words.isEmpty()) {
			if(widthOfLine(line + words.get(0) + " ") > pageWidth - TEXT_MARGIN) {
				lines.add(line);
				line = "";
			} else {
				line += words.remove(0) + " ";
			}
		}
		lines.add(line);
		return lines;
	}


	private static boolean isTooLong(String line) throws IOException {
		if(widthOfLine(line) > pageWidth - TEXT_MARGIN) {
			return true;
		} else {
			return false;
		}
	}


	private static float widthOfLine(String line) throws IOException {
		 return TEXT_SIZE * font.getStringWidth(line) / 1000;
	}


	private static String getSubCategories(List<ExpenseCategory> subCategories) {
		if(subCategories.isEmpty())
			return "nema";
		else {
			String text = "";
			for(ExpenseCategory subCategory : subCategories) {
				text += subCategory.getName() + ", ";
			}
			text = text.substring(0, text.length()-2);
			return text;
		}
	}


	private static String getSuperCategory(ExpenseCategory superCategory) {
		if(superCategory == null) {
			return "nema";
		} else { 
			return superCategory.getName();
		}
	}


	private static String getFixed(boolean fixed) {
		if(fixed) {
			return "da"; 
		} else {
			return "ne";
		}
		
	}


	private static void addTitlePage(String author, String owner, String name) throws IOException {		
		page = new PDPage();
		stream = new PDPageContentStream(document,page);
		stream.beginText();
		stream.setFont(fontBold, TITLE_SIZE_1);
		stream.setLeading(TITLE_LEADING);
		stream.newLineAtOffset(TITLE_OFFSET_X, TITLE_OFFSET_Y);
		stream.showText("-----------------");
		addEmptyLines(2);
		stream.showText("TROŠKOVNIK ŽIVOTA");
		addEmptyLines(2);
		stream.showText("-----------------");
		addEmptyLines(10);
		stream.setFont(font, TITLE_SIZE_2);
		stream.showText("   IME TROŠKOVNIKA: " + name);
		addEmptyLines(2);
		stream.showText("   AUTOR TROŠKOVNIKA: " + author);
		addEmptyLines(2);
		stream.showText("   VLASNIK TROŠKOVNIKA: " + owner);
		addEmptyLines(18);
		stream.setFont(font, TITLE_SIZE_3);
		stream.showText("Izradila grupa teflja. Sva prava pridržana. Strogo zabranjeno");
		stream.newLine();
		stream.showText("   kopiranje, mijenjanje ili dijeljenje u komercijalne svrhe.");
		stream.endText();
		stream.close();
		document.addPage(page);
	}

	private static void addEmptyLines(int numberOfLines) throws IOException {
		for(int i = 0; i < numberOfLines; i++) {
			stream.newLine();
			pageHeight -= EMPTY_LINE_HEIGHT;
		}
	}

	private static void setDocumentInfo(String author, String owner, String name) {
		PDDocumentInformation info = new PDDocumentInformation();
		info.setAuthor(author);
		info.setCreator(owner);
		info.setTitle(name);
		info.setSubject("Troskovnik zivota: " + name);
		document.setDocumentInformation(info);
	}
	
	

}
