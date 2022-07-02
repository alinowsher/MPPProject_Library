package librarySystem;

import java.awt.*;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import business.Address;
import business.Author;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;

public class AddBookAuthor extends JFrame implements LibWindow {

	public static final AddBookAuthor INSTANCE = new AddBookAuthor();
	ControllerInterface ci = new SystemController();
	private boolean isInitialized = false;

	private JTextField txtAuthorID, txtFirstName, txtLastName, txtTelephone, txtStreet, txtCity, txtState, txtZip,
			txtCredentials, txtShortBio;

	private JTable jt;

	private AddBookAuthor() {
	}

	@Override
	public void init() {

		JPanel pnlAuthorFields = new JPanel();

		pnlAuthorFields.setLayout(null);

		// left
		JLabel lblAuthorID = new JLabel("Author ID:");
		lblAuthorID.setBounds(20, 20, 100, 20);

		txtAuthorID = new JTextField(10);
		txtAuthorID.setBounds(100, 20, 100, 20);

		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(20, 50, 100, 20);

		txtFirstName = new JTextField(10);
		txtFirstName.setBounds(100, 50, 100, 20);

		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(20, 80, 100, 20);

		txtLastName = new JTextField(10);
		txtLastName.setBounds(100, 80, 100, 20);

		JLabel lblTelephone = new JLabel("Telephone:");
		lblTelephone.setBounds(20, 110, 100, 20);

		txtTelephone = new JTextField(10);
		txtTelephone.setBounds(100, 110, 100, 20);

		JLabel lblCredentials = new JLabel("Credentials:");
		lblCredentials.setBounds(20, 140, 100, 20);

		txtCredentials = new JTextField(10);
		txtCredentials.setBounds(100, 140, 100, 20);

		// right

		JLabel lblStreet = new JLabel("Street:");
		lblStreet.setBounds(230, 20, 100, 20);

		txtStreet = new JTextField(10);
		txtStreet.setBounds(290, 20, 100, 20);

		JLabel lblCity = new JLabel("City:");
		lblCity.setBounds(230, 50, 100, 20);

		txtCity = new JTextField(10);
		txtCity.setBounds(290, 50, 100, 20);

		JLabel lblState = new JLabel("State:");
		lblState.setBounds(230, 80, 100, 20);

		txtState = new JTextField(10);
		txtState.setBounds(290, 80, 100, 20);

		JLabel lblZip = new JLabel("Zip:");
		lblZip.setBounds(230, 110, 100, 20);

		txtZip = new JTextField(10);
		txtZip.setBounds(290, 110, 100, 20);

		JLabel lblShortBio = new JLabel("Short Bio:");
		lblShortBio.setBounds(230, 140, 100, 20);

		txtShortBio = new JTextField(10);
		txtShortBio.setBounds(290, 140, 100, 20);

		// left
		pnlAuthorFields.add(lblAuthorID);
		pnlAuthorFields.add(txtAuthorID);

		pnlAuthorFields.add(lblFirstName);
		pnlAuthorFields.add(txtFirstName);

		pnlAuthorFields.add(lblLastName);
		pnlAuthorFields.add(txtLastName);

		pnlAuthorFields.add(lblTelephone);
		pnlAuthorFields.add(txtTelephone);

		pnlAuthorFields.add(lblCredentials);
		pnlAuthorFields.add(txtCredentials);

		// right
		pnlAuthorFields.add(lblStreet);
		pnlAuthorFields.add(txtStreet);

		pnlAuthorFields.add(lblCity);
		pnlAuthorFields.add(txtCity);

		pnlAuthorFields.add(lblState);
		pnlAuthorFields.add(txtState);

		pnlAuthorFields.add(lblZip);
		pnlAuthorFields.add(txtZip);

		pnlAuthorFields.add(lblShortBio);
		pnlAuthorFields.add(txtShortBio);

		JPanel pnlButtonSave = new JPanel();

		JButton btnSave = new JButton("Save Author");
		addAuthorButtonListener(btnSave);

		JButton btnBacktoMain = new JButton("<< Back to Main");
		addBackButtonListener(btnBacktoMain);

		pnlButtonSave.add(btnBacktoMain);
		pnlButtonSave.add(btnSave);
		//pnlButtonSave.setBackground(Color.green);
		pnlAuthorFields.add(pnlButtonSave, BorderLayout.CENTER);

		pnlButtonSave.setBounds(20, 170, 360, 35);

		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Author ID");
		model.addColumn("Full Name");
		model.addColumn("City");

		jt = new JTable(model);

		JScrollPane sp = new JScrollPane(jt);
		sp.setBounds(20, 220, 360, 150);
		pnlAuthorFields.add(sp);

		// loading authors

		List<Author> dataa = ci.getAllAuthors();

		try {
			for (Author a : dataa) {
				model.addRow(new Object[] { a.getAuthorId(), a.getFirstName() + " " + a.getLastName(),
						a.getAddress().getCity() });
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		this.setTitle("Library Author");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setLayout(null);
		this.setSize(450, 450);
		this.setVisible(true);
		this.add(pnlAuthorFields);

		this.setTitle("Add Book Author");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.setSize(420, 420);
		this.setVisible(true);

	}

	// button listeners

	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		});
	}

	private void addAuthorButtonListener(JButton butn) {
		butn.addActionListener(evt -> {

			if (txtAuthorID.getText().trim().equals("") || txtFirstName.getText().trim().equals("")
					|| txtLastName.getText().trim().equals("") || txtTelephone.getText().equals("")
					|| txtStreet.getText().equals("") || txtCity.getText().equals("")
					|| txtState.getText().trim().equals("") || txtZip.getText().equals("")
					|| txtCredentials.getText().equals("") || txtShortBio.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Fields must be entered", "Fields can not be left empty.",
						JOptionPane.ERROR_MESSAGE);

				return;
			}

			try {

				Address addrs = new Address(txtStreet.getText(), txtCity.getText(), txtState.getText(),
						txtZip.getText());
				Author author = new Author(txtAuthorID.getText().trim(), txtFirstName.getText().trim(),
						txtLastName.getText().trim(), txtTelephone.getText(), addrs, txtShortBio.getText());

				ci.addAuthor(author);

				DefaultTableModel model = (DefaultTableModel) jt.getModel();

				model.addRow(new Object[] { txtAuthorID.getText().trim(),
						txtFirstName.getText() + " " + txtLastName.getText(), txtCity.getText() });

				jt.setRowSelectionInterval(model.getRowCount() - 1, model.getRowCount() - 1);
				jt.scrollRectToVisible(new Rectangle(jt.getCellRect(model.getRowCount() - 1, 0, true)));

			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Save Failed", JOptionPane.ERROR_MESSAGE);
			}

			txtAuthorID.setText("");
			txtFirstName.setText("");
			txtLastName.setText("");
			txtTelephone.setText("");
			txtStreet.setText("");
			txtCity.setText("");
			txtState.setText("");
			txtZip.setText("");
			txtShortBio.setText("");
			txtCredentials.setText("");

			JOptionPane.showMessageDialog(this, "Save successful");
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

}
