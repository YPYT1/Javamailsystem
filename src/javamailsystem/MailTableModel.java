// MailTableModel.java 收件箱

package javamailsystem;

import javax.mail.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.table.AbstractTableModel;
import javax.mail.internet.*;

/**
 * Maps the messages in a Folder to the Swing's Table Model
 */

public class MailTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	Folder folder;
	Message[] messages;

	String[] columnNames = { "发件人", "主题", "接收时间" };

	public void setFolder(Folder what) throws MessagingException {
		if (what != null) {
			what.open(Folder.READ_WRITE);
			messages = what.getMessages();
		} else {
			messages = null;
		}
		// 关闭上次显示的文件夹，以新文件夹刷新邮件列表
		if (folder != null)
			folder.close(true);
		folder = what;
		fireTableDataChanged();
	}

	public void deleteEmail(int deleteIndex) {
		try {
			messages[deleteIndex].setFlag(Flags.Flag.DELETED, true);
			folder.close(true);
			folder.open(Folder.READ_WRITE);
			messages = folder.getMessages();
			fireTableDataChanged();
		} catch (Exception ex) {
			System.out.println("删除邮件时出错");
			ex.printStackTrace();
		}
	}

	public Message getMessage(int which) {
		return messages[which];
	}

	// 实现AbstractTableModel的相关方法
	public String getColumnName(int column) {
		return columnNames[column];
	}

	public Class getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public int getRowCount() {
		if (messages == null)
			return 0;
		return messages.length;
	}

	public boolean isCellEditable(int row, int col) {
		return false;
	}

	public Object getValueAt(int aRow, int aColumn) {
		String[] messageData = new String[3];
		try {
			Message m = messages[aRow];
			// from
			Address[] from = m.getFrom();
			String temp;
			if (from != null) {
				try {
					// 如果发件人地址采用64编码，则进行解码，否则，直接取其值
					temp = from[0].toString();
					if (temp.startsWith("=?"))
						messageData[0] = MimeUtility.decodeText(temp);
					else
						messageData[0] = temp;

				} catch (Exception e) {

				}
			} else {
				messageData[0] = "";
			}

			// subject
			temp = m.getSubject();
			try {
				if (temp != null) {
					if (temp.startsWith("=?"))
						messageData[1] = MimeUtility.decodeText(temp);
					else
						messageData[1] = temp;
				} else {
					messageData[1] = "no subject";
				}
			} catch (Exception ex) {
			}
			// date
			Date date = m.getSentDate();
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE,yyyy/MM/d hh:mm");

			if (date != null) {
				messageData[2] = dateFormat.format(date);
			} else {
				messageData[2] = "unknown";
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return messageData[aColumn];
	}
}
