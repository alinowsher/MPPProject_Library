package business;

import java.time.LocalDate;
import java.util.*;

import dataAccess.DataAccess;
import dataAccess.DataAccessFacade;

public class Main {

	public static void main(String[] args) {
		System.out.println(allWhoseZipContains3());
		System.out.println(allHavingOverdueBook());
		System.out.println(allHavingMultipleAuthors());

	}

	// Returns a list of all ids of LibraryMembers whose zipcode contains the digit
	// 3
	public static List<String> allWhoseZipContains3() {
		DataAccess da = new DataAccessFacade();
		Collection<LibraryMember> members = da.readMemberMap().values();
		List<LibraryMember> mems = new ArrayList<>();
		mems.addAll(members);
		// implement
		return null;

	}

	// Returns a list of all ids of LibraryMembers that have an overdue book
	public static List<String> allHavingOverdueBook() {
		DataAccess da = new DataAccessFacade();
		Collection<LibraryMember> members = da.readMemberMap().values();
		List<LibraryMember> mems = new ArrayList<>();
		mems.addAll(members);
		// implement
		List<CheckoutRecordNew> chkList = (new SystemController()).getCheckoutRecords();
		System.out.println("Below members have checkout overdue:");
		for (LibraryMember libraryMember : mems) {
			for (CheckoutRecordNew checkoutRecordNew : chkList) {
				if (libraryMember.getMemberId().equals(checkoutRecordNew.getMemberId())
						&& checkoutRecordNew.getDueDate().isAfter(LocalDate.now())) {
					System.out.println("Member Name: '" + libraryMember.getFirstName() +" "+ libraryMember.getLastName()+"' "+" Due date was: "+checkoutRecordNew.getDueDate());
				}				
			}
		}
		return null;

	}

	// Returns a list of all isbns of Books that have multiple authors
	public static List<String> allHavingMultipleAuthors() {
		DataAccess da = new DataAccessFacade();
		Collection<Book> books = da.readBooksMap().values();
		List<Book> bs = new ArrayList<>();
		bs.addAll(books);
		// implement
		return null;

	}

}
