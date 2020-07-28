package families.actions;

import superclasses.DataManager;
import superclasses.Entity;

import java.sql.SQLException;
import java.util.ArrayList;

import main.Constants;

public class FamilyActionDataManager extends DataManager implements Constants {
	private int parentId;
	
	public FamilyActionDataManager(int familyId) {
		parentId  = familyId;
	}
	
	@Override
	protected Entity getEntityByFields() throws SQLException {
		// rs - resource set from superclass DataManager
		int id = rs.getInt("id");
		String name = rs.getString("action");
		int period = rs.getInt("period");
		
		int healthLevel = rs.getInt("health_level");
		int foodLevel = rs.getInt("food_level");
		int wakeLevel = rs.getInt("wake_level");
		int neatLevel = rs.getInt("neat_level");
		int joyLevel = rs.getInt("joy_level");
		
		return new FamilyAction(id, name, period, healthLevel, foodLevel, wakeLevel, neatLevel, joyLevel);
	}
	
	@Override
	public ArrayList<Entity> getEntityList() {
		String sql = String.format("SELECT * FROM family_actions WHERE family_id LIKE '%d'", parentId);
		return selectFromRS(sql);
	}

	@Override
	public void addEntity(Entity ent) {
		FamilyAction fa = (FamilyAction) ent;
		
		String sql = "INSERT INTO family_actions ("
				+ "family_id, "
				+ "action, "
				+ "period, "
				
				+ "health_level, "
				+ "food_level, "
				+ "wake_level, "
				+ "neat_level, "
				+ "joy_level"
				
				+ ") ";
		
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
				
				parentId, 
				fa.getName(), 
				fa.getPeriod(), 
				
				fa.getLevel(HEALTH), 
				fa.getLevel(FOOD), 
				fa.getLevel(WAKE), 
				fa.getLevel(NEAT), 
				fa.getLevel(JOY)
			);
		
		workDM.exec(sql, "INSERT");
	}

	@Override
	public void updateEntity(Entity ent) {
		FamilyAction fa = (FamilyAction) ent;
		
		String sql = "UPDATE family_actions SET "
				+ "action='%s', "
				+ "period=%d, "
				+ "health_level=%d, "
				+ "food_level=%d, "
				+ "wake_level=%d, "
				+ "neat_level=%d, "
				+ "joy_level=%d ";
		
		sql = String.format(sql, 
				fa.getName(), 
				fa.getPeriod(), 
				
				fa.getLevel(HEALTH), 
				fa.getLevel(FOOD), 
				fa.getLevel(WAKE), 
				fa.getLevel(NEAT), 
				fa.getLevel(JOY)
			);
		
		sql += "WHERE id=" + fa.getId();
		
		workDM.exec(sql, "UPDATE");
	}

	@Override
	public void deleteEntity(int id) {
		deleteFromTable("family_actions", id);
	}
}