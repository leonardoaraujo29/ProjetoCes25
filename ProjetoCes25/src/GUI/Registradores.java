package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class Registradores extends Window {

	/**
	 * Create the frame.
	 */
	public Registradores() {
		this.setTitle("Registradores");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 723, 271);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(202, Short.MAX_VALUE))
		);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"R0", null, null, null, "R8", null, null, null, "R16", null, null, null, "R24", null, null, null,},
				{"R1", null, null, null, "R9", null, null, null, "R17", null, null, null, "R25", null, null, null,},
				{"R2", null, null, null, "R10", null, null, null, "R18", null, null, null, "R26", null, null, null,},
				{"R3", null, null, null, "R11", null, null, null, "R19", null, null, null, "R27", null, null, null,},
				{"R4", null, null, null, "R12", null, null, null, "R20", null, null, null, "R28", null, null, null,},
				{"R5", null, null, null, "R13", null, null, null, "R21", null, null, null, "R29", null, null, null,},
				{"R6", null, null, null, "R14", null, null, null, "R22", null, null, null, "R30", null, null, null,},
				{"R7", null, null, null, "R15", null, null, null, "R23", null, null, null, "R31", null, null, null,},
			},
			new String[] {
				"", "ReorderNum", "Busy", "Value","", "ReorderNum", "Busy", "Value","", "ReorderNum", "Busy", "Value","", "ReorderNum", "Busy", "Value"
			}
		) {

			boolean[] columnEditables = new boolean[] {
				false, true, true, true, false, true, true, true, false, true, true, true, false, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
	}
}
