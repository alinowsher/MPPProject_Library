package business;

import java.time.LocalDate;

public class CheckoutRecordNew {
	
	private int copyNumber;
	private String memberID;
	private LocalDate issueDate;
	private LocalDate dueDate;
	

	public CheckoutRecordNew (BookCopy bookCopy, LocalDate issueDate, String memberID) {
		copyNumber = bookCopy.getCopyNum();
		this.memberID=memberID;
		this.issueDate = issueDate;		
		dueDate = issueDate.plusDays(bookCopy.getBook().getMaxCheckoutLength());
	}
	
	public int getCopyNumber() {
		return copyNumber;
	}
	
	public String getMemberId() {
		return memberID;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}
	
	public LocalDate getDueDate() {
		return dueDate;
	}
}
