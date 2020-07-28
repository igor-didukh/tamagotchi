package pets.actions;

import main.Common;
import superclasses.ListPanel;
import superclasses.actions.Action;
import superclasses.actions.ActionEditDialog;

class PetActionEditDialog extends ActionEditDialog {
	private static final long serialVersionUID = 1L;

	public PetActionEditDialog(Action act) {
		super(act);
	}

	@Override
	protected void doAction(String cmd) {
		ListPanel.objFromDialog = null;
		
		if (cmd == "SAVE")
			ListPanel.objFromDialog =
				new PetAction(
					id,
					txtName.getText().trim(),
					Common.parseInt(txtPeriod.getText().trim()),
					levels.getLevel(HEALTH),
		            levels.getLevel(FOOD),
		            levels.getLevel(WAKE),
		            levels.getLevel(NEAT),
		            levels.getLevel(JOY)
				);
	}
}