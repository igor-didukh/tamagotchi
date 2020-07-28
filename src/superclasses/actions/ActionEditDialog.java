package superclasses.actions;

import main.Constants;
import main.PanelLevels;
import main.TextFieldFilter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;

public abstract class ActionEditDialog extends JDialog implements ActionListener, Constants {
	private static final long serialVersionUID = 1L;
	
	protected int id = 0;
	protected JTextField txtName, txtPeriod;
	protected PanelLevels levels;

	protected abstract void doAction(String cmd);
	
	public ActionEditDialog(Action act) {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setSize(715, 20);
		setTitle("Add / edit action");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 390, 226);
		getContentPane().setLayout(null);
		
		JPanel panelFields = new JPanel();
		panelFields.setBounds(0, 0, 386, 162);
		panelFields.setLayout(null);
		getContentPane().add(panelFields);
		
		JLabel lblName = new JLabel("Action:");
		lblName.setBounds(12, 10, 46, 25);
		panelFields.add(lblName);
		
		txtName = new JTextField();
		txtName.setBounds(75, 10, 300, 25);
		txtName.setColumns(10);
		panelFields.add(txtName);
		
		JLabel lblPeriod = new JLabel("Period (s):");
		lblPeriod.setBounds(12, 43, 60, 25);
		panelFields.add(lblPeriod);
		
		levels = new PanelLevels();
		levels.setBounds(12, 79, 362, 79);
		panelFields.add(levels);
		
		txtPeriod = new JTextField();
		txtPeriod.setHorizontalAlignment(SwingConstants.CENTER);
		txtPeriod.setBounds(75, 43, 82, 25);
		txtPeriod.setColumns(10);
		panelFields.add(txtPeriod);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(98, 167, 86, 23);
		btnSave.setActionCommand("SAVE");
		btnSave.addActionListener(this);
		getContentPane().add(btnSave);
		
		getRootPane().setDefaultButton(btnSave);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(199, 167, 86, 23);
		btnCancel.addActionListener(this);
		getContentPane().add(btnCancel);
		
		((AbstractDocument) txtPeriod.getDocument()).setDocumentFilter(new TextFieldFilter());
		
		initFields(act);
	}
	
	private void initFields(Action act) {
		if (act != null) {
			id = act.getId();
            txtName.setText(act.getName());
            txtPeriod.setText("" + act.getPeriod());
            
            for (int i = 1; i <= PARAMS; i++)
				levels.setLevel(i, (int) act.getLevel(i));
		}
    }
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		doAction(ae.getActionCommand());
		dispose();
	}
}