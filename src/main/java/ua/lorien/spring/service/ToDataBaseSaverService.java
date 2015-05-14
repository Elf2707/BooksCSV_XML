package ua.lorien.spring.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class ToDataBaseSaverService implements ToDbSaver {

	private NamedParameterJdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void saveBooks(List<String> booksNames, List<String> booksContent,
			List<Integer> booksPages, List<String> booksISBN,
			List<String> booksPublYear, List<String> booksImagesPaths,
			List<String> authorsNames, List<String> genresNames,
			List<String> publishersNames) {

		final String AUTHORS_QUERY = "SELECT id, fio FROM author";
		final String GENRES_QUERY = "SELECT id, name FROM genre";
		final String PUBLISHERS_QUERY = "SELECT id, name FROM publisher";
		final String BOOKS_INSERT_QUERY = "INSERT INTO book (name, content, page_count, isbn, genre_id, author_id, publish_year, "
				+ "publisher_id, image, rate) VALUES(:bookName, :bookContent, :bookPageCount, :bookISBN, :bookGenreId, :bookAuthorId, "
				+ ":bookPublishYear, :bookPublisherId, :bookImage, :rate);";

		// Building maps from authors, genres, publishers
		final Map<String, Integer> authors = new HashMap<>();
		final Map<String, Integer> genres = new HashMap<>();
		final Map<String, Integer> publishers = new HashMap<>();

		jdbcTemplate.query(AUTHORS_QUERY, new RowMapper<Object>() {

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				authors.put(rs.getString("fio"), rs.getInt("id"));
				return null;
			}
		});

		jdbcTemplate.query(GENRES_QUERY, new RowMapper<Object>() {

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				genres.put(rs.getString("name"), rs.getInt("id"));
				return null;
			}
		});

		jdbcTemplate.query(PUBLISHERS_QUERY, new RowMapper<Object>() {

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				publishers.put(rs.getString("name"), rs.getInt("id"));
				return null;
			}
		});

		// Saving books
		for (int i = 0; i < booksNames.size(); i++) {
			// Making params map
			final Map<String, Object> booksQueryParams = new HashMap<>();
			booksQueryParams.put("bookName", booksNames.get(i));
			booksQueryParams.put("bookContent", booksContent.get(i));
			booksQueryParams.put("bookPageCount", booksPages.get(i));
			booksQueryParams.put("bookISBN", booksISBN.get(i));
			booksQueryParams.put("bookGenreId", genres.get(genresNames.get(i)));
			booksQueryParams.put("bookAuthorId",
					authors.get(authorsNames.get(i)));
			booksQueryParams.put("bookPublishYear", booksPublYear.get(i));
			booksQueryParams.put("bookPublisherId",
					publishers.get(publishersNames.get(i)));

			try {
				BufferedImage image = ImageIO.read(new File(booksImagesPaths
						.get(i)));
				ByteArrayOutputStream imageBytesStrm = new ByteArrayOutputStream();
				ImageIO.write(image, "jpg", imageBytesStrm);
				imageBytesStrm.flush();
				booksQueryParams.put("bookImage", imageBytesStrm.toByteArray());
				imageBytesStrm.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			booksQueryParams.put("rate", 10);
			jdbcTemplate.update(BOOKS_INSERT_QUERY, booksQueryParams);
		}
	}

	@Override
	public void saveAuthors(Set<String> authors) {
		final String AUTHOR_INSERT_QUERY = "INSERT INTO author (fio, birthday) VALUES(:fio, :birthDay);";
		final Random randDate = new Random();
		final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// the basic date for birthday
		final Calendar basicDate = Calendar.getInstance();

		for (final String author : authors) {
			final Map<String, Object> params = new HashMap<>();
			// birthday current date minus about 30 years
			// something near 1970 - 1990 year
			int rollingYear = 30 + randDate.nextInt(10);
			basicDate.roll(Calendar.YEAR, -rollingYear);
			params.put("fio", author);
			params.put("birthDay", dateFormat.format(basicDate.getTime()));

			jdbcTemplate.update(AUTHOR_INSERT_QUERY, params);
			// roll back base year
			basicDate.roll(Calendar.YEAR, rollingYear);
		}
	}

	@Override
	public void saveGenre(Set<String> genres) {
		final String GENRE_INSERT_QUERY = "INSERT INTO genre (name) VALUES(:name);";

		for (final String genre : genres) {
			final Map<String, Object> params = new HashMap<>();

			params.put("name", genre);
			jdbcTemplate.update(GENRE_INSERT_QUERY, params);
		}
	}

	@Override
	public void savePublisher(Set<String> publishers) {
		final String PUBLISHER_INSERT_QUERY = "INSERT INTO publisher (name) VALUES(:name);";

		for (final String publisher : publishers) {
			final Map<String, Object> params = new HashMap<>();

			params.put("name", publisher);
			jdbcTemplate.update(PUBLISHER_INSERT_QUERY, params);
		}
	}
}
