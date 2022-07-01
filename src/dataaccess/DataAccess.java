package dataaccess;

import java.util.HashMap;

import business.Author;
import business.Book;
import business.CheckoutRecord;
import business.CheckoutRecordNew;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;

public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public HashMap<String, CheckoutRecordNew> readCheckoutMap();
	
	
	// i added here
	public HashMap<String, Author> readAuthorMap();
	
	public void saveNewMember(LibraryMember member);
	public void saveNewBook(Book book);
	public void saveBookMap(HashMap<String, Book> books);
	public void saveUserMap(HashMap<String, User> users);
	public void saveMemberMap(HashMap<String, LibraryMember> members);
	
	//i changed here
	public void saveNewAuthor(Author author);
	public void addCheckoutRecord(CheckoutRecordNew checkoutRecord);
	
	
	public void init();
}
