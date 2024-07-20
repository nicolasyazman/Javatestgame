package core;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Lobby {

	public static int CHATROOM_PORT = 8888;
	public static int MAX_MESSAGE_SIZE_BYTES = 1000;
	public static int MAX_CHAT_HISTORY_SIZE = 100;
	
	private Player host;
	private ArrayDeque<Player> playersInLobby;
	private DatagramSocket senderSocket = null;
	private ArrayDeque<String> chatHistory;
	
	
	// Mostly for testing for now.
	private byte[] receivedPacketEcho;
	
	// For testing
	public ArrayDeque<String> getChatHistory() {
		return chatHistory;
	}
	
	public Lobby(Player host) {
		this.host = host;
		this.playersInLobby = new ArrayDeque<Player>();
		this.receivedPacketEcho = new byte[MAX_MESSAGE_SIZE_BYTES];
		this.chatHistory = new ArrayDeque<String>();
		Arrays.fill(this.receivedPacketEcho, (byte)0);
		this.tryToConnect();
		ReceiverThread receiverThread = new ReceiverThread();
		receiverThread.start();
		
	}
	
	public void tryToConnect() {
		try {
			if (this.senderSocket == null) {
				this.senderSocket = new DatagramSocket(null );
				this.senderSocket.bind(new InetSocketAddress(host.getIPAddress(), CHATROOM_PORT));
		
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Mostly for testing for now
	 */
	private class ReceiverThread extends Thread {
		
		public void run() {
			
			// For now we don't necessarily want to terminate the thread.
			while (true) {
				DatagramPacket packetReceive = new DatagramPacket(receivedPacketEcho, MAX_MESSAGE_SIZE_BYTES);
				try {
					
					senderSocket.receive(packetReceive);
					System.out.println("Received packet: " + new String(packetReceive.getData()));
					// Adding it to chat history
					while (chatHistory.size() >= MAX_CHAT_HISTORY_SIZE) {
						chatHistory.pop(); // We don't want to keep an archive of the chat so just depop.
					}
					
					chatHistory.add(getReceivedPacketEcho());
					Arrays.fill(receivedPacketEcho, (byte)0);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void sendMessageFromHostBroadcast(String message) {
		
		try {
			// First we create a datagram socket to send datagram packets.
			// Why Datagram? UDP because we don't care about the proper reception of the packets or not.
					
			//byte[] receiveBuffer = new byte[message.getBytes().length];
	
			
			
			//s.bind(new InetSocketAddress(host.getIPAddress(), CHATROOM_PORT));
			
			// Creating an iterator
			Iterator<Player> value = this.playersInLobby.iterator();
			while (value.hasNext()) {
				Player p = value.next();
				InetSocketAddress address = new InetSocketAddress(p.getIPAddress(), CHATROOM_PORT);
				this.senderSocket.send(new DatagramPacket(message.getBytes(), message.getBytes().length, address));
				System.out.println("Sent message: " + message);
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
	}
	
	public ArrayDeque<Player> getPlayersInLobby() {
		return playersInLobby;
	}
	
	// Getters and setters
	public Player getHost() {
		return this.host;
	}
	
	public String getReceivedPacketEcho() {
		int idx = 0;
		
		while (idx < MAX_MESSAGE_SIZE_BYTES && receivedPacketEcho[idx] != 0) {
			idx++;
		}
		
		return new String(receivedPacketEcho, 0, idx, StandardCharsets.UTF_8);
	}
	
	public String getChatHistoryMessage(int messageNumber) {
		if (messageNumber < 0) {
			return null;
		}
		if (messageNumber >= chatHistory.size()) {
			return null;
		}
		
		Iterator<String> i = chatHistory.iterator();
		int idx = 0;
		
		String currentMessage;
		while (i.hasNext())
		{
			currentMessage = i.next();
			if (messageNumber == idx) {
				return currentMessage;
			}
			idx++;
		}
		
		return null;
	}
}
