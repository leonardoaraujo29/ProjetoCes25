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
public class EstReserva extends Window {

	/**
	 * Create the frame.
	 */
	public EstReserva() {
		this.setTitle("Estação de Reserva");
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
				{"ER1", "Load/Store", null, null, null, null, null, null, null, null},
				{"ER2", "Load/Store", null, null, null, null, null, null, null, null},
				{"ER3", "Load/Store", null, null, null, null, null, null, null, null},
				{"ER4", "Load/Store", null, null, null, null, null, null, null, null},
				{"ER5", "Load/Store", null, null, null, null, null, null, null, null},
				{"ER6", "Add", null, null, null, null, null, null, null, null},
				{"ER7", "Add", null, null, null, null, null, null, null, null},
				{"ER8", "Add", null, null, null, null, null, null, null, null},
				{"ER9", "Mult", null, null, null, null, null, null, null, null},
				{"ER10", "Mult", null, null, null, null, null, null, null, null},
				{"ER11", "Mult", null, null, null, null, null, null, null, null},
			},
			new String[] {
				"ID", "Tipo", "Busy", "Instru\u00E7\u00E3o", "Dest", "Vj", "Vk", "Qj", "Qk", "A"
			}
		) {

			boolean[] columnEditables = new boolean[] {
				false, false, true, true, true, true, true, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
	}
}
