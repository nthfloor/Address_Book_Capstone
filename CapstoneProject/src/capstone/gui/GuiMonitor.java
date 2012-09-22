package capstone.gui;

import capstone.DataStructure;
import capstone.Monitor;

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

}
