package pets;

import superclasses.DataManager;
import superclasses.Entity;

import java.sql.SQLException;
import java.util.ArrayList;

import main.Constants;

public class PetDataManager extends DataManager implements Constants {
	private int userId;
	
	private final String sqlSelect = "SELECT pets.*, "
			+ "users.id AS u_id, "
			+ "users.firstname AS uf_name, "
			+ "users.lastname AS ul_name, "
			+ "families.id AS f_id, "
			+ "families.family AS f_name, "
			+ "families.age AS f_age "
			+ "FROM users "
			+ "INNER JOIN (families "
			+ "INNER JOIN pets ON families.id = pets.family_id) "
			+ "ON users.id = pets.user_id ";
	
	public PetDataManager() {
		this.userId = 0;
	}
	
	public PetDataManager(int userId) {
		this.userId = userId;
	}
	
	@Override
	protected Entity getEntityByFields() throws SQLException {
		// rs - resource set from superclass DataManager
		Integer id = rs.getInt("id");
		String petName = rs.getString("nick");
		int familyId = rs.getInt("f_id"); 
		String familyName = rs.getString("f_name");
		int familyAge = rs.getInt("f_age");
		int userId = rs.getInt("u_id"); 
		String userName = rs.getString("uf_name") + " " + rs.getString("ul_name"); 
		String imageResource = rs.getString("image_resource");
		
		int healthWeight = rs.getInt("health_weight");
		int foodWeight = rs.getInt("food_weight");
		int wakeWeight = rs.getInt("wake_weight");
		int neatWeight = rs.getInt("neat_weight");
		int joyWeight = rs.getInt("joy_weight");
		
		int healthLevel = rs.getInt("health_level");
		int foodLevel = rs.getInt("food_level");
		int wakeLevel = rs.getInt("wake_level");
		int neatLevel = rs.getInt("neat_level");
		int joyLevel = rs.getInt("joy_level");
		
		int age = rs.getInt("age");
		int energy = rs.getInt("energy");
		
		return new Pet(
				id, petName, 
				familyId, familyName, familyAge, 
				userId, userName, 
				imageResource, 
				healthWeight, foodWeight, wakeWeight, neatWeight, joyWeight,
				healthLevel, foodLevel, wakeLevel, neatLevel, joyLevel, 
				age, energy
			);
	}
	
	@Override
	public ArrayList<Entity> getEntityList() {
		String sql = sqlSelect;
		if (userId != 0)
			sql += String.format(" WHERE ((user_id=%d) AND (energy>0))", userId);
		sql += " ORDER BY id";
		return selectFromRS(sql);
	}
	
	@Override
	public void addEntity(Entity ent) {
		Pet pet = (Pet) ent;
		String sql = "INSERT INTO pets ("
				+ "nick, "
				+ "family_id, "
				+ "user_id, "
				+ "image_resource, "
				
				+ "health_weight, "
				+ "food_weight, "
				+ "wake_weight, "
				+ "neat_weight, "
				+ "joy_weight, "
				
				+ "health_level, "
				+ "food_level, "
				+ "wake_level, "
				+ "neat_level, "
				+ "joy_level, "
				
				+ "age, "
				+ "energy"
				+ ") ";
		
		sql += String.format("VALUES ("
				+ "'%s', "
				+ "%d, "
				+ "%d, "
				+ "'%s', "
				
				+ "%d, "
				+ "%d, "
				+ "%d, "
				+ "%d, "
				+ "%d, "
				
				+ "%d, "
				+ "%d, "
				+ "%d, "
				+ "%d, "
				+ "%d, "
				
				+ "%d, "
				+ "%d"
				+ ")", 
				
				pet.getName(), 
				pet.getFamilyId(), 
				pet.getUserId(), 
				pet.getImageResource(),
				
				pet.getWeight(HEALTH),
				pet.getWeight(FOOD),
				pet.getWeight(WAKE),
				pet.getWeight(NEAT),
				pet.getWeight(JOY),
				
				pet.getLevel(HEALTH),
				pet.getLevel(FOOD),
				pet.getLevel(WAKE),
				pet.getLevel(NEAT),
				pet.getLevel(JOY),
				
				pet.getAge(),
				pet.getEnergy()
			);
		
		workDM.exec(sql, "INSERT");
	}

	@Override
	public void updateEntity(Entity ent) {
		Pet pet = (Pet) ent;
		
		String sql = "UPDATE pets SET "
				+ "nick='%s', "
				+ "family_id=%d, "
				+ "user_id=%d, "
				+ "image_resource='%s', "
				
				+ "health_weight=%d, "
				+ "food_weight=%d, "
				+ "wake_weight=%d, "
				+ "neat_weight=%d, "
				+ "joy_weight=%d, "
				
				+ "health_level=%d, "
				+ "food_level=%d, "
				+ "wake_level=%d, "
				+ "neat_level=%d, "
				+ "joy_level=%d, "
				
				+ "age=%d, "
				+ "energy=%d ";
		
		sql = String.format(
				sql, 
				pet.getName(), 
				pet.getFamilyId(), 
				pet.getUserId(), 
				pet.getImageResource(),
				
				pet.getWeight(HEALTH),
				pet.getWeight(FOOD),
				pet.getWeight(WAKE),
				pet.getWeight(NEAT),
				pet.getWeight(JOY),
				
				pet.getLevel(HEALTH),
				pet.getLevel(FOOD),
				pet.getLevel(WAKE),
				pet.getLevel(NEAT),
				pet.getLevel(JOY),
				
				pet.getAge(),
				pet.getEnergy()
			);
		
		sql += "WHERE id=" + pet.getId();
		
		workDM.exec(sql, "UPDATE");
	}
	
	public void updateLevels(Pet pet) {
		String sql = "UPDATE pets SET "
				+ "health_level=%d, "
				+ "food_level=%d, "
				+ "wake_level=%d, "
				+ "neat_level=%d, "
				+ "joy_level=%d, "
				
				+ "age=%d, "
				+ "energy=%d ";
		
		sql = String.format(sql, 
				pet.getLevel(HEALTH),
				pet.getLevel(FOOD),
				pet.getLevel(WAKE),
				pet.getLevel(NEAT),
				pet.getLevel(JOY),
				
				pet.getAge(),
				pet.getEnergy()
			);
		
		sql += "WHERE id=" + pet.getId();
		
		workDM.exec(sql, "UPDATE");
	}

	@Override
	public void deleteEntity(int id) {
		deleteFromTable("pets", id);
	}
	
	public boolean getIsPetsExistsFor(String parentField, int parentId) {
		String sql = String.format("SELECT * FROM pets WHERE %s LIKE '%d'", parentField, parentId);
		rs = workDM.exec(sql, "SELECT");
		try {
			return (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
}