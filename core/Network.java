package core;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayDeque;

public class Network {

	public static int MAX_PLAYER_NUMBER = 4;
	
	/**
	 * The queue of players that want to join the game
	 */
	private ArrayDeque<Player> playersInLobby;
	private ArrayDeque<Player> playersInGame;
	
	public Network() {
		playersInGame = new ArrayDeque();
		playersInLobby = new ArrayDeque();
	}
	
	/**
	 * Adding a new player to the lobby. Cannot be a player already in game, or already in the lobby.
	 * @param p Player we want to add to the lobby.
	 * @return True if the player was successfully added to the lobby. False otherwise.
	 */
	public boolean addNewPlayerInLobby(Player p) {
		if (playersInLobby.size() + playersInGame.size() >= MAX_PLAYER_NUMBER) {
			return false;
		}
		if (playersInGame.contains(p) || playersInLobby.contains(p)) {
			return false;
		}
		playersInLobby.add(p);
		return true;
	}
	
	public static String getIpAddress() {
		try(final DatagramSocket socket = new DatagramSocket()){
		  socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
		  String ip = socket.getLocalAddress().getHostAddress();
		  return ip;
		} catch (SocketException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	public void communicatePlayersPositions() {
		
	}
	
	public ArrayDeque<Player> getPlayersInLobby() {
		return playersInLobby;
	}
	
	public ArrayDeque<Player> getPlayersInGame() {
		return playersInGame;
	}
}
