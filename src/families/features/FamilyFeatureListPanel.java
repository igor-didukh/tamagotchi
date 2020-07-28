package families.features;

import superclasses.Entity;
import superclasses.features.FeatureListPanel;

import javax.swing.JDialog;

public class FamilyFeatureListPanel extends FeatureListPanel {
	private static final long serialVersionUID = 1L;
	
	public FamilyFeatureListPanel(int familyId) {
		super("features list");
		objDataManager = new FamilyFeatureDataManager(familyId);
		loadObjects();
	}
	
	@Override
	protected JDialog getEditDialog(Entity obj) {
		FamilyFeature feat = (FamilyFeature) obj;
		return new FamilyFeatureEditDialog(feat);
	}
}