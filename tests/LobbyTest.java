package tests;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import core.Lobby;
import core.Network;
import junit.framework.TestCase;

public class LobbyTest  {

	private static Lobby lobby;
	
	@BeforeClass
	public static void setup() {
		
			Network network = new Network();
			String myIp;
			lobby = network.createNewLobbyOrReturnsExistingLobby();
			myIp = Network.getIpAddress();
			network.joinLobby(myIp);
	}
	
	/**
	 * Testing the beginning of a chatroom.
	 */
	@Test
	public void testSendingMessageBroadcast() {
		// Arrange
		
		String messageToSend = "testSendingMessageBroadcast";
		
		// Act
		lobby.sendMessageFromHostBroadcast(messageToSend);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Assert
		assertEquals(messageToSend, lobby.getChatHistory().getLast());
	}
	
	@Test
	public void testSendingMultipleMessagesBroadcast() {
		// Arrange
		String[] messagesToSend = {"testSendingMessageBroadcast_message1", "testSendingMessageBroadcast_mess2"}; 
		String[] messageReceived = new String[messagesToSend.length];
		
		// Act
		for (int i = 0; i < messagesToSend.length; i++) {
			System.out.println(messagesToSend[i]);
			lobby.sendMessageFromHostBroadcast(messagesToSend[i]);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			messageReceived[i] = lobby.getChatHistory().getLast();
		}
		
		// Assert
		assertEquals(messagesToSend[0], messageReceived[0]);
		assertEquals(messagesToSend[1], messageReceived[1]);
	}
	
	@Test
	public void testSendingMessagesOverTheLimit() {
	
			// Arrange
			String messageToSend = "testSendingMessageBroadcast_message";
			int numberOfMessagesToSend = Lobby.MAX_CHAT_HISTORY_SIZE*2;
			String[] messageReceived = new String[numberOfMessagesToSend];
			
			// Act
			for (int i = 0; i < numberOfMessagesToSend; i++) {
				
				lobby.sendMessageFromHostBroadcast(messageToSend+i);
				
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				messageReceived[i] = lobby.getChatHistory().getLast();
			}
			
			// Assert
			for (int i = 0; i < numberOfMessagesToSend; i++) {
				assertEquals(messageToSend+i, messageReceived[i]);
			}
	}
}
