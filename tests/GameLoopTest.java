package tests;
import java.util.concurrent.Future.State;

import core.GameLoop;
import core.GameLoop.GameLoopExecution;

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
		  gl.Start();
		  assertEquals(gl.getStatus(), GameLoop.RUNNING);
	  }
	  
	  /**
	   * Checking that the run method returns a thread.
	   */
	  public void testGameLoopRunMethodReturnsThread() {
		  // Arrange
		  GameLoop gl = new GameLoop();
		  
		  // Act
		  Object runnable = gl.Start();
		  
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
		  Thread gameloopExecutionInstance = gl.Start();
		  
		  // Asserts
		  assertEquals(gameloopExecutionInstance.getState(), java.lang.Thread.State.RUNNABLE);  
	  }
	  
	  public void testGameLoopStop() {
		  // Arrange
		  GameLoop gl = new GameLoop();
		  
		  // Act
		  gl.Start();
		  gl.Stop();
		  
		  // Asserts
		  assertEquals(gl.getStatus(), GameLoop.TERMINATED);
	  }
	  
}
