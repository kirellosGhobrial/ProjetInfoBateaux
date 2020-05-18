import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class  Audio{
	
	File musique;
	Clip clip;
	boolean estSon = false;
		
	public Audio(String fichier){
		musique = new File(fichier);
		try{
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(musique));
		}catch(Exception ex){} 
		
	}
	
	public void mettreSon(){
		try{
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(musique));
			clip.start();
		}catch(Exception ex){} 		
	}
		
	public void changeSon(){
		if(estSon){
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
	
	public void arreteSon(){
		try{
			clip.stop();
		}catch(Exception ex){}
		estSon = false;		
	}
}
