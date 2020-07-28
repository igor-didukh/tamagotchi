package pets.features;

import families.features.FamilyFeature;
import superclasses.features.Feature;

class PetFeature extends Feature {
	public PetFeature(int id, String featureName, String featureData) {
		super(id, featureName, featureData);
	}
	
	public PetFeature(FamilyFeature ff) {
		super(0, ff.getName(), ff.getFeatureData());
	}
}