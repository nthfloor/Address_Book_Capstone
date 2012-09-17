package capstone.gui;

import java.awt.EventQueue;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JOptionPane;

import capstone.BinaryTree;
import capstone.DataStructure;
import capstone.MyHashtable;
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

	private static final String[] COMBO_ITEMS = { "<Select a data structure>", "SortedArray", "MyHashtable", "BinaryTree" };

	public static final int FILE_SIZE = 999;

	public static final String FILE_NAME = "1000.csv";

	private static AddressBookWindow frame;

	private static DataStructure listOfRecords;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ItemListener comboListener = new ComboListener();
					ItemListener walkBtnListener = new WalkBtnListener();
					ItemListener searchBtnListener = new SearchBtnListener();
					
					frame = new AddressBookWindow(comboListener, COMBO_ITEMS, walkBtnListener, searchBtnListener);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static class ComboListener implements ItemListener {

		public ComboListener() {

		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				if (e.getItem().toString().equals("SortedArray")) {
					listOfRecords = new SortedArray(FILE_SIZE);
				} else if (e.getItem().toString().equals("MyHashtable")) {
					listOfRecords = new MyHashtable(FILE_SIZE);
				} else if (e.getItem().toString().equals("BinaryTree")) {
					listOfRecords = new BinaryTree(FILE_SIZE);
				} else {
					return;
				}
				
				Runner.loadData(listOfRecords, FILE_NAME);
			}
		}

	}

	private static class WalkBtnListener implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				Runner.walkThrough(listOfRecords);
			}
		}
	}
	
	private static class SearchBtnListener implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				try {
					Runner.getRecord(listOfRecords, frame.getSearchTerm());
				} catch (RecordNotFoundException e1) {
					JOptionPane.showMessageDialog(frame, "Record not found.");
				}
			}
		}
	}
}
