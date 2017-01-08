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

/**
 * This class is used for generating a PDF document
 * based on given {@link ExpenseList}. It extracts
 * {@link ExpenseCategory}, {@link ExpenseItem} and
 * {@link IncomeItem} objects and prints their content
 * to the PDF file.
 * 
 * <p>Offers methods for setting the font of the 
 * document and generating the document based on the
 * given {@link ExpenseList}, {@link PDDocument} and
 * {@link ExpenseList}'s owner.
 * 
 * @author Vilim
 *
 */
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
	
	/**
	 * Generates the PDF document based on the given
	 * {@link ExpenseList} and {@link ExpenseList}'s
	 * owner and saves generated content to given
	 * {@link PDDocument}.
	 * 
	 * @param document whose content is generated
	 * @param owner name of the user who owns the expense list
	 * @param expenseList whose content will be put in the document
	 * 
	 * @throws IOException
	 */
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

	/**
	 * Sets the file path of the directory in which
	 * the fonts for the document text are.
	 * 
	 * @param pathToFonts
	 */
	public static void setPathToFonts(String pathToFonts) {
		PDFGenerator.pathToFonts = pathToFonts;
	}
	
	/**
	 * Adds a given title to the center of current
	 * line on the page.
	 * 
	 * @param title
	 * @throws IOException
	 */
	private static void addTitle(String title) throws IOException {
		stream.setFont(fontBold, TEXT_TITLE_SIZE);
		addTitleDashes(title.length());
		stream.showText("                " + title);
		stream.newLine();
		addTitleDashes(title.length());
		pageHeight -=  EMPTY_LINE_HEIGHT;
		stream.setFont(font, TEXT_SIZE);
	}

	/**
	 * Adds a given number of dashes to the center of 
	 * current line. 
	 * 
	 * @param length
	 * @throws IOException
	 */
	private static void addTitleDashes(int length) throws IOException {
		String dashes = "";
		for(int i = 0; i < length; i++) {
			dashes += "-";
		}
		stream.showText("                " + dashes);
		stream.newLine();
	}

	/**
	 * Loads fonts from the directory in which the font
	 * files are situated.
	 * 
	 * @throws IOException
	 */
	public static void loadFonts() throws IOException {
		File file = new File(pathToFonts + "/Courier Prime.ttf");
		font = PDType0Font.load(document, file);
		file = new File(pathToFonts + "/Courier Prime Bold.ttf");
		fontBold = PDType0Font.load(document, file);
		currentFont = font;
	}

	/**
	 * Returns a {@link List} of all {@link ExpenseCategory} and all their
	 * subcategories in the given list of categories.
	 * 
	 * @param expenseCategories
	 * @return
	 */
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

	/**
	 * Adds content of given {@link IncomeItem} to the
	 * document.
	 * 
	 * @param incomeItem
	 * @throws IOException
	 */
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

	/**
	 * Adds a given line with given offset to the 
	 * document but in bolded font.
	 * 
	 * @param line
	 * @param offset spaces which are used as line offset
	 * @throws IOException
	 */
	private static void addBoldedLine(String line, String offset) throws IOException {
		currentFont = fontBold;
		stream.setFont(fontBold, TEXT_SIZE);
		addLine(line, offset);
		currentFont = font;
		stream.setFont(font, TEXT_SIZE);
	}

	/**
	 * Returns a {@link String} generated on the basis of
	 * given {@link List} of {@link Double} objects. 
	 * Generated {@link String} will contain all {@link Double}
	 * objects separated by ", " (comma and a space).
	 * 
	 * @param amounts
	 * @return
	 */
	private static String getAmounts(List<Double> amounts) {
		String text = "";
		for(Double amount : amounts) {
			text += amount + ", ";
		}
		text = text.substring(0, text.length()-2);
		return text;
	}

	/**
	 * Returns a croatian representation of a meaning of
	 * given boolean value. If the value is <code>true</code>,
	 * the representation will be "placa", and otherwise
	 * the "ostalo" will be returned.
	 * 
	 * @param sallary
	 * @return croatian word
	 */
	private static String getDescription(boolean sallary) {
		if(sallary) {
			return "plaća";
		} else {
			return "ostalo";
		}
	}

	/**
	 * Adds content of given {@link ExpenseCategory} to 
	 * the document.
	 * 
	 * @param expenseCategory
	 * @throws IOException
	 */
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
	
	/**
	 * Returns a croatian representation of the amount
	 * of content of given {@link List} of {@link ExpenseItem}s.
	 * If the {@link List} is empty, the "nema" will be returned,
	 * the "" otherwise.
	 * 
	 * @param expenseItems
	 * @return
	 */
	private static String getExpenseItems(List<ExpenseItem> expenseItems) {
		if(expenseItems.isEmpty()) {
			return "nema";
		} else {
			return "";
		}
	}

	/**
	 * Adds a content of given {@link ExpenseItem} to the
	 * document.
	 * 
	 * @param expenseItem
	 * @throws IOException
	 */
	private static void addExpenseItem(ExpenseItem expenseItem) throws IOException {
		addBoldedLine("Ime troška: " + expenseItem.getName(), "     ");
		addLine("Iznos: " + getAmounts(expenseItem.getAmounts()), "       ");
		addLine("Period: " + getPeriod(expenseItem.getPeriod()), "       ");
		addLine("Komentar: " + expenseItem.getComment(), "       ");
		addLine("Datum početka: " + expenseItem.getStartDate(), "       ");
		addLine("Datum završetka: " + expenseItem.getEndDate(), "       ");
	}

	/**
	 * Returns a croatian representation of the 
	 * value of given {@link Period}.
	 * 
	 * @param period
	 * @return
	 */
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

	/**
	 * Creates a new {@link PDPage} in the document, opens 
	 * page's {@link PDPageContentStream} and sets the font,
	 * text leading and text offset.
	 * 
	 * 
	 * @throws IOException
	 */
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

	/**
	 * Finishes a current {@link PDPage} in the document, 
	 * closes page's {@link PDPageContentStream} and adds
	 * a page to the document.
	 * 
	 * @throws IOException
	 */
	private static void closePage() throws IOException {
		stream.endText();
		stream.close();
		document.addPage(page);
	}

	/**
	 * Adds a given line with given offset to the current
	 * {@link PDPage}. If the given line doesn't fit to 
	 * the current page, a current page will be closed and
	 * a new will be opened.
	 * 
	 * @param line
	 * @param offset spaces which are used as line offset
	 * @throws IOException
	 */
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
	
	/**
	 * Adds a given line to the current {@link PDPage}
	 * but to multiple lines because a given line is 
	 * too long to fit only one line.
	 * 
	 * @param longLine
	 * @param offset spaces which are used as line offset
	 * @throws IOException
	 */
	private static void addMultipleLines(String longLine, String offset) throws IOException {
		List<String> lines = getLines(longLine);
		for(String line : lines) {
			addLine(line, offset);
		}
	}

	/**
	 * Breaks a given too long line to multiple lines
	 * and returns a {@link List} of them.
	 * 
	 * @param longLine
	 * @return
	 * @throws IOException
	 */
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

	/**
	 * Checks if the given {@link String} is too 
	 * long to fit one line of the page.
	 * 
	 * @param line
	 * @return
	 * @throws IOException
	 */
	private static boolean isTooLong(String line) throws IOException {
		if(widthOfLine(line) > pageWidth - TEXT_MARGIN) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns a width of the given {@link String}
	 * calculated on the basis of the current font.
	 * 
	 * @param line
	 * @return
	 * @throws IOException
	 */
	private static float widthOfLine(String line) throws IOException {
		 return TEXT_SIZE * font.getStringWidth(line) / 1000;
	}

	/**
	 * Returns a {@link String} representation of the given {@link List}
	 * of {@link ExpenseCategory}s. If the {@link List} is
	 * empty, "nema" will be returned, otherwise the content
	 * of the list will be returned.
	 * 
	 * @param subCategories
	 * @return
	 */
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

	/**
	 * Returns a {@link String} representation of the
	 * given {@link ExpenseCategory}. If given
	 * category is <code>null</code>, "nema" will be
	 * returned, category's name otherwise.
	 * 
	 * @param superCategory
	 * @return
	 */
	private static String getSuperCategory(ExpenseCategory superCategory) {
		if(superCategory == null) {
			return "nema";
		} else { 
			return superCategory.getName();
		}
	}

	/**
	 * Returns a croatian representation of the given
	 * boolean value. Returns "da" if <code>true</code>,
	 * "ne" otherwise.
	 * 
	 * @param fixed
	 * @return
	 */
	private static String getFixed(boolean fixed) {
		if(fixed) {
			return "da"; 
		} else {
			return "ne";
		}
		
	}

	/**
	 * Adds a title {@link PDPage} to the document based
	 * on the given author, owner and name of the expense
	 * list.
	 * 
	 * @param author
	 * @param owner
	 * @param name
	 * @throws IOException
	 */
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
	
	/**
	 * Adds a given number of empty lines to the 
	 * current page.
	 * 
	 * @param numberOfLines
	 * @throws IOException
	 */
	private static void addEmptyLines(int numberOfLines) throws IOException {
		for(int i = 0; i < numberOfLines; i++) {
			stream.newLine();
			pageHeight -= EMPTY_LINE_HEIGHT;
		}
	}

	/**
	 * Sets the {@link PDDocumentInformation} of the
	 * {@link PDDocument} based on the given author,
	 * owner and name of the expense list.
	 * 
	 * @param author
	 * @param owner
	 * @param name
	 */
	private static void setDocumentInfo(String author, String owner, String name) {
		PDDocumentInformation info = new PDDocumentInformation();
		info.setAuthor(author);
		info.setCreator(owner);
		info.setTitle(name);
		info.setSubject("Troskovnik zivota: " + name);
		document.setDocumentInformation(info);
	}
	
	

}
