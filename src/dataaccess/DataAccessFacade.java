package dataAccess;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import business.Author;
import business.Book;
import business.BookCopy;
import business.CheckoutRecord;
import business.CheckoutRecordNew;
import business.LibraryMember;
import dataAccess.DataAccessFacade.StorageType;

public class DataAccessFacade implements DataAccess {

	enum StorageType {
		BOOKS, MEMBERS, USERS, AUTHORS, CHECKOUTRECORDS;
	}

	public static final String OUTPUT_DIR = System.getProperty("user.dir") + "\\src\\dataaccess\\storage";
	public static final String DATE_PATTERN = "MM/dd/yyyy";

	// implement: other save operations
	@Override
	public void saveNewMember(LibraryMember member) {
		HashMap<String, LibraryMember> mems = readMemberMap();
		String memberId = member.getMemberId();
		if (mems == null)
			mems = new HashMap<String, LibraryMember>();
		mems.put(memberId, member);
		saveToStorage(StorageType.MEMBERS, mems);
	}

	// i added here
	@Override
	public void saveNewAuthor(Author author) {
		HashMap<String, Author> authors = readAuthorMap();
		String authorFirstName = author.getFirstName();
		if (authors == null)
			authors = new HashMap<String, Author>();
		authors.put(authorFirstName, author);
		saveToStorage(StorageType.AUTHORS, authors);
	}

	@Override
	public void saveNewBook(Book book) {
		// TODO Auto-generated method stub
		HashMap<String, Book> books = readBooksMap();
		if (books == null)
			books = new HashMap<String, Book>();
		String isbn = book.getIsbn();
		books.put(isbn, book);
		saveToStorage(StorageType.BOOKS, books);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Book> readBooksMap() {
		// Returns a Map with name/value pairs being
		// isbn -> Book
		return (HashMap<String, Book>) readFromStorage(StorageType.BOOKS);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, CheckoutRecordNew> readCheckoutMap() {

		return (HashMap<String, CheckoutRecordNew>) readFromStorage(StorageType.CHECKOUTRECORDS);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, LibraryMember> readMemberMap() {
		// Returns a Map with name/value pairs being
		// memberId -> LibraryMember
		return (HashMap<String, LibraryMember>) readFromStorage(StorageType.MEMBERS);
	}

	// i added here
	@SuppressWarnings("unchecked")
	public HashMap<String, Author> readAuthorMap() {
		// Returns a Map with name/value pairs being
		// memberId -> LibraryMember
		return (HashMap<String, Author>) readFromStorage(StorageType.AUTHORS);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, User> readUserMap() {
		// Returns a Map with name/value pairs being
		// userId -> User
		return (HashMap<String, User>) readFromStorage(StorageType.USERS);
	}

	@Override
	public void saveBookMap(HashMap<String, Book> books) {
		saveToStorage(StorageType.BOOKS, books);
	}

	@Override
	public void saveUserMap(HashMap<String, User> users) {
		saveToStorage(StorageType.USERS, users);
	}
	
	public void createUserMap() {
		HashMap<String, User> ht = new HashMap<String, User>();
		User u = new User("1", "111", Auth.LIBRARIAN);		
		ht.put("1", u);
		u = new User("2", "111", Auth.ADMIN);		
		ht.put("2", u);
		u = new User("3", "111", Auth.BOTH);		
		ht.put("3", u);
		saveToStorage(StorageType.USERS, ht);
	}

	@Override
	public void saveMemberMap(HashMap<String, LibraryMember> mems) {
		saveToStorage(StorageType.MEMBERS, mems);
	}

	///// load methods - these place test data into the storage area
	///// - used just once at startup
	@Override
	public void init() {
		saveToStorage(StorageType.BOOKS, null);
		saveToStorage(StorageType.USERS, null);
		saveToStorage(StorageType.MEMBERS, null);
		saveToStorage(StorageType.AUTHORS, null);
		saveToStorage(StorageType.CHECKOUTRECORDS, null);
	}

	static void loadBookMap(List<Book> bookList) {
		HashMap<String, Book> books = new HashMap<String, Book>();
		bookList.forEach(book -> books.put(book.getIsbn(), book));
		saveToStorage(StorageType.BOOKS, books);
	}

	static void loadUserMap(List<User> userList) {
		HashMap<String, User> users = new HashMap<String, User>();
		userList.forEach(user -> users.put(user.getId(), user));
		saveToStorage(StorageType.USERS, users);
	}

	static void loadMemberMap(List<LibraryMember> memberList) {
		HashMap<String, LibraryMember> members = new HashMap<String, LibraryMember>();
		memberList.forEach(member -> members.put(member.getMemberId(), member));
		saveToStorage(StorageType.MEMBERS, members);
	}

	static void loadCheckoutRecordMap(List<CheckoutRecordNew> checkoutRecords) {
		HashMap<String, CheckoutRecordNew> cRecords = new HashMap<String, CheckoutRecordNew>();
		checkoutRecords.forEach(r -> cRecords.put(r.getMemberId(), r));
		saveToStorage(StorageType.CHECKOUTRECORDS, cRecords);
	}

	// I added here
	static void loadAuthorMap(List<Author> authorList) {
		HashMap<String, Author> authors = new HashMap<String, Author>();

		authorList.forEach(author -> authors.put(author.getAuthorId(), author));
		saveToStorage(StorageType.AUTHORS, authors);
	}

	static void saveToStorage(StorageType type, Object ob) {
		ObjectOutputStream out = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			out = new ObjectOutputStream(Files.newOutputStream(path));
			out.writeObject(ob);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception e) {
				}
			}
		}
	}

	static Object readFromStorage(StorageType type) {
		ObjectInputStream in = null;
		Object retVal = null;
		try {
			Path path = FileSystems.getDefault().getPath(OUTPUT_DIR, type.toString());
			in = new ObjectInputStream(Files.newInputStream(path));
			retVal = in.readObject();
		} catch (Exception e) {
			//e.printStackTrace();
			;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}
		return retVal;
	}

	final static class Pair<S, T> implements Serializable {

		S first;
		T second;

		Pair(S s, T t) {
			first = s;
			second = t;
		}

		@Override
		public boolean equals(Object ob) {
			if (ob == null)
				return false;
			if (this == ob)
				return true;
			if (ob.getClass() != getClass())
				return false;
			@SuppressWarnings("unchecked")
			Pair<S, T> p = (Pair<S, T>) ob;
			return p.first.equals(first) && p.second.equals(second);
		}

		@Override
		public int hashCode() {
			return first.hashCode() + 5 * second.hashCode();
		}

		@Override
		public String toString() {
			return "(" + first.toString() + ", " + second.toString() + ")";
		}

		private static final long serialVersionUID = 5399827794066637059L;
	}

	// i added bellow method
	@Override
	public void addCheckoutRecord(CheckoutRecordNew checkoutRecord) {

		HashMap<String, CheckoutRecordNew> recs = readCheckoutMap();
		int copyNumber = checkoutRecord.getCopyNumber();
		if (recs == null)
			recs = new HashMap<String, CheckoutRecordNew>();
		recs.put(copyNumber + "", checkoutRecord);

		saveToStorage(StorageType.CHECKOUTRECORDS, recs);

	}

//	@SuppressWarnings("unchecked")
//	public HashMap<String, CheckoutRecordNew> readCheckoutMap() {
//		//Returns a Map with name/value pairs being
//		//   userId -> User
//		return (HashMap<String, CheckoutRecordNew>)readFromStorage(StorageType.CHECKOUTRECORDS);
//	}

}
