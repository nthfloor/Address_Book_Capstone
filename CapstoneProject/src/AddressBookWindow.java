

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ProgressMonitor;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTextPane;
import javax.swing.JLabel;

/**
 * GUI class for Address Book (Capstone project)
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 * 
 */
@SuppressWarnings("serial")
public class AddressBookWindow extends JFrame implements ProgressUpdater {

	private JPanel contentPane;
	private JTextField searchTextField;
	private JButton btnWalkThrough;
	private JButton btnSearch;
	private JProgressBar progressBar;
	private DefaultListModel dataModel;
	private JComboBox keyComboBox;
	private JLabel statusBar;

//	//for progress bar for loading data into program
//	private ProgressMonitor progressPopupBar = null;
//	private boolean popUpNow = false;
//	private int numberOfRecords = 0;

	/**
	 * Create the frame.
	 */
	public AddressBookWindow(ItemListener datastructureComboListener, Object[] datastructureComboItems, ActionListener walkBtnListener, ActionListener searchBtnListener, Object[] keyComboItems) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.setLayout(new MigLayout("", "[grow]", "[][grow][][]"));

		JComboBox datastructureComboBox = new JComboBox(datastructureComboItems);
		datastructureComboBox.addItemListener(datastructureComboListener);
		contentPane.add(datastructureComboBox, "cell 0 0,growx");

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, "cell 0 1,grow");

		JPanel walkThroughPanel = new JPanel();
		tabbedPane.addTab("Walk through", null, walkThroughPanel, null);
		walkThroughPanel.setLayout(new MigLayout("", "[grow]", "[][grow]"));

		btnWalkThrough = new JButton("Walk through");
		walkThroughPanel.add(btnWalkThrough, "flowy,cell 0 0,alignx center,aligny top");
		btnWalkThrough.setEnabled(false);
		btnWalkThrough.addActionListener(walkBtnListener);

		JScrollPane scrollPane_1 = new JScrollPane();
		walkThroughPanel.add(scrollPane_1, "cell 0 1,grow");

		JTextPane textPane = new JTextPane();
		scrollPane_1.setViewportView(textPane);
		textPane.setPreferredSize(new Dimension(300, 300));

		JPanel searchPanel = new JPanel();
		tabbedPane.addTab("Search", null, searchPanel, null);
		searchPanel.setLayout(new MigLayout("", "[grow][grow][grow]", "[][grow][grow]"));

		searchTextField = new JTextField();
		searchTextField.setEnabled(false);
		searchPanel.add(searchTextField, "cell 0 0,growx,aligny top");
		searchTextField.setColumns(10);

		keyComboBox = new JComboBox(keyComboItems);
		searchPanel.add(keyComboBox, "cell 1 0,growx,aligny top");

		btnSearch = new JButton("Search");
		btnSearch.setEnabled(false);
		btnSearch.addActionListener(searchBtnListener);
		searchPanel.add(btnSearch, "cell 2 0,growx,aligny top");

		dataModel = new DefaultListModel();

		JScrollPane scrollPane = new JScrollPane();
		searchPanel.add(scrollPane, "cell 0 1 3 1,grow");
		JList list = new JList(dataModel);
		scrollPane.setViewportView(list);

		progressBar = new JProgressBar();
		progressBar.setPreferredSize(new Dimension(800, 10));
		setShowProgressBar(false);
		contentPane.add(progressBar, "cell 0 2");
		
		statusBar = new JLabel("No tasks executing.");
		contentPane.add(statusBar, "cell 0 3");

//		progressPopupBar = new ProgressMonitor(this, "Loading records...", "Progress", 0, 100);
	}

//	public void setNumberOfRecords(int numRecs) {
//		numberOfRecords = numRecs;
//	}

	public String getSearchTerm() {
		return searchTextField.getText();
	}

	public void enableComponents() {
		btnWalkThrough.setEnabled(true);
		btnSearch.setEnabled(true);
		searchTextField.setEnabled(true);
	}

	public void disableComponents() {
		btnWalkThrough.setEnabled(false);
		btnSearch.setEnabled(false);
		searchTextField.setEnabled(false);
	}

	public void setProgress(int value) {
//		progressPopupBar.setProgress(value);

		progressBar.setValue(value);
//		progressBar.repaint();
	}

	public void addToJList(ArrayList<Record> records) {
		if (records != null) {
			for (int i = 0; i < records.size(); i++) {
				dataModel.add(i, records.get(i));
			}
		}
	}

	public Record.SearchType getSortField() {
		if (keyComboBox.getSelectedItem().equals("PHONE"))
			return Record.SearchType.PHONE;
		else if (keyComboBox.getSelectedItem().equals("FIRSTNAME"))
			return Record.SearchType.FIRSTNAME;
		else if (keyComboBox.getSelectedItem().equals("LASTNAME"))
			return Record.SearchType.LASTNAME;
		else
			return null;
	}

	public void clearJList() {
		dataModel.clear();
	}

	public void setShowProgressBar(boolean show) {
		progressBar.setVisible(show);
	}

	public void displayTime(String timeInfo) {
		statusBar.setText(timeInfo);
	}

//	public void paint(Graphics g){
//		super.paint(g);
//		
//		if(popUpNow){
//			progressPopupBar = new ProgressMonitor(this, "Loading records...", "Progress", 0, numberOfRecords);
//			popUpNow = false;
//		}
//	}

}
