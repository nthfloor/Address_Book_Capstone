package capstone.cli;

import capstone.Monitor;
import capstone.datastructures.DataStructure;


public class CommandLineMonitor extends Monitor {
	
	public CommandLineMonitor(DataStructure dsToBeMonitored) {
		super(dsToBeMonitored);
	}

	protected void updateProgress(double progress) {
		final int width = 50; //progress bar width in chars
		StringBuilder bar = new StringBuilder("[");
		int i = 0;
		for (; i < (int) (progress * width) - 1; i++)
			bar.append("=");
		bar.append(">");
		i++;
		for (; i < width; i++)
			bar.append(" ");

		bar.append("]   " + (int) Math.ceil(progress * 100) + "%");
		System.out.print("\r" + bar.toString()); //progress bar
	}

	/**
	 * Shallowish copy (creates a reference to a new Monitor, but the new monitor shares the datastructure of the original)
	 * 
	 * @return the copy
	 */
	@Override
	protected Monitor copy() {
		return new CommandLineMonitor(getMonitoredDS());
	}
}
