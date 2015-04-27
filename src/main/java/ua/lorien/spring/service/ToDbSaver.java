package ua.lorien.spring.service;

import java.util.List;
import java.util.Set;

import javax.swing.ImageIcon;

public interface ToDbSaver {

	public abstract void saveAuthors(Set<String> authors);

	public abstract void saveGenre(Set<String> genre);

	public abstract void savePublisher(Set<String> publisher);

	public void saveBooks(List<String> booksNames, List<String> booksContent,
			List<Integer> booksPages, List<String> booksISBN,
			List<String> booksPublYear, List<ImageIcon> booksImages,
			List<String> authorsNames, List<String> genresName,
			List<String> publishersNames);

}