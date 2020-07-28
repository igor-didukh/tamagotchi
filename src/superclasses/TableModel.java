package superclasses;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public abstract class TableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	
	private String[] headers;
	private ArrayList<Entity> list;
    
    public TableModel(ArrayList<Entity> list, String[] headers) {
        this.list = list;
        this.headers = headers;
    }
    
    public ArrayList<Entity> getList() {
        return list;
    }
    
    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public String getColumnName(int col) {
        return headers[col];
    }
    
    @Override
    public Object getValueAt(int row, int col) {
        return list.get(row);
    }
}