// AttachmentDialog.java �����������

package javamailsystem;

import java.awt.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;

import java.util.*;
import java.awt.event.*;
import java.io.*;

public class AttachmentDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	JPanel panel1 = new JPanel();
	XYLayout xYLayout1 = new XYLayout();
	JLabel jLabel1 = new JLabel();
	JButton jButton1 = new JButton();
	JButton jButton2 = new JButton();

	Vector attachmentFiles;
	Vector attachmentInputStream;
	JList attachmentList = new JList();

	MainFrame frame;

	public AttachmentDialog(MainFrame frame, String title, boolean modal) {
		super(frame, title, modal);
		this.frame = frame;
		try {
			jbInit();
			pack();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public AttachmentDialog() {
		this(null, "", false);
	}

	private void jbInit() throws Exception {
		panel1.setLayout(xYLayout1);
		panel1.setMinimumSize(new Dimension(350, 200));
		panel1.setPreferredSize(new Dimension(350, 200));
		jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabel1.setIcon(null);
		jLabel1.setText("�����б�");
		jButton1.setFont(new java.awt.Font("Dialog", 0, 12));
		jButton1.setIcon(null);
		jButton1.setText("��   ��");
		jButton1.addActionListener(new AttachmentDialog_jButton1_actionAdapter(this));
		jButton2.setFont(new java.awt.Font("Dialog", 0, 12));
		jButton2.setText("ȡ   ��");
		jButton2.addActionListener(new AttachmentDialog_jButton2_actionAdapter(this));
		attachmentList.setPreferredSize(new Dimension(200, 150));
		panel1.add(attachmentList, new XYConstraints(43, 51, 175, 103));
		panel1.add(jButton1, new XYConstraints(246, 67, -1, -1));
		// panel1.add(jButton2, new XYConstraints(246, 109, -1, -1));
		panel1.add(jLabel1, new XYConstraints(39, 25, 71, 23));
		this.getContentPane().add(panel1, BorderLayout.NORTH);
	}

	void setAttachment(Vector attachmentFiles, Vector attachmentInputStream) {
		this.attachmentFiles = attachmentFiles;
		this.attachmentInputStream = attachmentInputStream;
		// �������ļ������뵽list��
		attachmentList.setListData(attachmentFiles);
	}

	void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(true);
	}

	void jButton1_actionPerformed(ActionEvent e) {
		int fileIndex = attachmentList.getSelectedIndex();
		String fileName = attachmentList.getSelectedValue().toString();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setSelectedFile(new File(fileName));
		int operation = fileChooser.showSaveDialog(frame);
		if (operation == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			// �����ļ�
			boolean saveOrNot = true;
			// �����ļ�
			if (file.exists()) {
				int choice = JOptionPane.showConfirmDialog(frame, "���ļ��Ѿ����ڣ���Ҫ������", "��Ϣ��ʾ",
						JOptionPane.OK_CANCEL_OPTION);
				switch (choice) {
				case 0: // �����ļ�
					break;
				default: // ������
					saveOrNot = false;
				}
			}
			if (saveOrNot) {// �����ļ�
				try {
					FileOutputStream fileOut = new FileOutputStream(file);
					InputStream fileIn = (InputStream) attachmentInputStream.get(fileIndex);
					int buffer;
					while ((buffer = fileIn.read()) != -1) {
						fileOut.write(buffer);
					}
					fileOut.close();
					fileIn.close();
				} catch (Exception ex) {
					System.out.println("�����������");
				}
			}
		}
	}
}

class AttachmentDialog_jButton2_actionAdapter implements java.awt.event.ActionListener {
	AttachmentDialog adaptee;

	AttachmentDialog_jButton2_actionAdapter(AttachmentDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButton2_actionPerformed(e);
	}
}

class AttachmentDialog_jButton1_actionAdapter implements java.awt.event.ActionListener {
	AttachmentDialog adaptee;

	AttachmentDialog_jButton1_actionAdapter(AttachmentDialog adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButton1_actionPerformed(e);
	}
}
