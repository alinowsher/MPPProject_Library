package librarySystem;

import javax.swing.JFrame;
import javax.swing.JPanel;

import business.ControllerInterface;
import business.SystemController;

public class AddBookCopy extends JFrame implements LibWindow{

	public static final AddBookCopy INSTANCE = new AddBookCopy();
    ControllerInterface ci = new SystemController();
	private boolean isInitialized = false;
	
	private AddBookCopy() {}
	@Override
	public void init() {
		JPanel panelbookFields = new JPanel();
		panelbookFields.setLayout(null);	
		
		
		this.setTitle("Create Book Copy");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setLayout(null);
		this.setSize(420,420);
		this.setVisible(true);
		//this.add(pnlMemberFields);
	}

	@Override
	public boolean isInitialized() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void isInitialized(boolean val) {
		// TODO Auto-generated method stub
		
	}

}
