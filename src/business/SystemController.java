package business;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import business.CheckoutRecord.CheckoutRecordEntry;
import dataAccess.Auth;
import dataAccess.DataAccess;
import dataAccess.DataAccessFacade;
import dataAccess.User;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;

	public void login(String id, String password) throws LoginException {		
		
		// To insert new users.
		//DataAccessFacade da1 = new DataAccessFacade();
		//da1.createUserMap();

		
		DataAccess da = new DataAccessFacade();		
		HashMap<String, User> map = da.readUserMap();
		if (map == null) {
			throw new LoginException("System has no users!, Add a User");
		}
		if (!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if (!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		currentAuth = map.get(id).getAuthorization();

	}

	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> mems = da.readMemberMap();
		if (mems == null)
			return null;
		List<String> retval = new ArrayList<>();
		retval.addAll(mems.keySet());
		return retval;
	}

	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> bookmap = da.readBooksMap();
		if (bookmap == null)
			return null;
		List<String> retval = new ArrayList<>();
		retval.addAll(bookmap.keySet());
		return retval;
	}

	@Override
	public void addLibraryMember(LibraryMember member) {
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(member);
	}
	
	@Override
	public void addLibraryMember(String txtMemberID, String txtFirstName, 
			String txtLastName, String txtTelephone, Address address) {
	
		DataAccess da = new DataAccessFacade();
		LibraryMember member = new LibraryMember(txtMemberID, txtFirstName, 
				txtLastName, txtTelephone, address);

		da.saveNewMember(member);
	}

//	@Override
//	public void addCheckoutRecordEntry(String memberId, String isbn) throws LibrarySystemException {
//		DataAccess da = new DataAccessFacade();
//
//		HashMap<String, LibraryMember> mems = da.readMemberMap();
//		if (mems == null) {
//			throw new LibrarySystemException("System has no members!, Add a member first");
//		}
//		LibraryMember member = mems.get(memberId);
//		if (member == null) {
//			throw new LibrarySystemException(memberId + " member not found in the system");
//		}
//		HashMap<String, Book> bookmap = da.readBooksMap();
//		if (bookmap == null) {
//			throw new LibrarySystemException("System has no books!, Add a book first");
//		}
//		Book book = bookmap.get(isbn);
//		if (book == null) {
//			throw new LibrarySystemException(isbn + " book not found in the system");
//		}
//
//		BookCopy bookcopy = book.getNextAvailableCopy();
//		if (bookcopy == null) {
//			throw new LibrarySystemException(isbn + " book is not available currently");
//		}
//
//		CheckoutRecord checkoutRecord = member.getCheckoutRecord();
//		CheckoutRecordEntry entry = checkoutRecord.new CheckoutRecordEntry(bookcopy);
//		// I stopped here
//		// checkoutRecord.addEntry(entry);
//		// bookcopy.occupy(entry);
//
//		da.saveMemberMap(mems);
//		da.saveBookMap(bookmap);
//	}

	@Override
	public void addCopyOfBook(String isbn) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> bookmap = da.readBooksMap();
		if (bookmap == null) {
			throw new LibrarySystemException("System has no books!, Add a book first");
		}
		Book book = bookmap.get(isbn);
		if (book == null) {
			throw new LibrarySystemException(isbn + " book not found in the system");
		}
		book.addCopy();
		da.saveBookMap(bookmap);
	}

	@Override
	public void addBook(String isbn, String title, int maxCheckoutLength, List<Author> authors) {
		DataAccess da = new DataAccessFacade();
		da.saveNewBook(new Book(isbn, title, maxCheckoutLength, authors));
	}
	// i changed here
//	@Override
//	public void addAuthor(List<Author> authors, String fname, String lname, String tel, String bio, String street, String city, String state,
//			String zip) {
//		Address address=new Address(street, city, state, zip);
//		authors.add(new Author(fname, lname, tel, address, bio));
//	}

	@Override
	public void addAuthor(Author author) {
		DataAccess da = new DataAccessFacade();
		da.saveNewAuthor(author);

	}

	@Override
	public String printCheckoutRecord(String memberId) {
		LibraryMember member = getMember(memberId);
		return member.getCheckoutRecord().toString();
	}

	// i changed the bellow method
//	@Override
//	public String getBookStatus(String isbn) {
//		
//		Book book=getBook(isbn);
//		if (book==null)	return null;
//		return book.getCopiesStatus();
//	}

	@Override
	public Book getBook(String isbn) {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> bookmap = da.readBooksMap();
		if (bookmap == null)
			return null;
		return bookmap.get(isbn);
	}

	@Override
	public LibraryMember getMember(String memberId) {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> mems = da.readMemberMap();
		if (mems == null)
			return null;
		return mems.get(memberId);
	}
//	@Override
//	public Set<Author> getAllAuthors() {
//		Set<Author> allAuthors=new HashSet<Author>();
//		DataAccess da = new DataAccessFacade();
//		HashMap<String,Book> bookmap = da.readBooksMap();
//		if(bookmap==null) return null;
//		List <Book> books=new ArrayList<>(bookmap.values());
//		for(Book book:books) {
//			for(Author author:book.getAuthors()) {
//				allAuthors.add(author);
//			}
//		}
//		return allAuthors;
//	}

	// i added here
	@Override
	public List<Author> getAllAuthors() {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Author> authors = da.readAuthorMap();
		if (authors == null)
			return null;
		return new ArrayList<>(authors.values());
	}

	@Override
	public List<LibraryMember> getAllMembers() {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> mems = da.readMemberMap();
		if (mems == null)
			return null;
		return new ArrayList<>(mems.values());
	}

	@Override
	public List<Book> getAllBooks() {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> books = da.readBooksMap();
		if (books == null)
			return null;
		return new ArrayList<>(books.values());
	}

	@Override
	public void UpdateLibraryMember(String memberId, String fname, String lname, String tel, String street, String city,
			String state, String zip) throws LibrarySystemException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> mems = da.readMemberMap();
		if (mems == null) {
			throw new LibrarySystemException("System has no members!, Add a member first");
		}
		LibraryMember member = mems.get(memberId);
		if (member == null) {
			throw new LibrarySystemException(memberId + " member not found in the system");
		}
		Address address = new Address(street, city, state, zip);
		member.setAddress(address);
		member.setFirstName(fname);
		member.setLastName(lname);
		member.setTelephone(tel);

		da.saveMemberMap(mems);
	}

	// i added bellow to methods
	public List<CheckoutRecordNew> getCheckoutRecords() {
		DataAccess da = new DataAccessFacade();
		HashMap<String, CheckoutRecordNew> checkoutMap = da.readCheckoutMap();
		if (checkoutMap == null)
			return null;

		return new ArrayList<>(checkoutMap.values());

	}

	public void addCheckoutRecord(CheckoutRecordNew checkoutRecord) {
		DataAccess da = new DataAccessFacade();
		da.addCheckoutRecord(checkoutRecord);

	}

//	@Override
//	public List<String> allBookIds() {
//		DataAccess da = new DataAccessFacade();
//		HashMap<String,Book> bookmap = da.readBooksMap();
//		if (bookmap==null) return null;
//		List<String> retval = new ArrayList<>();
//		retval.addAll(bookmap.keySet());
//		return retval;
//	}
}
