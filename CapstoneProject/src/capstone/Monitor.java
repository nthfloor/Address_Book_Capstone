package capstone;

import capstone.datastructures.DataStructure;

/**
 * Thread class used for monitoring progress taken when loading in data into data structure
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 * 
 */

public abstract class Monitor extends Thread {

	private static final long SLEEP_INTERVAL = 100; // in milliseconds
	
	private DataStructure monitoredDS;

	public Monitor(DataStructure dsToBeMonitored) {
		monitoredDS = dsToBeMonitored;
	}

	abstract protected void updateProgress(double progress);
	
	//monitor progress of loading in of data, with progress bar
	public void run() {
		try {
			double progress = 0.0;

			while (progress < 1) {
				progress = monitoredDS.getProgress();
				//System.out.println(progress);
				updateProgress(progress);
				Thread.sleep(SLEEP_INTERVAL);
			}

		} catch (InterruptedException e) {
			/* Do nothing */

			updateProgress(1);
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	abstract protected Monitor copy();
	
	public DataStructure getMonitoredDS() {
		return monitoredDS;
	}
}
