package main;

import main.Common;
import superclasses.Entity;
import pets.Pet;
import pets.PetDataManager;
import pets.actions.PetAction;
import pets.actions.PetActionListPanel;
import pets.features.PetFeatureListPanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

class UserFrame extends JFrame implements ActionListener, Constants {
	private static final long serialVersionUID = 1L;
	
	private final String EXIT = "EXIT", LOAD = "LOAD", DO = "DO", PREV = "PREV", NEXT = "NEXT", PLAY = "PLAY", SELECT = "SELECT";
	private final String MODE_VIEW = "...View mode...", MODE_SELECT = "...Select mode...", MODE_PLAY = "!!! PLAY MODE !!!";
	private final boolean fromAdmin;
	
	private ArrayList<Entity> petList;
	private int petIndex;
	private Pet pet;
	private boolean playMode = false;

	private JButton btnPrevious, btnNext, btnSelect, btnAction;
	private JPanel contentPane, panelMain, panelName, panelFeatures, panelActions;
	private JLabel lblName, lblMedal, lblImage, lblStatus;
	private JMenu mnFile, mnMode;
	private JMenuBar menuBar;
	private JMenuItem mntmPlay;
	private JRadioButtonMenuItem mitemShowOwn, mitemShowAll;
	private JTextField txtAge, txtFamilyAge;
	private SliderEnergy sliderEnergy;
	private PanelWeight[] weights = new PanelWeight[PARAMS + 1];
	private SliderLevel[] levels = new SliderLevel[PARAMS + 1];
	
	private PetActionListPanel petActions;
	private HashMap<Integer, Long> actions = new HashMap<Integer, Long>();
	
	private Timer timer;
	
	public UserFrame(boolean fromAdmin) {
		this.fromAdmin = fromAdmin;
		
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent evt) {
				closeFrame(evt);
			}
		});
		
		setTitle("" + Common.getRegisteredUser());
		
		if (fromAdmin)
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		else 
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		setBounds(100, 100, 730, 720);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(238, 238, 238));
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panelStatus = new JPanel();
		panelStatus.setLayout(new BorderLayout(0, 0));
		panelStatus.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		
		lblStatus = new JLabel(MODE_VIEW);
		lblStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelStatus.add(lblStatus, BorderLayout.CENTER);
		
		contentPane.add(panelStatus, BorderLayout.SOUTH);
		
		menuBar = new JMenuBar();
		contentPane.add(menuBar, BorderLayout.NORTH);
		ButtonGroup btnGroupMode = new ButtonGroup();
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.setActionCommand(EXIT);
		mntmExit.addActionListener(this);
		mnFile.add(mntmExit);
		
		mnMode = new JMenu("Mode");
		menuBar.add(mnMode);
		
		mitemShowOwn = new JRadioButtonMenuItem("Show only own pets");
		mitemShowOwn.setActionCommand(LOAD);
		mitemShowOwn.addActionListener(this);
		mitemShowOwn.setSelected(true);
		btnGroupMode.add(mitemShowOwn);
		mnMode.add(mitemShowOwn);
		
		mitemShowAll = new JRadioButtonMenuItem("Show all available pets");
		mitemShowAll.setActionCommand(LOAD);
		mitemShowAll.addActionListener(this);
		btnGroupMode.add(mitemShowAll);
		mnMode.add(mitemShowAll);
		
		mntmPlay = new JMenuItem("Play");
		mntmPlay.setActionCommand(PLAY);
		mntmPlay.addActionListener(this);
		menuBar.add(mntmPlay);
		
		panelMain = new JPanel();
		panelMain.setAutoscrolls(true);
		contentPane.add(panelMain);
		panelMain.setLayout(null);
		
		btnPrevious = new JButton("<<");
		btnPrevious.setActionCommand(PREV);
		btnPrevious.setBounds(5, 105, 50, 50);
		btnPrevious.setEnabled(false);
		btnPrevious.addActionListener(this);
		panelMain.add(btnPrevious);
		
		btnNext = new JButton(">>");
		btnNext.setActionCommand(NEXT);
		btnNext.setBounds(667, 105, 50, 50);
		btnNext.addActionListener(this);
		panelMain.add(btnNext);
		
		panelName = new JPanel();
		panelName.setBounds(59, 5, 603, 50);
		panelName.setLayout(new BorderLayout(0, 0));
		panelMain.add(panelName);
		
		JPanel panelName0 = new JPanel();
		panelName0.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		lblName = new JLabel("PetName");
		lblName.setVerticalAlignment(SwingConstants.CENTER);
		lblName.setForeground(new Color(0, 0, 128));
		lblName.setFont(new Font("Comic Sans MS", Font.PLAIN, 28));
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		panelName0.add(lblName);
		
		btnSelect = new JButton("Select...");
		btnSelect.setActionCommand(SELECT);
		btnSelect.addActionListener(this);
		btnSelect.setVisible(false);
		panelName0.add(btnSelect);
		
		panelName.add(panelName0, BorderLayout.CENTER);
		
		JPanel panelImage = new JPanel();
		panelImage.setBounds(59, 57, 150, 150);
		panelMain.add(panelImage);
		panelImage.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelImage.setLayout(new BorderLayout(0, 0));
		
		lblImage = new JLabel("");
		lblImage.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		panelImage.add(lblImage, BorderLayout.CENTER);
		
		JPanel panelCharacter = new JPanel();
		panelCharacter.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelCharacter.setBounds(59, 209, 150, 150);
		panelMain.add(panelCharacter);
		panelCharacter.setLayout(null);
		
		LabelWeight lblHealth = new LabelWeight("Health:");
		lblHealth.setBounds(12, 10, 36, 17);
		panelCharacter.add(lblHealth);
		weights[HEALTH] = new PanelWeight(58, 10);
		
		LabelWeight lblFood = new LabelWeight("Food:");
		lblFood.setBounds(12, 37, 36, 17);
		panelCharacter.add(lblFood);
		weights[FOOD] = new PanelWeight(58, 37);
		
		LabelWeight lblWake = new LabelWeight("Wake:");
		lblWake.setBounds(12, 64, 36, 17);
		panelCharacter.add(lblWake);
		weights[WAKE] = new PanelWeight(58, 64);
		
		LabelWeight lblNeat = new LabelWeight("Neat:");
		lblNeat.setBounds(12, 91, 36, 17);
		panelCharacter.add(lblNeat);
		weights[NEAT] = new PanelWeight(58, 91);
		
		LabelWeight lblJoy = new LabelWeight("Joy:");
		lblJoy.setBounds(12, 118, 36, 17);
		panelCharacter.add(lblJoy);
		weights[JOY] = new PanelWeight(58, 118);
		
		panelFeatures = new JPanel();
		panelFeatures.setAutoscrolls(true);
		panelFeatures.setBounds(213, 57, 449, 150);
		panelFeatures.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelFeatures.setLayout(new BorderLayout(0, 0));
		panelMain.add(panelFeatures);
		
		panelActions = new JPanel();
		panelActions.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelActions.setAutoscrolls(true);
		panelActions.setBounds(213, 209, 449, 150);
		panelActions.setLayout(new BorderLayout(0, 0));
		panelMain.add(panelActions);
		
		btnAction = new JButton("do");
		btnAction.setActionCommand(DO);
		btnAction.addActionListener(this);
		btnAction.setForeground(Color.BLUE);
		btnAction.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAction.setBounds(667, 272, 50, 28);
		btnAction.setEnabled(false);
		panelMain.add(btnAction);
		
		JLabel lblAge = new JLabel("Age (s)");
		lblAge.setForeground(Color.BLUE);
		lblAge.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAge.setHorizontalAlignment(SwingConstants.CENTER);
		lblAge.setBounds(60, 363, 65, 17);
		panelMain.add(lblAge);
		
		txtAge = new JTextField();
		txtAge.setBounds(60, 382, 65, 25);
		txtAge.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtAge.setHorizontalAlignment(SwingConstants.CENTER);
		txtAge.setEditable(false);
		txtAge.setColumns(10);
		panelMain.add(txtAge);
		
		JLabel lblFamilyAge = new JLabel("of");
		lblFamilyAge.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblFamilyAge.setHorizontalAlignment(SwingConstants.CENTER);
		lblFamilyAge.setBounds(60, 409, 65, 17);
		lblFamilyAge.setEnabled(false);
		panelMain.add(lblFamilyAge);
		
		txtFamilyAge = new JTextField();
		txtFamilyAge.setBounds(60, 429, 65, 25);
		txtFamilyAge.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtFamilyAge.setHorizontalAlignment(SwingConstants.CENTER);
		txtFamilyAge.setEditable(false);
		txtFamilyAge.setEnabled(false);
		txtFamilyAge.setColumns(10);
		panelMain.add(txtFamilyAge);
		
		lblMedal = new JLabel();
		lblMedal.setBounds(74, 460, 37, 50);
		lblMedal.setHorizontalAlignment(SwingConstants.CENTER);
		lblMedal.setIcon(new ImageIcon(UserFrame.class.getResource("/res/medal.png")));
		panelMain.add(lblMedal);
		
		JLabel lblEnergy = new JLabel("Energy");
		lblEnergy.setForeground(Color.BLUE);
		lblEnergy.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEnergy.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnergy.setBounds(130, 363, 55, 17);
		panelMain.add(lblEnergy);
		
		sliderEnergy = new SliderEnergy();
		sliderEnergy.setBounds(130, 382, 55, 262);
		panelMain.add(sliderEnergy);
		
		JPanel panelCare = new JPanel();
		panelCare.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panelCare.setAutoscrolls(true);
		panelCare.setBounds(213, 361, 449, 283);
		panelCare.setLayout(null);
		panelMain.add(panelCare);
		
		LabelLevelUp lblHealthy = new LabelLevelUp("healthy");
		lblHealthy.setBounds(24, 3, 55, 17);
		panelCare.add(lblHealthy);
		
		LabelLevelDown lblSick = new LabelLevelDown("sick");
		lblSick.setBounds(24, 263, 55, 17);
		panelCare.add(lblSick);
		
		levels[HEALTH] = new SliderLevel(24);
		
		LabelLevelUp lblFed = new LabelLevelUp("fed");
		lblFed.setBounds(108, 3, 55, 17);
		panelCare.add(lblFed);
		
		LabelLevelDown lblHungry = new LabelLevelDown("hungry");
		lblHungry.setBounds(108, 263, 55, 17);
		panelCare.add(lblHungry);
		
		levels[FOOD] = new SliderLevel(108);
		
		LabelLevelUp lblVigorous = new LabelLevelUp("vigorous");
		lblVigorous.setBounds(192, 3, 55, 17);
		panelCare.add(lblVigorous);
		
		LabelLevelDown lblSleepy = new LabelLevelDown("sleepy");
		lblSleepy.setBounds(192, 263, 55, 17);
		panelCare.add(lblSleepy);
		
		levels[WAKE] = new SliderLevel(192);
		
		LabelLevelUp lblClean = new LabelLevelUp("clean");
		lblClean.setBounds(276, 3, 55, 17);
		panelCare.add(lblClean);
		
		LabelLevelDown lblDirty = new LabelLevelDown("dirty");
		lblDirty.setBounds(276, 263, 55, 17);
		panelCare.add(lblDirty);
		
		levels[NEAT] = new SliderLevel(276);
		
		LabelLevelUp lblGlad = new LabelLevelUp("glad");
		lblGlad.setBounds(360, 3, 55, 17);
		panelCare.add(lblGlad);
		
		LabelLevelDown lblAngry = new LabelLevelDown("angry");
		lblAngry.setBounds(360, 263, 55, 17);
		panelCare.add(lblAngry);
		
		levels[JOY] = new SliderLevel(360);
		
		for (int i = 1; i <= PARAMS; i++) {
			panelCharacter.add(weights[i]);
			panelCare.add(levels[i]);
		}
		
		loadPets();
	}
	
	private void stopTimersAndSave() {
		if (playMode) {
			if (timer != null) {
				timer.cancel();
		        timer = null;
			}
			
			PetDataManager dm = new PetDataManager();
			dm.updateLevels(pet);
			if (pet.isTimer()) 
				pet.stopTimer();
		}
	}
	
	private void closeFrame(java.awt.AWTEvent evt) {
		if (fromAdmin) {
			stopTimersAndSave();
			this.dispose();
		}
		else
			if (Common.showConfirmDialog(this, "You really want to exit?", "Exit") == YES) {
				stopTimersAndSave();
				System.exit(0);
			}
    }
	
	private void setPlayButtonEnablity() {
		if (petList.size() == 0)
			mntmPlay.setEnabled(false);
		else
			mntmPlay.setEnabled(mitemShowOwn.isSelected());
	}
	
	private void setNavButtonsEnablity() {
		btnPrevious.setEnabled(petIndex != 0);
		if (petList.size() == 0) {
			mntmPlay.setEnabled(false);
			btnNext.setEnabled(false);
		} else {
			mntmPlay.setEnabled(mitemShowOwn.isSelected());
			btnNext.setEnabled(petIndex != petList.size()-1);
		}
	}
	
	private void clearControls() {
		lblName.setText("");
    	setImage("");
    	panelFeatures.removeAll();
    	panelActions.removeAll();
	}
	
	private void loadPets() {
		clearControls();
    	
    	pet = null;
    	petActions = null;
		petIndex = 0;
		
    	int ownerId = (mitemShowOwn.isSelected()) ? Common.getRegisteredUser().getId() : 1;
		petList = new PetDataManager(ownerId).getEntityList();
		
		if (petList.size() != 0) {
			pet = (Pet) petList.get(0);
			initFields();
		}
		
		setPlayButtonEnablity();
		setNavButtonsEnablity();
	}
	
	private void setImage(String path) {
		try {
			lblImage.setIcon( new ImageIcon(UserFrame.class.getResource(path)) );
		} catch (Exception e) {
			lblImage.setIcon(null);
			lblImage.setText("No image");
		}
	}
	
	private void initLevels() {
		if (pet != null) {
			lblMedal.setVisible(pet.isOld());
			if (pet.getEnergy() > 0) {
				txtAge.setText("" + pet.getAge());
				sliderEnergy.setValue(pet.getEnergy());
				
				for (int i = 1; i <= PARAMS; i++)
					levels[i].setValue(pet.getLevel(i));	
				
				panelName.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
			} else {
				pet.stopTimer();
				panelName.setBorder(new LineBorder(new Color(0, 0, 0), 5, true));
			}
		}
	}
	
	private void initFields() {
		clearControls();
    	
        if (pet != null) {
        	txtFamilyAge.setText("" + pet.getFamilyAge());
            lblName.setText( String.format("%s (%s) ", pet.getName(), pet.getFamilyName()));
            lblMedal.setVisible(pet.isOld());
            setImage(pet.getImageResource());
            
            PetFeatureListPanel petFeatures = new PetFeatureListPanel(pet);
            petFeatures.setButtonsVisiblity(false, false, false);
            panelFeatures.add(petFeatures, BorderLayout.CENTER);
            
            petActions = new PetActionListPanel(pet, true);
            petActions.setButtonsVisiblity(false, false, false);
            panelActions.add(petActions, BorderLayout.CENTER);
            
            for (int i = 1; i <= PARAMS; i++)
            	weights[i].draw(pet.getWeight(i));
            
            initLevels();
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		
		switch (action) {
        case EXIT:
        	closeFrame(e);
            break;
        case LOAD:
        	loadPets();
        	if (mitemShowOwn.isSelected()) {
        		lblStatus.setText(MODE_VIEW);
        		btnSelect.setVisible(false);
        	}
        	else {
        		lblStatus.setText(MODE_SELECT);
        		btnSelect.setVisible(petList.size() != 0);
        	}
            break;
        case PREV:
        	if (petIndex > 0) {
				petIndex--;
				pet = (Pet) petList.get(petIndex);
				initFields();
				setNavButtonsEnablity();
			}
            break;
        case NEXT:
        	if (petIndex < petList.size() - 1) {
				petIndex++;
				pet = (Pet) petList.get(petIndex);
				initFields();
				setNavButtonsEnablity();
			}
            break;
        case SELECT:
        	if (pet != null) {
        		if (Common.showConfirmDialog(this, "Are you really want to select this pet?", "Select") == YES) {
        			pet.setUserId( Common.getRegisteredUser().getId() );
        			new PetDataManager().updateEntity(pet);
        			
        			lblStatus.setText(MODE_VIEW);
        			btnSelect.setVisible(false);
        			mitemShowOwn.setSelected(true);
        			
        			loadPets();
        		}
        	}
            break;
        case DO:
        	if (petActions != null) {
        		PetAction petAction = (PetAction) petActions.getSelectedEntity();
        		int petActionId = petAction.getId();
        		Long time1 = System.currentTimeMillis();
        		
        		if (actions.containsKey(petActionId)) {
        			Long time0 = actions.get(petActionId);
        			if ((time1 - time0) / 1000 < petAction.getPeriod()) {
        				Common.showErrorMessage(this, "Too fast!");
        				break;
        			}
        		}
        		
        		actions.put(petActionId, time1);
        		for (int i = 1; i <= PARAMS; i++) {
        			int newLevel = pet.getLevel(i) + petAction.getLevel(i);
        			newLevel = Math.max(newLevel, LEVEL_DOWN);
        			newLevel = Math.min(newLevel, LEVEL_UP);
        			pet.setLevel(i, newLevel);
				}
        	}
            break;
        case PLAY:
        	if (Common.showConfirmDialog(this, "Do you really want to play?", "Play") == YES) {
        		playMode = true;
        		
        		lblStatus.setText(MODE_PLAY);
        		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        		
        		mnMode.setVisible(false);
        		mntmPlay.setVisible(false);
        		btnPrevious.setVisible(false);
        		btnNext.setVisible(false);
        		
        		btnAction.setEnabled(true);
        		
				timer = new Timer();
				timer.schedule(new FormTask(), USERFORM_TIMETICK, USERFORM_TIMETICK);
				
				if (!pet.isTimer()) 
                	pet.startTimer();
        	}
            break;
		}
	}
	
	class LabelWeight extends JLabel {
		private static final long serialVersionUID = 1L;
		
		LabelWeight(String text) {
			super(text);
			setFont(new Font("Tahoma", Font.PLAIN, 11));
			setEnabled(false);
			setHorizontalAlignment(SwingConstants.LEFT);	
		}
	}

	class PanelWeight extends JPanel {
		private static final long serialVersionUID = 1L;
		
		private final int l = 85, h = 17;
		private int x, y;
		
		PanelWeight(int x, int y) {
			setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
			setBackground(new Color(0, 153, 255));
			setBounds(x, y, l, h);
			this.x = x;
			this.y = y;
		}
		
		void draw(int value) {
			int l1 = value * l / 10;
			setBounds(x, y, l1, h);
		}
	}

	class LabelLevel extends JLabel {
		private static final long serialVersionUID = 1L;
		
		LabelLevel() {
			setHorizontalAlignment(SwingConstants.CENTER);
			setForeground(new Color(0, 102, 0));
			setFont(new Font("Tahoma", Font.BOLD, 12));
		}
	}

	class LabelLevelUp extends LabelLevel {
		private static final long serialVersionUID = 1L;
		
		LabelLevelUp(String text) {
			setText(text);
			setForeground(new Color(0, 102, 0));
		}
	}

	class LabelLevelDown extends LabelLevel {
		private static final long serialVersionUID = 1L;
		
		LabelLevelDown(String text) {
			setText(text);
			setForeground(Color.RED);
		}
	}

	class Slider extends JSlider {
		private static final long serialVersionUID = 1L;

		Slider() {
			setPaintTicks(true);
			setPaintLabels(true);
			setOrientation(SwingConstants.VERTICAL);
			setEnabled(false);
			setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		}
	}

	class SliderEnergy extends Slider {
		private static final long serialVersionUID = 1L;

		SliderEnergy() {
			setMinimum(0);
			setMaximum(100);
			setMinorTickSpacing(1);
			setMajorTickSpacing(10);
			setBackground(Color.WHITE);
		}
	}

	class SliderLevel extends Slider implements Constants {
		private static final long serialVersionUID = 1L;

		SliderLevel(int x) {
			super();
			setMinimum(LEVEL_DOWN);
			setMaximum(LEVEL_UP);
			setMinorTickSpacing(1);
			setMajorTickSpacing(2);
			setBounds(x, 20, 55, 244);
		}
	}
	
	class FormTask extends TimerTask {
		public void run() {
	    	initLevels();
	    }
	}
}
