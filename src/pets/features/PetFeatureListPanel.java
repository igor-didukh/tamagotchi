package pets.features;

import superclasses.Entity;
import superclasses.features.FeatureListPanel;
import pets.Pet;

import javax.swing.JDialog;

public class PetFeatureListPanel extends FeatureListPanel {
	private static final long serialVersionUID = 1L;
	
	public PetFeatureListPanel(Pet pet) {
		super("features list");
		objDataManager = new PetFeatureDataManager(pet);
		loadObjects();
	}
	
	@Override
	protected JDialog getEditDialog(Entity obj) {
		PetFeature feat = (PetFeature) obj;
		return new PetFeatureEditDialog(feat);
	}
}