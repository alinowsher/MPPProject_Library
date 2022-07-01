package business;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import business.Book;
import dataAccess.DataAccess;
import dataAccess.DataAccessFacade;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public void addLibraryMember(LibraryMember member);
	
	//i changed bellow (addCheckoutRecord added newly)
	public void addCheckoutRecord(CheckoutRecordNew rec);
	public void addCheckoutRecordEntry(String memberId, String isbn) throws LibrarySystemException;
	//i changed bellow. (need to be changed because book copies are being saved already from addBook Method hough its creating only one. There should have another BookCopy[] argument in book constructor  )
	public void addCopyOfBook(String isbn) throws LibrarySystemException;
	
	public List<CheckoutRecordNew> getCheckoutRecords();
		
	
	public void addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors);
	// i changed here
	//public void addAuthor(List<Author> authors, String fname, String lname, String tel, String bio, String street, String city, String state, String zip);
	
	public void addAuthor(Author author);
	
	
	public String printCheckoutRecord(String memberId);
	// i changed bellow
	//public String getBookStatus(String isbn);
	//public boolean getBookStatus(String copyNumber);
	public Book getBook(String isbn);
	public LibraryMember getMember(String memberId);
	//public Set<Author> getAllAuthors();
	
	public List<Author> getAllAuthors();
	
	public List<LibraryMember> getAllMembers();
	public List<Book> getAllBooks();
	public void UpdateLibraryMember(String memberId, String fname, String lname, String tel, String street, String city, String state, String zip) throws LibrarySystemException;
	
	
}
