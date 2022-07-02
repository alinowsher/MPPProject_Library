package librarySystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import java.awt.*;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableModel;

import business.Author;
import business.Book;
import business.BookCopy;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;

public class AddBook extends JFrame implements LibWindow{

	public static final AddBook INSTANCE = new AddBook();
    ControllerInterface ci = new SystemController();
	private boolean isInitialized = false;
	
	private AddBook() {}
	
	JTextField txtISBN, txtTitle, txtCheckoutLength, txtCopyNumber;
	
	
	
	JComboBox<Author> cmbAuthors;
	
	List<Author> authors = new ArrayList<>();
	DefaultListModel<Author> listModel = new DefaultListModel<Author>();
	
	JList<Author> lstAuthors ;
	
	
	List<BookCopy> lstBookCopies = new ArrayList<>();
	DefaultListModel<String> modelBookCopies = new DefaultListModel<String>();
	
	JList <String> bookCopies;
	
	JTable jt;
	
	@Override
	public void init() {
		
		JPanel panelbookFields = new JPanel();
		panelbookFields.setLayout(null);	
		
		JLabel lblISBN = new JLabel("ISBN");
		lblISBN.setBounds(20,20,100,20);
		
		txtISBN = new JTextField(10);
		txtISBN.setBounds(130,20,100,20);
		
		JLabel lblTitle = new JLabel ("Title");
		lblTitle.setBounds(20,50,100,20);
		
		txtTitle = new JTextField(10);
		txtTitle.setBounds(130,50,100,20);
		
		JLabel lblCheckOutLenght = new JLabel("Checkout Lenght");
		lblCheckOutLenght.setBounds(20,80,100,20);
		
		txtCheckoutLength = new JTextField(10);
		txtCheckoutLength.setBounds(130,80,100,20);
		
		
		
		JLabel lblAuthors = new JLabel("Book Authors");
		lblAuthors.setBounds(20,110,100,20); //x,y,w,h
		
		lstAuthors = new JList<Author> ();
		
		lstAuthors.setModel(listModel);
		lstAuthors.setBounds(130,110,150,80);
		
	    cmbAuthors = new JComboBox<Author>();
	    cmbAuthors.setBounds(130,200,110,20);
	    //load authors
	    List<Author> dbAuthors = ci.getAllAuthors();
	    for(Author a: dbAuthors) {
	    	cmbAuthors.addItem(a);
	    }
	    
	    cmbAuthors.setRenderer(new AuthorRenderer());
	    
	    JButton btnOk=new JButton("+");
	    btnOk.setBounds(250,200,50,20);
	    addOkButtonListener(btnOk);
	    
	    JLabel lblBookCopy = new JLabel("Book Copies");
	    lblBookCopy.setBounds(20,230,100,20); //x,y,w,h
		
	    bookCopies = new JList<String>();
	    bookCopies.setModel(modelBookCopies);
	    bookCopies.setBounds(130,230,150,80);
	    
	    txtCopyNumber = new JTextField();
	    txtCopyNumber.setBounds(130,320,60,20);
	    JButton btnCreateCopy = new JButton("Create");
	    btnCreateCopy.setBounds(200,320,80,20);
	    createCopyListener(btnCreateCopy);
	    
	    
	    JButton btnBackToMain = new JButton("<< Back to Main");
		addBackButtonListener(btnBackToMain);
		
		JButton btnSave = new JButton("Save Book");
		addBtnSaveListener(btnSave);
		
		JPanel pnlButtonSave = new JPanel();
		pnlButtonSave.add(btnBackToMain);
		pnlButtonSave.add(btnSave);
		pnlButtonSave.setBounds(20, 370,670, 35);
		pnlButtonSave.setBackground(Color.green);
		
		//jTable
		 DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ISBN");
        model.addColumn("Copy Number");
        model.addColumn("Book Title");
        model.addColumn("Availability");
	        
	        
	     jt = new JTable(model);
	        
	     jt.getColumnModel().getColumn(0).setPreferredWidth(20);
	     jt.getColumnModel().getColumn(1).setPreferredWidth(27);
	     jt.getColumnModel().getColumn(3).setPreferredWidth(70);
	     jt.getColumnModel().getColumn(3).setPreferredWidth(22);
		 JScrollPane sp=new JScrollPane(jt);  
		 sp.setBounds(310,20,375,340);
		 panelbookFields.add(sp);
		 
		 
		 //load books
		    List<Book> dataa = ci.getAllBooks();
			
			
			for(Book lm :dataa) {
					String isbn = lm.getIsbn();
					String title = lm.getTitle();
					
				for( BookCopy bc: lm.getCopies()) {
					model.addRow(new Object[]{ isbn, bc.getCopyNum(), title, bc.isAvailable()});
				}
				
				//model.addRow(new Object[]{ " ", " ", " ", " "});
			}
			
			
		panelbookFields.add(lblISBN);
		panelbookFields.add(txtISBN);
		
		panelbookFields.add(lblTitle);
		panelbookFields.add(txtTitle);
		
		panelbookFields.add(lblCheckOutLenght);
		panelbookFields.add(txtCheckoutLength);
		
		panelbookFields.add(lblAuthors);
		panelbookFields.add(lstAuthors);
		
		panelbookFields.add(cmbAuthors);
		
		panelbookFields.add(btnOk);
		
		panelbookFields.add(lblBookCopy);
		panelbookFields.add(bookCopies);
		
		panelbookFields.add(txtCopyNumber);
		panelbookFields.add(btnCreateCopy);
		
		panelbookFields.add(pnlButtonSave , BorderLayout.CENTER);
		
		
		this.setTitle("Add Library Book");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setLayout(null);
		this.setSize(720,460);
		this.setVisible(true);
		this.add(panelbookFields);
	}

	
	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		});
	}
	
	
	private void addOkButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			
			authors.add((Author)cmbAuthors.getSelectedItem());
			listModel.addElement((Author)cmbAuthors.getSelectedItem());
			lstAuthors.setCellRenderer(new AuthorRenderer());
						
		});
	}
	
	
	private void createCopyListener(JButton butn) {
		butn.addActionListener(evt -> {
			
			//lstBookCopies.add((BookCopy)cmbAuthors.getSelectedItem());
			modelBookCopies.addElement(txtCopyNumber.getText().trim());
			txtCopyNumber.setText("");
						
		});
	}
	
	private void addBtnSaveListener(JButton butn) {
		butn.addActionListener(evt -> {
			
			
			if(txtISBN.getText().equals("") || txtTitle.getText().equals("") || txtCheckoutLength.getText().equals("") || 
					listModel.size()==0 || modelBookCopies.size()==0) {
				JOptionPane.showMessageDialog(this,"Fields can not be left blank","Required fields can not be empty", JOptionPane.ERROR_MESSAGE);
				
			}
			//Book book = new Book(txtISBN.getText().trim(), txtTitle.getText().trim(), Integer.parseInt( txtCheckoutLength.getText().trim()), authors);
			try {
				ci.addBook(txtISBN.getText().trim(), txtTitle.getText().trim(), Integer.parseInt( txtCheckoutLength.getText().trim()), authors);
				
				
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this,e.getMessage(),"Save Failed", JOptionPane.ERROR_MESSAGE);
				
			}
			
			
			DefaultTableModel model = (DefaultTableModel) jt.getModel();
			model.setRowCount(0); // clear data
			 //load books
			    List<Book> dataa = ci.getAllBooks();
				
				
				for(Book lm :dataa) {
						String isbn = lm.getIsbn();
						String title = lm.getTitle();
						
					for( BookCopy bc: lm.getCopies()) {
						model.addRow(new Object[]{ isbn, bc.getCopyNum(), title, bc.isAvailable()});
					}
					
					//model.addRow(new Object[]{ " ", " ", " ", " "});
				}
				
				JOptionPane.showMessageDialog(this,"Save successful");	
				
				authors.clear();
				txtISBN.setText("");
				txtTitle.setText("");
				txtCheckoutLength.setText("");
				listModel.clear();
				modelBookCopies.clear();
				
				
		});
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
	
	
	public class AuthorRenderer extends JLabel implements ListCellRenderer<Author> {
		 @Override
		    public Component getListCellRendererComponent(JList<? extends Author> list, Author author, int index,
		        boolean isSelected, boolean cellHasFocus) {
		          
		        String authorId = author.getAuthorId();
		        
		         
		       
		        setText(author.getFirstName() + " " + author.getLastName());
		         
		        return this;
		    }
	}

}
