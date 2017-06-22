package GUI;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

@SuppressWarnings("serial")
public abstract class Window extends JFrame {

	protected JPanel contentPane;
	protected JTable table;

	public Window() throws HeadlessException {
		super();
	}

	public Window(GraphicsConfiguration gc) {
		super(gc);
	}

	public Window(String title) throws HeadlessException {
		super(title);
	}

	public Window(String title, GraphicsConfiguration gc) {
		super(title, gc);
	}

	public void setValueAt(int value, int row, int column) {
		this.table.setValueAt(value, row, column);
	}
	public void setValueAt(String value, int row, int column) {
		this.table.setValueAt(value, row, column);
	}

}