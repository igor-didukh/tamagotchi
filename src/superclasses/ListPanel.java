package superclasses;

import main.Common;
import main.Constants;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public abstract class ListPanel extends JPanel implements ActionListener, Constants {
	private static final long serialVersionUID = 1L;
	
	private int selectedRow = 0;
	private ArrayList<Entity> entities;
    
	private static final String ADD = "ADD";
    private static final String EDIT = "EDIT";
    private static final String DELETE = "DELETE";
    
    private JButton btnAdd, btnEdit, btnDelete; 
    
	public static Entity objFromDialog;
	
	protected String listTitle;
	protected final JTable objTable = new JTable();
	protected DataManager objDataManager;

	protected abstract TableModel getTableModel(ArrayList<Entity> objects);
    protected abstract JDialog getEditDialog(Entity obj);
    
    public ListPanel(String title, int width) {
    	this(title);
    	setBounds(100, 100, width, 400);
    }
	
	public ListPanel(String title) {
		setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), " " + title + ": ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panelButtons = new JPanel();
		panelButtons.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		add(panelButtons, BorderLayout.NORTH);
		GridBagLayout gbl_panelButtons = new GridBagLayout();
		gbl_panelButtons.columnWidths = new int[] {80, 80, 80, 0};
		gbl_panelButtons.rowWeights = new double[] {0.0};
		gbl_panelButtons.columnWeights = new double[] {0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelButtons.setLayout(gbl_panelButtons);
		
		btnAdd = new JButton("Add");
		btnAdd.setActionCommand(ADD);
		btnAdd.addActionListener(this);
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.insets = new Insets(6, 6, 6, 0);
		gbc_btnAdd.anchor = GridBagConstraints.WEST;
		gbc_btnAdd.gridheight = GridBagConstraints.REMAINDER;
		gbc_btnAdd.fill = GridBagConstraints.BOTH;
		panelButtons.add(btnAdd, gbc_btnAdd);
		
		btnEdit = new JButton("Edit");
		btnEdit.setActionCommand(EDIT);
		btnEdit.addActionListener(this);
		GridBagConstraints gbc_btnEdit = new GridBagConstraints();
		gbc_btnEdit.insets = new Insets(6, 6, 6, 0);
		gbc_btnEdit.anchor = GridBagConstraints.NORTH;
		gbc_btnEdit.gridheight = GridBagConstraints.REMAINDER;
		gbc_btnEdit.fill = GridBagConstraints.BOTH;
		panelButtons.add(btnEdit, gbc_btnEdit);
		
		btnDelete = new JButton("Delete");
		btnDelete.setActionCommand(DELETE);
		btnDelete.addActionListener(this);
		GridBagConstraints gbc_btnDelete = new GridBagConstraints();
		gbc_btnDelete.insets = new Insets(6, 6, 6, 0);
		gbc_btnDelete.anchor = GridBagConstraints.NORTH;
		gbc_btnDelete.gridheight = GridBagConstraints.REMAINDER;
		gbc_btnDelete.fill = GridBagConstraints.BOTH;
		panelButtons.add(btnDelete, gbc_btnDelete);
		
		objTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					editObject();
			}
		});
		
		objTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2)
		            editObject();
			}
		});
		
		JScrollPane panelTable = new JScrollPane(objTable); 
		add(panelTable, BorderLayout.CENTER);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch (action) {
        case ADD:
            addObject();
            break;
        case EDIT:
            editObject();
            break;
        case DELETE:
            deleteObject();
            break;
		}
	}
	
	public void setButtonsVisiblity(boolean btnAddVisible, boolean btnEditVisible, boolean btnDeleteVisible) {
		btnAdd.setVisible(btnAddVisible);
		btnEdit.setVisible(btnEditVisible);
		btnDelete.setVisible(btnDeleteVisible);
	}
	
	private Entity getEntity(int id) {
		Entity res = null;
    	for (Entity ent : entities)
			if (ent.getId() == id) {
				res = ent;
				break;
			}
    	return res;
	}
	
    protected void decorateTableColumn(JTable table, int colummnIndex, int width, int alignment) {
		TableColumn column = table.getColumnModel().getColumn(colummnIndex);
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(alignment);
		column.setCellRenderer(renderer);
		if (width != 0)
			column.setMaxWidth(width);
	}
    
    protected void decorateTable() {
    	decorateTableColumn(objTable, 0, 30, SwingConstants.CENTER);
    }
	
	protected void loadObjects() {
		entities = objDataManager.getEntityList();
		TableModel ltm = getTableModel(entities);
		
        objTable.setModel(ltm);
        decorateTable();
        
        if (entities.size() != 0)
        	objTable.setRowSelectionInterval(selectedRow, selectedRow);
	}
	
    private void saveObject() {
    	if (objFromDialog != null) {
            if (objFromDialog.getId() != 0) {
                objDataManager.updateEntity(objFromDialog);
            } else {
                objDataManager.addEntity(objFromDialog);
            }
            loadObjects();
            objFromDialog = null;
        }
    }
	
	private void addObject() {
		JDialog dialog = getEditDialog(null);
		if (dialog != null) {
			Common.showFrame(dialog);
			saveObject();
		}
	}
	
	private void editObject() {
		if (!btnEdit.isVisible()) return;
		
		selectedRow = objTable.getSelectedRow();
        if (selectedRow != -1) {
        	int id = (int) objTable.getModel().getValueAt(selectedRow,0);
        	
        	Entity selEnt = getEntity(id);
        	if (selEnt != null) {
        		JDialog dialog = getEditDialog(selEnt);
        		Common.showFrame(dialog);
                saveObject();
        	}
            
        } else {
        	Common.showErrorMessage(this, "No row is selected!");
        }
	}
	
	protected boolean extraDeleteCheck(int id) {
    	return true;
    }
	
	private void deleteObject() {
        selectedRow = objTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) objTable.getModel().getValueAt(selectedRow,0);
            
            if ( !extraDeleteCheck(id) ) return;
            
            Entity obj = getEntity(id);
            
    		if (Common.showConfirmDialog(this, "You really want to delete record: \n" + obj + "?", "Delete record") != YES) 
    			return;
            
            objDataManager.deleteEntity(id);
            selectedRow = 0;
            loadObjects();
        } else {
        	Common.showErrorMessage(this, "No row is selected!");
        }
	}
	
	public Entity getSelectedEntity() {
        selectedRow = objTable.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) objTable.getModel().getValueAt(selectedRow,0);
            return getEntity(id);
        } else {
        	return null;
        }
	}
	
	public ArrayList<Entity> getList() {
        return entities;
	}
}