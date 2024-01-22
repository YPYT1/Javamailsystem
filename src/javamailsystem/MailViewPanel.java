// MailViewPanel.java 邮件处理

package javamailsystem;

import java.awt.*;
import javax.swing.*;

import javax.mail.*;
import javax.mail.internet.*;
import com.borland.jbcl.layout.*;
import java.util.Vector;
import java.awt.event.*;

public class MailViewPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	BorderLayout borderLayout1 = new BorderLayout();
	JPanel jPanel1 = new JPanel();
	JPanel jPanel2 = new JPanel();

	Message message = null;
	XYLayout xYLayout1 = new XYLayout();
	JLabel FromAndToLabel = new JLabel();
	JLabel subjectLabel = new JLabel();
	JButton attachmentButton = new JButton();
	BorderLayout borderLayout2 = new BorderLayout();
	JScrollPane jScrollPane1 = new JScrollPane();
	JEditorPane mailContentArea = new JEditorPane();

	MainFrame frame;
	Vector attachmentFiles = new Vector();
	Vector attachmentInputStream = new Vector();

	public MailViewPanel(MainFrame frame) {
		this.frame = frame;
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void jbInit() throws Exception {
		this.setLayout(borderLayout1);
		jPanel1.setBorder(BorderFactory.createEtchedBorder());
		jPanel1.setPreferredSize(new Dimension(600, 50));
		jPanel1.setLayout(xYLayout1);
		jPanel2.setBorder(BorderFactory.createEtchedBorder());
		jPanel2.setLayout(borderLayout2);
		this.setPreferredSize(new Dimension(600, 350));
		attachmentButton.setText("附件");
		attachmentButton.addActionListener(new MailViewPanel_attachmentButton_actionAdapter(this));
		FromAndToLabel.setText("");
		subjectLabel.setText("");
		this.add(jPanel1, BorderLayout.NORTH);
		jPanel1.add(FromAndToLabel, new XYConstraints(6, 2, 490, 18));
		jPanel1.add(subjectLabel, new XYConstraints(6, 23, 490, 18));
		jPanel1.add(attachmentButton, new XYConstraints(500, 11, 60, 24));
		this.add(jPanel2, BorderLayout.CENTER);
		jPanel2.add(jScrollPane1, BorderLayout.CENTER);
		jScrollPane1.getViewport().add(mailContentArea, null);

		attachmentButton.setEnabled(false);

	}

	void setMessage(Message message) {
		this.message = message;
		mailContentArea.setText("");
		if (message != null) {
			// 显示有关邮件头信息
			loadHeader();
			attachmentFiles.clear();
			attachmentInputStream.clear();

			// 解释并显示邮件内容与附件
			loadBody(message);
		}
	}

	void loadHeader() {
		String temp;
		String from = null;
		String to = null;
		String subject = null;
		try {
			// from
			temp = message.getFrom()[0].toString();
			if (temp.startsWith("=?")) {
				from = MimeUtility.decodeText(temp);
			} else
				from = temp;
			// to
			temp = message.getRecipients(Message.RecipientType.TO)[0].toString();
			if (temp.startsWith("=?")) {
				to = MimeUtility.decodeText(temp);
			} else
				to = temp;
			// suject
			temp = message.getSubject();
			if (temp.startsWith("=?")) {
				subject = MimeUtility.decodeText(temp);
			} else
				subject = temp;

		} catch (Exception ex) {
		}
		FromAndToLabel.setText("发件人：" + from + "  收件人：" + to);
		subjectLabel.setText("主题：" + subject);
	}

	void loadBody(Part part) {// 对邮件内容进行递归处理的方法
		try {
			if (part.isMimeType("Multipart/*")) {// 为multipart类型
				Multipart mpart = (Multipart) part.getContent();
				System.out.println("mpart  " + mpart);
				int count = mpart.getCount();
				for (int i = 0; i < count; i++) {
					loadBody(mpart.getBodyPart(i));
				}
				return;
			} else {// 为邮件正文或附件
				String disposition = part.getDisposition();
				System.out.println(disposition);
				if ((disposition == null)) {// 为邮件正文
					if (part.isMimeType("text/plain")) {// 邮件正文为文本格式
						String mailContent = new String(part.getContent().toString().getBytes("gb2312"));
						mailContentArea.setText(mailContent);
						mailContentArea.setEditable(false);
					} else {// 邮件内容为非文本格式
						mailContentArea.setText("Error：邮件内容为非文本，本系统无法显示");
					}
				} else if ((disposition != null) && disposition.equals(Part.ATTACHMENT)
						|| disposition.equals(Part.INLINE)) {
					// 为附件
					String tempFileName = part.getFileName();
					System.out.println(tempFileName);
					String attachmentFileName;
					if (tempFileName.startsWith("=?")) {
						attachmentFileName = MimeUtility.decodeText(tempFileName);
					} else
						attachmentFileName = new String(tempFileName.getBytes("GBK"));
					attachmentFiles.add(attachmentFileName);
					attachmentInputStream.add(part.getDataHandler().getInputStream());
				}
			}

		} catch (Exception e) {
			System.out.println("显示邮件内容时出错!");
		}
		if (attachmentFiles.size() == 0) {
			attachmentButton.setEnabled(false);
		} else {
			attachmentButton.setEnabled(true);
		}
	}

	void attachmentButton_actionPerformed(ActionEvent e) {
		AttachmentDialog attachmentDialog = new AttachmentDialog(frame, "邮件附件处理", false);
		attachmentDialog.setAttachment(attachmentFiles, attachmentInputStream);
		attachmentDialog.show();
	}
}

class MailViewPanel_attachmentButton_actionAdapter implements java.awt.event.ActionListener {
	MailViewPanel adaptee;

	MailViewPanel_attachmentButton_actionAdapter(MailViewPanel adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.attachmentButton_actionPerformed(e);
	}
}