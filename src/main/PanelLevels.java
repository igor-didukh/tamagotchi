package main;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PanelLevels extends JPanel implements Constants{
	class Label extends JLabel {
		private static final long serialVersionUID = 1L;
		
		Label(String text) {
			super(text);
			setFont(new Font("Tahoma", Font.PLAIN, 11));
			setEnabled(false);
			setHorizontalAlignment(SwingConstants.CENTER);
		}
	}
	
	class SpinLevel extends JSpinner {
		private static final long serialVersionUID = 1L;
		SpinLevel() {
			super.setModel(new SpinnerNumberModel(LEVEL_INIT, LEVEL_DOWN, LEVEL_UP, 1));	
		}
	}
	
	private static final long serialVersionUID = 1L;
	private SpinLevel[] spinLevels;
	
	public PanelLevels() {
		setLayout(null);
		setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), " Levels: ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		
		Label lblHealthLevel = new Label("Health:");
		lblHealthLevel.setBounds(22, 15, 45, 16);
		add(lblHealthLevel);
		
		Label lblFoodLevel = new Label("Food:");
		lblFoodLevel.setBounds(89, 15, 45, 16);
		add(lblFoodLevel);
		
		Label lblWakeLevel = new Label("Wake:");
		lblWakeLevel.setBounds(156, 15, 45, 16);
		add(lblWakeLevel);
		
		Label lblNeatLevel = new Label("Neat:");
		lblNeatLevel.setBounds(223, 15, 45, 16);
		add(lblNeatLevel);
		
		Label lblJoyLevel = new Label("Joy:");
		lblJoyLevel.setBounds(290, 15, 45, 16);
		add(lblJoyLevel);
		
		spinLevels = new SpinLevel[PARAMS+1];
		
		spinLevels[HEALTH] = new SpinLevel();
		spinLevels[HEALTH].setBounds(22, 33, 45, 25);
		add(spinLevels[HEALTH]);
		
		spinLevels[FOOD] = new SpinLevel();
		spinLevels[FOOD].setBounds(89, 33, 45, 25);
		add(spinLevels[FOOD]);
		
		spinLevels[WAKE] = new SpinLevel();
		spinLevels[WAKE].setBounds(156, 33, 45, 25);
		add(spinLevels[WAKE]);
		
		spinLevels[NEAT] = new SpinLevel();
		spinLevels[NEAT].setBounds(223, 33, 45, 25);
		add(spinLevels[NEAT]);
		
		spinLevels[JOY] = new SpinLevel();
		spinLevels[JOY].setBounds(290, 33, 45, 25);
		add(spinLevels[JOY]);
	}
	
	public void setLevel(int level, int value) {
		spinLevels[level].setValue((int) value);
	}
	
	public int getLevel(int level) {
		return (int) spinLevels[level].getValue();
	}
}