package pets;

import main.Common;
import superclasses.Entity;
import superclasses.ListPanel;
import superclasses.TableModel;
import families.FamilyDataManager;

import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.SwingConstants;

public class PetListPanel extends ListPanel {
	private static final long serialVersionUID = 1L;
	
	public PetListPanel() {
		super("Pets list", 800);
		objDataManager = new PetDataManager();
		loadObjects();
	}
	
	@Override
	protected TableModel getTableModel(ArrayList<Entity> objects) {
		return new PetTableModel(objects);
	}
	
	@Override
	protected JDialog getEditDialog(Entity obj) {
		if (new FamilyDataManager().getEntityList().size() == 0) {
			Common.showErrorMessage(this, "Family list is empty");
			return null;
		} else
			return new PetEditDialog(obj);
	}
	
	@Override
	protected void decorateTable() {
		decorateTableColumn(objTable, 0, 30, SwingConstants.CENTER);
		decorateTableColumn(objTable, 4, 60, SwingConstants.CENTER);
		decorateTableColumn(objTable, 5, 50, SwingConstants.CENTER);
		decorateTableColumn(objTable, 6, 50, SwingConstants.CENTER);
	}
}