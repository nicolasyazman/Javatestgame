package tests;

import core.GameLoop;
import core.Network;
import core.Player;
import junit.framework.TestCase;

public class GameLoopTest extends TestCase{


	  /**
	   * Simple test checking that the GameLoop is running with the correct status.
	   */
	  public void testGameLoopInitalizationStatus() {
	    GameLoop gl = new GameLoop();
	    assertEquals(gl.getStatus(), GameLoop.NOT_INITIALIZED);
	  }
	  
	  /**
	   * Test that the status changes when the gameloop is running.
	   */
	  public void testGameLoopInitializationStatusChangeWhenRun() {
		  GameLoop gl = new GameLoop();
		  gl.start();
		  assertEquals(gl.getStatus(), GameLoop.RUNNING);
	  }
	  
	  /**
	   * Checking that the run method returns a thread.
	   */
	  public void testGameLoopRunMethodReturnsThread() {
		  // Arrange
		  GameLoop gl = new GameLoop();
		  
		  // Act
		  Object runnable = gl.start();
		  
		  // Asserts
		  assertEquals(runnable.getClass(), Thread.class);
	  }
	  
	  /**
	   * Checking that the run method of the thread really runs the thread
	   */
	  public void testGameLoopRunningStatus() {
		  // Arrange
		  GameLoop gl = new GameLoop();
		  
		  // Act
		  Thread gameloopExecutionInstance = gl.start();
		  
		  // Asserts
		  assertEquals(gameloopExecutionInstance.getState(), java.lang.Thread.State.RUNNABLE);  
	  }
	  
	  public void testGameLoopStop() {
		  // Arrange
		  GameLoop gl = new GameLoop();
		  
		  // Act
		  gl.start();
		  gl.stop();
		  
		  // Asserts
		  assertEquals(gl.getStatus(), GameLoop.TERMINATED);
	  }
	  
	 public void testAddPlayerToGameThroughTheGameLoop() {
		 // Arrange
		 Network network = new Network();
		 Player player = new Player();
		 GameLoop gameloop = new GameLoop(network);
		 
		 // Act
		 network.addNewPlayerInLobby(player);
		 gameloop.start();
		 try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 gameloop.stop();
		 
		 // Assert
		 assertEquals(true, network.getPlayersInGame().contains(player));
		 assertEquals(1, network.getPlayersInGame().size());
		 assertEquals(false, network.getPlayersInLobby().contains(player));
		 assertEquals(0, network.getPlayersInLobby().size());
		 
		 
		 
	 }
	  
	 public void testAddingTenPlayers() {
		 // Arrange
		 boolean res[] = new boolean[10];
		 Network network = new Network();
		 GameLoop gameloop = new GameLoop(network);
		 
		 // Act
		 gameloop.start();
		 for (int i = 0; i < 10; i++) {
			 res[i] = network.addNewPlayerInLobby(new Player());
		 }
		 try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 // Assert
		 assertEquals(true, res[0]);
		 assertEquals(true, res[1]);
		 assertEquals(true, res[2]);
		 assertEquals(true, res[3]);
		 assertEquals(false, res[4]);
		 assertEquals(false, res[5]);
		 assertEquals(false, res[6]);
		 assertEquals(false, res[7]);
		 assertEquals(false, res[8]);
		 assertEquals(false, res[9]);
		 
		 assertEquals(4, network.getPlayersInGame().size());
		 assertEquals(0, network.getPlayersInLobby().size());
	 }
}
