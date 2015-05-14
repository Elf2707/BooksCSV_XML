package ua.lorien.spring.ui;

import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.support.ResourcePropertySource;

import ua.lorien.spring.service.ToDbSaver;

public class SaveToDBAction extends AbstractAction implements
		ApplicationContextAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ApplicationContext appContext;

	private ToDbSaver dbService;
	private ResourcePropertySource props;

	public void setProps(ResourcePropertySource props) {
		this.props = props;
	}

	public ToDbSaver getDbService() {
		return dbService;
	}

	public void setDbService(ToDbSaver dbService) {
		this.dbService = dbService;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JTable csvTable = (JTable) appContext.getBean("tableCSV");
		DefaultTableModel tableModel = (DefaultTableModel) csvTable.getModel();
		// Making hash sets from authors, publishers, genres and others books
		// parameters
		int authorIndex = Integer.parseInt((String) props
				.getProperty("column_author"));
		HashSet<String> authorsSet = new HashSet<>();
		// This lists for future getting IDs by name of author, genre,
		// publisher, when we will come to insert books to db.
		List<String> authorsList = new ArrayList<>();

		int genreIndex = Integer.parseInt((String) props
				.getProperty("column_genre"));
		HashSet<String> genresSet = new HashSet<>();
		List<String> genresList = new ArrayList<>();

		int publisherIndex = Integer.parseInt((String) props
				.getProperty("column_publisher"));
		HashSet<String> publishersSet = new HashSet<>();
		// For future get ID off publisher when we will come to insert books to
		// db
		List<String> publishersList = new ArrayList<>();

		// books parameters may be equal
		int bookNameIndex = Integer.parseInt((String) props
				.getProperty("column_book_name"));
		List<String> booksNames = new ArrayList<>();

		int bookContentIndex = Integer.parseInt((String) props
				.getProperty("column_content"));
		List<String> booksContent = new ArrayList<>();

		int bookPageCountIndex = Integer.parseInt((String) props
				.getProperty("column_pages"));
		List<Integer> booksPages = new ArrayList<>();

		int bookISBNIndex = Integer.parseInt((String) props
				.getProperty("column_isbn"));
		List<String> booksISBN = new ArrayList<>();

		int bookPublYearIndex = Integer.parseInt((String) props
				.getProperty("column_year"));
		List<String> booksPublYear = new ArrayList<>();

		int bookImageIndex = Integer.parseInt((String) props
				.getProperty("column_oblozhka"));
		List<String> booksImagesPaths = new ArrayList<>();

		// Taking data from csv file
		for (int i = 0; i < tableModel.getRowCount(); i++) {
			String authorName = (String) tableModel.getValueAt(i, authorIndex);
			// I do not want to have an empty strings so i replace it with
			// Unknown string
			if (authorName.equals("")) {
				authorName = "UnknownA";
			}
			authorsSet.add(authorName);
			authorsList.add(authorName);

			String genreName = (String) tableModel.getValueAt(i, genreIndex);
			if (genreName.equals("")) {
				genreName = "UnknownG";
			}
			genresSet.add(genreName);
			genresList.add(genreName);

			String publisherName = (String) tableModel.getValueAt(i,
					publisherIndex);
			if (publisherName.equals("")) {
				publisherName = "UnknownP";
			}
			publishersSet.add(publisherName);
			publishersList.add(publisherName);

			// book
			booksNames.add((String) tableModel.getValueAt(i, bookNameIndex));
			booksContent.add((String) tableModel
					.getValueAt(i, bookContentIndex));
			booksPages.add(Integer.parseInt((String) tableModel.getValueAt(i,
					bookPageCountIndex)));
			booksISBN.add((String) tableModel.getValueAt(i, bookISBNIndex));

			int year = Integer.parseInt((String) tableModel.getValueAt(i,
					bookPublYearIndex));

			Calendar publishYear = Calendar.getInstance();
			publishYear.set(year, Calendar.JANUARY, 1);
			SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			booksPublYear.add(sqlDateFormat.format(publishYear.getTime()));
			ImageIcon image = (ImageIcon) tableModel.getValueAt(i, bookImageIndex);
			booksImagesPaths.add(image.toString()); //path to image file
		}

		dbService.saveAuthors(authorsSet);
		dbService.saveGenre(genresSet);
		dbService.savePublisher(publishersSet);
		dbService.saveBooks(booksNames, booksContent, booksPages, booksISBN,
				booksPublYear, booksImagesPaths, authorsList, genresList,
				publishersList);

	}

	public void setName(String name) {
		putValue(Action.NAME, name);
	}

	@Override
	public void setApplicationContext(ApplicationContext appContext)
			throws BeansException {
		this.appContext = appContext;
	}

}
