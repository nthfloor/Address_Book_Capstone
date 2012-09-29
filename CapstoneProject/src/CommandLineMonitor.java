
/**
 * Command-line thread class for monitoring progress of operations in GUI interface.
 * Inherits from abstract Monitor class
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 */

public class CommandLineMonitor extends Monitor {
	
	public CommandLineMonitor(DataStructure list) {
		super(list);
	}

	//updates progress for progress bar on terminal, converting to percentage.
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
}
