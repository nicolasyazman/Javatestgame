package tests;

import core.Lobby;
import core.Network;
import junit.framework.TestCase;

public class LobbyTest extends TestCase {

	
	/**
	 * Testing the beginning of a chatroom.
	 */
	public void testSendingMessageBroadcast() {
		// Arrange
		Network network = new Network();
		String myIp;
		String messageToSend = "testSendingMessageBroadcast";
		
		// Act
		Lobby lobby = network.createNewLobbyOrReturnsExistingLobby();
		myIp = Network.getIpAddress();
		network.joinLobby(myIp);
		lobby.sendMessageFromHostBroadcast(messageToSend);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Assert
		System.out.println("Received message: " + lobby.getReceivedPacketEcho());
		System.out.println(lobby.getReceivedPacketEcho().length());
		assertEquals(messageToSend, lobby.getReceivedPacketEcho());
	}
}
