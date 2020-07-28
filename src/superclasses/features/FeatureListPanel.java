package superclasses.features;

import superclasses.Entity;
import superclasses.ListPanel;
import superclasses.TableModel;

import java.util.ArrayList;
import javax.swing.JDialog;

public abstract class FeatureListPanel extends ListPanel {
	private static final long serialVersionUID = 1L;
	
	public FeatureListPanel(String title) {
		super(title);
	}
	
	@Override
	protected TableModel getTableModel(ArrayList<Entity> objects) {
		return new FeatureTableModel(objects);
	}
	
	@Override
	protected JDialog getEditDialog(Entity obj) {
		return null;
	}
}