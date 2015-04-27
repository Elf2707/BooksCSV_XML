package ua.lorien.spring.ui;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import ua.lorien.spring.service.MyCSVReader;

public class ReadCSVFileAction extends AbstractAction implements
		ApplicationContextAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ApplicationContext appContext;

	@Override
	public void actionPerformed(ActionEvent e) {
		// open CSV file and Move and read it and display it into the table
		JFileChooser fileChooser = (JFileChooser) appContext
				.getBean("fileChooser");
		if (fileChooser != null) {
			int retCode = fileChooser.showOpenDialog(((JButton) e.getSource())
					.getTopLevelAncestor());
			if (retCode == JFileChooser.APPROVE_OPTION) {
				File csvFile = fileChooser.getSelectedFile();
				MyCSVReader csvDataReader = (MyCSVReader) appContext.getBean(
						MyCSVReader.class, csvFile);

				JTable csvTable = (JTable) appContext.getBean("tableCSV");
				DefaultTableModel tableModel = (DefaultTableModel) csvTable
						.getModel();
				try {
					// Clear rows
					for (int i = tableModel.getRowCount(); i > 0; i--) {
						tableModel.removeRow(0);
					}
					// reads all data from csv file
					List<String[]> csvData = csvDataReader.readAll();
					// if list not empty fill table with data from list
					if (!csvData.isEmpty()) {

						String[] headers = csvData.get(0);
						// set headers of table
						tableModel.setColumnIdentifiers(headers);
						// find index for picture column for making picture
						// oblozhka
						int oblozhkaIndex = -1;
						for (int i = 0; i < headers.length; i++) {
							if (headers[i].toLowerCase().contains("oblozh")) {
								oblozhkaIndex = i;
								break;
							}
						}

						// Add all data to table
						ListIterator<String[]> listIterator = csvData
								.listIterator(1);
						for (int i = 0; listIterator.hasNext(); i++) {

							String[] rowDataInStringArray = listIterator.next();
							Object[] rowData = new Object[rowDataInStringArray.length];

							// Copying data to array of Objects and add images
							for (int j = 0; j < rowDataInStringArray.length; j++) {
								if (j == oblozhkaIndex) {
									// making picture for oblozhka
									Icon oblozhkaIcon = new ImageIcon(
											csvFile.getParent()
													+ "\\images\\"
													+ rowDataInStringArray[oblozhkaIndex]);
									rowData[j] = oblozhkaIcon;
									continue;
								}
								rowData[j] = rowDataInStringArray[j];
							}
							tableModel.insertRow(i, rowData);
							csvTable.revalidate();
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					csvDataReader.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
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
