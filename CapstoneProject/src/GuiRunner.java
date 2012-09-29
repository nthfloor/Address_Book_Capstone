

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 * GUI driver class for Address Book (Capstone project)
 * Manages graphical interface for program
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 * 
 */
public class GuiRunner {

	private static final String[] DS_COMBO_ITEMS = { "<Select a data structure>", "SortedArray", "MyHashtable", "BinaryTree" };

	public static final int FILE_SIZE = 350000; //349996

	public static final String FILE_NAME = "350000.csv";
	public static final String FILE_NAME_TREE = "350000_2.csv";

	private static final Object[] KEY_COMBO_ITEMS = { "PHONE", "FIRSTNAME", "LASTNAME" };

	private static AddressBookWindow  frame;

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

	//manages the selection of a data structure and the subsequent loading of data into that data structure. 
	private static class DSComboListener implements ItemListener {

		public DSComboListener() {

		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {

				frame.disableComponents();
				listOfRecords = null;
				if (e.getItem().toString().equals("SortedArray")) {
					listOfRecords = new SortedArray(FILE_SIZE);
				} else if (e.getItem().toString().equals("MyHashtable")) {
					listOfRecords = new MyHashtable(FILE_SIZE);
				} else if (e.getItem().toString().equals("BinaryTree")) {					
					listOfRecords = new BinaryTree(FILE_SIZE);
				} else {
					return;
				}
				
				// Safely execute loadData()
				SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
					
					@Override
					protected Void doInBackground() throws Exception {
						frame.setShowProgressBar(true);
						frame.updateProgressLabel("Loading data...");
						if(listOfRecords instanceof BinaryTree){
							System.out.println(FILE_NAME_TREE+" "+Record.currentSearchType+" "+Record.selectedSearchType);
							System.out.println(((BinaryTree) listOfRecords).isEmpty());
							Runner.loadData(listOfRecords, FILE_NAME_TREE, new GuiMonitor(listOfRecords, frame));
						}
						else{
							System.out.println(FILE_NAME+" "+Record.currentSearchType+" "+Record.selectedSearchType);
							Runner.loadData(listOfRecords, FILE_NAME, new GuiMonitor(listOfRecords, frame));
						}
						return null;
					}
					
					@Override
					protected void done() {
						frame.setShowProgressBar(false);
						frame.updateProgressLabel("Data loaded into data structure. Time taken: " + Runner.getTimeData()+" seconds");
						frame.enableComponents();
					}
				};
				worker.execute();
			}
		}

	}

	//manages sequential walkthrough
	private static class WalkBtnListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			
			frame.disableComponents();
			
			// Safe execution of walkThrough()
			SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
				
				@Override
				protected Void doInBackground() throws Exception {
					frame.setShowProgressBar(true);
					frame.updateProgressLabel("Performing walkthrough...");
					Runner.walkThrough(listOfRecords, new GuiMonitor(listOfRecords, frame));
					
					return null;
				}
				
				@Override
				protected void done() {
					frame.setShowProgressBar(false);
					frame.updateProgressLabel("Walked through data structure. Time taken: " + Runner.getTimeData()+" seconds");
					frame.enableComponents();
				}
			};
			worker.execute();
		}
	}

	//manages the selection of search field filter and search parameter
	//all records matching parameter are then displayed in list view
	private static class SearchBtnListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			frame.disableComponents();
			Record.currentSearchType = frame.getSortField();
			System.out.println(Record.currentSearchType);
			
			frame.clearJList();
			
			// Safe execution of getRecords()
			SwingWorker<ArrayList<Record>, Void> worker = new SwingWorker<ArrayList<Record>, Void>() {
				
				@Override
				protected ArrayList<Record> doInBackground() throws Exception {
					frame.setShowProgressBar(true);

					try {
						System.out.println(frame.getSearchTerm()+" "+Record.currentSearchType+" "+Record.selectedSearchType);
						return Runner.getRecords(listOfRecords, frame.getSearchTerm(), new GuiMonitor(listOfRecords, frame));
					} catch (RecordNotFoundException e1) {
						JOptionPane.showMessageDialog(frame, "Record not found.");
						
						return new ArrayList<Record>();
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

					frame.updateProgressLabel("Searched for records. Time taken: " + Runner.getTimeData()+" seconds");
					
					frame.enableComponents();
				}
			};
			worker.execute();
		}
	}
}
