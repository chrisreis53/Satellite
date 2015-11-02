package satellite;



public class trackerThread implements Runnable{

	int seconds = 0;
	public void run(){
			while(true){
			System.out.println("Running for: "+ seconds);
			seconds++;
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
