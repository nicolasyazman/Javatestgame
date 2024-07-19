package core;



public class GameLoop {

	public static int NOT_INITIALIZED = 0;
	public static int RUNNING = 1;
	public static int TERMINATED = 2;

	private int Status;
	private Thread gameLoopExecution;

	public class GameLoopExecution extends Thread {

		public void run() {
			// TODO Auto-generated method stub
			while (Status == GameLoop.RUNNING) {
				
				System.out.println("GameLoop alive.");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public GameLoop() {
		Status = NOT_INITIALIZED;
		gameLoopExecution = null;
	}
	
	public static void main(String[] args) {
		GameLoop mainGameLoop = new GameLoop();
	}
	
	public boolean Stop() {
		if (this.Status != GameLoop.RUNNING) {
			System.out.println("Cannot stop a thread that is not running.");
			return false;
		}
		if (gameLoopExecution == null) {
			System.err.println("Wrong status of the gameloopexecution, either a programming error or an usage error.");
			return false;
		}
		Status = TERMINATED;
		return true;
	}
	
	public Thread Start() {
		this.Status = GameLoop.RUNNING;
		Thread gameLoopExecutionThread = new Thread(new GameLoopExecution());
		gameLoopExecution = gameLoopExecutionThread;
		gameLoopExecution.start();
		return gameLoopExecution;
	}
	
	public int getStatus() {
		return Status;
	}
}
