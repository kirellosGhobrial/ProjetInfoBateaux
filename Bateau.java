public class Bateau {
    
    public int orientation;   
    public String orientStr;          
    public int typeDeBateau;
    public int tailleBateau;
    public String nomBateau;
    public int coordonneesBateauX;
    public int coordonneesBateauY;
    public int[][] posBat;
    public boolean[] posTouche;
    public String coule = "le bateau n'est pas encore coule";

    public Bateau(int nb,int orient, int x, int y) {
        if( nb == 4){
		    this.tailleBateau = 2;
		    this.nomBateau = "barque";
		}else if(nb ==3 ){
			this.tailleBateau = 3;
			this.nomBateau = "voilier";
    	}else if( nb == 2){
			this.tailleBateau = 4;
			this.nomBateau = "cargo";
        }else if( nb == 1){
			this.tailleBateau = 5;
			this.nomBateau = "porte-avion";
        }else{
			System.out.println("le bateau est invalide, veuillez recommencer");	
		}
		System.out.println("X "+x + " Y " + y);
		this.orientation = orient;
		this.coordonneesBateauX = x;
		this.coordonneesBateauY = y;
		posBat = new int[tailleBateau][2];
		posTouche = new boolean[tailleBateau];
		if(orient == 1){
			orientStr ="vertical";
			for(int i = 0; i<tailleBateau; i++){
				posBat[i][0] = y+i;
				posBat[i][1] = x ;
			}
		}else if(orient == 2){
			orientStr ="horizontal";
			for(int i = 0; i<tailleBateau; i++){
				posBat[i][0] = y;
				posBat[i][1] = x +i;
			}
		}
		System.out.println(orientStr+ "");
    }
    
    public boolean estCoule(){
        int a = 0;
        for(int i = 0; i < posTouche.length; i++){
            if(posTouche[i]){
                a++;
			}
        }
		return a==this.tailleBateau;
	}
	
	public String toString() {
        String description = "Le bateau est un " + this.nomBateau 
                             + " orienté " + this.orientStr 
                             + " avec pour coordonnées " +coordonneesBateauX +","+coordonneesBateauY
                             + " de taille " + this.tailleBateau;
        return description;
    }
}
