import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class BulletinBoard {
	
	public static ArrayList<Service> board = new ArrayList<Service>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		ExecutorService cluster = Executors.newFixedThreadPool(10);
		for(int i = 0; i < 6; i++){
			Runnable work;
			Service service = new Service(Services.getRandomService(),i);
			//System.out.println("Adding To List: " + service.type);
			if(i%2==0){
				work = new Client(i,board,service);
			}else{
				work = new Provider(i,board,service);
			}
			cluster.execute(work);

		}

		cluster.shutdown();
		while(!cluster.isTerminated()){};
		System.out.println("Finished");
		System.out.println(board.size());
		for(int i = 0; i < board.size(); i++){
			if(board.get(i).getClientID() == -1 || board.get(i).getProviderID() == -1){
			System.out.println(board.get(i).id + " : " + board.get(i).type);
			System.out.println("Client ID: " + board.get(i).getClientID() + " Provider ID: " + board.get(i).getProviderID());
			}

		}
	}

}
