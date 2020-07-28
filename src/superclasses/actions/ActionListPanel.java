package superclasses.actions;

import superclasses.Entity;
import superclasses.ListPanel;
import superclasses.TableModel;

import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.SwingConstants;

public abstract class ActionListPanel extends ListPanel {
	private static final long serialVersionUID = 1L;
	
	boolean simpleMode = false;
	
	public ActionListPanel(String title) {
		super(title);
	}
	
	public ActionListPanel(String title, boolean simpleMode) {
		super(title);
		this.simpleMode = true;
	}
	
	@Override
	protected TableModel getTableModel(ArrayList<Entity> objects) {
		if (simpleMode)
			return new ActionTableModelSimple(objects);
		else
			return new ActionTableModel(objects);
	}
	
	@Override
	protected JDialog getEditDialog(Entity obj) {
		return null;
	}
	
	@Override
	protected void decorateTable() {
		if (simpleMode) {
			decorateTableColumn(objTable, 0, 30, SwingConstants.CENTER);
			decorateTableColumn(objTable, 2, 60, SwingConstants.CENTER);
		} else {
			decorateTableColumn(objTable, 0, 30, SwingConstants.CENTER);
			decorateTableColumn(objTable, 2, 60, SwingConstants.CENTER);
			decorateTableColumn(objTable, 3, 25, SwingConstants.CENTER);
			decorateTableColumn(objTable, 4, 25, SwingConstants.CENTER);
			decorateTableColumn(objTable, 5, 25, SwingConstants.CENTER);
			decorateTableColumn(objTable, 6, 25, SwingConstants.CENTER);
			decorateTableColumn(objTable, 7, 25, SwingConstants.CENTER);
		}
	}
}