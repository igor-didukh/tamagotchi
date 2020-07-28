package pets;

import superclasses.DataManager;
import superclasses.Entity;
import superclasses.ListPanel;
import users.User;
import users.UserDataManager;
import families.Family;
import families.FamilyDataManager;
import main.Common;
import main.Constants;
import main.PanelLevels;
import main.TextFieldFilter;
import pets.actions.PetActionListPanel;
import pets.features.PetFeatureListPanel;

import java.util.List;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.ImageIcon;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;

class PetEditDialog extends JDialog implements ActionListener, Constants {
	
	class Label extends JLabel {
		private static final long serialVersionUID = 1L;
		
		Label(String text) {
			super(text);
			setFont(new Font("Tahoma", Font.PLAIN, 11));
			setEnabled(false);
			setHorizontalAlignment(SwingConstants.CENTER);
		}
	}
	
	class SpinWeight extends JSpinner implements Constants {
		private static final long serialVersionUID = 1L;
		SpinWeight(int x) {
			super.setModel(new SpinnerNumberModel(WEIGHT_INIT, WEIGHT_DOWN, WEIGHT_UP, 1));
			setBounds(x, 33, 45, 25);
		}
	}

	private static final long serialVersionUID = 1L;
	
	private final String LOAD_IMAGE = "LOAD_IMAGE", SAVE = "SAVE", CANCEL = "CANCEL", RESET = "RESET";
	
	private Pet pet;
	private int id = 0;
	
	private JTextField txtId, txtName, txtImage, txtAge, txtMaxAge;
	private JLabel lblImage;
	private JComboBox<Entity> comboFamily, comboUser;
	private JPanel panelFeatures, panelActions;
	private JSpinner spinEnergy;
	private SpinWeight[] weights = new SpinWeight[PARAMS+1];
	PanelLevels levels;
	
	public PetEditDialog(Entity pet) {
		this.pet = (Pet) pet;
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setSize(713, 20);
		setTitle("Add / edit pet");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 880, (pet == null) ? 280 : 650);
		getContentPane().setLayout(null);
		
		JPanel panelFields = new JPanel();
		panelFields.setBounds(10, 0, 855, 198);
		panelFields.setLayout(null);
		getContentPane().add(panelFields);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(12, 10, 14, 25);
		panelFields.add(lblId);
		
		txtId = new JTextField();
		txtId.setBounds(69, 10, 39, 25);
		txtId.setHorizontalAlignment(SwingConstants.CENTER);
		txtId.setEditable(false);
		txtId.setColumns(10);
		panelFields.add(txtId);
		
		JLabel lblAge = new JLabel("Age (s):");
		lblAge.setBounds(118, 10, 45, 25);
		panelFields.add(lblAge);
		
		txtAge = new JTextField();
		txtAge.setBounds(166, 10, 65, 25);
		txtAge.setHorizontalAlignment(SwingConstants.CENTER);
		((AbstractDocument) txtAge.getDocument()).setDocumentFilter(new TextFieldFilter());
		txtAge.setColumns(10);
		panelFields.add(txtAge);
		
		JLabel lblMaxAge = new JLabel("of:");
		lblMaxAge.setBounds(235, 10, 31, 25);
		panelFields.add(lblMaxAge);
		
		txtMaxAge = new JTextField();
		txtMaxAge.setBounds(256, 10, 65, 25);
		txtMaxAge.setHorizontalAlignment(SwingConstants.CENTER);
		txtMaxAge.setEditable(false);
		txtMaxAge.setColumns(10);
		panelFields.add(txtMaxAge);
		
		JLabel lblName = new JLabel("Nick:");
		lblName.setBounds(12, 40, 39, 25);
		panelFields.add(lblName);
		
		txtName = new JTextField();
		txtName.setBounds(69, 40, 254, 25);
		txtName.setColumns(10);
		panelFields.add(txtName);
		
		JLabel lblFamily = new JLabel("Family:");
		lblFamily.setBounds(12, 70, 47, 25);
		panelFields.add(lblFamily);
		
		comboFamily = new JComboBox<Entity>(); 
		comboFamily.setBounds(69, 70, 254, 25);
		comboFamily.setEnabled(pet == null);
		panelFields.add(comboFamily);
		
		JLabel lblOwner = new JLabel("Owner:");
		lblOwner.setBounds(12, 100, 47, 25);
		panelFields.add(lblOwner);
		
		comboUser = new JComboBox<Entity>();
		comboUser.setBounds(69, 100, 254, 25);
		panelFields.add(comboUser);
		
		JPanel panelImage = new JPanel();
		panelImage.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelImage.setBounds(335, 9, 150, 150);
		panelFields.add(panelImage);
		panelImage.setLayout(new BorderLayout(0, 0));
		
		lblImage = new JLabel("");
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		panelImage.add(lblImage, BorderLayout.CENTER);
		
		JButton btnSelectImage = new JButton("Select image...");
		btnSelectImage.setActionCommand(LOAD_IMAGE);
		btnSelectImage.addActionListener(this);
		btnSelectImage.setBounds(352, 164, 122, 26);
		panelFields.add(btnSelectImage);
		
		JLabel lblEnergy = new JLabel("Energy:");
		lblEnergy.setBounds(12, 130, 47, 25);
		panelFields.add(lblEnergy);
		
		spinEnergy = new JSpinner();
		spinEnergy.setModel(new SpinnerNumberModel(100, 0, 100, 1));
		spinEnergy.setBounds(69, 130, 45, 25);
		panelFields.add(spinEnergy);
		
		txtImage = new JTextField();
		txtImage.setVisible(false);
		txtImage.setEnabled(false);
		txtImage.setEditable(false);
		txtImage.setBounds(89, 167, 236, 23);
		txtImage.setColumns(10);
		panelFields.add(txtImage);
		
		JPanel panelWeights = new JPanel();
		panelWeights.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), " Weights: ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		panelWeights.setBounds(487, 2, 362, 79);
		panelFields.add(panelWeights);
		panelWeights.setLayout(null);
		
		Label lblHealthWeight = new Label("Health:");
		lblHealthWeight.setBounds(22, 15, 45, 16);
		panelWeights.add(lblHealthWeight);
		
		Label lblFoodWeight = new Label("Food:");
		lblFoodWeight.setBounds(89, 15, 45, 16);
		panelWeights.add(lblFoodWeight);
		
		Label lblWakeWeight = new Label("Wake:");
		lblWakeWeight.setBounds(156, 15, 45, 16);
		panelWeights.add(lblWakeWeight);
		
		Label lblNeatWeight = new Label("Neat:");
		lblNeatWeight.setBounds(223, 15, 45, 16);
		panelWeights.add(lblNeatWeight);
		
		Label lblJoyWeight = new Label("Joy:");
		lblJoyWeight.setBounds(290, 15, 45, 16);
		panelWeights.add(lblJoyWeight);
		
		weights[HEALTH] = new SpinWeight(22);
		weights[FOOD] = new SpinWeight(89);
		weights[WAKE] = new SpinWeight(156);
		weights[NEAT] = new SpinWeight(223);
		weights[JOY] = new SpinWeight(290);
		
		for (int i = 1; i <= PARAMS; i++)
			panelWeights.add(weights[i]);
		
		levels = new PanelLevels();
		levels.setBounds(487, 82, 362, 79);
		panelFields.add(levels);
		
		JPanel panelTables = new JPanel();
		panelTables.setBounds(10, 197, 855, 406);
		getContentPane().add(panelTables);
		
		JButton btnSave = new JButton("Save");
		btnSave.setActionCommand(SAVE);
		btnSave.addActionListener(this);
		btnSave.setBounds(79, 164, 86, 26);
		panelFields.add(btnSave);
		
		getRootPane().setDefaultButton(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setActionCommand(CANCEL);
		btnCancel.addActionListener(this);
		btnCancel.setBounds(169, 164, 86, 26);
		panelFields.add(btnCancel);
		
		JButton btnReset = new JButton("Reset...");
		btnReset.setActionCommand(RESET);
		btnReset.addActionListener(this);
		btnReset.setBounds(506, 164, 86, 26);
		panelFields.add(btnReset);
		
		if (pet == null) {
			panelTables.setLayout(new FlowLayout());
			JLabel lblWarn = new JLabel("First save to operate with pet features and actions!");
			lblWarn.setForeground(Color.BLUE);
			lblWarn.setFont(new Font("Dialog", Font.BOLD, 16));
			panelTables.add(lblWarn);
		} 
		else {
			panelTables.setLayout(new GridLayout(0, 2, 0, 0));
			
			panelFeatures = new JPanel();
			panelFeatures.setLayout(new BorderLayout(0, 0));
			panelTables.add(panelFeatures);
			
			panelActions = new JPanel();
			panelActions.setLayout(new BorderLayout(0, 0));
			panelTables.add(panelActions);
		}
		
		initFields();
	}
	
	
	
	private void setImage(String path) {
		try {
			lblImage.setIcon( new ImageIcon(PetEditDialog.class.getResource(path)) );
		} catch (Exception e) {
			lblImage.setIcon(null);
			lblImage.setText("No image");
		}
	}
	
	private void setComboItems(JComboBox<Entity> combo, DataManager dataManager, int parentId) {
		List<Entity> entities = dataManager.getEntityList();
		
		int index = 0;
		for (int i = 0; i < entities.size(); i++) {
			Entity ent = entities.get(i);
			if (ent.getId() == parentId)
				index = i;
			combo.addItem(ent);	
		}
		
		combo.setSelectedIndex(index);
	}
	
	private void initFields() {
		int userId = 0, familyId = 0;
		
        if (pet != null) {
        	id = pet.getId();
            txtId.setText("" + id);
            txtName.setText(pet.getName());
            txtAge.setText("" + pet.getAge());
            txtMaxAge.setText("" + pet.getFamilyAge());
            txtImage.setText(pet.getImageResource());
            setImage(pet.getImageResource());
            
            userId = pet.getUserId(); 
            familyId = pet.getFamilyId();
            
            spinEnergy.setValue((int) pet.getEnergy());
            
            for (int i = 1; i <= PARAMS; i++) {
            	weights[i].setValue((int) pet.getWeight(i));
            	levels.setLevel(i, (int) pet.getLevel(i));
            }
            
            panelFeatures.add(new PetFeatureListPanel(pet), BorderLayout.CENTER);
            panelActions.add(new PetActionListPanel(pet), BorderLayout.CENTER);
        }
        
        setComboItems(comboUser, new UserDataManager(), userId);
        setComboItems(comboFamily, new FamilyDataManager(), familyId);
    }
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		ListPanel.objFromDialog = null;
		
		switch (ae.getActionCommand()) {
		case LOAD_IMAGE:
			String imgName = Common.selectImage(this);
			txtImage.setText(imgName);
			setImage(imgName);
			break;
		case SAVE: 
			Family petFamily = (Family) comboFamily.getSelectedItem();
			User petUser = (User) comboUser.getSelectedItem();
			
			ListPanel.objFromDialog =
				new Pet(
					id,
					txtName.getText().trim(), 
					petFamily.getId(), 
					petFamily.getName(),
					petFamily.getAge(),
					petUser.getId(), 
					petUser.getName(), 
					txtImage.getText(), 
					
					(int) weights[HEALTH].getValue(),
					(int) weights[FOOD].getValue(),
					(int) weights[WAKE].getValue(),
					(int) weights[NEAT].getValue(),
					(int) weights[JOY].getValue(),
					
					levels.getLevel(HEALTH),
		            levels.getLevel(FOOD),
		            levels.getLevel(WAKE),
		            levels.getLevel(NEAT),
		            levels.getLevel(JOY),
					
					Common.parseInt(txtAge.getText().trim()),
					(int) spinEnergy.getValue()
				);
			dispose();
			break;
		case CANCEL:
			dispose();
			break;
		case RESET:
			if (Common.showConfirmDialog(this, "Reset pet data?", "Reset") == YES) {
				txtAge.setText("0");
				spinEnergy.setValue(100);
				for (int i = 1; i <= PARAMS; i++) {
					weights[i].setValue(10);
					levels.setLevel(i, 0);
				}
			}
			break;
		default:
			break;
		}
	}
}