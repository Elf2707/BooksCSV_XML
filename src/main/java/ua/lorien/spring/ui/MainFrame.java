package ua.lorien.spring.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

	List<Component> components = new ArrayList<>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() {
		for (Component c : components) {
			add(c);
		}
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(new Dimension(screenDim.width - 200, 400));
		setState(Frame.NORMAL);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public void setLayoutMgr(LayoutManager mgr) {
		setLayout(new BoxLayout(getContentPane(), 1));
	}
}
