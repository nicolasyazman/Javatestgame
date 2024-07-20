package core;

public class Player {

	private String IP_address;
	private Network network;
	
	/**
	 * Constructor mostly used for tests.
	 */
	public Player() {
		
	}
	
	/**
	 * Network player, allows multigame.
	 * @param IP The IP address of the player.
	 * @param network The network associated with this player.
	 */
	public Player(String IP_address, Network network) {
		this.IP_address = IP_address;
		this.network = network;
	}
	
	public String getIPAddress() {
		return this.IP_address;
	}
	
}
