package superclasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.DAO;

public abstract class DataManager {
	protected static DAO workDM = new DAO();
	protected ResultSet rs;
	
	protected abstract Entity getEntityByFields() throws SQLException;
	public abstract ArrayList<Entity> getEntityList();
	protected abstract void addEntity(Entity ent);
	protected abstract void updateEntity(Entity ent);
	protected abstract void deleteEntity(int id);
	
	protected ArrayList<Entity> selectFromRS(String sql) {
		ArrayList<Entity> list = new ArrayList<Entity>();
		rs = workDM.exec(sql, "SELECT");
		
		if (rs != null)
			try {
				while (rs.next()) {
				 	list.add( getEntityByFields() );
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		return list;
	}
	
	protected Entity selectOneFromTable(String table, int id) {
		String sql = String.format("SELECT * FROM %s WHERE id LIKE '%d'", table, id);
		return selectFromRS(sql).get(0);
	}
	
	protected void deleteFromTable(String table, int id) {
		String sql = String.format("DELETE FROM %s WHERE id=%d", table, id);
		workDM.exec(sql, "UPDATE");
	}
}