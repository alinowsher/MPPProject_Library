package librarySystem;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.JFrame;

import dataAccess.DataAccess;
import dataAccess.DataAccessFacade;
import dataAccess.User;
/*
 * Group members:
 * 1. Sushanta Acharjee
 * 2. Mohammad Nowsher Ali
 * 3. Kollol Chakraborty shekhor
 * 4. Sumayya Jahan
 * 5. Muhammad Hizbullah Fuad
*/

public class Main {

	public static void main(String[] args) {		
		
	      EventQueue.invokeLater(() -> 
	         {
	            LibrarySystem.INSTANCE.setTitle("Library Application");
	            LibrarySystem.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            
	            LibrarySystem.INSTANCE.init();
	            centerFrameOnDesktop(LibrarySystem.INSTANCE);
	            LibrarySystem.INSTANCE.setVisible(true);
	         });
	   }
	   
	   public static void centerFrameOnDesktop(Component f) {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			int height = toolkit.getScreenSize().height;
			int width = toolkit.getScreenSize().width;
			int frameHeight = f.getSize().height;
			int frameWidth = f.getSize().width;
			f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
		}
}
