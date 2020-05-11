public class Joueur{
	
	int[][] tabBat;
	int[][] numBat;
	int[][] tabEn;
	String nomJoueur = "Joueur ";
	Bateau[] listBat = new Bateau[5];
	int c = 0;
	
	public Joueur(int lon, int lar,String nom){
		tabBat = new int[lon][lar];	
		tabEn = new int[lon][lar];
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
	
	public void attaqueBat(int y, int x){
		int[] tempTab = {y,x};
		for(int i = 0; i<listBat.length; i++){
			for(int j = 0; j<listBat[i].posBat.length; j++){
				if(listBat[i].posBat[j][0] == y && listBat[i].posBat[j][1] == x){
					System.out.print(listBat[i].posTouche[j] + " attaque faite a "
									+ y + " " + x + "  "+listBat[i].toString()+ " "
									);
					listBat[i].posTouche[j] = true;
					System.out.println(listBat[i].posTouche[j]);
					return;
				}
			}
		}
		System.out.println("attaque non faite ");
	}
	
	public void effacerBat(){
		c = 0;
		for(int i=0; i<tabBat.length; i++){
			for(int j=0; j<tabBat.length; j++){
				tabBat[i][j] = 0;
			}
		}
		
	}
	
	public void imprim(int[][] tab){
		System.out.println("tableau de "+ nomJoueur);
		for(int i=0; i<tab.length; i++){
			for(int j=0; j<tab.length; j++){
				System.out.print(tab[i][j] + " ");
				//System.out.print(i+""+ j+ " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public int nomBatCoul(){
		int num =0;
		for(int i =0; i<listBat.length; i++){
			if(listBat[i].estCoule()){
			num++;
			}
		}
		return num;
	}
}