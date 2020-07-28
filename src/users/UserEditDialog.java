package users;

import superclasses.Entity;
import superclasses.ListPanel;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JCheckBox;

public class UserEditDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private int id = 0;
	
	private JTextField txtId;
	private JCheckBox checkAdmin;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JTextField txtLogin;
	private JTextField txtPassword;
	private JTextField txtPhone;
	private JTextField txtEmail;
	
	public UserEditDialog(Entity user) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setSize(713, 20);
		setTitle("Add / edit user");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 325, 295);
		getContentPane().setLayout(null);
		
		JPanel panelFields = new JPanel();
		panelFields.setBounds(10, 0, 299, 218);
		getContentPane().add(panelFields);
		panelFields.setLayout(null);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(12, 12, 14, 16);
		panelFields.add(lblId);
		
		JLabel lblFirstName = new JLabel("First name:");
		lblFirstName.setBounds(12, 40, 72, 25);
		panelFields.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Surname:");
		lblLastName.setBounds(12, 70, 55, 25);
		panelFields.add(lblLastName);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBounds(12, 100, 34, 25);
		panelFields.add(lblLogin);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(12, 130, 61, 25);
		panelFields.add(lblPassword);
		
		JLabel lblPhone = new JLabel("Phone:");
		lblPhone.setBounds(12, 160, 39, 25);
		panelFields.add(lblPhone);
		
		JLabel lblEmail = new JLabel("e-mail:");
		lblEmail.setBounds(12, 190, 38, 25);
		panelFields.add(lblEmail);
		
		txtId = new JTextField();
		txtId.setBounds(85, 12, 55, 20);
		txtId.setHorizontalAlignment(SwingConstants.CENTER);
		txtId.setEditable(false);
		txtId.setColumns(10);
		panelFields.add(txtId);
		
		checkAdmin = new JCheckBox("admin");
		checkAdmin.setBounds(211, 8, 61, 24);
		panelFields.add(checkAdmin);
		
		txtFirstName = new JTextField();
		txtFirstName.setBounds(85, 40, 187, 25);
		txtFirstName.setColumns(10);
		panelFields.add(txtFirstName);
		
		txtLastName = new JTextField();
		txtLastName.setBounds(85, 70, 187, 25);
		txtLastName.setColumns(10);
		panelFields.add(txtLastName);
		
		txtLogin = new JTextField();
		txtLogin.setBounds(85, 100, 87, 25);
		txtLogin.setColumns(10);
		panelFields.add(txtLogin);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(85, 130, 87, 25);
		txtPassword.setColumns(10);
		panelFields.add(txtPassword);
		
		txtPhone = new JTextField();
		txtPhone.setBounds(85, 160, 187, 25);
		txtPhone.setColumns(10);
		panelFields.add(txtPhone);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(85, 190, 187, 25);
		txtEmail.setColumns(10);
		panelFields.add(txtEmail);
		
		JButton btnSave = new JButton("Save");
		btnSave.setActionCommand("SAVE");
		btnSave.setBounds(70, 230, 86, 23);
		btnSave.addActionListener(this);
		getContentPane().add(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(165, 230, 86, 23);
		btnCancel.addActionListener(this);
		getContentPane().add(btnCancel);
		
		getRootPane().setDefaultButton(btnSave);
		
		initFields(user);
	}
	
	private void initFields(Entity ent) {
		User user = (User) ent;
		
        if (user != null) {
            id = user.getId();
            
            txtId.setText("" + id);
            checkAdmin.setSelected(user.getRights()==1);
            
            txtFirstName.setText(user.getName());
            txtLastName.setText(user.getLastName());
            txtLogin.setText(user.getLogin());
            txtPassword.setText(user.getPassword());
            txtEmail.setText(user.getEmail());
            txtPhone.setText(user.getPhone());
            
            if (id == 1) {
    			checkAdmin.setEnabled(false);
    			txtFirstName.setEnabled(false);
    			txtLastName.setEnabled(false);
    		}
        }
    }
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		ListPanel.objFromDialog = null;
		
		if (ae.getActionCommand() == "SAVE")
			ListPanel.objFromDialog =
				new User (
					id, 
					txtFirstName.getText().trim(), 
					txtLastName.getText().trim(), 
					txtLogin.getText().trim(), 
					txtPassword.getText().trim(), 
					txtPhone.getText().trim(), 
					txtEmail.getText().trim(),
					checkAdmin.isSelected() ? 1 : 0
				);
		dispose();
	}
}