import java.util.Random;


public enum Locations {
	Wellington, Auckland, Dunedin, Chirstchurch;
	
	public static Locations getRandomLocation(){
		Random random = new Random();
		return values()[random.nextInt(values().length)];
	}
}
