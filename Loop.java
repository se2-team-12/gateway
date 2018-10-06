package mainEventLoop;
 
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class Loop {

	public static void main(String[] args) {
	    
		Runnable runnable = new Runnable() {
	    		public void run() {
	    			readPythonCommand.ReadPython.readPython();
	        }
	    };
	    
	    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
	    service.scheduleAtFixedRate(runnable, 0, 5, TimeUnit.SECONDS);
	}

}
