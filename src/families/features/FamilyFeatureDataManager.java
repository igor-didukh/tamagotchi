package families.features;

import superclasses.DataManager;
import superclasses.Entity;

import java.sql.SQLException;
import java.util.ArrayList;

public class FamilyFeatureDataManager extends DataManager {
	private int parentId;
	
	public FamilyFeatureDataManager(int familyId) {
		parentId  = familyId;
	}
	
	@Override
	protected Entity getEntityByFields() throws SQLException {
		// rs - resource set from superclass DataManager
		Integer id = rs.getInt("id");
		String featureName = rs.getString("feature");
		String featureData = rs.getString("feature_data");
		
		return new FamilyFeature(id, featureName, featureData);
	}
	
	@Override
	public ArrayList<Entity> getEntityList() {
		String sql = String.format("SELECT * FROM family_features WHERE family_id LIKE '%d'", parentId);
		return selectFromRS(sql);
	}

	@Override
	public void addEntity(Entity ent) {
		FamilyFeature ff = (FamilyFeature) ent;
		
		String sql = "INSERT INTO family_features ("
				+ "family_id, "
				+ "feature, "
				+ "feature_data"
				+ ") ";
		
		sql += String.format("VALUES ("
				+ "%d, "
				+ "'%s', "
				+ "'%s'"
				+ ")",
				
				parentId, 
				ff.getName(), 
				ff.getFeatureData()
			);
		
		workDM.exec(sql, "INSERT");
	}

	@Override
	public void updateEntity(Entity ent) {
		FamilyFeature ff = (FamilyFeature) ent;
		
		String sql = "UPDATE family_features SET "
				+ "feature='%s', "
				+ "feature_data='%s' ";
		
		sql = String.format(
				sql, 
				ff.getName(), 
				ff.getFeatureData()
			);
		
		sql += "WHERE id=" + ff.getId();
		
		workDM.exec(sql, "UPDATE");
	}

	@Override
	public void deleteEntity(int id) {
		deleteFromTable("family_features", id);
	}
}