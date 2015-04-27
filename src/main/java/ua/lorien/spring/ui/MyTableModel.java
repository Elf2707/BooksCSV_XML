package ua.lorien.spring.ui;

import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int colOblojkaIndex;
	
	public void setColOblojkaIndex(int colOblojkaIndex) {
		this.colOblojkaIndex = colOblojkaIndex;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		
		if(columnIndex == colOblojkaIndex){
			//Here is a picture of oblozchka
			return ImageIcon.class;
		}
		return Object.class;
	}
	
	

}
