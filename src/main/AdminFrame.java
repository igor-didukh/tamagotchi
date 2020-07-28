package main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;

import families.FamilyListPanel;
import main.Common;
import pets.PetListPanel;
import users.UserListPanel;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("unused")
class AdminFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private static final String USERS = "USERS";
	private static final String FAMILIES = "FAMILIES";
	private static final String PETS = "PETS";
	private static final String PLAY = "PLAY";
	
	private JPanel contentPane;
	
	private void closeFrame(java.awt.AWTEvent evt) {
		//if (Common.showConfirmDialog(this, "You really want to exit?", "Exit") == JOptionPane.YES_OPTION) 
			System.exit(0);
    }

	public AdminFrame() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				closeFrame(arg0);
			}
		});
		
		setTitle("Admin: " + Common.getRegisteredUser());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 540, 200);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeFrame(e);
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnCatalog = new JMenu("Catalog");
		menuBar.add(mnCatalog);
		
		JMenuItem mntmUsers = new JMenuItem("Users");
		mntmUsers.setActionCommand(USERS);
		mntmUsers.addActionListener(this);
		mnCatalog.add(mntmUsers);
		
		JMenuItem mntmFamilies = new JMenuItem("Types");
		mntmFamilies.setActionCommand(FAMILIES);
		mntmFamilies.addActionListener(this);
		mnCatalog.add(mntmFamilies);
		
		JMenuItem mntmPets = new JMenuItem("Pets");
		mntmPets.setActionCommand(PETS);
		mntmPets.addActionListener(this);
		mnCatalog.add(mntmPets);
		
		JMenuItem mntmUserForm = new JMenuItem("UserForm");
		mntmUserForm.setActionCommand(PLAY);
		mntmUserForm.addActionListener(this);
		menuBar.add(mntmUserForm);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panelWork = new JPanel();
		panelWork.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), " Work with catalog of: ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelWork.setLayout(null);
		contentPane.add(panelWork);
		
		JButton btnUsers = new JButton("Users");
		btnUsers.setActionCommand(USERS);
		btnUsers.addActionListener(this);
		btnUsers.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnUsers.setBounds(12, 20, 160, 50);
		panelWork.add(btnUsers);
		
		JButton btnFamilies = new JButton("Families");
		btnFamilies.setActionCommand(FAMILIES);
		btnFamilies.addActionListener(this);
		btnFamilies.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnFamilies.setBounds(180, 20, 160, 50);
		panelWork.add(btnFamilies);
		
		JButton btnPets = new JButton("Pets");
		btnPets.setActionCommand(PETS);
		btnPets.addActionListener(this);
		btnPets.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnPets.setBounds(350, 20, 160, 50);
		panelWork.add(btnPets);
		
		getRootPane().setDefaultButton(btnPets);
		
		JPanel panelPlay = new JPanel();
		panelPlay.setBorder(new TitledBorder(null, " or: ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelPlay.setLayout(new BorderLayout(0, 0));
		contentPane.add(panelPlay, BorderLayout.SOUTH);
		
		JButton btnUserForm = new JButton("Open user form");
		btnUserForm.setActionCommand("PLAY");
		btnUserForm.setFont(new Font("Dialog", Font.PLAIN, 16));
		btnUserForm.addActionListener(this);
		panelPlay.add(btnUserForm, BorderLayout.NORTH);
		
		if (Common.getRegisteredUser().getId() == 1) {
			mntmUserForm.setVisible(false);
			panelPlay.setVisible(false);
			setBounds(100, 100, 540, 140);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case USERS:
			Common.makeFrame(new UserListPanel());
			break;
		case FAMILIES:
			Common.makeFrame(new FamilyListPanel());
			break;
		case PETS:
			Common.makeFrame(new PetListPanel());
			break;
		case PLAY:
			Common.showFrame(new UserFrame(true));
			break;
		default:
			break;
		}
	}
}
