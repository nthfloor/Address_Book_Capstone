package capstone.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import capstone.RunHelper;
import capstone.WalkThroughMessangerException;
import capstone.cli.CliRunner;
import capstone.datastructures.BinaryTree;
import capstone.datastructures.DataStructure;
import capstone.datastructures.MyHashtable;
import capstone.datastructures.Record;
import capstone.datastructures.RecordNotFoundException;
import capstone.datastructures.SortedArray;

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

	private static RunHelper helper;
	
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

				frame.disableComponents();
				
				if (e.getItem().toString().equals("SortedArray")) {
					listOfRecords = new SortedArray(FILE_SIZE);
				} else if (e.getItem().toString().equals("MyHashtable")) {
					listOfRecords = new MyHashtable(FILE_SIZE);
				} else if (e.getItem().toString().equals("BinaryTree")) {
					listOfRecords = new BinaryTree(FILE_SIZE);
				} else {
					return;
				}
				
				helper = new RunHelper(listOfRecords, new GuiMonitor(listOfRecords, frame));
				
				// Safely execute loadData()
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					
					@Override
					protected Void doInBackground() {
						frame.setShowProgressBar(true);
						helper.loadData(FILE_NAME);
						return null;
					}
					
					@Override
					protected void done() {
						frame.setShowProgressBar(false);
						frame.setStatusMessage("Data loaded into data structure. Time taken: " + helper.getTimeData());
						frame.enableComponents();
					}
				};
				worker.execute();
			}
		}

	}

	private static class WalkBtnListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			
			frame.disableComponents();
			
			// Safe execution of walkThrough()
			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
				
				@Override
				protected Void doInBackground() throws WalkThroughMessangerException {
					frame.setShowProgressBar(true);
					
					helper.walkThrough(frame);
					
					return null;
				}
				
				@Override
				protected void done() {
					frame.setShowProgressBar(false);
					frame.setStatusMessage("Walked through data structure. Time taken: " + helper.getTimeData());
					frame.enableComponents();
				}
			};
			worker.execute();
		}
	}

	private static class SearchBtnListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.disableComponents();
			Record.currentSearchType = frame.getSortField();
			
			frame.clearJList();
			
			// Safe execution of getRecords()
			SwingWorker<ArrayList<Record>, Void> worker = new SwingWorker<ArrayList<Record>, Void>() {
				
				@Override
				protected ArrayList<Record> doInBackground() {
					frame.setShowProgressBar(true);

					try {
						return helper.getRecords(frame.getSearchTerm());
					} catch (RecordNotFoundException e1) {
						JOptionPane.showMessageDialog(frame, "Record not found.");
						
						return null;
					}
				}
				
				@Override
				protected void done(){
					frame.setShowProgressBar(false);
					
					try {
						frame.addToJList(get());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					frame.setStatusMessage("Searched for records. Time taken: " + helper.getTimeData());
					
					frame.enableComponents();
				}
			};
			worker.execute();
		}
	}
}
