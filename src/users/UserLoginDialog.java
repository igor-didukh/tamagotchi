package users;

import javax.swing.JDialog;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import main.Common;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;

public class UserLoginDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
    private JPanel contentPane;
	
	private KeyListener exitOnEsc() {
		return new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		        	System.exit(0);
			}
		};
	}

	public UserLoginDialog() {
		setModal(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setResizable(false);
		setTitle("Login");
		setBounds(100, 100, 242, 157);
		getContentPane().setLayout(null);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setForeground(Color.BLUE);
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLogin.setBounds(12, 23, 46, 14);
		contentPane.add(lblLogin);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPassword.setForeground(Color.BLUE);
		lblPassword.setBounds(12, 52, 73, 14);
		contentPane.add(lblPassword);
		
		JTextField editLogin = new JTextField();
		editLogin.addKeyListener(exitOnEsc());
		editLogin.setBounds(95, 21, 122, 20);
		contentPane.add(editLogin);
		editLogin.setText("user");
				
		JPasswordField editPassword = new JPasswordField();
		editPassword.addKeyListener(exitOnEsc());
		editPassword.setBounds(95, 50, 122, 20);
		contentPane.add(editPassword);
		editPassword.setText("123");
		
		JButton btnLogin = new JButton("OK");
		btnLogin.addKeyListener(exitOnEsc());

		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String login = editLogin.getText();
				String password = new String(editPassword.getPassword());
				
				User user = new UserDataManager().getUserByLogin(login, password);
				if (user == null) 
					Common.showErrorMessage(contentPane, "Invalid user name or password!");
				else {
					Common.setRegisteredUser(user);
	                dispose();
				}
			}
		});
		
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnLogin.setBounds(22, 90, 89, 23);
		contentPane.add(btnLogin);
		
		getRootPane().setDefaultButton(btnLogin);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addKeyListener(exitOnEsc());
		
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnCancel.setBounds(121, 90, 89, 23);
		contentPane.add(btnCancel);
	}
}
