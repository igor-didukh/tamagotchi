package pets.features;

import pets.Pet;
import superclasses.DataManager;
import superclasses.Entity;
import families.features.FamilyFeature;
import families.features.FamilyFeatureDataManager;

import java.sql.SQLException;
import java.util.ArrayList;

class PetFeatureDataManager extends DataManager {
	private Pet pet;
	
	public PetFeatureDataManager(Pet pet) {
		this.pet = pet;
	}
	
	@Override
	protected Entity getEntityByFields() throws SQLException {
		// rs - resource set from superclass DataManager
		Integer id = rs.getInt("id");
		String featureName = rs.getString("feature");
		String featureData = rs.getString("feature_data");
		
		return new PetFeature(id, featureName, featureData);
	}
	
	@Override
	public ArrayList<Entity> getEntityList() {
		String sql = String.format("SELECT * FROM pet_features WHERE pet_id LIKE '%d'", pet.getId());
		
		if (selectFromRS(sql).size() == 0) {
			ArrayList<Entity> familyFeatures = new FamilyFeatureDataManager(pet.getFamilyId()).getEntityList();
			for (Entity ent : familyFeatures) {
				FamilyFeature ff = (FamilyFeature) ent;
				addEntity(new PetFeature(ff));
			}
		}
		
		return selectFromRS(sql);
	}

	@Override
	public void addEntity(Entity ent) {
		PetFeature pf = (PetFeature) ent;
		
		String sql = "INSERT INTO pet_features ("
				+ "pet_id, "
				+ "feature, "
				+ "feature_data"
				+ ")";
		
		sql += String.format("VALUES ("
				+ "%d, "
				+ "'%s', "
				+ "'%s'"
				+ ")",
				pet.getId(), 
				pf.getName(), 
				pf.getFeatureData()
			);
		
		workDM.exec(sql, "INSERT");
	}

	@Override
	public void updateEntity(Entity ent) {
		PetFeature pf = (PetFeature) ent;
		
		String sql = "UPDATE pet_features SET "
				+ "feature='%s', "
				+ "feature_data='%s' ";
				
		
		sql = String.format(
				sql, 
				pf.getName(), 
				pf.getFeatureData()
			);
		
		sql += "WHERE id=" + pf.getId();
		
		workDM.exec(sql, "UPDATE");
	}

	@Override
	public void deleteEntity(int id) {
		deleteFromTable("pet_features", id);
	}
}