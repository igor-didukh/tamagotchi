package families;

import main.Common;
import superclasses.Entity;
import superclasses.ListPanel;
import superclasses.TableModel;
import pets.PetDataManager;

import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.SwingConstants;

public class FamilyListPanel extends ListPanel {
	private static final long serialVersionUID = 1L;
	
	public FamilyListPanel() {
		super("Familes list", 400);
		objDataManager = new FamilyDataManager();
		loadObjects();
	}
	
	@Override
	protected TableModel getTableModel(ArrayList<Entity> objects) {
		return new FamilyTableModel(objects);
	}
	
	@Override
	protected JDialog getEditDialog(Entity ent) {
		return new FamilyEditDialog(ent);
	}
	
	@Override
	protected boolean extraDeleteCheck(int id) {
		if (new PetDataManager().getIsPetsExistsFor("family_id", id)) {
	    	Common.showErrorMessage(this, "There are pets that belong to selected family!");
	    	return false;
	    }
		return true;
	}
	
	@Override
	protected void decorateTable() {
		decorateTableColumn(objTable, 0, 30, SwingConstants.CENTER);
		decorateTableColumn(objTable, 2, 80, SwingConstants.CENTER);
	}
}