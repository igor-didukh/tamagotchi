package superclasses.features;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class FeatureEditDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	protected int id = 0;
	protected JTextField txtName, txtValue;
	
	protected abstract void doAction(String cmd);
	
	public FeatureEditDialog(Feature feature) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setSize(715, 20);
		setTitle("Add / edit feature");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 328, 138);
		getContentPane().setLayout(null);
		
		JPanel panelFields = new JPanel();
		panelFields.setBounds(0, 0, 311, 77);
		getContentPane().add(panelFields);
		panelFields.setLayout(null);
		
		JLabel lblName = new JLabel("Feature:");
		lblName.setBounds(12, 12, 46, 25);
		panelFields.add(lblName);
		
		JLabel lblValue = new JLabel("Value:");
		lblValue.setBounds(12, 42, 46, 25);
		panelFields.add(lblValue);
		
		txtName = new JTextField();
		txtName.setBounds(67, 12, 234, 25);
		panelFields.add(txtName);
		txtName.setColumns(10);
		
		txtValue = new JTextField();
		txtValue.setColumns(10);
		txtValue.setBounds(67, 42, 234, 25);
		panelFields.add(txtValue);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(64, 77, 86, 23);
		getContentPane().add(btnSave);
		btnSave.setActionCommand("SAVE");
		btnSave.addActionListener(this);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(155, 77, 86, 23);
		getContentPane().add(btnCancel);
		btnCancel.addActionListener(this);
		
		getRootPane().setDefaultButton(btnSave);
		
		initFields(feature);
	}
	
	private void initFields(Feature feat) {
        if (feat != null) {
        	id = feat.getId();
            txtName.setText(feat.getName());
            txtValue.setText(feat.getFeatureData());
        }
    }
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		doAction(ae.getActionCommand());
		dispose();
	}
}