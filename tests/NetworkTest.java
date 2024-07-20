package tests;

import junit.framework.TestCase;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.Lobby;
import core.Network;
import core.Player;

public class NetworkTest extends TestCase{

	public void testAddingNewPlayerToQueue() {
		
		// Arrange
		Network network = new Network();
		Player p1 = new Player();
		
		// Act
		network.addNewPlayerInLobby(p1);
		
		// Assert
		assertEquals(network.getPlayersInLobby().contains(p1), true);
		assertEquals(network.getPlayersInLobby().size(),1);
	}
	
	public void testAddingFivePlayersReturnsFalse() {
		// Arrange
		Network network = new Network();
		Player p1 = new Player();
		Player p2 = new Player();
		Player p3 = new Player();
		Player p4 = new Player();
		Player p5 = new Player();
		
		// Act
		boolean res1 = network.addNewPlayerInLobby(p1);
		boolean res2 = network.addNewPlayerInLobby(p2);
		boolean res3 = network.addNewPlayerInLobby(p3);
		boolean res4 = network.addNewPlayerInLobby(p4);
		boolean res5 = network.addNewPlayerInLobby(p5);
		
		// Assert
		assertEquals(res1, true);
		assertEquals(res2, true);
		assertEquals(res3, true);
		assertEquals(res4, true);
		assertEquals(res5, false);
		assertEquals(network.getPlayersInLobby().contains(p1), true);
		assertEquals(network.getPlayersInLobby().contains(p2), true);
		assertEquals(network.getPlayersInLobby().contains(p3), true);
		assertEquals(network.getPlayersInLobby().contains(p4), true);
		assertEquals(network.getPlayersInLobby().contains(p5), false);
		assertEquals(network.getPlayersInLobby().size(), 4);
		
	}
	
	public void testIpAddress() {
		// Arrange
		
		// Act
		String myIp = Network.getIpAddress();
		
		// Assert
		assertNotEquals("127.0.0.1", myIp);
		assertNotEquals("0.0.0.0", myIp);
		assertNotEquals(null, myIp);
		
	}
	
	public void testCreateLobby() {
		// Arrange
		Network network = new Network();
		
		// Act
		Lobby lobby = network.createNewLobbyOrReturnsExistingLobby();
		
		// Assert
		assertNotEquals(null,lobby.getHost().getIPAddress()); 
		assertNotEquals("127.0.0.1",lobby.getHost().getIPAddress());
		
	}
	
	public void testJoinLobby() {
		// Arrange
		Network network = new Network();
		String myIp;
		
		// Act
		Lobby lobby = network.createNewLobbyOrReturnsExistingLobby();
		myIp = Network.getIpAddress();
		network.joinLobby(myIp);
		int numberOfPlayersInLobby = lobby.getPlayersInLobby().size();
		
		// Assert
		assertEquals(1, numberOfPlayersInLobby);
		
	}
	
	public void testCreateNewLobbyReturnsLobbyWhenUsedTwice() {
		// Arrange
		Lobby lobby1, lobby2;
		Network network = new Network();
		
		// Act
		lobby1 = network.createNewLobbyOrReturnsExistingLobby();
		lobby2 = network.createNewLobbyOrReturnsExistingLobby();
		
		// Assert
		assertEquals(lobby1, lobby2);
	}
	

	
	
}
