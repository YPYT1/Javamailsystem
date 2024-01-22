// MainFrame.java ���������

package javamailsystem;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Properties;
import java.io.*;
import javax.mail.*;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	JPanel contentPane;
	BorderLayout borderLayout1 = new BorderLayout();
	JToolBar jToolBar1 = new JToolBar();
	JButton propertiesSetButton = new JButton();
	JButton deleteEmailButton = new JButton();
	JButton receiveEmailButton = new JButton();
	JButton createEmailButton = new JButton();
	JSplitPane jSplitPane1 = new JSplitPane();

	static MailListPanel mailListPanel;
	static MailViewPanel mailViewPanel;

	// Construct the frame
	public MainFrame() {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Component initialization
	private void jbInit() throws Exception {
		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(borderLayout1);
		this.setSize(new Dimension(603, 502));
		this.setTitle("�ʼ��շ�ϵͳ�ͻ���");
		propertiesSetButton.setFont(new java.awt.Font("Dialog", 0, 14));
		propertiesSetButton.setPreferredSize(new Dimension(80, 40));
		propertiesSetButton.setText("POP3/SMTP����");
		propertiesSetButton.addActionListener(new MainFrame_propertiesSetButton_actionAdapter(this));
		deleteEmailButton.setFont(new java.awt.Font("Dialog", 0, 14));
		deleteEmailButton.setText("ɾ���ʼ�");
		deleteEmailButton.addActionListener(new MainFrame_deleteEmailButton_actionAdapter(this));
		receiveEmailButton.setFont(new java.awt.Font("Dialog", 0, 14));
		receiveEmailButton.setText("�����ʼ�");
		receiveEmailButton.addActionListener(new MainFrame_receiveEmailButton_actionAdapter(this));
		createEmailButton.setFont(new java.awt.Font("Dialog", 0, 14));
		createEmailButton.setText("�����ʼ�");
		createEmailButton.addActionListener(new MainFrame_createEmailButton_actionAdapter(this));
		jToolBar1.setBorder(BorderFactory.createEtchedBorder());
		jToolBar1.setMargin(new Insets(5, 5, 5, 5));
		jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		contentPane.add(jToolBar1, BorderLayout.NORTH);
		jToolBar1.add(createEmailButton, null);
		jToolBar1.addSeparator();
		jToolBar1.add(receiveEmailButton, null);
		jToolBar1.add(deleteEmailButton, null);
		jToolBar1.addSeparator();
		jToolBar1.add(propertiesSetButton, null);
		contentPane.add(jSplitPane1, BorderLayout.CENTER);
		jSplitPane1.setDividerLocation(200);

		// ��ʾ�ʼ��б�Panel,���ʼ���ʾ����Panel
		mailListPanel = new MailListPanel();
		mailViewPanel = new MailViewPanel(this);
		jSplitPane1.setTopComponent(mailListPanel);
		jSplitPane1.setBottomComponent(mailViewPanel);

	}

	// Overridden so we can exit when window is closed
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			System.exit(0);
		}
	}

	void receiveEmailButton_actionPerformed(ActionEvent e) {
		// ȡ��pop3.properties�ļ��е�pop3������趨
		String popHost = "", userName = "", password = "";
		Properties p = new Properties();
		try {
			FileInputStream in = new FileInputStream("pop3.properties");
			p.load(in);
			popHost = p.getProperty("pop3.host");
			userName = p.getProperty("pop3.username");
			password = p.getProperty("pop3.password");
			in.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// ������pop3��������session����
		p = new Properties();
		p.put("mail.pop3.host", popHost);
		Session session = Session.getInstance(p, null);
		session.setDebug(true);

		// ��ȡ�ʼ��������ϵ��ʼ��У������䴫�ݸ�MailListPanel��ʾ
		try {
			Store store = session.getStore("pop3");
			store.connect(popHost, userName, password);

			// ȡ��store�е�Folder�ʼ���
			Folder folder = store.getFolder("Inbox");
			// ��folder���ݸ�MailTableModel,ʹ��folder�ļ��е��ʼ���ʾ��MailListPanel��
			mailListPanel.model.setFolder(folder);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "����������ʧ�ܣ���鿴�������ӻ��ʼ����������á�", "������Ϣ", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}

	void propertiesSetButton_actionPerformed(ActionEvent e) {
		ProfileDialog profileDialog = new ProfileDialog(this, "�ʼ���������������", false);
		profileDialog.show();
	}

	void createEmailButton_actionPerformed(ActionEvent e) {
		NewMailFrame newMailFrame = new NewMailFrame();
		newMailFrame.setSize(500, 400);
		newMailFrame.show();
	}

	void deleteEmailButton_actionPerformed(ActionEvent e) {
		// ��λҪ��ɾ�����ļ�
		int selectedIndex = mailListPanel.jTable1.getSelectedRow();
		// ����MailTableModel���е�ɾ���������ʼ�ɾ������������ʾ
		mailListPanel.model.deleteEmail(selectedIndex);
	}
}

class MainFrame_receiveEmailButton_actionAdapter implements java.awt.event.ActionListener {
	MainFrame adaptee;

	MainFrame_receiveEmailButton_actionAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.receiveEmailButton_actionPerformed(e);
	}
}

class MainFrame_propertiesSetButton_actionAdapter implements java.awt.event.ActionListener {
	MainFrame adaptee;

	MainFrame_propertiesSetButton_actionAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.propertiesSetButton_actionPerformed(e);
	}
}

class MainFrame_createEmailButton_actionAdapter implements java.awt.event.ActionListener {
	MainFrame adaptee;

	MainFrame_createEmailButton_actionAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.createEmailButton_actionPerformed(e);
	}
}

class MainFrame_deleteEmailButton_actionAdapter implements java.awt.event.ActionListener {
	MainFrame adaptee;

	MainFrame_deleteEmailButton_actionAdapter(MainFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.deleteEmailButton_actionPerformed(e);
	}
}
