package families.features;

import superclasses.ListPanel;
import superclasses.features.Feature;
import superclasses.features.FeatureEditDialog;

class FamilyFeatureEditDialog extends FeatureEditDialog {
	private static final long serialVersionUID = 1L;
	
	public FamilyFeatureEditDialog(Feature feat) {
		super(feat);
	}

	@Override
	protected void doAction(String cmd) {
		ListPanel.objFromDialog = null;
		
		if (cmd == "SAVE")
			ListPanel.objFromDialog =
				new FamilyFeature(
					id,
					txtName.getText().trim(),
					txtValue.getText().trim()
				);
	}
}