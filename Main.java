import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main{
	
	
	JFrame frame1 = new JFrame(); 	//Crée une nouvelle fenêtre
	
	JPanel panel1 = new JPanel(new FlowLayout());
	JPanel panel2 = new JPanel(new FlowLayout());
	JLabel label1 = new JLabel("Nom du premier joueur : ");
	JLabel label2 = new JLabel("Nom du deuxieme joueur : ");
	JLabel label3 = new JLabel("Veuillez choisir votre mode de jeu");
	JLabel label4 = new JLabel("Veuillez choisir votre nom");
	JTextField textField1 = new JTextField(12);
	JTextField textField2 = new JTextField(12);
	
	Audio sonBataille = new Audio("BattleshipSong.wav");
	JButton commencer = new JButton("Commencer la bataille");
	JButton multijoueur = new JButton("Multijoueur");
	JButton ordinateur = new JButton("Ordinateur");
	String image= "batailleNavale.png" ; 			//Insertion d'une image à partir de son URL
	ImageIcon battle = new ImageIcon(image); 		//		
	//Création d'un bouton à partir d'une icône
	ImageIcon musiqueOn = new ImageIcon(new ImageIcon("musicOn.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
	ImageIcon mute = new ImageIcon(new ImageIcon("mute.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
	JButton buttonMusique = new JButton(musiqueOn);
	JLabel label5 = new JLabel(battle, JLabel.CENTER);
	boolean contreOrdi = false;
	
	//Crée deux variables pour stocker le nom des Joueurs
	String nameJ1;
	String nameJ2;
	
	public static void main(String[] args){
		new Main();
	}
	
	public Main(){
		
		//le son du jeu
		sonBataille.changeSon();
		buttonMusique.setFocusable(false);
		buttonMusique.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
				sonBataille.changeSon();
				if(sonBataille.estSon){
					buttonMusique.setIcon(musiqueOn);	//Fais un swtich dans l'icône en fonction du son
				}else{
					buttonMusique.setIcon(mute);
				}
			}
		});

		frame1.setLayout(new BorderLayout());
		frame1.setSize(1000,400); 		//Taille de la fenêtre
		frame1.setResizable(false);     //(False) N'autorise pas la modification de la taille de la fenêtre 	
			
		textField1.setBounds(10, 200, 100, 10);
		textField2.setSize(label2.getPreferredSize());
		label1.setSize(label2.getPreferredSize());
		commencer.setBounds(100, 500, 50, 50);
		label2.setSize(label2.getPreferredSize());
		
		//Action de la souris sur le bouton "Multijoueur"
		multijoueur.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
				contreOrdi = false;			//Configure un jeu en mode "multijoueur"
				multijoueur.setVisible(false);		//Cache les boutons 
				ordinateur.setVisible(false);
				label3.setVisible(false);
				//Ajoute le texte et les boutons dans le mode "multijoueur"
				label4.setVisible(true);
				panel1.add(label1);
				panel1.add(textField1);
				panel1.add(label2);
				panel1.add(textField2);
				panel1.add(commencer);
			}
			
		});

		//Action de la souris sur le bouton "Ordinateur"
		ordinateur.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
				contreOrdi = true;
				multijoueur.setVisible(false);
				ordinateur.setVisible(false);
				label3.setVisible(false);
				label4.setVisible(true);
				panel1.add(label1);
				panel1.add(textField1);
				
				panel1.add(commencer);
			}
		});
		
		//Action de la souris sur le bouton "Commencer le jeu"
		commencer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
				String nameJ1 = textField1.getText(); 		//Stocke le nom entré par les joueurs
				if(nameJ1.isEmpty()){
					nameJ1 = "Joueur 1";        		//Remplace le nom de Joueur1 par "Joueur 1"
				}
				if(contreOrdi){
					nameJ2 = "ordi";
				}else{
					nameJ2 = textField2.getText();
					if(nameJ2.isEmpty()){			//Remplace le nom de Joueur2 par "Joueur 2"
						nameJ2 = "Joueur 2";
					}
				}
				sonBataille.arreteSon();
				GameBoard start = new GameBoard(nameJ1, nameJ2, contreOrdi); //Le jeu débute
				frame1.dispose();	
			}
		});
		
		
	
		//Ajoute les boutons
		panel2.add(multijoueur);
		panel2.add(ordinateur);
		panel2.add(buttonMusique, BorderLayout.NORTH);

		
		//Ajoute la fenêtre principale et configure la dispotion de ses éléments
		frame1.add(panel2, BorderLayout.SOUTH);
		panel1.add(label3, BorderLayout.NORTH);
		frame1.add(panel1, BorderLayout.CENTER);
		frame1.add(label5, BorderLayout.NORTH);
		frame1.setLocationRelativeTo(null);	//Centre la fenêtre
		frame1.setVisible(true);		//Rend la fenêtre visible
		frame1.validate();			//Valide la disposition de la fenêtre		
	}
}
