package users;

import java.util.ArrayList;

import superclasses.Entity;
import superclasses.TableModel;

class UserTableModel extends TableModel {
	private static final long serialVersionUID = 1L;
	private static final String[] headers = {"ID", "First name", "Last name", "Login", "Phone", "Email", "Admin"};
    
    public UserTableModel(ArrayList<Entity> users) {
        super(users, headers);
    }
    
    @Override
    public Object getValueAt(int row, int col) {
        User user = (User) super.getValueAt(row, col);
        switch (col) {
            case 0:
                return user.getId();
            case 1:
                return user.getName();
            case 2:
                return user.getLastName();
            case 3:
                return user.getLogin();
            case 4:
            	return user.getPhone();
            case 5:
            	return user.getEmail();
            default: {
            	return (user.getRights()==1) ? "+" : "";
            }
                
        }
    }
}
