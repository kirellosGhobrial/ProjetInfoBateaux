import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class  Audio{
	
	File musique;					//Création d'un attribut de type File
	Clip clip;						//Création d'un attribut clip
	boolean estSon = false;			//Création d'un boolean estSon
		
	public Audio(String fichier){		
		musique = new File(fichier);
		try{
			clip = AudioSystem.getClip();		//Permet d'obtenir le son à partir du fichier .wav présent sur l'ordinateur
			clip.open(AudioSystem.getAudioInputStream(musique));
		}catch(Exception ex){} 
		
	}
	
	
	//Méthode mettreSon qui déclenche le son
	public void mettreSon(){
		try{
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(musique));
			clip.start();
		}catch(Exception ex){} 		
	}
	
	
	//Méthode changeSon qui permet de changer le son 
	public void changeSon(){
		if(estSon){			//Si le son est activé, la méthode va couper le son ou sinon le contraire.
			try{
				clip.stop();
			}catch(Exception ex){}
			estSon = false;
		}else{
			try{
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}catch(Exception ex){} 
			estSon = true;
		}
	}	
	
	
	//Méthode arreteSon qui permet d'arrêter le son
	public void arreteSon(){
		try{
			clip.stop();
		}catch(Exception ex){}
		estSon = false;		
	}
}
