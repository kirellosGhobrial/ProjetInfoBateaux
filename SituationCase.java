public class SituationCase {
	
	public static void main (String[] args) {
		int t[][] = new int[10][10];
		for (int i = 0; i < t.length; i++){
			for (int j = 0; j < t.length; j++){
				if(t[i][j] == 0){
					System.out.println("Raté !");
				} else { 
					if(t[i][j] == 1){
						System.out.println("Touché !");
					} else {
						if(t[i][j] == 2){
							System.out.println("Coulé !");
						}
					}
				}
			}
		}
	}
}

