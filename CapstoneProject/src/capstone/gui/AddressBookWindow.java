package capstone.gui;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import javax.swing.JList;

import capstone.Record;
import capstone.Record.SearchType;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import javax.swing.JScrollPane;

/**
 * GUI class for Address Book (Capstone project)
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 * 
 */
@SuppressWarnings("serial")
public class AddressBookWindow extends JFrame {

	private JPanel contentPane;
	private JTextField searchTextField;
	private JButton btnWalkThrough;
	private JButton btnSearch;
	private JProgressBar progressBar;
	private DefaultListModel dataModel;
	private JComboBox keyComboBox;

	/**
	 * Create the frame.
	 * 
	 * @param datastructureComboItems
	 * @param searchBtnListener
	 * @param keyComboBoxListener 
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
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
		mnFile.add(mntmExit);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.setLayout(new MigLayout("", "[grow]", "[][grow]"));

		JComboBox datastructureComboBox = new JComboBox(datastructureComboItems);
		datastructureComboBox.addItemListener(datastructureComboListener);
		contentPane.add(datastructureComboBox, "cell 0 0,growx");

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, "cell 0 1,grow");

		JPanel walkThroughPanel = new JPanel();
		tabbedPane.addTab("Walk through", null, walkThroughPanel, null);

		JPanel panel = new JPanel();
		walkThroughPanel.add(panel);

		btnWalkThrough = new JButton("Walk through");
		panel.add(btnWalkThrough);
		btnWalkThrough.setEnabled(false);

		progressBar = new JProgressBar();
		panel.add(progressBar);
		btnWalkThrough.addActionListener(walkBtnListener);

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
	}

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
		progressBar.setValue(value);
		progressBar.repaint();
	}

	public void addToJList(ArrayList<Record> records) {
		for (int i = 0; i < records.size(); i++) {
			dataModel.add(i, records.get(i));
		}
	}

	public SearchType getSortField() {
		if (keyComboBox.getSelectedItem().equals("PHONE"))
			return SearchType.PHONE;
		else if (keyComboBox.getSelectedItem().equals("FIRSTNAME"))
			return SearchType.FIRSTNAME;
		else if (keyComboBox.getSelectedItem().equals("LASTNAME"))
			return SearchType.LASTNAME;
		else
			return null;
				
	}

	public void clearJList() {
		dataModel.clear();
	}
}
