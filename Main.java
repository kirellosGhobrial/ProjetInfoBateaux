import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main{
	
	
	public static void main(String[] args){
		
		//Joue le son du jeu
		JButton buttonMusique = new JButton("Couper le son");
		Audio sonBataille = new Audio("BattleshipSong.wav");
		sonBataille.changeSon();
		buttonMusique.setFocusable(false);
		buttonMusique.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
				sonBataille.changeSon();
				if(sonBataille.estSon){
					JButton button2 = new JButton("Couper le son");
				}else{
					JButton button2 = new JButton("Mettre le son");
				}
			}
		});
				
		//Crée une nouvelle fenêtre
		JFrame frame1 = new JFrame();
		frame1.setLayout(new BorderLayout());
		frame1.setSize(1000,400); 		//Taille de la fenêtre
		frame1.setResizable(false);     //(False) N'autorise pas la modification de la taille de la fenêtre 
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		//Crée un nouveau Panel
		JPanel panel1 = new JPanel(new FlowLayout());
		JPanel panel2 = new JPanel(new FlowLayout());
		
		//Crée un nouveau button
		JButton commencer = new JButton("Commencer la bataille");
		JButton multijoueur = new JButton("Multijoueur");
		JButton ordinateur = new JButton("Ordinateur");
		
		
		//Crée de nouveaux Label et les configure
		JLabel label1 = new JLabel("Nom du premier joueur : ");
		JLabel label2 = new JLabel("Nom du deuxieme joueur : ");
		JLabel label3 = new JLabel("Veuillez choisir votre mode de jeu");
		JLabel label4 = new JLabel("Veuillez choisir votre nom");
		JTextField textField1 = new JTextField(12);
		JTextField textField2 = new JTextField(12);
		textField1.setBounds(10, 200, 100, 10);
		textField2.setSize(label2.getPreferredSize());
		label1.setSize(label2.getPreferredSize());
		commencer.setBounds(100, 500, 50, 50);
		label2.setSize(label2.getPreferredSize());
		
		//Insertion d'une image à partir de son URL
		String image= "/Users/aminemr/Pictures/bataillenavale.png" ;
		ImageIcon battle = new ImageIcon(image);
		JLabel label5 = new JLabel(battle, JLabel.CENTER);

	 
		
		//Crée deux variables pour stocker le nom des Joueurs
		String nameJ1 = "a" ;
		String nameJ2 = "b" ;
		
		//Action de la souris sur le bouton "Multijoueur"
		multijoueur.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
				multijoueur.setVisible(false);
				ordinateur.setVisible(false);
				label3.setVisible(false);
				label4.setVisible(true);
				panel1.add(label1);
				panel1.add(textField1);
				panel1.add(label2);
				panel1.add(textField2);
				String nameJ1 = textField1.getText(); 			//Stocke le nom entré par les joueurs
				String nameJ2 = textField2.getText();
				if(nameJ1.isEmpty()){
					nameJ1 = "Joueur 1";        				//Remplace le nom de Joueur1 par "Joueur 1"
				}
				if(nameJ2.isEmpty()){							//Remplace le nom de Joueur2 par "Joueur 2"
					nameJ2 = "Joueur 2";
				}
				panel1.add(commencer);
			}
		});
		
		//Action de la souris sur le bouton "Ordinateur"
		ordinateur.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
				multijoueur.setVisible(false);
				ordinateur.setVisible(false);
				label3.setVisible(false);
				label4.setVisible(true);
				panel1.add(label1);
				panel1.add(textField1);
				String nameJ1 = textField1.getText(); 			//Stocke le nom entré par les joueurs
				String nameJ2 = "Ordinateur";
				if(nameJ1.isEmpty()){
					nameJ1 = "Joueur 1";        				//Remplace le nom de Joueur1 par "Joueur 1"
				}
				panel1.add(commencer);
			}
		});
		
		//Action de la souris sur le bouton "Commencer le jeu"
		commencer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
				GameBoard start = new GameBoard(nameJ1, nameJ2);			//Le jeu débute
				frame1.dispose();	
			}
		});
		
		
	
		//Ajoute les boutons
		panel2.add(multijoueur);
		panel2.add(ordinateur);
		panel2.add(buttonMusique, BorderLayout.NORTH);

		
		//Ajoute la fenêtre principale et la configure
		frame1.add(panel2, BorderLayout.SOUTH);
		panel1.add(label3, BorderLayout.NORTH);
		frame1.add(panel1, BorderLayout.CENTER);
		frame1.add(label5, BorderLayout.NORTH);
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
		frame1.validate();
	}
}
