package librarySystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;
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
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import dataAccess.DataAccess;
import dataAccess.DataAccessFacade;

public class AddMember extends JFrame implements LibWindow {

	public static final AddMember INSTANCE = new AddMember();
	ControllerInterface ci = new SystemController();
	private boolean isInitialized = false;

	private JTextField txtMemberID, txtFirstName, txtLastName, txtTelephone, txtStreet, txtCity, txtState, txtZip;

	JTable jt;

	private AddMember() {
	}

	@Override
	public void init() {
		isInitialized = true;
		JPanel pnlMemberFields = new JPanel();

		pnlMemberFields.setLayout(null);

		// left
		JLabel lblMemberID = new JLabel("Member ID:");
		lblMemberID.setBounds(20, 20, 100, 20);

		txtMemberID = new JTextField(10);
		txtMemberID.setBounds(100, 20, 100, 20);

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

		// right

		JLabel lblStreet = new JLabel("Street:");
		lblStreet.setBounds(230, 20, 100, 20);

		txtStreet = new JTextField(10);
		txtStreet.setBounds(280, 20, 100, 20);

		JLabel lblCity = new JLabel("City:");
		lblCity.setBounds(230, 50, 100, 20);

		txtCity = new JTextField(10);
		txtCity.setBounds(280, 50, 100, 20);

		JLabel lblState = new JLabel("State:");
		lblState.setBounds(230, 80, 100, 20);

		txtState = new JTextField(10);
		txtState.setBounds(280, 80, 100, 20);

		JLabel lblZip = new JLabel("Zip:");
		lblZip.setBounds(230, 110, 100, 20);

		txtZip = new JTextField(10);
		txtZip.setBounds(280, 110, 100, 20);

		// left
		pnlMemberFields.add(lblMemberID);
		pnlMemberFields.add(txtMemberID);

		pnlMemberFields.add(lblFirstName);
		pnlMemberFields.add(txtFirstName);

		pnlMemberFields.add(lblLastName);
		pnlMemberFields.add(txtLastName);

		pnlMemberFields.add(lblTelephone);
		pnlMemberFields.add(txtTelephone);

		// right
		pnlMemberFields.add(lblStreet);
		pnlMemberFields.add(txtStreet);

		pnlMemberFields.add(lblCity);
		pnlMemberFields.add(txtCity);

		pnlMemberFields.add(lblState);
		pnlMemberFields.add(txtState);

		pnlMemberFields.add(lblZip);
		pnlMemberFields.add(txtZip);

		JPanel pnlButtonSave = new JPanel();

		JButton btnSave = new JButton("Save");
		addMemberButtonListener(btnSave);

		JButton btnBacktoMain = new JButton("<< Back to Main");
		addBackButtonListener(btnBacktoMain);

		pnlButtonSave.add(btnBacktoMain);
		pnlButtonSave.add(btnSave);
		pnlButtonSave.setBackground(Color.gray);
		pnlMemberFields.add(pnlButtonSave, BorderLayout.CENTER);

		pnlButtonSave.setBounds(20, 150, 360, 35);

		DefaultTableModel model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};
		model.addColumn("Member ID");
		model.addColumn("Full Name");
		model.addColumn("City");

		jt = new JTable(model);

		JScrollPane sp = new JScrollPane(jt);
		sp.setBounds(20, 200, 360, 150);
		pnlMemberFields.add(sp);

		// load members
		List<LibraryMember> dataa = ci.getAllMembers();

		for (LibraryMember lm : dataa) {
			model.addRow(new Object[] { lm.getMemberId(), lm.getFirstName() + " " + lm.getLastName(),
					lm.getAddress().getCity() });
		}

		this.setTitle("Add/Edit Library Member");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// this.setLayout(null);
		this.setSize(420, 420);
		this.setVisible(true);
		this.add(pnlMemberFields);
	}

	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			resetFields();
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		});
	}

	private void addMemberButtonListener(JButton butn) {
		butn.addActionListener(evt -> {

//			if (txtMemberID.getText().trim().equals("") || txtFirstName.getText().trim().equals("")
//					|| txtLastName.getText().trim().equals("") || txtTelephone.getText().equals("")
//					|| txtStreet.getText().equals("") || txtCity.getText().equals("")
//					|| txtState.getText().trim().equals("") || txtZip.getText().equals("")) {
//				JOptionPane.showMessageDialog(this, "Fields must be entered", "Fields can not be left empty.",
//						JOptionPane.ERROR_MESSAGE);
//
//				return;
//			}

			try {
				LibraryMember member = new LibraryMember(txtMemberID.getText().trim(), txtFirstName.getText().trim(),
						txtLastName.getText().trim(), txtTelephone.getText().trim(),
						new Address(txtStreet.getText().trim(), txtCity.getText().trim(), txtState.getText().trim(),
								txtZip.getText().trim()));

				ci.addLibraryMember(member);

				DefaultTableModel model = (DefaultTableModel) jt.getModel();

//				for(LibraryMember lm :dataa) {
//					
//				}

				model.addRow(new Object[] { member.getMemberId(), member.getFirstName() + " " + member.getLastName(),
						member.getAddress().getCity() });

				jt.setRowSelectionInterval(model.getRowCount() - 1, model.getRowCount() - 1);
				jt.scrollRectToVisible(new Rectangle(jt.getCellRect(model.getRowCount() - 1, 0, true)));

			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(this, "Fields must be entered", "Fields can not be left empty.",
						JOptionPane.ERROR_MESSAGE);
				return;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Save Failed", JOptionPane.ERROR_MESSAGE);
				return;
			}

			resetFields();

			JOptionPane.showMessageDialog(this, "Save successful");
		});
	}

	public void resetFields() {
		txtMemberID.setText("");
		txtFirstName.setText("");
		txtLastName.setText("");
		txtTelephone.setText("");
		txtStreet.setText("");
		txtCity.setText("");
		txtState.setText("");
		txtZip.setText("");
	}

	@Override
	public boolean isInitialized() {

		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;

	}

}
