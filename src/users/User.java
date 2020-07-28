package users;

import superclasses.Entity;

public class User extends Entity {
    private String lastName;
    private String login;
    private String password;
    private String phone;
    private String email;
    private int rights;
    
    public User(int id, String firstName, String lastName, String login, String password, String phone, String email, int rights) {
    	super(id, firstName);
    	
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.rights = rights;
    }
 
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getFullName() {
        return super.getName() + " " + lastName;
    }
    
    public String getLogin() {
        return login;
    }
 
    public void setLogin(String login) {
        this.login = login;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getPhone() {
        return phone;
    }
 
    public void setPhone(String phone) {
        this.phone = phone;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getRights() {
        return rights;
    }
 
    public void setRights(int rights) {
        this.rights = rights;
    }
 
    @Override
    public String toString() {
        return getFullName();
    }
}