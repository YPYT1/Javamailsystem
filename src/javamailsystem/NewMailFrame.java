// NewMailFrame.java 发送新邮件

package javamailsystem;

import javax.swing.*;
import com.borland.jbcl.layout.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.*;
import java.util.Vector;
import java.util.StringTokenizer;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.util.Date;
import sun.misc.BASE64Encoder;

public class NewMailFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	BorderLayout borderLayout1 = new BorderLayout();
	JToolBar jToolBar1 = new JToolBar();
	JButton sendButton = new JButton();
	JPanel jPanel1 = new JPanel();
	BorderLayout borderLayout2 = new BorderLayout();
	JPanel jPanel2 = new JPanel();
	JPanel jPanel3 = new JPanel();
	XYLayout xYLayout1 = new XYLayout();
	JLabel jLabel1 = new JLabel();
	JTextField toTextField = new JTextField();
	JLabel jLabel2 = new JLabel();
	JTextField ccTextField = new JTextField();
	JLabel jLabel3 = new JLabel();
	JTextField subjectTextField = new JTextField();
	JLabel jLabel4 = new JLabel();
	JTextField attachTextField = new JTextField();
	JButton attachmentButton = new JButton();
	BorderLayout borderLayout3 = new BorderLayout();
	JScrollPane jScrollPane1 = new JScrollPane();
	JTextArea contentArea = new JTextArea();

	public NewMailFrame() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.getContentPane().setLayout(borderLayout1);
		sendButton.setFont(new java.awt.Font("Dialog", 0, 13));
		sendButton.setPreferredSize(new Dimension(60, 30));
		sendButton.setText("发   送");
		sendButton.addActionListener(new NewMailFrame_sendButton_actionAdapter(this));
		jPanel1.setBorder(BorderFactory.createEtchedBorder());
		jPanel1.setLayout(borderLayout2);
		this.setTitle("创建与发送邮件");
		jPanel2.setBorder(BorderFactory.createEtchedBorder());
		jPanel2.setPreferredSize(new Dimension(500, 150));
		jPanel2.setLayout(xYLayout1);
		jPanel3.setBorder(BorderFactory.createEtchedBorder());
		jPanel3.setLayout(borderLayout3);
		jLabel1.setFont(new java.awt.Font("Dialog", 0, 12));
		jLabel1.setText("收件人：");
		toTextField.setFont(new java.awt.Font("Dialog", 0, 13));
		toTextField.setText("");
		jLabel2.setText("抄   送：");
		jLabel2.setFont(new java.awt.Font("Dialog", 0, 12));
		ccTextField.setText("");
		ccTextField.setFont(new java.awt.Font("Dialog", 0, 13));
		jLabel3.setText("主   题：");
		jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
		subjectTextField.setText("");
		subjectTextField.setFont(new java.awt.Font("Dialog", 0, 13));
		jLabel4.setText("附   件：");
		jLabel4.setFont(new java.awt.Font("Dialog", 0, 12));
		attachTextField.setText("");
		attachTextField.setFont(new java.awt.Font("Dialog", 0, 13));
		attachmentButton.setFont(new java.awt.Font("Dialog", 0, 12));
		attachmentButton.setText("添   加");
		attachmentButton.addActionListener(new NewMailFrame_attachmentButton_actionAdapter(this));
		contentArea.setText("");
		this.getContentPane().add(jToolBar1, BorderLayout.NORTH);
		this.getContentPane().add(jPanel1, BorderLayout.CENTER);
		jPanel1.add(jPanel2, BorderLayout.NORTH);
		jPanel2.add(jLabel1, new XYConstraints(22, 13, 65, 27));
		jPanel2.add(jLabel2, new XYConstraints(22, 46, 65, 27));
		jPanel2.add(jLabel3, new XYConstraints(22, 79, 65, 27));
		jPanel2.add(jLabel4, new XYConstraints(22, 112, 65, 27));
		jPanel2.add(subjectTextField, new XYConstraints(93, 79, 349, 29));
		jPanel2.add(toTextField, new XYConstraints(93, 14, 347, 29));
		jPanel2.add(ccTextField, new XYConstraints(93, 46, 348, 29));
		jPanel2.add(attachTextField, new XYConstraints(93, 113, 276, 29));
		jPanel2.add(attachmentButton, new XYConstraints(373, 114, 69, 26));
		jPanel1.add(jPanel3, BorderLayout.CENTER);
		jToolBar1.add(sendButton, null);
		jPanel3.add(jScrollPane1, BorderLayout.CENTER);
		jScrollPane1.getViewport().add(contentArea, null);
	}

	void sendButton_actionPerformed(ActionEvent e) {
		Session session = null;
		Transport transport = null;
		MimeMessage msg = null;
		// 获取邮件相关信息
		String to = toTextField.getText().trim();
		String cc = ccTextField.getText().trim();
		String subject = subjectTextField.getText().trim();
		String attachField = attachTextField.getText().trim();
		// 处理多附件的情况,以字符串数组方式保存附件文件名
		Vector attatchFiles = new Vector();
		StringTokenizer strToken = new StringTokenizer(attachField, ",");
		while (strToken.hasMoreElements()) {
			attatchFiles.add(strToken.nextElement().toString());
		}
		if (to.equals("") && cc.equals("")) {
			JOptionPane.showMessageDialog(this, "未输入邮件发送地址", "邮件发送错误", JOptionPane.ERROR_MESSAGE);
		}

		// 装载服务器属性，并与服务器建立连接
		String smtpHost = null, sendAddress = null, userName = null, password = null;
		Properties p = new Properties();
		try {
			// 从文件中读入相关的服务器属性设置
			FileInputStream fileIn = new FileInputStream("smtp.properties");
			p.load(fileIn);
			smtpHost = p.getProperty("smtp.host");
			sendAddress = p.getProperty("smtp.address");
			userName = p.getProperty("smtp.username");
			password = p.getProperty("smtp.password");
			fileIn.close();
			// 创建与服务器的对话
			p = new Properties();
			p.put("mail.smtp,host", smtpHost);
			p.put("mail.smtp.auth", "true");// 设置身份验证为真,如果发邮件时需要身份验证必须设为真
			session = Session.getInstance(p, null);
			session.setDebug(true);
		} catch (Exception ex) {
			System.out.println("装载服务器属性出错！");
			ex.printStackTrace();
		}

		// 建构邮件信息
		try {
			msg = new MimeMessage(session);
			// 处理邮件头部
			if (!to.equals(""))
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			if (!cc.equals(""))
				msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(cc));
			msg.setSubject(subject);// 设置邮件主题
			msg.setFrom(new InternetAddress(sendAddress));
			msg.setSentDate(new Date());// 设置邮件发送日期

			Multipart mp = new MimeMultipart();// 创建用于封装邮件的Multipart对象
			// 处理邮件正文
			MimeBodyPart mbp1 = new MimeBodyPart();
			mbp1.setText(contentArea.getText());
			mp.addBodyPart(mbp1);
			// 处理邮件附件
			MimeBodyPart mbpAttatch;
			FileDataSource fds;
			BASE64Encoder enco = new BASE64Encoder();
			String sendFileName = "";
			if (attatchFiles.size() != 0) {
				for (int i = 0; i < attatchFiles.size(); i++) {
					mbpAttatch = new MimeBodyPart();
					fds = new FileDataSource(attatchFiles.get(i).toString());
					mbpAttatch.setDataHandler(new DataHandler(fds));
					// 将文件名进行BASE64编码
					sendFileName = "=?GB2312?B?"
							+ enco.encode(new String(fds.getName().getBytes(), "gb2312").getBytes("gb2312")) + "?=";
					mbpAttatch.setFileName(sendFileName);
					mp.addBodyPart(mbpAttatch);
				}
			}
			// 封装并保存邮件信息
			msg.setContent(mp);
			msg.saveChanges();
		} catch (Exception ex) {
			System.out.println("构建邮件出错！");
			ex.printStackTrace();
		}

		// 发送邮件
		try {
			transport = session.getTransport("smtp");
			transport.connect(smtpHost, userName, password);
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			JOptionPane.showMessageDialog(this, "发送邮件成功!", "信息提示", JOptionPane.CLOSED_OPTION);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "发送邮件失败", "信息提示", JOptionPane.ERROR_MESSAGE);
		}
	}

	void attachmentButton_actionPerformed(ActionEvent e) {
		// 弹出一个文件对话框，并从中选出文件名
		String attachFiles = attachTextField.getText();// 取出已添加的附件
		JFileChooser fileChooser = new JFileChooser();
		int operation = fileChooser.showSaveDialog(this);
		if (operation == JFileChooser.APPROVE_OPTION) {
			File attach = fileChooser.getSelectedFile();
			String attachName = null;
			if (attach.isFile()) {
				attachName = attach.getAbsolutePath();// 取出所选择的文件名
				if (attachFiles.length() > 0)
					attachFiles += "," + attachName;
				else
					attachFiles = attachName;
			}
		}
		attachTextField.setText(attachFiles);
	}
}

class NewMailFrame_sendButton_actionAdapter implements java.awt.event.ActionListener {
	NewMailFrame adaptee;

	NewMailFrame_sendButton_actionAdapter(NewMailFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.sendButton_actionPerformed(e);
	}
}

class NewMailFrame_attachmentButton_actionAdapter implements java.awt.event.ActionListener {
	NewMailFrame adaptee;

	NewMailFrame_attachmentButton_actionAdapter(NewMailFrame adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.attachmentButton_actionPerformed(e);
	}
}