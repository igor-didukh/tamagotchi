package main;

import users.User;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Window;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

public class Common implements Constants {
	private static User registeredUser;
	public static User getRegisteredUser() {
		return registeredUser;
	}
	public static void setRegisteredUser(User user) {
		registeredUser = user;
	}
	
	/**
	 * Show frame on the center of screen 
	 */
	public static void showFrame(Window frame) {
		int screenWidth = 0, screenHeight = 0;
		
		GraphicsDevice[] screenDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        for (GraphicsDevice graphicsDevice : screenDevices) {
            screenWidth = graphicsDevice.getDefaultConfiguration().getBounds().width;
            screenHeight = graphicsDevice.getDefaultConfiguration().getBounds().height;
        }
		
        Rectangle r = frame.getBounds();
		
		int frameWidth = r.width, frameHeight = r.height;
		int posX = (screenWidth - frameWidth) / 2;
		int posY = (screenHeight - frameHeight) / 2 - 40;
		
		frame.setPreferredSize(new Dimension(frameWidth, frameHeight));
		frame.setBounds(posX, posY, r.width, r.height);
		
		frame.setVisible(true);
	}
	
	public static void makeFrame(JPanel panel) {
		Rectangle r = panel.getBounds();
		
		JFrame frame = new JFrame();
		frame.setTitle("" +  Common.getRegisteredUser());
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setBounds(r.x, r.y, r.width, r.height);
		frame.add(panel);
		
		showFrame(frame);
	}
	
	public static int parseInt(String s) {
		int n = 0;
		try {
			n = Integer.parseInt(s);
		} catch (Exception e) {}
		return n;
	}
	
	public static void showErrorMessage(Component cmp, String message) {
		JOptionPane.showMessageDialog(cmp, message, "Error!", JOptionPane.ERROR_MESSAGE);
	}
	
	public static int showConfirmDialog(Component cmp, String message, String title) {
		Object[] options = { "Yes", "No" };
        return JOptionPane.showOptionDialog(
        		cmp, message, title,
        		JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
        		null, options, options[1]
        );
	}
	
	public static String selectImage(Component cmp) {
		String imgName = "";
		
		String s = cmp.getClass().getResource("").toString();
		s = s.substring(6, s.length()-6);
		s += "/res";
		
		if (SELECT_IMG_SWT) {
			Display display = new Display ();
	        Shell shell = new Shell (display);
	        FileDialog dialog = new FileDialog (shell, SWT.OPEN);
	        String [] filterExtensions = new String [] {"*.jpg", "*.gif", "*.png"};
	        String filterPath = s;
	        dialog.setFilterExtensions(filterExtensions);
	        dialog.setFilterPath(filterPath);
	        dialog.open();
	        
	        imgName = dialog.getFileName();
	        if (imgName.length() != 0)
	        	imgName = "/res/" + imgName;
	        
	        shell.close();
	        while (!shell.isDisposed ()) {
	            if (!display.readAndDispatch ()) display.sleep ();
	        }
	        display.dispose ();	
		} else {
	        File f = new File(s);
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(f);
			fc.setFileFilter(new ImageFilesFilter());
			
			int ret = fc.showDialog(null, "Select");
			
			if (ret == JFileChooser.APPROVE_OPTION) {
				imgName = fc.getSelectedFile().getPath();
				imgName = imgName.replace("\\", "/");
				imgName = imgName.replace(s, "");
				imgName = "/res" + imgName;
	        }	
		}
		
        return imgName;
	}
}

class ImageFilesFilter extends javax.swing.filechooser.FileFilter {
    @Override
    public boolean accept(File file) {
    	String path = file.getAbsolutePath();
    	String[] ext = {".jpg", ".gif", ".png"};
    	
    	for (int i = 0; i < ext.length; i++) {
			if (path.endsWith(ext[i]))
				return true;
		}
    	return false;
    }
    @Override
    public String getDescription() {
        return "Images";
    }
}