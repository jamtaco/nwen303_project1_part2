import java.util.ArrayList;

public class Provider implements Runnable {

	private int id;
	public ArrayList<Service> board;
	private Service service;
	private boolean satisfied = false;
	private boolean first = true;
	private int timeout = 0;
	private int created = 0;

	public Provider(int id, ArrayList<Service> board, Service service) {
		this.id = id;
		this.board = board;
		this.service = service;
	}

	@Override
	public void run() {
		// System.out.println(Thread.currentThread().getName() +
		// "Starting Provider Thread: " + name);
		//service = new Service(Services.Build, service.id);

		System.out.println("Starting Provider Thread: " + id);
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
		//System.out.println("Ending Provider Thread: " + id);

	}
	
	private void post() {
		while(!satisfied){
				if(first){
					first = false;
					int place = checkBoard();
					System.out.println("Provider Thread: " + id + " Checking For: " + service.type + " : " + service.id + " Returning: " + place);
					if (place >= 0) {
						remove(place);
					}else{
						service.addProvider();
						service.setProviderID(id);
						board.add(service);
						System.out.println("Provider Thread: " + id + " Added: " + service.type);
					}
			}else if(service.getProviderID() == this.id && service.getClientID() < 0){
				try {
					System.out.println("Provider Thread: " + id + " Waiting For Client...");
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
		if (board.get(place).type == this.service.type && board.get(place).hasProvider == false) {
			//board.remove(place);
			board.get(place).addProvider();
			board.get(place).setProviderID(id);
			satisfied = true;
			System.out.println("Provider Thread: " + id + " Was Set As Provider For: " + board.get(place).id);
		}else{
			System.out.println("Provider Thread:" + id + " Was Too Slow");
			first = true;
		}
	}
	
	private int checkBoard() {
		for (int i = 0; i < board.size(); i++) {
			if (board.get(i).type == this.service.type && board.get(i).hasProvider == false) {
				return i;
			}
		}
		return -1;
	}

}