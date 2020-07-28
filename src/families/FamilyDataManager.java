package families;

import superclasses.DataManager;
import superclasses.Entity;

import java.sql.SQLException;
import java.util.ArrayList;

public class FamilyDataManager extends DataManager {
	@Override
	protected Entity getEntityByFields() throws SQLException {
		// rs - resource set from superclass DataManager
		Integer id = rs.getInt("id");
		String familyName = rs.getString("family");
		Integer age = rs.getInt("age");
		return new Family(id, familyName, age);
	}
	
	@Override
	public ArrayList<Entity> getEntityList() {
		return selectFromRS("SELECT * FROM families");
	}

	@Override
	public void addEntity(Entity ent) {
		Family family = (Family) ent;
		
		String sql = "INSERT INTO families ("
				+ "family, "
				+ "age"
				+ ") ";
		
		sql += String.format("VALUES ("
				+ "'%s', "
				+ "%d"
				+ ")",
				
				family.getName(),
				family.getAge()
			);
		
		workDM.exec(sql, "INSERT");
	}

	@Override
	public void updateEntity(Entity ent) {
		Family family = (Family) ent;
		
		String sql = "UPDATE families SET "
				+ "family='%s', "
				+ "age=%d ";
		
		sql = String.format(sql, 
				family.getName(),
				family.getAge()
			);
		
		sql += "WHERE id=" + family.getId();
		
		workDM.exec(sql, "UPDATE");
	}

	@Override
	public void deleteEntity(int id) {
		deleteFromTable("families", id);
	}
}