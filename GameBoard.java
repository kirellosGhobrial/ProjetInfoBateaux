import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
@SuppressWarnings("unchecked") //il donne un warning quand on déclare un tableau de String ( String[] );

public class GameBoard{
	
	Joueur j1 = new Joueur(10, 10, "Joueur 1");
	Joueur j2 = new Joueur(10, 10, "Joueur 2");
	int nbAttaque = 1;
	int jou = 1;
	int batLon = 3;
	int batDir = 2;
	int longueur=10 ;
	int largeur=10;
	int coordX = 0;
	int coordY = 0;
	int tailleEcranX;
	int tailleEcranY;
	int[] coor = new int[2];
	int numBat = 0;
	int[][] grille1Int = new int[longueur][largeur];
	int[][] grille2Int = new int[longueur][largeur];
	int[][] carteOrdi = new int[longueur][largeur];
	boolean SourisCarteD = false;
	boolean SourisCarteG = false;
	boolean posAttChoisi = false; 
	boolean ordi;
	boolean difficile;
	boolean effetAudio = true;
	// tout ce qui est lié à l'interface
	JFrame mainFrame = new JFrame();
	JPanel top = new JPanel();	
	JPanel centre = new JPanel(new BorderLayout());
	JPanel carteD = new JPanel(new GridLayout(longueur,largeur,5,5));
	JPanel carteG = new JPanel(new GridLayout(longueur,largeur,5,5));
	JLabel[][] grille1 = new JLabel[longueur][largeur];
	JLabel[][] grille2 = new JLabel[longueur][largeur];
	JLabel lblTop = new JLabel("Bataille Navale");
	JLabel lblCentre1 = new JLabel();
	JLabel lblCentre2 = new JLabel();
	JLabel lblCentre3 = new JLabel();
	JLabel lblCentre4 = new JLabel();
	JLabel lblCentre5 = new JLabel();
	String[] listNomBat = {"porte avion de taille 5", "cargo de taille 4", "voilier de taille 3", "voilier de taille 3", "barque de taille 2"};	
	JList liste;
	DefaultListModel listModel = new DefaultListModel();
	JButton btnBateau = new JButton("commencer");
	JButton btnAttaque = new JButton("attaque");
	JButton btnMusique = new JButton();
	JButton btnSon = new JButton();
	ImageIcon imgMusique  = new ImageIcon(new ImageIcon("musique.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
	ImageIcon imgMusiqueMute = new ImageIcon(new ImageIcon("musiqueMute.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
	ImageIcon imgSon  = new ImageIcon(new ImageIcon("son.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
	ImageIcon imgSonMute = new ImageIcon(new ImageIcon("sonMute.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
	JMenuBar mbMenu = new JMenuBar();    
	JMenu mMenu = new JMenu("Menu");    
	JMenuItem miRecommencer = new JMenuItem("recommencer");
	JMenuItem miNouveau = new JMenuItem("Nouveau jeu");
	JMenuItem miAide = new JMenuItem("Aide");
	Audio musiqueBataille = new Audio("BattleshipSong.wav");
	Audio explosion = new Audio("Explosion.wav");
	Audio tombeDansLeau = new Audio("tombeDansLeau.wav");	
		
	public GameBoard(String name1, String name2, int mode){
		musiqueBataille.changeSon();
		j1.nomJoueur = name1;
		j2.nomJoueur = name2;
		if(mode == 0){
			ordi = false;
		}else if(mode == 1){
			ordi = true;
			difficile = false;
		}else{
			ordi = true;
			difficile = true;
		}

		//set frame
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setTitle("BATAILLE NAVALE");
		Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
		tailleEcranX = tailleEcran.width-50;
		tailleEcranY = tailleEcran.height-50;
		mainFrame.setSize(tailleEcranX, tailleEcranY);
		mainFrame.setResizable(false);
		//set menu
		mMenu.add(miRecommencer);
		mMenu.add(miNouveau);
		mMenu.add(miAide);
		mbMenu.add(mMenu);
		mainFrame.setJMenuBar(mbMenu);
		
		//cartes
		mainFrame.add(carteD, BorderLayout.EAST);
		mainFrame.add(carteG, BorderLayout.WEST);
		int tailleGrilleX = tailleEcranX /(3*largeur);
		int tailleGrilleY = (tailleEcranY- 40) / longueur ;
		
		//carte droite
		for(int i=0; i<grille1.length; i++){
			for(int j =0; j<grille1[0].length; j++){
				grille1[i][j] = new JLabel();
				grille1[i][j].addMouseListener(new  mvtSourisCarteD());
				grille1[i][j].setOpaque(true);
				grille1[i][j].setBackground(Color.CYAN);
				carteD.add(grille1[i][j]);
				grille1[i][j].setMaximumSize(new Dimension(tailleGrilleX, tailleGrilleY));
				grille1[i][j].setMinimumSize(new Dimension(tailleGrilleX, tailleGrilleY));
				grille1[i][j].setPreferredSize(new Dimension(tailleGrilleX, tailleGrilleY));
			}
		}
		carteD.setBackground(Color.BLACK);

		//carte gauche
		for(int i=0; i<grille2.length; i++){
			for(int j =0; j<grille2[0].length; j++){
				
				grille2[i][j] = new JLabel();
				grille2[i][j].setSize(30,30);
				grille2[i][j].addMouseListener(new  mvtSourisCarteG());
				carteG.add(grille2[i][j]);
				grille2[i][j].setOpaque(true);
				grille2[i][j].setBackground(Color.CYAN);
				grille2[i][j].setMaximumSize(new Dimension(tailleGrilleX, tailleGrilleY));
				grille2[i][j].setMinimumSize(new Dimension(tailleGrilleX, tailleGrilleY));
				grille2[i][j].setPreferredSize(new Dimension(tailleGrilleX, tailleGrilleY));
			}
		}
		carteG.setBackground(Color.BLACK);
		
		//top
		mainFrame.add(top, BorderLayout.NORTH);
		top.setLayout(null);
		top.setPreferredSize(new Dimension(0,30));
		lblTop.setSize(lblTop.getPreferredSize());
		lblTop.setBounds( (tailleEcranX/2) - (lblTop.getWidth()/2) , 0, 200,30);
		top.add(lblTop);
		btnMusique.setBounds( tailleEcranX*90/100 -5 , 0, tailleEcranX*10/100, 30);
		btnMusique.setIcon(imgMusique);
		top.add(btnMusique);
		btnMusique.setFocusable(false);
		btnSon.setBounds( tailleEcranX*80/100 -5 , 0, tailleEcranX*10/100, 30);
		btnSon.setIcon(imgSon);
		top.add(btnSon);
		btnSon.setFocusable(false);
		//centre
		mainFrame.add(centre, BorderLayout.CENTER);
		centre.setLayout(null);
		Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
		centre.setBorder(border);
		//add , visible
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
		//l'affichage du centre
		int wd = centre.getWidth();
		int hgt = centre.getHeight();
		btnBateau.setBounds(wd /3, hgt*40/100, wd/3 , hgt*5/100);
		btnAttaque.setBounds(wd /3, hgt*60/100, wd/3 , hgt*5/100);
		centre.add(btnBateau);
		btnBateau.requestFocus();
		//haut à droite
		lblCentre1.setBounds(wd*51/100, hgt*15/100, wd*48/100, hgt*10/100);
		lblCentre1.setText(j1.nomJoueur);
		lblCentre1.setHorizontalAlignment(JLabel.CENTER);
		lblCentre1.setFont(new Font("", Font.BOLD, 30));
		centre.add(lblCentre1);
		//haut à gauche
		lblCentre2.setBounds(wd*1/100, hgt*15/100, wd*48/100, hgt*10/100);
		lblCentre2.setText(j2.nomJoueur);
		lblCentre2.setHorizontalAlignment(JLabel.CENTER);
		lblCentre2.setFont(new Font("", Font.BOLD, 30));
		centre.add(lblCentre2);
		//en bas à droite
		lblCentre3.setBounds(wd*51/100, hgt*25/100, wd*48/100, hgt*10/100);
		lblCentre3.setHorizontalAlignment(SwingConstants.CENTER);
		lblCentre3.setFont(new Font("", Font.PLAIN, 20));
		centre.add(lblCentre3);
		//en bas à gauche
		lblCentre4.setBounds(wd*1/100, hgt*25/100, wd*48/100, hgt*10/100);
		lblCentre4.setHorizontalAlignment(SwingConstants.CENTER);
		lblCentre4.setFont(new Font("", Font.PLAIN, 20));		
		centre.add(lblCentre4);
		//au milieu
		lblCentre5.setBounds(wd/4, hgt*50/100, wd/2 , hgt*10/100);
		lblCentre5.setHorizontalAlignment(JLabel.CENTER);
		lblCentre5.setFont(new Font("", Font.BOLD, 20));
		centre.add(lblCentre5);	
		//liste
		for(String nom : listNomBat){
			listModel.addElement(nom);
		}
		liste = new JList(listModel);
		liste.setBounds(wd*1/4, hgt*70/100, wd*1/2, hgt*15/100);
		centre.add(liste);
		
		
		//les ActionListener des boutons
		//btnAttaque
		btnAttaque.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
				int intTemp;
				if(posAttChoisi){
					if(jou==1){//si c'est le premier joueur
						SourisCarteG = false; //arrêter les actions de la souris sur la carte à gauche
						System.out.println("attaque numero " + nbAttaque);
						System.out.println("attaque faite a Y: " + coordY+ " X: " + coordX);
						System.out.println("tableau d'attaques de "+j1.nomJoueur);
						attaqueJou(coordY, coordX, 1);
						j1.imprim(j1.tabAtt);
						intTemp = j2.nomBatCoul();
						System.out.println("nombre de bateaux coules : "+intTemp);
						System.out.println();
						System.out.println();
						if(intTemp ==5){ // si tous les bateaux sont coulés, il termine le jeu
							jeuTermine(j1);
							return;
						}
						majCarteG(j1.tabAtt); //maj la carte d'attaque à gauche pour afficher la nouvelle attaque
						Timer timer = new Timer(1000, new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent evt){
								jou =2; //changement du joueur
								if(ordi){ //si le deuxième joueur est l'ordinatuer
									ordiAttaque();
								}else{
									//effacer les deux cartes
									lblCentre5.setText("");
									lblCentre3.setText("");									
									lblCentre1.setForeground(Color.BLACK);
									mapClear(grille1);
									mapClear(grille2);
									//message qui s'affiche pour changer le joueur
									JOptionPane.showMessageDialog(mainFrame,"Changement de joueur");
									//remettre les cartes du l'autre joueur
									majCarteD(j2.tabBat);
									majCarteG(j2.tabAtt);
									lblCentre2.setForeground(Color.GREEN);
									lblCentre4.setText("Attaquez !");
									SourisCarteG = true; //permettre les actions de la souris sur la carte d'attaque à gauche
								}
							}
						});
						timer.setRepeats(false);
						timer.start();
					}else{
						SourisCarteG = false;
						System.out.println("attaque numero " + nbAttaque);
						System.out.println("attaque faite a Y: " + coordY+ " X: " + coordX);
						attaqueJou(coordY, coordX, 2);
						System.out.println("tableau d'attaques de "+j2.nomJoueur);
						j2.imprim(j2.tabAtt);
						intTemp = j1.nomBatCoul();
						System.out.println("nombre de bateaux coules : "+intTemp);
						System.out.println();
						System.out.println();
						if(intTemp ==5){
							jeuTermine(j2);
							return;
						}
						majCarteG(j2.tabAtt);
						Timer timer = new Timer(1000, new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent evt){
								lblCentre5.setText("");
								lblCentre4.setText("");
								lblCentre2.setForeground(Color.BLACK);
								mapClear(grille1);
								mapClear(grille2);
								JOptionPane.showMessageDialog(mainFrame,"Changement de joueur");
								majCarteD(j1.tabBat);
								majCarteG(j1.tabAtt);
								jou=1;
								nbAttaque++; // augmenter le nombre d'attaques faites 
								SourisCarteG = true;
								lblCentre1.setForeground(Color.GREEN);								
								lblCentre3.setText("Attaquez !");
							}
						});
						timer.setRepeats(false);
						timer.start();
					}
					posAttChoisi = false;
				}
			}
		});
		
		//btnBateau
		btnBateau.addActionListener(new ActionListener(){
			// préparer les grilles pour mettre (ou remettre) les bateaux et effacer les bateaux déjà mis (s'il y en a)
			@Override
			public void actionPerformed(ActionEvent evt){
				listModel.removeAllElements();
				for(String nom : listNomBat){
					listModel.addElement(nom);
				}
				numBat=0;
				mapClear(grille1);
				mapIntClear(grille1Int);
				SourisCarteD= true; //permettre les actions de la souris sur la carte à droite
				btnBateau.setText("recommencer");
				if(jou == 1){
					j1.effacerTabBat();
					System.out.println("tableau de bateaux");
					j1.imprim(j1.tabBat);
					placerBateau(j1);
				}else{
					j2.effacerTabBat();
					System.out.println("tableau de bateaux");
					j2.imprim(j2.tabBat);
					placerBateau(j2);
				}
			}
		});
		
		//btnMusique
		btnMusique.addActionListener(new ActionListener(){
			//couper la musique ou la remettre si elle est coupée
			@Override
			public void actionPerformed(ActionEvent evt){
				musiqueBataille.changeSon();
				if(musiqueBataille.estSon){
					btnMusique.setIcon(imgMusique);
				}else{
					btnMusique.setIcon(imgMusiqueMute);
				}
			}
		});
		
		//btnSon
		btnSon.addActionListener(new ActionListener(){
			//couper les effets audio ou les remettre
			@Override
			public void actionPerformed(ActionEvent evt){
				if(effetAudio){
					btnSon.setIcon(imgSonMute);
					effetAudio = false;
				}else{
					btnSon.setIcon(imgSon);
					effetAudio = true;
				}
			}
		});
		
		//recommencer dans le menu
		miRecommencer.addActionListener(new ActionListener(){
			//créer un nouveau jeu avec les mêmes paramètres
			@Override
			public void actionPerformed(ActionEvent evt){
				mainFrame.dispose();
				musiqueBataille.arreteSon();
				new GameBoard(name1, name2, mode);
			}
		});
		
		//nouveau jeu dans le menu
		miNouveau.addActionListener(new ActionListener(){
			//fermer le jeu et retourner au menu principal
			@Override
			public void actionPerformed(ActionEvent evt){
				mainFrame.dispose();
				musiqueBataille.arreteSon();
				new Main();
			}
		});
			
		//l'affichage de comment jouer
		miAide.addActionListener(new ActionListener(){
			//fermer le jeu et retourner au menu principal
			@Override
			public void actionPerformed(ActionEvent evt){
				JFrame aide = new JFrame();
				aide.setLayout(new FlowLayout());
				aide.setTitle("Aide"); 
				aide.setSize(800,500);
				JLabel lblAide = new JLabel();
				lblAide.setText("<html><body style ='text-align:center'> pour placer un bateau faites un clic gauche  sur la grille a droite<br> "
								+ " en verifiant que la couleur du bateau est vert avant de le placer <br><br>"
								+ "changez l'orientation du bateau avec un clic droit <br><br>"
								+ " pour attaquer : choisissez une case sur la grille a gauche et appuyez sur le button <br> "
								+ " vous pouvez attaquer a l'aide de la barre d'espace sur le clavier " );
				lblAide.setFont(new Font("",Font.PLAIN,15));
				aide.add(lblAide);
				aide.setLocationRelativeTo(null);
				aide.setVisible(true);
			}
		});
	}
	
	//les actions à éxecuter quand le souris interagit avec la grille à droite
	public class mvtSourisCarteD implements MouseListener{
		//le souris se met sur une case
		@Override 
		public void mouseEntered(MouseEvent evt){
			if(SourisCarteD){
				JLabel carreau = (JLabel)evt.getSource(); 
				coor = trouverIndice(grille1, carreau); 	// chercher les coordonnées de la grille
				Color color = Color.GREEN;
				if(batDir == 1){ // batDir = 1 -> horizontal , 2 -> vertical
					for(int i = coor[0]; i<coor[0]+batLon; i++){
						if(i>=grille1.length){	 // vérifier qu'il ne sort pas de la carte
							lblCentre5.setText("<html> pas d'espace ");
							color = Color.RED;
							break;
						}else if(grille1Int[i][coor[1]] == 1){	// vérifier qu'il n'y a pas d'autres bateaux
							lblCentre5.setText("<html> il y a un bateau ");						
							color = Color.RED;
							break;
						}
					}
					for(int i = coor[0]; i<coor[0]+batLon; i++){
						if(i<grille1.length){ 
							if(grille1Int[i][coor[1]] == 0){
								grille1[i][coor[1]].setBackground(color); //changer la couleur selon les conditions
							}
						}
					}
				}else{
					for(int i = coor[1]; i<coor[1]+batLon; i++){
						if(i>=grille1.length ){
							lblCentre5.setText("<html> pas d'espace ");
							color = Color.RED;
							break;
						}else if(grille1Int[coor[0]][i] == 1){
							lblCentre5.setText("<html> il y a un bateau ");
							color = Color.RED;
							break;
						}
					}
					for(int i = coor[1]; i<coor[1]+batLon; i++){
						if(i<grille1.length){
							if(grille1Int[coor[0]][i] == 0){
								grille1[coor[0]][i].setBackground(color);
							}
						}
					}
				}
			}
		}
		//le souris sort de la case
		@Override 
		public void mouseExited(MouseEvent evt){
			//remettre les couleurs initiales avant que la souris entre dans cette case
			if(SourisCarteD){
				lblCentre5.setText("");
				JLabel carreau = (JLabel)evt.getSource();
				coor = trouverIndice(grille1, carreau);
				if(batDir == 1){ 
					for(int i = coor[0]; i<coor[0]+batLon; i++){
						if(i<grille1.length && grille1Int[i][coor[1]] == 0){
							grille1[i][coor[1]].setBackground(Color.CYAN);
						}
					}
				}else{
					for(int i = coor[1]; i<coor[1]+batLon; i++){
						if(i<grille1.length && grille1Int[coor[0]][i] == 0){
							grille1[coor[0]][i].setBackground(Color.CYAN);
						}
					}
				}
			}
		}
		//un click sur une case
		@Override 
		public void mouseClicked(MouseEvent evt) {
			if(SourisCarteD){
				JLabel carreau = (JLabel)evt.getSource();
				coor = trouverIndice(grille1, carreau);
				if(evt.getButton() == MouseEvent.BUTTON1){ //BUTTON1 c'est le clic gauche
					if(batDir == 1){
						for(int i = coor[0]; i<coor[0]+batLon; i++){
							if(i>=grille1.length){   
								return;
							}
							if(grille1Int[i][coor[1]] == 1){  
								return;
							}
						}
						for(int i = coor[0]; i<coor[0]+batLon; i++){
							if(i<grille1.length && grille1Int[i][coor[1]] == 0){
								grille1[i][coor[1]].setBackground(Color.LIGHT_GRAY);		//changement de couleur pour marquer la sélection
								grille1Int[i][coor[1]] = 1;
							}
						}
					}else{
						for(int i = coor[1]; i<coor[1]+batLon; i++){
							if(i>=grille1.length){
								return;
							}
							if(grille1Int[coor[0]][i] == 1){
								return;
							}
						}
						for(int i = coor[1]; i<coor[1]+batLon; i++){
							if(i<grille1.length && grille1Int[coor[0]][i] == 0){
								grille1[coor[0]][i].setBackground(Color.LIGHT_GRAY);
								grille1Int[coor[0]][i] = 1;
							}
						}
					}
					//si toutes les conditions sont vérifiées, il place le bateau
					if(jou==1){
						placerBateau(j1);
					}else{
						placerBateau(j2);
					}
				}else if(evt.getButton() == MouseEvent.BUTTON3){ // BUTTON3 -> clic droit, il change l'orientation du bateau
					//remettre les couleurs initiales 
					if(batDir == 1){
						for(int i = coor[0]; i<coor[0]+batLon; i++){
							if(i<grille1.length && grille1Int[i][coor[1]] == 0){
								grille1[i][coor[1]].setBackground(Color.CYAN);
							}
						}
					}else{
						for(int i = coor[1]; i<coor[1]+batLon; i++){
							if(i<grille1.length && grille1Int[coor[0]][i] == 0){
								grille1[coor[0]][i].setBackground(Color.CYAN);
							}
						}
					}
					//changer l'orientation
					if(batDir == 1){
						batDir = 2;
					}else{
						batDir = 1;
					}
					//colorier les nouvelles cases sélectionnées
					Color color = Color.RED;
					if(batDir == 1){
						if(grille1.length-coor[0]-batLon >=0){
							color = Color.GREEN;
						}
						for(int i = coor[0]; i<coor[0]+batLon; i++){
							if(i<grille1.length && grille1Int[i][coor[1]] == 0){
								grille1[i][coor[1]].setBackground(color);
							}
						}
					}else{
						if(grille1.length-coor[1]-batLon >=0){
							color = Color.GREEN;
						}
						for(int i = coor[1]; i<coor[1]+batLon; i++){
							if(i<grille1.length && grille1Int[coor[0]][i] == 0){
								grille1[coor[0]][i].setBackground(color);
								
							}
						}
					}
				}
			}
		}
		@Override public void mousePressed(MouseEvent evt) { }
		@Override public void mouseReleased(MouseEvent evt) { }
		
	}
	
	//les actions à éxecuter quand le souris interagit avec la grille à gauche
	public class  mvtSourisCarteG implements MouseListener{
		@Override 
		public void mouseEntered(MouseEvent evt){
			if(SourisCarteG){	
				JLabel carreau = (JLabel)evt.getSource();
				coor = trouverIndice(grille2, carreau);
				if(grille2Int[coor[0]][coor[1]] == 0){
					grille2[coor[0]][coor[1]].setBackground(Color.LIGHT_GRAY); //changement de couleur pour marquer la case
				}
			}
		}
		
		@Override 
		public void mouseExited(MouseEvent evt){
			if(SourisCarteG){	
					JLabel carreau = (JLabel)evt.getSource();
					int[] coor = trouverIndice(grille2, carreau);
				if(grille2Int[coor[0]][coor[1]] == 0){
					grille2[coor[0]][coor[1]].setBackground(Color.CYAN); // remettre les coulers initiales en sortant de la case
				}
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent evt) {
			if(SourisCarteG){
				JLabel carreau = (JLabel)evt.getSource();
				coor = trouverIndice(grille2, carreau);
				if(grille2Int[coordY][coordX]==3){ //si il y avait une case déjà sélectionnée, il remet sa couleur initiale
						grille2[coordY][coordX].setBackground(Color.CYAN);
						grille2Int[coordY][coordX] = 0;
					}
				if(grille2Int[coor[0]][coor[1]] == 0){
					grille2[coor[0]][coor[1]].setBackground(Color.GREEN);
					grille2Int[coor[0]][coor[1]] = 3;
					coordX = coor[1]; 	//définir les nouvelles coordonnées sélectionnés
					coordY = coor[0];
					posAttChoisi = true;
					btnAttaque.requestFocus(); //permet d'appuyer sur la barre d'espace pour attaquer
				}
			}
		}
		
		@Override public void mousePressed(MouseEvent evt) { }
		@Override public void mouseReleased(MouseEvent evt) { }		
	}
	
	public int[] trouverIndice(JLabel[][] tab, JLabel lbl){
		//Trouver les coordonnées d'une case dans la grille
		//et retourner les coordonnés sous forme de tableau avec 2 valeurs
		for(int i = 0; i<tab.length; i++){
			for(int j=0; j<tab[0].length; j++){
				if(lbl == tab[i][j]){
					int[] newTab = {i, j};
					return newTab;
				}
			}
		}
		return null ;
	}
	
	public void majCarteD(int[][] tab){
		//mettre à jour la carte à droite
		//selon un tableau int 2D
		for(int i=0; i<tab.length;i++){
			for(int j=0; j<tab[0].length; j++){
				switch(tab[i][j]){
					case 0 :
						grille1[i][j].setBackground(Color.CYAN);
						break;
					case 1 :
						grille1[i][j].setBackground(Color.GRAY);
						break;
					case 2 : 
						grille1[i][j].setBackground(Color.ORANGE);
						break;
					case 4 :
						grille1[i][j].setBackground(Color.RED);
						break;
					case 5 :
						grille1[i][j].setBackground(Color.BLACK);
						break;	
					default:
						break;
				}
			}
		}
	}
	
	public void majCarteG(int[][] tab){
		//mettre à jour la carte à gauche
		//selon un tableau int 2D
		grille2Int = tab;
		for(int i=0; i<tab.length;i++){
			for(int j=0; j<tab[0].length; j++){
				switch(tab[i][j]){
					case 0 :
						grille2[i][j].setBackground(Color.CYAN);
						break;
					case 1 :
						grille2[i][j].setBackground(Color.BLACK);
						break;
					case 2 : 
						grille2[i][j].setBackground(Color.ORANGE);
						break;
					case 3 :
						grille2[i][j].setBackground(Color.GREEN);
						break;
					case 4 :
						grille2[i][j].setBackground(Color.RED);
					default:
						break;
				}
			}
		}
	}
 
	public void mapClear(JLabel[][] map){
		// effacer la carte
		for(int i=0; i<map.length;i++){
			for(int j=0; j<map[0].length; j++){
				map[i][j].setBackground(Color.CYAN);
			}
		}
	}
	
	public void mapIntClear(int[][] mapInt){
		// effacer le tableau 2D qui représente une carte
		for(int i=0; i<mapInt.length;i++){
			for(int j=0; j<mapInt[0].length; j++){
				mapInt[i][j] = 0;
			}
		}
	}
	
	public void placerBateau(Joueur joueur){
		// pour aligner les phrases dans JLabel au centre et permette d'écrire sur plusieurs lignes
		String alignCenter = "<html><body style='text-align: center'>"; 
		JLabel lbl1 = lblCentre3;
		JLabel lbl2 = lblCentre4;
		if(jou==2){
			lbl1 = lblCentre4;
			lbl2 = lblCentre3;
		}
		//numBat est un compteur
		switch(numBat){
			case 0: //le joueur va commencer à placer le premier bateau
				lbl1.setText(alignCenter+"Placez le premier bateau, c'est un porte-avion ");
				batLon = 5; 
				break;
			case 1: //le joueur a mis son premier bateau
				Bateau bat1 = new Bateau(1, batDir, coor[1], coor[0]); 					//créer un nouveau bateau
				joueur.ajouteBateau(bat1); 												//mettre le bateau dans la liste des bateaux du joueur
				listModel.remove(0);  													//supprimer le bateau de la liste affichée sur l'interface
				lbl1.setText(alignCenter+"Placez le deuxieme bateau, c'est un cargo ");	
				batLon = 4;
				break;
			case 2:
				Bateau bat2 = new Bateau(2, batDir, coor[1], coor[0]);
				joueur.ajouteBateau(bat2);
				listModel.remove(0);
				lbl1.setText(alignCenter+"Placez le troisime bateau, c'est un voilier");
				batLon = 3;
				break;
			case 3:
				Bateau bat3 = new Bateau(3, batDir, coor[1], coor[0]);
				joueur.ajouteBateau(bat3);
				listModel.remove(0);
				lbl1.setText(alignCenter+"Placez  le quatrieme bateau, c'est un voilier ");
				batLon = 3;
				break;
			case 4:
				Bateau bat4 = new Bateau(3, batDir, coor[1], coor[0]);
				joueur.ajouteBateau(bat4);
				listModel.remove(0);
				lbl1.setText(alignCenter+"Placez le cinquieme bateau, c'est un barque ");
				batLon = 2;
				break;
			case 5:
				Bateau bat5 = new Bateau(4, batDir, coor[1], coor[0]);
				joueur.ajouteBateau(bat5);
				listModel.remove(0);
				lbl1.setText("");
				if(jou==2){ // si 2eme joueur a mis tous les bateaux
					centre.remove(liste);
					mainFrame.invalidate();
					mainFrame.validate();
					mainFrame.repaint();
					lblCentre3.setText("");
					lblCentre4.setText("");
					SourisCarteD = false;
					jou = 1;
					if(!ordi){
						mapClear(grille1);
						mapClear(grille2);
						JOptionPane.showMessageDialog(mainFrame,"Changement de joueur");
						lblCentre1.setForeground(Color.GREEN);
						lblCentre3.setText("Attaquez !");
						majCarteD(j1.tabBat);
						centre.remove(btnBateau);
						centre.add(btnAttaque);
						mainFrame.invalidate();
						mainFrame.validate();
						mainFrame.repaint();
						SourisCarteG = true;
					}
					break;
				}
				jou =2;		//changement du joueur
				numBat = 0; //et réinitialisation du compteur
				mapClear(grille1);
				mapIntClear(grille1Int); //effacer les grilles
				batLon = 5;
				lbl2.setText(alignCenter+"Placez le premier bateau, c'est un porte-avion ");
				for(String nom : listNomBat){
					listModel.addElement(nom); // remettre la liste des bateaux sur l'interface
				}
				if(ordi){ //si le 2eme joueur est le bateau
					numBat = 1;
					ordiPlacerBateau(); 
					lblCentre4.setText(alignCenter+"L'ordinateur est en train de mettre ses bateaux");
					//laisser un délai du temps pour rendre le jeu plus réaliste
					Timer timer = new Timer(1000, new ActionListener(){ 
						//le code à éxecuter après le délai
						@Override 
						public void actionPerformed(ActionEvent evt){
							lblCentre1.setForeground(Color.GREEN);
							lblCentre3.setText("Attaquez !");
							majCarteD(j1.tabBat); // mettre les 
							centre.remove(btnBateau);
							centre.add(btnAttaque);
							mainFrame.invalidate();
							mainFrame.validate();
							mainFrame.repaint();
							lblCentre4.setText("");
							SourisCarteG = true;
						}
					});
					timer.setRepeats(false);
					timer.start();
				}
				System.out.println("changement de joueur");
				break;
			default :
				break;
		}
		numBat++;
		System.out.println("tableau de bateaux");
		joueur.imprim(joueur.tabBat); 
	}
	
	public void attaqueJou(int y,int x, int jInt){
		Joueur jTemp1;
		Joueur jTemp2;
		if(jInt == 1){
			jTemp1 = j1;
			jTemp2 = j2;
		}else{
			jTemp1 = j2;
			jTemp2 = j1;
		}
		System.out.println("attaque de "+jTemp1.nomJoueur);	
		switch(jTemp2.tabBat[y][x]){ //regarder la case choisie chez le joueur attaqué
			case 0: // pas de  bateaux dans cette case
				lblCentre5.setText("attaque ratee");
				lblCentre5.setForeground(Color.RED);
				jTemp1.tabAtt[y][x] =  1; // marquer dans le tableau d'attaque de l'attaquant qu'il a une attaque ratée dans ces coordonnées
				jTemp2.tabBat[y][x] = 5;  // marquer dans le tableau de bateaux du joueur attaqué qu'il y a une attaque ratée
				if(effetAudio){
					tombeDansLeau.mettreSon();
				}
				if(ordi && jou ==2){
					carteOrdi[y][x] = 0; //si c'est l'ordi qui a joué, mettre la valeur 0 dans sa carte d'attaque
				}
				break;
			case 1: // il y a un bateau
				lblCentre5.setText("attaque reussiee");
				lblCentre5.setForeground(Color.GREEN);
				// marquer dans les deux tableaux que l'attaque est réussie
				jTemp1.tabAtt[y][x] = 2; 
				jTemp2.tabBat[y][x] = 2;
				Bateau batTemp = jTemp2.attaqueBat(y,x); //la méthode attaqueBat dans Joueur retourne le bateau attaqué
				if(effetAudio){
					explosion.mettreSon();
				}
				if(batTemp.estCoule()){ //vérifier si le bateau attaqué est coulé 
					for(int n=0; n<batTemp.posBat.length; n++){
						//marquer dans les deux tableaux que le bateau est coulé
						jTemp2.tabBat[batTemp.posBat[n][0]][batTemp.posBat[n][1]] = 4; 
						jTemp1.tabAtt[batTemp.posBat[n][0]][batTemp.posBat[n][1]] = 4; 
					}
					//mettre à jour la carte d'ordi 
					if(difficile){
						majCarteOrdi();
					}
				}else if(ordi && jou ==2 && difficile){ // si le bateau n'est pas coulé
					for (int i = 0; i < carteOrdi.length; ++i) {
						for (int j = 0; j < carteOrdi[0].length; ++j) {
							//l'augmentation de la valuer des cases non attaquées
							//autour des attaques réussies
							if(j2.tabAtt[i][j]==0){ 
								if( (Math.abs(y-i) < 3 && x==j) || (Math.abs(x-j) < 3 && y==i) ){
									if(Math.abs(y-i) == 1 || Math.abs(x-j) == 1){
										carteOrdi[i][j] = carteOrdi[i][j] +2;
									}else{
										carteOrdi[i][j]++;
									}
								} 
							}
						}
					}	
					carteOrdi[y][x] = 0; //remettre à 0 la case attaquée
				}
				break;
			default:
				break;		
		}
	} 
	
	public void jeuTermine(Joueur j){
		mainFrame.dispose();		//fermer la fenêtre du jeu
		musiqueBataille.arreteSon(); //arrêter la musique
		JFrame frameFini = new JFrame(); //créer une nouvelle fenêtre avec le nom du gagnant
		frameFini.setSize(300,300);
		frameFini.setLayout(new BorderLayout());
		JPanel panelFini = new JPanel(new FlowLayout());
		JLabel lblfini = new JLabel("BRAVO " +j.nomJoueur+", vous avez gagne");
		if(j.nomJoueur == "ordi"){
			lblfini = new JLabel("GAME OVER, vous avez perdu !!!");			
		}
		panelFini.add(lblfini);
		frameFini.add(panelFini, BorderLayout.CENTER);
		frameFini.setLocationRelativeTo(null);
		frameFini.setVisible(true);
	}
	
	//les méthodes liées à l'ordinateur
	public void ordiPlacerBateau(){
		for(int n=1; n<=5; n++){
			System.out.println("bateau "+ n + " mis pour l'ordi");
			loop1:
			while(true){
				//génération aléatoire des coordonnées et de l'orientation du bateau
				batDir = (int) ( Math.random() * 2 + 1 );
				coor[0] = (int) ( Math.random() * largeur );
				coor[1] = (int) ( Math.random() * longueur );
				if(batDir == 1){
					for(int i = coor[0]; i<coor[0]+batLon; i++){
						if(i>=grille1.length){
							continue loop1; //si le bateau dépasse la carte, choisir nouvelles coordonnées
						}
						if(j2.tabBat[i][coor[1]] == 1){
							continue loop1; //si il y a un autre bateau, choisir nouvelles coordonnées
						}
					}
				}else{
					for(int i = coor[1]; i<coor[1]+batLon; i++){
						if(i>=grille1.length){
							continue loop1;
						}
						if(j2.tabBat[coor[0]][i] == 1){
							continue loop1;
						}
					}
				}
				placerBateau(j2); //placer le bateau si tout est bon
				break loop1; //sortir de la boucle
			}
		}	
	}
	
	public void ordiAttaque(){
		lblCentre5.setText("");
		lblCentre1.setForeground(Color.BLACK);
		lblCentre3.setText("");
		lblCentre2.setForeground(Color.GREEN);
		lblCentre4.setText("<html><body style='text-align:center'> L'ordinateur est en train d'attaquer");
		//laisser du temps pour rendre le jeu réaliste
		Timer timer = new Timer(1000, new ActionListener(){
			@Override 
			public void actionPerformed(ActionEvent evt){
				int intTemp;
				int grandeVal = 0;
				/* si le mode du jeu est difficile. alors l'ordi
				 * cherche pour la valeur la plus grande du tableau carteOrdi (>0)
				 * et attaque à cet endroit
				 * par contre si le mode est facile, il attaque aléatoirement
				 */ 
				if(difficile){
					for(int i=0; i<carteOrdi.length; i++){
						for(int j=0; j<carteOrdi[0].length; j++){
							if(carteOrdi[i][j]>grandeVal){
								grandeVal = carteOrdi[i][j];
								coordX = j;
								coordY = i;
							}
						}
					}
				}
				//si toutes les valeurs de carteOrdi sont 0, il choisit aléatoirement
				if(grandeVal == 0){
					while(true){
						coor[0] = (int) ( Math.random() * largeur );
						coor[1] = (int) ( Math.random() * longueur );
						if(j2.tabAtt[coor[0]][coor[1]] == 0){ //il vérifie qu'il n'a pas attaqué à ces coordonnées
							coordX = coor[1];
							coordY = coor[0];
							break;
						}
					}
				}
				System.out.println("attaque numero " + nbAttaque + " pour " + j2.nomJoueur);
				System.out.println("attaque faite a Y: " + coordY+ " X: " + coordX);
				System.out.println("tableau d'attaques de "+j2.nomJoueur);
				attaqueJou(coordY, coordX, 2);
				j2.imprim(j2.tabAtt);
				intTemp = j1.nomBatCoul(); 
				System.out.println("nombre de bateaux coules : "+intTemp);
				System.out.println();
				System.out.println();
				majCarteD(j1.tabBat);
				Timer timer2 = new Timer(1000, new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent evt2){		
						if(intTemp ==5){ // si tous les bateaux sont coulés, il termine le jeu
							jeuTermine(j2);
							return;
						}
						jou=1; //changement du joueur
						nbAttaque++;
						//changement de l'affichage sur le terminal
						lblCentre5.setText("");
						lblCentre2.setForeground(Color.BLACK);
						lblCentre4.setText("");
						lblCentre1.setForeground(Color.GREEN);
						lblCentre3.setText("Attaquez");
						SourisCarteG = true;
					}
				});
				timer2.setRepeats(false);
				timer2.start();
			}
		});
		timer.setRepeats(false);
		timer.start();
	}

	public void majCarteOrdi(){
		/* mettre à jour la carte d'attaque d'ordi
		 * pour chaque case avec attaque réussie
		 * il ajoute la valeur des cases non attaquées autour de cette case
		 * 2 pour les cases les plus proches, 1 pour les suivantes
		 * (il ne change pas les cases sur les diagonales)
		 */ 
		mapIntClear(carteOrdi);
		for(int y = 0; y<j2.tabAtt.length; y++){
			for(int x = 0; x<j2.tabAtt[0].length; x++){
				if(j2.tabAtt[y][x] == 2){ 
					for (int i = 0; i < carteOrdi.length; ++i) {
						for (int j = 0; j < carteOrdi[0].length; ++j) {
							if(j2.tabAtt[i][j]==0){
								if( (Math.abs(y-i) < 3 && x==j) || (Math.abs(x-j) < 3 && y==i) ){
									if(Math.abs(y-i) == 1 || Math.abs(x-j) == 1){
										carteOrdi[i][j] = carteOrdi[i][j] +2;
									}else{
										carteOrdi[i][j]++;
									}
								} 
							}
						}
					}
				}
			}
		}	
		
	}
}
