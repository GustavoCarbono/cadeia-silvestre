package utils;

public class IdUnica {
	private static int idAnimal = 1;
	
	public static int getIdUnico() {
		return idAnimal++;
	}
}
