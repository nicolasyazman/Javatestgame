package core;



public class GameLoop {

	public static int NOT_INITIALIZED = 0;
	public static int RUNNING = 1;
	public static int TERMINATED = 2;

	private int status;
	private Thread gameLoopExecution;
	private Network network;

	public class GameLoopExecution extends Thread {

		/**
		 * Adds player from the lobby to the game if the gameloop is available for that.
		 * @return True if the player has been added to the GameLoop, false otherwise
		 */
		public boolean addPlayersFromLobbyToQueueIfPossible() {
			if (network != null) {
				if (network.getPlayersInLobby() != null && network.getPlayersInGame() != null) { // Sanity check
					if (network.getPlayersInLobby().size() > 0) {
						Player playerFromLobbyToAddToGame = network.getPlayersInLobby().pop();
						network.getPlayersInGame().add(playerFromLobbyToAddToGame);
						return true;
					}
				}
			}
			return false;
		}
		
		public void run() {
			// TODO Auto-generated method stub
			while (status == GameLoop.RUNNING) {
				
				// Adding players from lobby to the game.
				addPlayersFromLobbyToQueueIfPossible();
				
				// Communicate information
				network.communicatePlayersPositions();
				
				System.out.println("GameLoop alive.");
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * Offline or for testing
	 */
	public GameLoop() {
		status = NOT_INITIALIZED;
	}
	
	/**
	 * With Network handling
	 * @param network Reference to a network instance.
	 */
	public GameLoop(Network network) {
		status = NOT_INITIALIZED;
		gameLoopExecution = null;
		this.network = network;
	}
	
	public static void main(String[] args) {
		GameLoop mainGameLoop = new GameLoop();
	}
	
	public boolean stop() {
		if (this.status != GameLoop.RUNNING) {
			System.out.println("Cannot stop a thread that is not running.");
			return false;
		}
		if (gameLoopExecution == null) {
			System.err.println("Wrong status of the gameloopexecution, either a programming error or an usage error.");
			return false;
		}
		status = TERMINATED;
		return true;
	}
	
	public Thread start() {
		this.status = GameLoop.RUNNING;
		Thread gameLoopExecutionThread = new Thread(new GameLoopExecution());
		gameLoopExecution = gameLoopExecutionThread;
		gameLoopExecution.start();
		return gameLoopExecution;
	}
	
	public int getStatus() {
		return status;
	}
}
