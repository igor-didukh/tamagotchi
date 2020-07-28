package pets.actions;

import superclasses.Entity;
import superclasses.actions.ActionListPanel;
import pets.Pet;

import javax.swing.JDialog;

public class PetActionListPanel extends ActionListPanel {
	private static final long serialVersionUID = 1L;
	
	private void init(Pet pet) {
		objDataManager = new PetActionDataManager(pet);
		loadObjects();
	}
	
	public PetActionListPanel(Pet pet) {
		super("actions list");
		init(pet);
	}
	
	public PetActionListPanel(Pet pet, boolean simpleMode) {
		super("actions list", simpleMode);
		init(pet);
	}
	
	@Override
	protected JDialog getEditDialog(Entity obj) {
		PetAction act = (PetAction) obj;
		return new PetActionEditDialog(act);
	}
}