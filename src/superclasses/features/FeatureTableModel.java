package superclasses.features;

import java.util.ArrayList;

import superclasses.Entity;
import superclasses.TableModel;

public class FeatureTableModel extends TableModel {
	private static final long serialVersionUID = 1L;
	private static final String[] headers = {"ID", "Feature", "Value"};
    
    public FeatureTableModel(ArrayList<Entity> features) {
    	super(features, headers);
    }
    
    @Override
    public Object getValueAt(int row, int col) {
    	Feature feat = (Feature) super.getValueAt(row, col);
        switch (col) {
            case 0:
                return feat.getId();
            case 1:
                return feat.getName();
            default:
                return feat.getFeatureData();
        }
    }
}