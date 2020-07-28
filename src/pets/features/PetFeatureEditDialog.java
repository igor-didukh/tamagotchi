package pets.features;

import superclasses.ListPanel;
import superclasses.features.Feature;
import superclasses.features.FeatureEditDialog;

class PetFeatureEditDialog extends FeatureEditDialog {
	private static final long serialVersionUID = 1L;
	
	public PetFeatureEditDialog(Feature feat) {
		super(feat);
	}

	@Override
	protected void doAction(String cmd) {
		ListPanel.objFromDialog = null;
		
		if (cmd == "SAVE")
			ListPanel.objFromDialog =
				new PetFeature(
					id,
					txtName.getText().trim(),
					txtValue.getText().trim()
				);
	}	
}