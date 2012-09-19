package capstone.gui;

import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

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

	/**
	 * Create the frame.
	 * 
	 * @param comboItems
	 * @param searchBtnListener
	 * 
	 * @throws UnsupportedLookAndFeelException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 */
	public AddressBookWindow(ItemListener comboListener, Object[] comboItems, ItemListener walkBtnListener, ItemListener searchBtnListener) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
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

		JComboBox comboBox = new JComboBox(comboItems);
		comboBox.addItemListener(comboListener);
		contentPane.add(comboBox, "cell 0 0,growx");

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, "cell 0 1,grow");

		JPanel walkThroughPanel = new JPanel();
		tabbedPane.addTab("Walk through", null, walkThroughPanel, null);

		JToggleButton tglbtnWalkThrough = new JToggleButton("Walk through");
		tglbtnWalkThrough.addItemListener(walkBtnListener);
		walkThroughPanel.add(tglbtnWalkThrough);

		JPanel searchPanel = new JPanel();
		tabbedPane.addTab("Search", null, searchPanel, null);

		searchTextField = new JTextField();
		searchPanel.add(searchTextField);
		searchTextField.setColumns(10);

		JToggleButton tglbtnSearch = new JToggleButton("Search");
		tglbtnSearch.addItemListener(searchBtnListener);
		searchPanel.add(tglbtnSearch);
	}

	public String getSearchTerm() {
		return searchTextField.getText();
	}

}
