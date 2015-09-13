import java.util.Random;

	public enum Services{
		Paint, Mow, Fix, Build, Garden, Cook, Clean, Music, Drive, Work;
		
		public static Services getRandomService(){
			Random random = new Random();
			return values()[random.nextInt(values().length)];
		}
		
		
	}