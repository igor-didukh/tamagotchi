package users;

import java.sql.SQLException;
import java.util.ArrayList;

import superclasses.DataManager;
import superclasses.Entity;

public class UserDataManager extends DataManager {
	public UserDataManager() {
	}
	
	@Override
	protected Entity getEntityByFields() throws SQLException {
		// rs - resource set from superclass DataManager
		Integer id = rs.getInt("id");
		String firstName = rs.getString("firstname");
		String lastName = rs.getString("lastname");
		String login = rs.getString("login");
		String password = rs.getString("password");
		String phone = rs.getString("phone");
		String email = rs.getString("email");
		byte rights = rs.getByte("rights");
		
		return new User(id, firstName, lastName, login, password, phone, email, rights);
	}
	
	@Override
	public ArrayList<Entity> getEntityList() {
		return selectFromRS("SELECT * FROM users");
	}

	@Override
	public void addEntity(Entity ent) {
		User user = (User) ent;
		
		String sql = "INSERT INTO users (firstname, lastname, login, password, phone, email, rights) ";
		
		sql += String.format(
				"VALUES ('%s', '%s', '%s', '%s', '%s', '%s', %d)", 
				user.getName(), 
				user.getLastName(), 
				user.getLogin(), 
				user.getPassword(), 
				user.getPhone(), 
				user.getEmail(),
				user.getRights()
			);
		
		workDM.exec(sql, "INSERT");
	}

	@Override
	public void updateEntity(Entity ent) {
		User user = (User) ent;
		
		String sql = "UPDATE users SET "
				+ "firstname='%s', "
				+ "lastname='%s', "
				+ "login='%s', "
				+ "password='%s', "
				+ "phone='%s', "
				+ "email='%s', "
				+ "rights=%d ";
		
		sql = String.format(
				sql, 
				user.getName(), 
				user.getLastName(), 
				user.getLogin(), 
				user.getPassword(), 
				user.getPhone(), 
				user.getEmail(), 
				user.getRights()
			);
		
		sql += "WHERE id=" + user.getId();
		workDM.exec(sql, "UPDATE");
	}

	@Override
	public void deleteEntity(int id) {
		deleteFromTable("users", id);
	}
	
	public User getUserByLogin(String login, String password) {
		String sql = String.format("SELECT * FROM users WHERE login LIKE '%s' AND password LIKE '%s'", login, password);
		return (User) selectFromRS(sql).get(0);
	}
}