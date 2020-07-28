package users;

import main.Common;
import pets.PetDataManager;
import superclasses.Entity;
import superclasses.ListPanel;
import superclasses.TableModel;

import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.SwingConstants;

public class UserListPanel extends ListPanel {
	private static final long serialVersionUID = 1L;
	
	public UserListPanel() {
		super("Users list", 1200);
		objDataManager = new UserDataManager();
		loadObjects();
	}
	
	@Override
	protected TableModel getTableModel(ArrayList<Entity> objects) {
		return new UserTableModel(objects);
	}
	
	@Override
	protected JDialog getEditDialog(Entity obj) {
		return new UserEditDialog(obj);
	}
	
	@Override
	protected void decorateTable() {
		decorateTableColumn(objTable, 0, 30, SwingConstants.CENTER);
		decorateTableColumn(objTable, 6, 45, SwingConstants.CENTER);
	}
	
	@Override
	protected boolean extraDeleteCheck(int id) {
		if (id == 1) {
	    	Common.showErrorMessage(this, "You can't delete default user!");
	    	return false;
	    }
		
		if (Common.getRegisteredUser().getId() == id) {
	    	Common.showErrorMessage(this, "You can't delete yours own account!");
	    	return false;
	    }
		
		if (new PetDataManager().getIsPetsExistsFor("user_id", id)) {
	    	Common.showErrorMessage(this, "There are pets that belong to selected user!");
	    	return false;
	    }
		return true;
	}
}
