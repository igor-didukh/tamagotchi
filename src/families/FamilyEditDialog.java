package families;

import superclasses.Entity;
import superclasses.ListPanel;
import families.actions.FamilyActionListPanel;
import families.features.FamilyFeatureListPanel;
import main.Common;
import main.TextFieldFilter;

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
import javax.swing.text.AbstractDocument;

class FamilyEditDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private int id = 0;
	private JTextField txtId, txtName, txtAge;
	private JPanel panelFeatures, panelActions;
	
	public FamilyEditDialog(Entity family) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setSize(713, 20);
		setTitle("Add / edit pet family");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 880, (family == null) ? 180 : 500);
		getContentPane().setLayout(null);
		
		JPanel panelFields = new JPanel();
		panelFields.setBounds(10, 0, 855, 48);
		getContentPane().add(panelFields);
		panelFields.setLayout(null);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(12, 10, 14, 25);
		panelFields.add(lblId);
		
		JLabel lblName = new JLabel("Family name:");
		lblName.setBounds(84, 10, 81, 25);
		panelFields.add(lblName);
		
		JLabel lblAge = new JLabel("Max.age (s):");
		lblAge.setBounds(371, 10, 80, 25);
		panelFields.add(lblAge);
		
		txtId = new JTextField();
		txtId.setBounds(31, 10, 41, 25);
		txtId.setHorizontalAlignment(SwingConstants.CENTER);
		txtId.setEditable(false);
		txtId.setColumns(10);
		panelFields.add(txtId);
		
		txtName = new JTextField();
		txtName.setBounds(172, 10, 187, 25);
		txtName.setColumns(10);
		panelFields.add(txtName);
		
		txtAge = new JTextField();
		txtAge.setHorizontalAlignment(SwingConstants.CENTER);
		txtAge.setColumns(10);
		txtAge.setBounds(445, 10, 81, 25);
		((AbstractDocument) txtAge.getDocument()).setDocumentFilter(new TextFieldFilter());
		panelFields.add(txtAge);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(659, 11, 86, 23);
		btnSave.setActionCommand("SAVE");
		btnSave.addActionListener(this);
		panelFields.add(btnSave);
		
		getRootPane().setDefaultButton(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(757, 11, 86, 23);
		btnCancel.addActionListener(this);
		panelFields.add(btnCancel);
		
		JPanel panelTables = new JPanel();
		panelTables.setBounds(10, 60, 855, 406);
		getContentPane().add(panelTables);
		
		if (family == null) {
			panelTables.setLayout(new FlowLayout());
			JLabel lblWarn = new JLabel("First save to operate with family features and actions!");
			lblWarn.setForeground(Color.BLUE);
			lblWarn.setFont(new Font("Dialog", Font.BOLD, 16));
			panelTables.add(lblWarn);
		} else {
			panelTables.setLayout(new GridLayout(0, 2, 0, 0));
			
			panelFeatures = new JPanel();
			panelFeatures.setLayout(new BorderLayout(0, 0));
			panelTables.add(panelFeatures);
			
			panelActions = new JPanel();
			panelActions.setLayout(new BorderLayout(0, 0));
			panelTables.add(panelActions);
		}
		
		initFields(family);
	}
	
	private void initFields(Entity ent) {
		Family family = (Family) ent;
		
        if (family != null) {
        	id = family.getId();
            txtId.setText("" + id);
            txtName.setText(family.getName());
            txtAge.setText("" + family.getAge());
            
            panelFeatures.add(new FamilyFeatureListPanel(id), BorderLayout.CENTER);
            panelActions.add(new FamilyActionListPanel(id), BorderLayout.CENTER);
        }
    }
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		ListPanel.objFromDialog = null;
		
		if (ae.getActionCommand() == "SAVE")
			ListPanel.objFromDialog =
				new Family(
					id,
					txtName.getText().trim(),
					Common.parseInt(txtAge.getText().trim())
				);
			
		dispose();
	}
}