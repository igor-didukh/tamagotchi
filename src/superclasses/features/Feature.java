package superclasses.features;

import superclasses.Entity;

public abstract class Feature extends Entity {
	private String featureData;
	
	public Feature(int id, String featureName, String featureData) {
    	super(id, featureName);
    	this.featureData = featureData;
    }
	
	public String getFeatureData() {
		return featureData;
	}

	public void setFeatureData(String featureData) {
		this.featureData = featureData;
	}
	
	@Override
    public String toString() {
        return " [ feature: id=" + getId() + ", " + getName() + " ]";
    }
}