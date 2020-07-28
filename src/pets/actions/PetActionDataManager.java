package pets.actions;

import pets.Pet;
import superclasses.DataManager;
import superclasses.Entity;
import families.actions.FamilyAction;
import families.actions.FamilyActionDataManager;
import main.Constants;

import java.sql.SQLException;
import java.util.ArrayList;

class PetActionDataManager extends DataManager implements Constants {
	private Pet pet;
	
	public PetActionDataManager(Pet pet) {
		this.pet = pet;
	}
	
	@Override
	protected Entity getEntityByFields() throws SQLException {
		// rs - resource set from superclass DataManager
		Integer id = rs.getInt("id");
		String name = rs.getString("action");
		int period = rs.getInt("period");
		
		int healthLevel = rs.getInt("health_level");
		int foodLevel = rs.getInt("food_level");
		int wakeLevel = rs.getInt("wake_level");
		int neatLevel = rs.getInt("neat_level");
		int joyLevel = rs.getInt("joy_level");
		
		return new PetAction(id, name, period, healthLevel, foodLevel, wakeLevel, neatLevel, joyLevel);
	}
	
	@Override
	public ArrayList<Entity> getEntityList() {
		String sql = String.format("SELECT * FROM pet_actions WHERE pet_id LIKE '%d'", pet.getId());
		
		if (selectFromRS(sql).size() == 0) {
			ArrayList<Entity> familyActions = new FamilyActionDataManager(pet.getFamilyId()).getEntityList();
			for (Entity ent : familyActions) {
				FamilyAction fa = (FamilyAction) ent;
				addEntity(new PetAction(fa));
			}
		}
		
		return selectFromRS(sql);
	}

	@Override
	public void addEntity(Entity ent) {
		PetAction pa = (PetAction) ent;
		
		String sql = "INSERT INTO pet_actions ("
				+ "pet_id, "
				+ "action, "
				+ "period, "
				
				+ "health_level, "
				+ "food_level, "
				+ "wake_level, "
				+ "neat_level, "
				+ "joy_level"
				
				+ ")";
		
		sql += String.format("VALUES ("
				+ "%d, "
				+ "'%s', "
				+ "%d, "
				
				+ "%d, "
				+ "%d, "
				+ "%d, "
				+ "%d, "
				+ "%d"
				
				+ ")",
				
				pet.getId(), 
				pa.getName(), 
				pa.getPeriod(), 
				
				pa.getLevel(HEALTH), 
				pa.getLevel(FOOD), 
				pa.getLevel(WAKE), 
				pa.getLevel(NEAT), 
				pa.getLevel(JOY)
			);
		
		workDM.exec(sql, "INSERT");
	}

	@Override
	public void updateEntity(Entity ent) {
		PetAction pa = (PetAction) ent;
		
		String sql = "UPDATE pet_actions SET "
				+ "action='%s', "
				+ "period=%d, "
				
				+ "health_level=%d, "
				+ "food_level=%d, "
				+ "wake_level=%d, "
				+ "neat_level=%d, "
				+ "joy_level=%d ";
		
		sql = String.format(
				sql, 
				pa.getName(), 
				pa.getPeriod(), 
				
				pa.getLevel(HEALTH), 
				pa.getLevel(FOOD), 
				pa.getLevel(WAKE), 
				pa.getLevel(NEAT), 
				pa.getLevel(JOY)
			);
		
		sql += "WHERE id=" + pa.getId();
		
		workDM.exec(sql, "UPDATE");
	}

	@Override
	public void deleteEntity(int id) {
		deleteFromTable("pet_actions", id);
	}
}