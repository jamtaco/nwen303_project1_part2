import java.util.ArrayList;

public class Service {
	
	Services type;
	int id;
	boolean hasClient = false;
	boolean hasProvider = false;
	private int clientID = -1;
	private int providerID = -1;
	private int maxClients;
	private Locations orig;
	private Locations dest;
	private ArrayList<Client> clients = new ArrayList<Client>();
	
	public Service(Services type, int id){
		this.type = type;
		this.id = id;
		if(type == Services.Drive){
			maxClients = 3;
		}else{
			maxClients = 1;
		}
	}
	
	public ArrayList<Client> getClientList(){
		return clients;
	}
	
	public Services getService(){
		return type;
	}
	
	public int getID(){
		return id;
	}
	
	public void addClient(Client cl){
		clients.add(cl);
		hasClient = true;
		
	}
	
	public boolean getClient(){
		return hasClient;
	}
	
	public void addProvider(){
		hasProvider = true;
	}
	
	public boolean getProvider(){
		return hasProvider;
	}
	
	public boolean satisfied(){
		if(hasClient == true && hasProvider == true && clients.size() == maxClients){
			return true;
		}
		return false;
	}

	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public int getProviderID() {
		return providerID;
	}

	public void setProviderID(int providerID) {
		this.providerID = providerID;
	}

}
