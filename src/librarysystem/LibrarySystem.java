package librarysystem;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import business.ControllerInterface;
import business.SystemController;
import dataaccess.Auth;

public class LibrarySystem extends JFrame implements LibWindow {
	ControllerInterface ci = new SystemController();
	public final static LibrarySystem INSTANCE = new LibrarySystem();
	JPanel mainPanel;
	JMenuBar menuBar;
	JMenu options;
	JMenuItem login, logOut, allBookIds, addLibraryMember, addNewMember, addBook, addBookCopy, addBookAuthor,
			checkOutBook;
	String pathToImage;
	private boolean isInitialized = false;

	private static LibWindow[] allWindows = { LibrarySystem.INSTANCE, LoginWindow.INSTANCE, AllMemberIdsWindow.INSTANCE,
			AllBookIdsWindow.INSTANCE, AddBook.INSTANCE, AddMember.INSTANCE, CheckoutBook.INSTANCE,
			CreateBookCopy.INSTANCE };

	public static void hideAllWindows() {

		for (LibWindow frame : allWindows) {
			frame.setVisible(false);

		}
	}

	private LibrarySystem() {
	}

	public void init() {
		formatContentPane();
		setPathToImage();
		insertSplashImage();

		createMenus(null);
		// pack();
		setSize(660, 500);
		isInitialized = true;
	}

	private void formatContentPane() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 1));
		getContentPane().add(mainPanel);
	}

	private void setPathToImage() {
		String currDirectory = System.getProperty("user.dir");
		pathToImage = currDirectory + "\\src\\librarysystem\\library.jpg";
	}

	private void insertSplashImage() {
		ImageIcon image = new ImageIcon(pathToImage);
		mainPanel.add(new JLabel(image));
	}

	public void createMenus(Auth authh) {
		menuBar = new JMenuBar();
		menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
		addMenuItems(authh);
		setJMenuBar(menuBar);
	}

	private void addMenuItems(Auth authenticationMode) {
		options = new JMenu("Options");
		menuBar.add(options);

		login = new JMenuItem("Login");
		login.addActionListener(new LoginListener());

		logOut = new JMenuItem("Logout");
		logOut.addActionListener(new LogoutListener());

		allBookIds = new JMenuItem("All Book Ids");
		allBookIds.addActionListener(new AllBookIdsListener());

		addLibraryMember = new JMenuItem("Add/Edit Library Member");
		addLibraryMember.addActionListener(new AddMemberListener());

		addBook = new JMenuItem("Add New Book");
		addBook.addActionListener(new AddBookListener());

		addBookCopy = new JMenuItem("Create Book Copy");
		addBookCopy.addActionListener(new AddBookCopyListener());

		addBookAuthor = new JMenuItem("Add Book Author");
		addBookAuthor.addActionListener(new AddBookAuthorListener());

		checkOutBook = new JMenuItem("Checkout Book");
		checkOutBook.addActionListener(new AddCheckoutListener());

		if (authenticationMode == null) {
			options.add(login);
		} else if (authenticationMode == authenticationMode.ADMIN) {
			options.add(logOut);
			options.add(addBook);
			options.add(addBookCopy);

			options.add(addBookAuthor);
			options.add(addLibraryMember);
			// options.add(checkOutBook);

		} else if (authenticationMode == authenticationMode.BOTH) {
			options.add(logOut);
			options.add(addBook);
			options.add(addBookCopy);

			options.add(addBookAuthor);
			options.add(addLibraryMember);
			options.add(checkOutBook);

		} else if (authenticationMode == authenticationMode.LIBRARIAN) {
			options.add(logOut);
			options.add(checkOutBook);
		}

	}

	class LoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			LoginWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
			LoginWindow.INSTANCE.setVisible(true);

		}

	}

	class AddBookListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AddBook.INSTANCE.init();

			AddBook.INSTANCE.setVisible(true);
			Util.centerFrameOnDesktop(AddBook.INSTANCE);
			AddBook.INSTANCE.setVisible(true);

		}

	}

	class AddBookCopyListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			CreateBookCopy.INSTANCE.init();

			CreateBookCopy.INSTANCE.setVisible(true);
			Util.centerFrameOnDesktop(CreateBookCopy.INSTANCE);
			CreateBookCopy.INSTANCE.setVisible(true);

		}

	}

	class AddBookAuthorListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AddBookAuthor.INSTANCE.init();

			AddBookAuthor.INSTANCE.setVisible(true);
			Util.centerFrameOnDesktop(AddBookAuthor.INSTANCE);
			AddBookAuthor.INSTANCE.setVisible(true);

		}

	}

	class AllBookIdsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();

			List<String> ids = ci.allBookIds();
			Collections.sort(ids);
			StringBuilder sb = new StringBuilder();
			for (String s : ids) {
				sb.append(s + "\n");
			}
			System.out.println(sb.toString());
			AllBookIdsWindow.INSTANCE.setData(sb.toString());
			AllBookIdsWindow.INSTANCE.pack();
			// AllBookIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
			AllBookIdsWindow.INSTANCE.setVisible(true);

		}

	}

	class AddMemberListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			LibrarySystem.hideAllWindows();
			AddMember.INSTANCE.init();

			AddMember.INSTANCE.setVisible(true);

			Util.centerFrameOnDesktop(AddMember.INSTANCE);
			AddMember.INSTANCE.setVisible(true);

		}

	}

	class AddCheckoutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			LibrarySystem.hideAllWindows();
			CheckoutBook.INSTANCE.init();

			CheckoutBook.INSTANCE.setVisible(true);

			Util.centerFrameOnDesktop(CheckoutBook.INSTANCE);
			CheckoutBook.INSTANCE.setVisible(true);

		}

	}

	class LogoutListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			createMenus(null);

			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.init();

			LibrarySystem.INSTANCE.setVisible(true);

			Util.centerFrameOnDesktop(LibrarySystem.INSTANCE);
			LibrarySystem.INSTANCE.setVisible(true);

		}

	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;

	}

}
