package business;

import java.io.Serializable;
import java.time.LocalDate;

final public class CheckoutRecordNew implements Serializable {
	private static final long serialVersionUID = 1L;	
	
	private int copyNumber;
	private String memberID;
	private LocalDate issueDate;
	private LocalDate dueDate;

	public CheckoutRecordNew(BookCopy bookCopy, LocalDate issueDate, String memberID) {
		copyNumber = bookCopy.getCopyNum();
		this.memberID = memberID;
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
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
