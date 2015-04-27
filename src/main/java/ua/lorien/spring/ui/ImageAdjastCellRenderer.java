package ua.lorien.spring.ui;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

public class ImageAdjastCellRenderer extends JLabel implements
		TableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
	
		ImageIcon image = null;

		if (value instanceof ImageIcon) {
			image = (ImageIcon) value;
		}
		table.setRowHeight(row, image.getIconHeight());
        setIcon(image);
		if (hasFocus) {
			setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
		} else {
			setBorder(null);
		}
		return this;
	}

}
