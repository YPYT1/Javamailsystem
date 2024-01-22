// MailListPanel.java 事件监听器

package javamailsystem;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import javax.mail.*;

public class MailListPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	BorderLayout borderLayout1 = new BorderLayout();
	JScrollPane jScrollPane1 = new JScrollPane();

	MailTableModel model = new MailTableModel();
	JTable jTable1 = new JTable(model);

	public MailListPanel() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void jbInit() throws Exception {
		this.setLayout(borderLayout1);
		jScrollPane1.getViewport().setBackground(Color.white);
		this.setPreferredSize(new Dimension(454, 300));
		this.add(jScrollPane1, BorderLayout.CENTER);
		jTable1.setShowHorizontalLines(false);
		jTable1.setShowVerticalLines(false);
		jScrollPane1.getViewport().add(jTable1, null);

		// 注册表格的事件监听器
		jTable1.getSelectionModel().addListSelectionListener(new MailListListener());
	}

	class MailListListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();
			int selected = lsm.getMaxSelectionIndex();
			if (selected != -1) {
				Message message = model.getMessage(selected);
				MainFrame.mailViewPanel.setMessage(message);
			}
		}
	}

}
