package librarysystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import business.Author;
import business.Book;
import business.BookCopy;
import business.CheckoutRecord;
import business.CheckoutRecordNew;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import librarysystem.AddBook.AuthorRenderer;

public class CheckoutBook extends JFrame implements LibWindow{

	public static final CheckoutBook INSTANCE = new CheckoutBook();
    ControllerInterface ci = new SystemController();
	private boolean isInitialized = false;
	
	private CheckoutBook() {}
	
	private boolean idWasValidated =false;
	
	JTextField txtISBN, txtMemberID;
	JComboBox<BookCopy> cmbCopies;
	JTable jt;
	
	@Override
	public void init() {
		JPanel panelCheckoutFields = new JPanel();
		panelCheckoutFields.setLayout(null);	
		
		this.setTitle("Checkout Book");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		JLabel lblISBN = new JLabel("ISBN");
		lblISBN.setBounds(20,20,100,20);
		
		txtISBN = new JTextField(10);
		txtISBN.setBounds(110,20,100,20);
		
		
		
		JLabel lblMemberID = new JLabel ("Member ID");
		lblMemberID.setBounds(20,50,100,20);
		
		txtMemberID = new JTextField(10);
		txtMemberID.setBounds(110,50,100,20);
		
		JButton btnCheckID = new JButton("Check ID");
		btnCheckID.setBounds(110,80,100,20);
		addCheckIDListener(btnCheckID);
		
		JLabel lblSelectCopy = new JLabel("Select Copy:");
		lblSelectCopy.setBounds(20,110,100,20);
		
		
		cmbCopies = new JComboBox<BookCopy>();
	    cmbCopies.setBounds(110,110,100,20);
	    
	    
	    JButton btnBackToMain = new JButton("<< Back to Main");
	    addBackButtonListener(btnBackToMain);
	    
		JButton btnCheckOut = new JButton("Confirm Checkout");
		addCheckoutBtnListener(btnCheckOut);
		
	    JPanel pnlButtonSave = new JPanel();
		pnlButtonSave.add(btnBackToMain);
		pnlButtonSave.add(btnCheckOut);
		pnlButtonSave.setBounds(20, 150,360, 35);
		pnlButtonSave.setBackground(Color.green);
		
		
		DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Copy No");
        model.addColumn("Member");
        model.addColumn("Issue Date");
        model.addColumn("Due Date");
        
        
	     jt = new JTable(model);
	        
	        
		 JScrollPane sp=new JScrollPane(jt);  
		 sp.setBounds(20,200,360,150);
		 panelCheckoutFields.add(sp);
		    
		//load checkOutRecord
		 
		 try {
			 List<CheckoutRecordNew> dataa = ci.getCheckoutRecords();
			 
				if(dataa.size()!=0) {
					for(CheckoutRecordNew cr :dataa) {
						model.addRow(new Object[]{cr.getCopyNumber(),cr.getMemberId(), cr.getIssueDate(), cr.getDueDate()});
					}
					
					
				}
				
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	
		 model.addRow(new Object [] {"2", "1001", LocalDate.now(), LocalDate.now().plusDays(7)});
		 model.addRow(new Object [] {"1", "1002", LocalDate.now(), LocalDate.now().plusDays(21)});
	    
		panelCheckoutFields.add(lblMemberID);
		panelCheckoutFields.add(txtISBN);
		
		panelCheckoutFields.add(lblISBN);
		panelCheckoutFields.add(txtMemberID);
		
		panelCheckoutFields.add(btnCheckID);
		
		panelCheckoutFields.add(lblSelectCopy);
		panelCheckoutFields.add(cmbCopies);
		panelCheckoutFields.add(pnlButtonSave , BorderLayout.CENTER);
		
		this.setSize(420,420);
		this.setVisible(true);
		this.add(panelCheckoutFields);
	}
	
	

	private void addCheckIDListener(JButton butn) {
		butn.addActionListener(evt -> {
			Book book = ci.getBook(txtISBN.getText().trim());
			
			if(book == null) {
				System.out.println(txtISBN.getText().trim());
				JOptionPane.showMessageDialog(this,"Book not found for the ISBN","Not Found", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			LibraryMember member = ci.getMember(txtMemberID.getText().trim());
			
			if(member == null) {
				JOptionPane.showMessageDialog(this,txtMemberID.getText().trim());	
				JOptionPane.showMessageDialog(this,"Member not found for the ID","Not Found", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			 //load book copies
		    BookCopy[] copies = ci.getBook(txtISBN.getText().trim()).getCopies();
		    for(BookCopy bc: copies) {
		    	cmbCopies.addItem(bc);
		    }
		    
		    idWasValidated = true;
		    
		    cmbCopies.setRenderer(new BookCopyRenderer());
			
		});
	}
	
	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			
			
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		});
	}
	
	private void addCheckoutBtnListener(JButton butn) {
		butn.addActionListener(evt -> {

			
			BookCopy bookCopy = (BookCopy)cmbCopies.getSelectedItem();
			
			if(bookCopy == null) {
				JOptionPane.showMessageDialog(this,"Pls select a book from the list.","Save Failed", JOptionPane.ERROR_MESSAGE);
				
				return;
			}else if(!bookCopy.isAvailable()) {
				JOptionPane.showMessageDialog(this,"Selected book is not available.","Save Failed", JOptionPane.ERROR_MESSAGE);
				return;
			}else if(!idWasValidated) {
				JOptionPane.showMessageDialog(this,"Enter valid ID then click CheckID.","Save Failed", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				CheckoutRecordNew checkOutRec = new CheckoutRecordNew(bookCopy, LocalDate.now(), txtMemberID.getText().trim());
				
				
				ci.addCheckoutRecord(checkOutRec);
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			JOptionPane.showMessageDialog(this,"Save successful");
			
			DefaultTableModel modell = (DefaultTableModel) jt.getModel();
			BookCopy bc = (BookCopy) cmbCopies.getSelectedItem();
			modell.addRow(new Object[]{bc.getCopyNum(),txtMemberID.getText().trim(), LocalDate.now(), LocalDate.now().plusDays(bc.getBook().getMaxCheckoutLength()) });

			jt.setRowSelectionInterval(modell.getRowCount()-1, modell.getRowCount()-1);
			jt.scrollRectToVisible(new Rectangle(jt.getCellRect(modell.getRowCount()-1, 0, true)));
			
			
			txtISBN.setText("");
			txtMemberID.setText("");
			cmbCopies.removeAllItems();
			idWasValidated=false;
			
			
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
	
	
	public class BookCopyRenderer extends JLabel implements ListCellRenderer<BookCopy> {
		 @Override
		    public Component getListCellRendererComponent(JList<? extends BookCopy> list, BookCopy bookCopy, int index,
		        boolean isSelected, boolean cellHasFocus) {
		          
		        int copyNumber = bookCopy.getCopyNum();
		        
		         
		       
		        setText(copyNumber + ", IsAvailable:" + bookCopy.isAvailable());
		         
		        return this;
		    }
	}
	
	
}
