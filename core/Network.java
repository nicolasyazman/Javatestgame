package core;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayDeque;
import java.util.HashMap;

public class Network {

	public static int MAX_PLAYER_NUMBER = 4;
	
	/**
	 * The queue of players that want to join the game
	 */
	private ArrayDeque<Player> playersInLobby;
	private ArrayDeque<Player> playersInGame;
	
	/**
	 * Maps the lobbies to the host's IP Address. Forces to have only one lobby per player.
	 */
	private HashMap<String, Lobby> lobbyMap;
	
	
	public Network() {
		playersInGame = new ArrayDeque();
		playersInLobby = new ArrayDeque();
		lobbyMap = new HashMap<String, Lobby>();
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
	
	/**
	 * This function creates a new lobby with our own IP address.
	 * @return The created lobby or the existing lobby associated with our own IP address.
	 */
	public Lobby createNewLobbyOrReturnsExistingLobby() {
		String myIp = Network.getIpAddress();
		
		if (this.lobbyMap.containsKey(myIp)) {
			return this.lobbyMap.get(myIp); 
		}
		
		Lobby lobby = new Lobby(new Player(myIp, this));
		this.lobbyMap.put(myIp, lobby);
		return lobby;
	}
	
	/**
	 * Call this function to join a lobby.
	 * @param hostIpAddress The IP address of the player hosting the game.
	 */
	public boolean joinLobby(String hostIpAddress) {
		if (hostIpAddress == null) {
			return false;
		}
		
		Lobby hostLobby = this.lobbyMap.get(hostIpAddress);
		if (hostLobby == null) {
			return false;
		}
		
		hostLobby.getPlayersInLobby().add(new Player(getIpAddress(), this));
		return true;
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
