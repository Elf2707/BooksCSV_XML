package ua.lorien.spring.ui;

import java.awt.Component;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;

public class TableScrollPanel extends JScrollPane {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	List<Component> components = new ArrayList<>();

	public TableScrollPanel(Component componet){
		super(componet);
	}
	
	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public void setLayoutMgr(LayoutManager mgr) {
		setLayout(mgr);
		
	}

	public void init() {
	}
}
