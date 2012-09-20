package capstone.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JOptionPane;

import capstone.BinaryTree;
import capstone.DataStructure;
import capstone.MyHashtable;
import capstone.Record;
import capstone.RecordNotFoundException;
import capstone.Runner;
import capstone.SortedArray;

/**
 * GUI driver class for Address Book (Capstone project)
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 * 
 */
public class GuiRunner {

	private static final String[] DS_COMBO_ITEMS = { "<Select a data structure>", "SortedArray", "MyHashtable", "BinaryTree" };

	public static final int FILE_SIZE = 349996;

	public static final String FILE_NAME = "350000.csv";

	private static final Object[] KEY_COMBO_ITEMS = { "PHONE", "FIRSTNAME", "LASTNAME" };

	private static AddressBookWindow frame;

	private static DataStructure listOfRecords;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ItemListener dsComboListener = new DSComboListener();
					ActionListener walkBtnListener = new WalkBtnListener();
					ActionListener searchBtnListener = new SearchBtnListener();

					frame = new AddressBookWindow(dsComboListener, DS_COMBO_ITEMS, walkBtnListener, searchBtnListener, KEY_COMBO_ITEMS);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static class DSComboListener implements ItemListener {

		public DSComboListener() {

		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				if (e.getItem().toString().equals("SortedArray")) {
					listOfRecords = new SortedArray(FILE_SIZE);
				} else if (e.getItem().toString().equals("MyHashtable")) {
					listOfRecords = new MyHashtable(FILE_SIZE);
				} else if (e.getItem().toString().equals("BinaryTree")) {
					listOfRecords = new BinaryTree();
				} else {
					frame.disableComponents();
					return;
				}

				frame.enableComponents();
				Runner.loadData(listOfRecords, FILE_NAME, frame);
			}
		}

	}

	private static class WalkBtnListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Runner.walkThrough(listOfRecords, frame);
			System.out.println(e);
		}
	}

	private static class SearchBtnListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Record.currentSearchType = frame.getSortField();
			
			frame.clearJList();
			
			try {
				frame.addToJList(Runner.getRecords(listOfRecords, frame.getSearchTerm(), frame));
			} catch (RecordNotFoundException e1) {
				JOptionPane.showMessageDialog(frame, "Record not found.");
			}
		}
	}
}
