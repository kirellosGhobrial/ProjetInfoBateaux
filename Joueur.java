public class Joueur{
	
	int[][] tabBat;
	int[][] tabAtt;
	String nomJoueur = "Joueur ";
	Bateau[] listBat = new Bateau[5];
	int c = 0;
	
	public Joueur(int lon, int lar,String nom){
		tabBat = new int[lon][lar];	//tableau 2D qui représente la carte des bateaux du joueur
		tabAtt = new int[lon][lar]; // tableau 2D qui représente la carte d'attaque
		nomJoueur = nom;
	}
	
	public void ajouteBateau(Bateau bat){
		//l'ajout d'un bateau sur la carte tabBat du joueur
		if(bat.orientation == 1){
			for(int i=bat.coordonneesBateauY; i<bat.coordonneesBateauY+bat.tailleBateau; i++){
				tabBat[i][bat.coordonneesBateauX] = 1;			
			}
		}else{
			for(int i=bat.coordonneesBateauX; i<bat.coordonneesBateauX+bat.tailleBateau; i++){
				tabBat[bat.coordonneesBateauY][i] = 1;
			}
		}
		listBat[c] = bat; //l'ajout du tableau dans la liste des Bateau
		c++;
	}
	
	public Bateau attaqueBat(int y, int x){
		/* chercher le bateau trouvé dans ces coordonnées
		 * et changer le tableau de boolean posTouche[] en true
		 */ 
		for(int i = 0; i<listBat.length; i++){
			for(int j = 0; j<listBat[i].posBat.length; j++){
				if(listBat[i].posBat[j][0] == y && listBat[i].posBat[j][1] == x){
					System.out.print(" attaque faite a "
									+ y + " " + x + "  "+listBat[i].toString()+ " "
									);
					listBat[i].posTouche[j] = true;
					return listBat[i];
				}
			}
		}
		//au cas où il y a un problème et le bateau n'est pas trouvé
		System.out.println("attaque non faite ");
		return null;
	}
	
	public void effacerTabBat(){
		//effacer la carte des bateaux et la liste 
		c = 0;
		listBat = new Bateau[5];
		for(int i=0; i<tabBat.length; i++){
			for(int j=0; j<tabBat.length; j++){
				tabBat[i][j] = 0;
			}
		}
		
	}
	
	public void imprim(int[][] tab){
		//afficher sur le terminal la carte
		System.out.println("tableau de "+ nomJoueur);
		for(int i=0; i<tab.length; i++){
			for(int j=0; j<tab.length; j++){
				System.out.print(tab[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public int nomBatCoul(){
		//calculer le nombre de bateaux coulés
		int num =0;
		for(int i =0; i<listBat.length; i++){
			if(listBat[i].estCoule()){
			num++;
			}
		}
		return num;
	}
}
