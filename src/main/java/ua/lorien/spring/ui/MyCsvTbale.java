package ua.lorien.spring.ui;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MyCsvTbale extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	TableCellRenderer imageColumnRenderer;

	public void setImageColumnRenderer(TableCellRenderer imageColumnRenderer) {
		this.imageColumnRenderer = imageColumnRenderer;
		setDefaultRenderer(ImageIcon.class, imageColumnRenderer);
	}
}
