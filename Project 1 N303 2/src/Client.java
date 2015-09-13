import java.util.ArrayList;

public class Client implements Runnable {

	private int id;
	public ArrayList<Service> board;
	private Service service;
	private boolean satisfied = false;
	private boolean first = true;
	private int timeout = 0;
	private int created = 0;

	public Client(int id, ArrayList<Service> board, Service service) {
		this.id = id;
		this.service = service;
		this.board = board;
	}

	@Override
	public void run() {
		//service = new Service(Services.Build, service.id);

		// System.out.println(Thread.currentThread().getName() +
		// "Starting Client Thread: " + name);
		System.out.println("Starting Client Thread: " + id);

		while (created < 10) {
			post();
			satisfied = false;
			first = true;
			service = new Service(Services.getRandomService(),board.size()); 
			created++;
			try{
				Thread.sleep(1000);
			}catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// System.out.println(Thread.currentThread().getName() + " End.");
		//System.out.println("Ending Client Thread: " + id);

	}

	private void post() {
		while(!satisfied){
				if(first){
					first = false;
					int place = checkBoard();
					System.out.println("Client Thread: " + id + " Checking For: " + service.type + " : " + service.id + " Returning: " + place);
					if (place >= 0) {
						remove(place);
					}else{
						service.addClient(this);
						service.setClientID(id);
						board.add(service);
						System.out.println("Client Thread: " + id + " Added: " + service.type);
					}
			}else if(service.getClientID() == this.id && service.getProviderID() < 0){
				try {
					System.out.println("CLient Thread: " + id + " Waiting For Provider...");
					Thread.sleep(500);
					timeout++;
					if(timeout > 5)satisfied = true;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else{
				satisfied = true;
			}
		}

	}

	private void remove(int place) {
		if (board.get(place).type == this.service.type && board.get(place).hasClient == false) {
			//board.remove(place);
			board.get(place).addClient(this);
			board.get(place).setClientID(id);
			satisfied = true;
			System.out.println("CLient Thread: " + id + " Was Set As Client For: " + board.get(place).id);
		}else{
			System.out.println("Client Thread:" + id + " Was Too Slow");
			first = false;
		}
	}

	private int checkBoard() {
		for (int i = 0; i < board.size(); i++) {
			if (board.get(i).type == this.service.type && board.get(i).hasClient == false) {
				return i;
			}
		}
		return -1;
	}
}
