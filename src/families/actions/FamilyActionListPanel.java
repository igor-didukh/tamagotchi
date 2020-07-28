package families.actions;

import superclasses.Entity;
import superclasses.actions.ActionListPanel;

import javax.swing.JDialog;

public class FamilyActionListPanel extends ActionListPanel {
	private static final long serialVersionUID = 1L;
	
	public FamilyActionListPanel(int familyId) {
		super("actions list");
		objDataManager = new FamilyActionDataManager(familyId);
		loadObjects();
	}
	
	@Override
	protected JDialog getEditDialog(Entity obj) {
		FamilyAction act = (FamilyAction) obj;
		return new FamilyActionEditDialog(act);
	}
}