package capstone;

/**
 * Thread class used for monitoring progress taken when loading in data into data structure 
 * 
 * @author Nathan Floor
 * @author Ryan Saunders
 * 
 */

public class Monitor extends Thread{
	
	private DataStructure listOfRecords;
	

	public Monitor(DataStructure list){		
		listOfRecords = list;		
	}
	
	private static void updateProgress(double progress){
		final int width = 50; //progress bar width in chars
		StringBuilder bar = new StringBuilder("[");
		int i=0;
		for(;i<(int)(progress*width)-1;i++)
			bar.append("=");
		bar.append(">");
		i++;
		for(;i<width;i++)
			bar.append(" ");

		bar.append("]   " + (int)Math.ceil(progress*100) + "%");
		System.out.print("\r" + bar.toString()); //progress bar
	}
	
	//monitor progress of loading in of data, with progress bar
	public void run(){
		try{
			double progress = 0.0;
			
			while(progress < 1){
				progress = listOfRecords.getProgress();
				//System.out.println(progress);
				updateProgress(progress);
				Thread.sleep(1);
			}
			
		}catch(InterruptedException e){
			/*Do nothing*/
			
			updateProgress(1);
		}
		catch(Exception e){e.printStackTrace();}
		System.out.println("");
	}
}
