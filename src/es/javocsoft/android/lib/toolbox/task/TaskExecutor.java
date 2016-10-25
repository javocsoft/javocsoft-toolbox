package es.javocsoft.android.lib.toolbox.task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.util.Log;
import es.javocsoft.android.lib.toolbox.ToolBox;

/**
 * An executor, {@link ThreadPoolExecutor}, class that executes task in a queue of
 * type {@link LinkedBlockingQueue}.
 * <br><br> 
 * Note: This executor have a maximum pool size and max pool size qeual to
 * the number of cores in the device.
 * 
 * @author JavocSoft 2016
 * @version 1.0
 */
public class TaskExecutor {

	private static TaskExecutor jsTaskExecutor = null;
	private static ThreadPoolExecutor poolExecutor;
	private final BlockingQueue<Runnable> mDecodeWorkQueue;
    
	/*
     * Gets the number of available cores
     * (not always the same as the maximum number of cores)
     */
    private static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    // Sets the amount of time an idle thread waits before terminating
    private static final int KEEP_ALIVE_TIME = 1;
    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
	
    
	static  {     
        jsTaskExecutor = new TaskExecutor();
    }
	
	
	private TaskExecutor() {
		// Instantiates the queue of Runnables as a LinkedBlockingQueue
        mDecodeWorkQueue = new LinkedBlockingQueue<Runnable>();
        
        // Creates a thread pool manager
        poolExecutor = new ThreadPoolExecutor(
        		NUMBER_OF_CORES,       // Initial pool size
        		NUMBER_OF_CORES,       // Max pool size
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                mDecodeWorkQueue);
	}
	
	private static void addTask(Thread task) {
		if(ToolBox.LOG_ENABLE)
			Log.d(ToolBox.TAG, "Executing in thread pool executor task: [" + (task.getName()!=null?task.getName():task.getId()) + "].");
		poolExecutor.execute(task);
	}
	
	/**
	 * Adds a runnable to be executed in the executor.
	 * 
	 * @param task		The runnable to run
	 * @param taskName	The task name
	 */
	public static void addTask(ExecutorRunnable task, String taskName) {
		if(task!=null) {
			Thread t = null;
			if(taskName!=null)
				t = new Thread(task, taskName);
			else
				t = new Thread(task);
			
			addTask(t);
		}
	}
 
	
	/**
	 * An executor runnable.
	 * 
	 * @author JavocSoft 2016
	 * @version 1.0
	 */
	public static abstract class ExecutorRunnable implements Runnable {
		
		/**
		 * The job to do by this runnable.
		 */
		protected abstract void doJobTask();
		
		
		@Override
		public void run() {
			//Do job task
			try{
				doJobTask();
			}catch(Exception e){
				Log.e(ToolBox.TAG, "Error TaskExecutor. Error in task [" + e.getMessage() + "]", e);
			}						
		}
	}
}
