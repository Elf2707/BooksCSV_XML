package ua.lorien.spring.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import au.com.bytecode.opencsv.CSVReader;

public class MyCSVReader extends CSVReader {

	public MyCSVReader(Reader reader) {
		super(reader);
	}
	
	/***
	 * New CSV File
	 * @param csvFile - file object
	 * @param separator - separator in csv File
	 * @throws FileNotFoundException
	 */
	public MyCSVReader( File csvFile, String separator) throws FileNotFoundException{
		super(new FileReader(csvFile), separator.charAt(0));
	}
	
	public MyCSVReader( File csvFile ) throws FileNotFoundException{
		super(new FileReader(csvFile), ';');
	}
}
