package capstone.gui;

import capstone.Monitor;
import capstone.cli.CommandLineMonitor;
import capstone.datastructures.DataStructure;

public class GuiMonitor extends Monitor {

	private ProgressUpdater updater;
	
	public GuiMonitor(DataStructure list, ProgressUpdater updater) {
		super(list);
		
		this.updater = updater;
	}

	@Override
	protected void updateProgress(double progress) {
		updater.setProgress((int) (progress*100));
	}

	/**
	 * Shallowish copy (creates a reference to a new Monitor, but the new monitor shares
	 * the datastructure and the updater of the original)
	 * 
	 * @return the copy
	 */
	@Override
	protected Monitor copy() {
		return new GuiMonitor(getMonitoredDS(), updater);
	}

}
