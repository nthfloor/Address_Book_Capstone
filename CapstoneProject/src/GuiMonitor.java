
/**
 * Gui thread class for monitoring progress of operations in GUI interface.
 * Inherits from abstract Monitor class
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 */

public class GuiMonitor extends Monitor {

	private ProgressUpdater updater;
	
	public GuiMonitor(DataStructure list, ProgressUpdater updater) {
		super(list);
		
		this.updater = updater;
	}

	//updates progress for swing progress bar, converting to percentage.
	@Override
	protected void updateProgress(double progress) {
		updater.setProgress((int) (progress*100));
	}

}
