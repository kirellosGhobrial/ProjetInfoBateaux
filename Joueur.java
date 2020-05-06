public class Joueur{
	
	int[][] tabBat;
	String nomJoueur = "Joueur ";
	Bateau[] listBat = new Bateau[5];
	int c = 0;
	
	public Joueur(int lon, int lar,String nom){
		tabBat = new int[lon][lar];	
		nomJoueur = nom;
	}
	
	public void ajouteBateau(Bateau bat){
		if(bat.orientation == 1){
			for(int i=bat.coordonneesBateauY; i<bat.coordonneesBateauY+bat.tailleBateau; i++){
				tabBat[i][bat.coordonneesBateauX] = 1;
			}
		}else{
			for(int i=bat.coordonneesBateauX; i<bat.coordonneesBateauX+bat.tailleBateau; i++){
				tabBat[bat.coordonneesBateauY][i] = 1;
			}
		}
		listBat[c] = bat;
		c++;
	}
	
	public void effacerBat(){
		c = 0;
		for(int i=0; i<tabBat.length; i++){
			for(int j=0; j<tabBat.length; j++){
				tabBat[i][j] = 0;
			}
		}
		
	}
	public void imprim(){
		System.out.println("tableau de "+ nomJoueur);
		for(int i=0; i<tabBat.length; i++){
			for(int j=0; j<tabBat.length; j++){
				System.out.print(tabBat[i][j] + " ");
				//System.out.print(i+""+ j+ " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}
