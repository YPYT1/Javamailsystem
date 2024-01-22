// ProfileDialog.java �����ʼ���������POP/SMTP��������

package javamailsystem;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import javax.swing.border.*;

import java.util.Properties;
import java.io.*;
import java.awt.event.*;

public class ProfileDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	Border border1;
	Border border2;
	BorderLayout borderLayout1 = new BorderLayout();
	JLabel jLabel1 = new JLabel();
	JLabel jLabel2 = new JLabel();
	JLabel jLabel3 = new JLabel();
	JLabel jLabel4 = new JLabel();
	JLabel jLabel5 = new JLabel();
	JLabel jLabel6 = new JLabel();
	JLabel jLabel7 = new JLabel();
	JLabel jLabel8 = new JLabel();
	JPanel jPanel1 = new JPanel();
	JPanel jPanel2 = new JPanel();
	JPanel jPanel3 = new JPanel();
	JPanel jPanel4 = new JPanel();
	JTabbedPane jTabbedPane1 = new JTabbedPane();
	JPanel panel1 = new JPanel();
	JTextField popAddressField = new JTextField();
	JButton popCancelButton = new JButton();
	JTextField popHostField = new JTextField();
	JButton popOkButton = new JButton();
	JPasswordField popPassword = new JPasswordField();
	JTextField popUserNameField = new JTextField();
	JTextField smtpAddressField = new JTextField();
	JButton smtpCancelCancel = new JButton();
	JTextField smtpHostField = new JTextField();
	JButton smtpOkButton = new JButton();
	JPasswordField smtpPassword = new JPasswordField();
	JTextField smtpUserNameField = new JTextField();
	TitledBorder titledBorder1;
	XYLayout xYLayout1 = new XYLayout();
	XYLayout xYLayout2 = new XYLayout();
	XYLayout xYLayout3 = new XYLayout();
	XYLayout xYLayout4 = new XYLayout();

	public ProfileDialog() {
		this(null, "", false);
	}

	public ProfileDialog(Frame frame, String title, boolean modal) {
		super(frame, title, modal);
		try {
			jbInit();
			pack();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		border1 = BorderFactory.createLineBorder(SystemColor.controlText, 1);
		titledBorder1 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(148, 145, 140)),
				"POP3����");
		border2 = BorderFactory.createCompoundBorder(
				new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(148, 145, 140)), ""),
				BorderFactory.createEmptyBorder(6, 6, 6, 6));
		panel1.setLayout(borderLayout1);
		panel1.setPreferredSize(new Dimension(400, 300));
		jPanel1.setLayout(xYLayout1);
		jPanel3.setBorder(border2);
		jPanel3.setLayout(xYLayout2);
		popOkButton.setFont(new java.awt.Font("Dialog", 0, 13));
		popOkButton.setText("ȷ   ��");
		popOkButton.addActionListener(new ProfileDialog_popOkButton_actionAdapter(this));
		popCancelButton.setText("ȡ   ��");
		popCancelButton.addActionListener(new ProfileDialog_popOkButton_actionAdapter(this));
		popCancelButton.setFont(new java.awt.Font("Dialog", 0, 13));

		jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabel1.setText("POP3��������");
		popHostField.setFont(new java.awt.Font("Dialog", 0, 12));
		popHostField.setText("");
		jLabel3.setText("��   ��   ����");
		jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
		popUserNameField.setFont(new java.awt.Font("Dialog", 0, 12));
		popUserNameField.setText("");
		jLabel4.setText("��           �룺");
		jLabel4.setFont(new java.awt.Font("Dialog", 0, 12));
		jPanel2.setLayout(xYLayout3);
		jLabel5.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabel5.setText("��           �룺");

		jLabel7.setText("SMTP��������");
		jLabel7.setFont(new java.awt.Font("Dialog", 0, 12));
		smtpHostField.setText("");
		smtpHostField.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabel8.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabel8.setText("��   ��   ����");
		smtpUserNameField.setText("");
		smtpUserNameField.setFont(new java.awt.Font("Dialog", 0, 12));
		jPanel4.setLayout(xYLayout4);
		jPanel4.setBorder(border2);
		smtpCancelCancel.setFont(new java.awt.Font("Dialog", 0, 13));
		smtpCancelCancel.setText("ȡ   ��");
		smtpCancelCancel.addActionListener(new ProfileDialog_smtpOkButton_actionAdapter(this));
		smtpOkButton.setText("ȷ   ��");
		smtpOkButton.addActionListener(new ProfileDialog_smtpOkButton_actionAdapter(this));
		smtpOkButton.setFont(new java.awt.Font("Dialog", 0, 13));
		popPassword.setText("");
		smtpPassword.setText("");
		jLabel2.setText("Emailַַ��");
		jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabel6.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabel6.setText("Emailַַ��");
		getContentPane().add(panel1);
		panel1.add(jTabbedPane1, BorderLayout.CENTER);
		jTabbedPane1.add(jPanel1, "POP3����");
		jPanel3.add(popPassword, new XYConstraints(108, 126, 235, 30));
		jPanel3.add(jLabel4, new XYConstraints(17, 125, 84, 29));
		jPanel3.add(popUserNameField, new XYConstraints(108, 84, 233, 28));
		jPanel3.add(jLabel3, new XYConstraints(17, 83, 84, 29));
		jPanel3.add(popAddressField, new XYConstraints(108, 42, 233, 28));
		jPanel3.add(jLabel2, new XYConstraints(17, 42, 84, 29));
		jPanel3.add(popHostField, new XYConstraints(108, 0, 233, 28));
		jPanel3.add(jLabel1, new XYConstraints(17, 0, 84, 29));
		jPanel1.add(popOkButton, new XYConstraints(91, 229, 84, 28));
		jPanel1.add(popCancelButton, new XYConstraints(224, 229, 84, 28));
		jPanel1.add(jPanel3, new XYConstraints(13, 21, 374, 199));
		jTabbedPane1.add(jPanel2, "SMTP����");
		jPanel4.add(smtpHostField, new XYConstraints(110, 0, 233, 28));
		jPanel4.add(jLabel7, new XYConstraints(19, 0, 84, 29));
		jPanel2.add(jPanel4, new XYConstraints(9, 22, 377, 197));
		jPanel2.add(smtpOkButton, new XYConstraints(90, 232, 84, 28));
		jPanel2.add(smtpCancelCancel, new XYConstraints(220, 232, 84, 28));
		jPanel4.add(smtpPassword, new XYConstraints(110, 126, 234, 29));
		jPanel4.add(jLabel5, new XYConstraints(19, 126, 84, 29));
		jPanel4.add(jLabel8, new XYConstraints(19, 84, 84, 29));
		jPanel4.add(smtpUserNameField, new XYConstraints(110, 84, 233, 28));
		jPanel4.add(smtpAddressField, new XYConstraints(110, 42, 233, 28));
		jPanel4.add(jLabel6, new XYConstraints(19, 42, 84, 29));

		loadPopProperties();
		loadSmtpProperties();

	}
	//

	void loadPopProperties() {
		Properties p = new Properties();
		try {
			// װ��pop3����
			FileInputStream in = new FileInputStream("pop3.properties");
			p.load(in);
			popHostField.setText(p.getProperty("pop3.host"));
			popAddressField.setText(p.getProperty("pop3.address"));
			popUserNameField.setText(p.getProperty("pop3.username"));
			popPassword.setText(p.getProperty("pop3.password"));

			in.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void loadSmtpProperties() {
		Properties p = new Properties();
		try {
			// װ��smtp����
			FileInputStream in = new FileInputStream("smtp.properties");
			p.load(in);
			smtpHostField.setText(p.getProperty("smtp.host"));
			smtpAddressField.setText(p.getProperty("smtp.address"));
			smtpUserNameField.setText(p.getProperty("smtp.username"));
			smtpPassword.setText(p.getProperty("smtp.password"));
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void popCancelButton_actionPerformed(ActionEvent e) {
		loadPopProperties();
	}

	void popOkButton_actionPerformed(ActionEvent e) {
		savePopProperties();
	}

	void savePopProperties() {
		Properties p = new Properties();
		try {
			// ����pop3����
			FileOutputStream out = new FileOutputStream("pop3.properties");

			p.setProperty("pop3.host", popHostField.getText());
			p.setProperty("pop3.address", popAddressField.getText());
			p.setProperty("pop3.username", popUserNameField.getText());
			p.setProperty("pop3.password", popPassword.getText());

			p.store(out, null);
			out.close();
			this.hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void saveSmtpProperties() {
		Properties p = new Properties();
		try {
			FileOutputStream out = new FileOutputStream("smtp.properties");
			p.setProperty("smtp.host", smtpHostField.getText());
			p.setProperty("smtp.address", smtpAddressField.getText());
			p.setProperty("smtp.username", smtpUserNameField.getText());
			p.setProperty("smtp.password", smtpPassword.getText());
			p.store(out, null);
			out.close();
			this.hide();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void smtpCancelCancel_actionPerformed(ActionEvent e) {
		loadSmtpProperties();
	}

	void smtpOkButton_actionPerformed(ActionEvent e) {
		saveSmtpProperties();
	}
}

class ProfileDialog_popCancelButton_actionAdapter implements java.awt.event.ActionListener {
	ProfileDialog adaptee;

	ProfileDialog_popCancelButton_actionAdapter(ProfileDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.popCancelButton_actionPerformed(e);
	}
}

class ProfileDialog_popOkButton_actionAdapter implements java.awt.event.ActionListener {
	ProfileDialog adaptee;

	ProfileDialog_popOkButton_actionAdapter(ProfileDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.popOkButton_actionPerformed(e);
	}
}

class ProfileDialog_smtpCancelCancel_actionAdapter implements java.awt.event.ActionListener {
	ProfileDialog adaptee;

	ProfileDialog_smtpCancelCancel_actionAdapter(ProfileDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.smtpCancelCancel_actionPerformed(e);
	}
}

class ProfileDialog_smtpOkButton_actionAdapter implements java.awt.event.ActionListener {
	ProfileDialog adaptee;

	ProfileDialog_smtpOkButton_actionAdapter(ProfileDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.smtpOkButton_actionPerformed(e);
	}
}