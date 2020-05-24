import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main{
	
	int mode = 1;	
	String nameJ1;
	String nameJ2;
	JFrame frame1 = new JFrame(); 	//Crée une nouvelle fenêtre
	JPanel panel1 = new JPanel(new FlowLayout());
	JPanel panel2 = new JPanel(new FlowLayout());
	JLabel label1 = new JLabel("Nom du premier joueur : ");
	JLabel label2 = new JLabel("Nom du deuxieme joueur : ");
	JLabel label3 = new JLabel("Veuillez choisir votre mode de jeu");
	JLabel label4 = new JLabel("Veuillez choisir votre nom");
	JLabel label6 = new JLabel("Difficulte : ");
	JTextField textField1 = new JTextField(12);
	JTextField textField2 = new JTextField(12);
	JButton commencer = new JButton("Commencer la bataille");
	JButton multijoueur = new JButton("Multijoueur");
	JButton ordinateur = new JButton("Ordinateur");
	JButton retourner = new JButton("retourner");
	JRadioButton rbDifficile = new JRadioButton("Difficile");
	JRadioButton rbFacile = new JRadioButton("Facile");
	ButtonGroup bgDifficulte = new ButtonGroup();
	//Insertion d'une image à partir de son URL
	ImageIcon battle = new ImageIcon("batailleNavale.png"); 
	// redimensionner l'image pour s'adapter à la taille du bouton
	ImageIcon musiqueOn = new ImageIcon(new ImageIcon("musique.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
	ImageIcon mute = new ImageIcon(new ImageIcon("musiqueMute.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
	Audio musiqueBataille = new Audio("BattleshipSong.wav");
	JButton btnMusique = new JButton(musiqueOn);
	JLabel label5 = new JLabel(battle);

	public static void main(String[] args){
		new Main();
	}
	
	public Main(){
		
		musiqueBataille.changeSon();
		Dimension tailleFenetre = Toolkit.getDefaultToolkit().getScreenSize();
		int tailleFenetreX = tailleFenetre.width*3/4;
		int tailleFenetreY = tailleFenetre.height*1/2;
		frame1.setLayout(new BorderLayout());
		frame1.setSize(900,500); 		//Taille de la fenêtre
		frame1.setResizable(false);     //(False) N'autorise pas la modification de la taille de la fenêtre 	
		textField1.setBounds(10, 200, 100, 10);
		textField2.setSize(label2.getPreferredSize());
		label1.setSize(label2.getPreferredSize());
		commencer.setBounds(100, 500, 50, 50);
		label2.setSize(label2.getPreferredSize());

		
		//Ajoute les boutons
		panel2.add(multijoueur);
		panel2.add(ordinateur);
		panel2.add(btnMusique, BorderLayout.NORTH);
		btnMusique.setFocusable(false);
		bgDifficulte.add(rbFacile);
		bgDifficulte.add(rbDifficile);
		
		//Ajoute la fenêtre principale et la configure
		frame1.add(panel2, BorderLayout.SOUTH);
		panel1.add(label3, BorderLayout.NORTH);
		frame1.add(panel1, BorderLayout.CENTER);
		frame1.add(label5, BorderLayout.NORTH);
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
		
		//Action de la souris sur le bouton "musique"
		btnMusique.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
				musiqueBataille.changeSon();
				if(musiqueBataille.estSon){
					btnMusique.setIcon(musiqueOn);
				}else{
					btnMusique.setIcon(mute);
				}
			}
		});

		//Action de la souris sur le bouton "Multijoueur"
		multijoueur.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
				mode = 0;
				multijoueur.setVisible(false);
				ordinateur.setVisible(false);
				label3.setVisible(false);
				label4.setVisible(true);
				panel1.add(label1);
				panel1.add(textField1);
				panel1.add(label2);
				panel1.add(textField2);
				panel1.add(commencer);
				panel2.add(retourner, BorderLayout.EAST);
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
				panel1.add(label6);
				panel1.add(rbFacile);
				rbFacile.setSelected(true);
				panel1.add(rbDifficile);
				panel1.add(commencer);
				panel2.add(retourner, BorderLayout.EAST);
			}
		});
		
		//Action de la souris sur le bouton "retourner"
		retourner.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
				frame1.dispose();
				musiqueBataille.arreteSon();
				new Main();
			}
		});
		
		//Action de la souris sur le bouton "Commencer le jeu"
		commencer.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
				String nameJ1 = textField1.getText(); 			//Stocke le nom entré par les joueurs
				if(nameJ1.isEmpty()){
					nameJ1 = "Joueur 1";        				//Remplace le nom de Joueur1 par "Joueur 1"
				}
				if(!(mode==0)){
					nameJ2 = "ordi";
					if(rbDifficile.isSelected()){
						mode = 2;
					}else if(rbFacile.isSelected()){
						mode = 1;
					}
				}else{
					nameJ2 = textField2.getText();
					if(nameJ2.isEmpty()){							//Remplace le nom de Joueur2 par "Joueur 2"
						nameJ2 = "Joueur 2";
					}
				}
				musiqueBataille.arreteSon();
				new GameBoard(nameJ1, nameJ2, mode); //Le jeu débute
				frame1.dispose();	
			}
		});
	}
}
