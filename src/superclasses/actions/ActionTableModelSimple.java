package superclasses.actions;

import java.util.ArrayList;

import main.Constants;
import superclasses.Entity;
import superclasses.TableModel;

public class ActionTableModelSimple extends TableModel implements Constants {
	private static final long serialVersionUID = 1L;
	private static final String[] headers = {"ID", "Action", "Period (s)"};
    
    public ActionTableModelSimple(ArrayList<Entity> actions) {
    	super(actions, headers);
    }
    
    @Override
    public Object getValueAt(int row, int col) {
    	Action act = (Action) super.getValueAt(row, col);
        switch (col) {
            case 0:
                return act.getId();
            case 1:
            	return act.getName();
            default:
                return act.getPeriod();
        }
    }
}