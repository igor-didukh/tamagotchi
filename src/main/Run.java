package main;

import java.awt.EventQueue;

import users.User;
import users.UserDataManager;

public class Run {
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				///*
				// ====== Temporary admin logon ======
				User user = new UserDataManager().getUserByLogin("user", "123");
				if (user != null)
					Common.setRegisteredUser(user);
				
				//Common.showFrame(new UserFrame(true));
				Common.showFrame(new AdminFrame());
				//Common.makeFrame(new pets.PetListPanel());
				// ===================================
				//*/
				
				/*
				Common.showFrame(new users.UserLoginDialog());
				if (Common.getRegisteredUser().getRights() == 1)
		        	Common.showFrame(new AdminFrame());
				else
					Common.showFrame(new UserFrame(false));
				*/
			}
		});
	}
}
