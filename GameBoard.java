import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.border.Border;

public class GameBoard extends JFrame{
	
	Joueur j1 = new Joueur(10, 10, "Joueur 1");
	Joueur j2 = new Joueur(10, 10, "Joueur 2");
	Joueur jTemp1;
	Joueur jTemp2;
	int tailleEcranX, tailleEcranY;
	int intTemp;
	int nomAttaque =1;
	int jou = 1;
	int batLon = 3;
	int batDir = 2;
	int longueur=10 ;
	int largeur=10;
	int coordX = 0;
	int coordY = 0;
	boolean ordi ;
	JFrame mainFrame = new JFrame();
	JLabel lblTop = new JLabel("Bataille navire");
	JLabel lbl1 = new JLabel();
	JLabel lbl2 = new JLabel("Appuyer sur 'bateau' pour commencer");
	JPanel top = new JPanel();
	GridBagConstraints Cons = new GridBagConstraints();
	JPanel centre = new JPanel(new BorderLayout());
	JPanel map1 = new JPanel(new GridLayout(longueur,largeur,5,5));
	JLabel[][] area1 = new JLabel[longueur][largeur];
	int[][] area1Int = new int[longueur][largeur];
	int[][] area2Int = new int[longueur][largeur];
	int[][] carteOrdi = new int[longueur][largeur];
	int[] coor = new int[2];
	JPanel map2 = new JPanel(new GridLayout(longueur,largeur,5,5));
	JLabel[][] area2 = new JLabel[longueur][largeur];
	JPanel buttons = new JPanel(new FlowLayout());
	JButton btnBateau = new JButton("bateau");
	JButton attaque = new JButton("attaque");
	ImageIcon musiqueOn = new ImageIcon(new ImageIcon("musicOn.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
	ImageIcon mute = new ImageIcon(new ImageIcon("mute.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
	JButton buttonMusique = new JButton(musiqueOn);
	boolean allowMouse1 = false;
	boolean allowMouse2 = false;
	boolean posAttChoisi = false; 
	int nbBat = 0;
	Audio sonBataille = new Audio("BattleshipSong.wav");
	Audio explosion = new Audio("Explosion.wav");
	Audio tombeDansLeau = new Audio("tombeDansLeau.wav");
		
		
	public GameBoard(String name1, String name2, boolean mode){
		//set frame
		sonBataille.changeSon();
		j1.nomJoueur = name1;
		j2.nomJoueur = name2;
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setTitle("BATAILLE NAVALE");
		Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
		tailleEcranX = tailleEcran.width-50;
		tailleEcranY = tailleEcran.height-50;
		mainFrame.setSize(tailleEcranX, tailleEcranY);
		mainFrame.setResizable(false);
		ordi = mode;
		
		//maps
		mainFrame.add(map1, BorderLayout.EAST);
		mainFrame.add(map2, BorderLayout.WEST);
		int tailleGrilleX = tailleEcranX /(3*largeur);
		int tailleGrilleY = (tailleEcranY- 40) / longueur ;
		//map1
		for(int i=0; i<area1.length; i++){
			for(int j =0; j<area1[0].length; j++){
				area1[i][j] = new JLabel();
				area1[i][j].addMouseListener(new mouseMvtMap1());
				area1[i][j].setOpaque(true);
				area1[i][j].setBackground(Color.CYAN);
				map1.add(area1[i][j]);
				area1[i][j].setMaximumSize(new Dimension(tailleGrilleX, tailleGrilleY));
				area1[i][j].setMinimumSize(new Dimension(tailleGrilleX, tailleGrilleY));
				area1[i][j].setPreferredSize(new Dimension(tailleGrilleX, tailleGrilleY));
			}
		}
		//map2
		for(int i=0; i<area2.length; i++){
			for(int j =0; j<area2[0].length; j++){
				
				area2[i][j] = new JLabel();
				area2[i][j].setSize(30,30);
				area2[i][j].addMouseListener(new mouseMvtMap2());
				map2.add(area2[i][j]);
				area2[i][j].setOpaque(true);
				area2[i][j].setBackground(Color.CYAN);
				area2[i][j].setMaximumSize(new Dimension(tailleGrilleX, tailleGrilleY));
				area2[i][j].setMinimumSize(new Dimension(tailleGrilleX, tailleGrilleY));
				area2[i][j].setPreferredSize(new Dimension(tailleGrilleX, tailleGrilleY));
			}
		}
		map1.setBackground(Color.BLACK);
		map2.setBackground(Color.BLACK);
		

		
		//top
		mainFrame.add(top, BorderLayout.NORTH);
		top.setLayout(null);
		top.setPreferredSize(new Dimension(0,30));
		lblTop.setSize(lblTop.getPreferredSize());
		lblTop.setBounds( (tailleEcranX/2) - (lblTop.getWidth()/2) , 0, 200,30);
		top.add(lblTop);
		buttonMusique.setBounds((int) (tailleEcranX*0.8)-5 , 0, (int) (tailleEcranX*0.2),30);
		top.add(buttonMusique);
		
		//centre
		mainFrame.add(centre, BorderLayout.CENTER);
		centre.setLayout(new FlowLayout());
		centre.add(btnBateau);
		//centre.add(attaque);
		btnBateau.requestFocus();
		buttonMusique.setFocusable(false);
		
		
		//buttons
		//attaque
		attaque.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
				if(posAttChoisi){
					if(jou==1){
						allowMouse2 = false;
						System.out.println("attaque numero " + nomAttaque);
						System.out.println("attaque faite a Y: " + coordY+ " X: " + coordX);
						attaqueJou(coordY, coordX, 1);
						System.out.println("tableau d'attaques de "+j1.nomJoueur);
						j1.imprim(j1.tabEn);
						intTemp = j2.nomBatCoul();
						System.out.println("nombre de bateaux coules : "+intTemp);
						System.out.println();
						System.out.println();
						if(intTemp ==5){
							jeuTermine(j1);
						}
						refreshMap2(j1.tabEn);
						Timer timer = new Timer(2000, new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent evt){
								refreshMap1(j2.tabBat);
								refreshMap2(j2.tabEn);
								jou =2;
								if(ordi){
									ordiAttaque();
								}
								allowMouse2 = true;
							}
						});
						timer.setRepeats(false);
						timer.start();
					}else{
						allowMouse2 = false;
						System.out.println("attaque numero " + nomAttaque);
						System.out.println("attaque faite a Y: " + coordY+ " X: " + coordX);
						attaqueJou(coordY, coordX, 2);
						System.out.println("tableau d'attaques de "+j2.nomJoueur);
						j2.imprim(j2.tabEn);
						intTemp = j1.nomBatCoul();
						System.out.println("nombre de bateaux coules : "+intTemp);
						System.out.println();
						System.out.println();
						if(intTemp ==5){
							jeuTermine(j2);
						}
						refreshMap2(j2.tabEn);
						Timer timer = new Timer(2000, new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent evt){
								refreshMap1(j1.tabBat);
								refreshMap2(j1.tabEn);
								jou=1;
								nomAttaque++;
								allowMouse2 = true;
							}
						});
						timer.setRepeats(false);
						timer.start();
					}
					posAttChoisi = false;
				}
			}
		});
		
		//bateau
		btnBateau.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
				nbBat=0;
				mapClear(area1);
				mapIntClear(area1Int);
				allowMouse1= true;
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
		
		
		
		buttonMusique.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
				sonBataille.changeSon();
				if(sonBataille.estSon){
					buttonMusique.setIcon(musiqueOn);
				}else{
					buttonMusique.setIcon(mute);
				}
			}
		});
		
		//add , visible
		
		
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}

	public class mouseMvtMap1 implements MouseListener{
		//mouse entered
		@Override 
		public void mouseEntered(MouseEvent evt){
			if(allowMouse1){
				JLabel square = (JLabel)evt.getSource();
				coor = trouverIndice(area1, square);
				Color color = Color.GREEN;
				//area1[coor[0]][coor[1]].setText(String.valueOf(area1Int[coor[0]][coor[1]]));
				//lbl1.setText( coor[0] + " et " + coor[1]);
				if(batDir == 1){
					for(int i = coor[0]; i<coor[0]+batLon; i++){
						if(i>=area1.length){
							//lbl1.setText(" pas d'espace ");
							color = Color.RED;
							break;
						}else if(area1Int[i][coor[1]] == 1){
							//lbl1.setText(" il y a un bateau ");						
							color = Color.RED;
							break;
						}
					}
					for(int i = coor[0]; i<coor[0]+batLon; i++){
						if(i<area1.length){ 
							if(area1Int[i][coor[1]] == 0){
								area1[i][coor[1]].setBackground(color);
							}
						}
					}
				}else{
					for(int i = coor[1]; i<coor[1]+batLon; i++){
						if(i>=area1.length ){
							//lbl1.setText(" pas d'espace ");
							color = Color.RED;
							break;
						}else if(area1Int[coor[0]][i] == 1){
							//lbl1.setText(" il y a un bateau ");
							color = Color.RED;
							break;
						}
					}
					for(int i = coor[1]; i<coor[1]+batLon; i++){
						if(i<area1.length){
							if(area1Int[coor[0]][i] == 0){
								area1[coor[0]][i].setBackground(color);
							}
						}
					}
				}
			}
		}
		//mouse exit
		@Override 
		public void mouseExited(MouseEvent evt){
			if(allowMouse1){
				JLabel square = (JLabel)evt.getSource();
				coor = trouverIndice(area1, square);
				if(batDir == 1){
					for(int i = coor[0]; i<coor[0]+batLon; i++){
						if(i<area1.length && area1Int[i][coor[1]] == 0){
							area1[i][coor[1]].setBackground(Color.CYAN);
						}
					}
				}else{
					for(int i = coor[1]; i<coor[1]+batLon; i++){
						if(i<area1.length && area1Int[coor[0]][i] == 0){
							area1[coor[0]][i].setBackground(Color.CYAN);
						}
					}
				}
				//lbl1.setText("");
			}
		}
		//mouse clicked
		@Override 
		public void mouseClicked(MouseEvent evt) {
			if(allowMouse1){
				JLabel square = (JLabel)evt.getSource();
				coor = trouverIndice(area1, square);
				if(evt.getButton() == MouseEvent.BUTTON1){
					if(batDir == 1){
						for(int i = coor[0]; i<coor[0]+batLon; i++){
							if(i>=area1.length){
								return;
							}
							if(area1Int[i][coor[1]] == 1){
								return;
							}
						}
						for(int i = coor[0]; i<coor[0]+batLon; i++){
							if(i<area1.length && area1Int[i][coor[1]] == 0){
								area1[i][coor[1]].setBackground(Color.LIGHT_GRAY);
								area1Int[i][coor[1]] = 1;
							}
						}
					}else{
						for(int i = coor[1]; i<coor[1]+batLon; i++){
							if(i>=area1.length){
								return;
							}
							if(area1Int[coor[0]][i] == 1){
								return;
							}
						}
						for(int i = coor[1]; i<coor[1]+batLon; i++){
							if(i<area1.length && area1Int[coor[0]][i] == 0){
								area1[coor[0]][i].setBackground(Color.LIGHT_GRAY);
								area1Int[coor[0]][i] = 1;
							}
						}
					}
					
					if(jou==1){
						placerBateau(j1);
					}else{
						placerBateau(j2);
					}
				}else if(evt.getButton() == MouseEvent.BUTTON3){
					if(batDir == 1){
						for(int i = coor[0]; i<coor[0]+batLon; i++){
							if(i<area1.length && area1Int[i][coor[1]] == 0){
								area1[i][coor[1]].setBackground(Color.CYAN);
							}
						}
					}else{
						for(int i = coor[1]; i<coor[1]+batLon; i++){
							if(i<area1.length && area1Int[coor[0]][i] == 0){
								area1[coor[0]][i].setBackground(Color.CYAN);
							}
						}
					}
					if(batDir == 1){
						batDir = 2;
					}else{
						batDir = 1;
					}
					Color color = Color.RED;
					if(batDir == 1){
						if(area1.length-coor[0]-batLon >=0){
							color = Color.GREEN;
						}
						for(int i = coor[0]; i<coor[0]+batLon; i++){
							if(i<area1.length && area1Int[i][coor[1]] == 0){
								area1[i][coor[1]].setBackground(color);
							}
						}
					}else{
						if(area1.length-coor[1]-batLon >=0){
							color = Color.GREEN;
						}
						for(int i = coor[1]; i<coor[1]+batLon; i++){
							if(i<area1.length && area1Int[coor[0]][i] == 0){
								area1[coor[0]][i].setBackground(color);
								
							}
						}
					}
				}
			}
		}
		@Override public void mousePressed(MouseEvent evt) { }
		@Override public void mouseReleased(MouseEvent evt) { }
		
	}
	
	public class mouseMvtMap2 implements MouseListener{
		@Override 
		public void mouseEntered(MouseEvent evt){
			if(allowMouse2){	
				JLabel square = (JLabel)evt.getSource();
				coor = trouverIndice(area2, square);
				if(area2Int[coor[0]][coor[1]] == 0){
					area2[coor[0]][coor[1]].setBackground(Color.LIGHT_GRAY);
				}
			}
		}
		
		@Override 
		public void mouseExited(MouseEvent evt){
			if(allowMouse2){	
					JLabel square = (JLabel)evt.getSource();
					int[] coor = trouverIndice(area2, square);
				if(area2Int[coor[0]][coor[1]] == 0){
					area2[coor[0]][coor[1]].setBackground(Color.CYAN);
				}
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent evt) {
			if(allowMouse2){
				JLabel square = (JLabel)evt.getSource();
				coor = trouverIndice(area2, square);
				if(area2Int[coordY][coordX]==3){
						area2[coordY][coordX].setBackground(Color.CYAN);
						area2Int[coordY][coordX] = 0;
					}
				if(area2Int[coor[0]][coor[1]] == 0){
					area2[coor[0]][coor[1]].setBackground(Color.GREEN);
					area2Int[coor[0]][coor[1]] = 3;
					coordX = coor[1];
					coordY = coor[0];
					posAttChoisi = true;
					attaque.requestFocus();
				}
			}
		}
		
		@Override public void mousePressed(MouseEvent evt) { }
		@Override public void mouseReleased(MouseEvent evt) { }		
	}
	
	public int[] trouverIndice(JLabel[][] tab, JLabel lbl){
		int[] newTab1 = {-1, -1};
		for(int i = 0; i<tab.length; i++){
			for(int j=0; j<tab[0].length; j++){
				if(lbl == tab[i][j]){
					int[] newTab = {i, j};
					return newTab;
				}
			}
		}
		return newTab1 ;
	}
	
	public void refreshMap1(int[][] tab){
		for(int i=0; i<tab.length;i++){
			for(int j=0; j<tab[0].length; j++){
				switch(tab[i][j]){
					case 0 :
						area1[i][j].setBackground(Color.CYAN);
						break;
					case 1 :
						area1[i][j].setBackground(Color.GRAY);
						break;
					case 2 : 
						area1[i][j].setBackground(Color.ORANGE);
						break;
					case 4 :
						area1[i][j].setBackground(Color.RED);
						break;
					case 5 :
						area1[i][j].setBackground(Color.BLACK);
						break;	
					default:
						break;
				}
			}
		}
	}
	
	public void refreshMap2(int[][] tab){
		area2Int = tab;
		for(int i=0; i<tab.length;i++){
			for(int j=0; j<tab[0].length; j++){
				switch(tab[i][j]){
					case 0 :
						area2[i][j].setBackground(Color.CYAN);
						break;
					case 1 :
						area2[i][j].setBackground(Color.BLACK);
						break;
					case 2 : 
						area2[i][j].setBackground(Color.ORANGE);
						break;
					case 3 :
						area2[i][j].setBackground(Color.GREEN);
						break;
					case 4 :
						area2[i][j].setBackground(Color.RED);
					default:
						break;
				}
			}
		}
	}
 
	public void refreshCarteOrdi(){
		mapIntClear(carteOrdi);
		for(int y = 0; y<j2.tabEn.length; y++){
			for(int x = 0; x<j2.tabEn[0].length; x++){
				if(j2.tabEn[y][x] == 2){
					for (int i = 0; i < carteOrdi.length; ++i) {
						for (int j = 0; j < carteOrdi[0].length; ++j) {
							if(j2.tabEn[i][j]==0){
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
 
	public void mapClear(JLabel[][] map){
		for(int i=0; i<map.length;i++){
			for(int j=0; j<map[0].length; j++){
				map[i][j].setBackground(Color.CYAN);
			}
		}
	}
	
	public void mapIntClear(int[][] mapInt){
		for(int i=0; i<mapInt.length;i++){
			for(int j=0; j<mapInt[0].length; j++){
				mapInt[i][j] = 0;
			}
		}
	}
	
	public void placerBateau(Joueur joueur){
		switch(nbBat){
			case 0: 
				//lbl2.setText(joueur.nomJoueur + " placer le premier bateau, c'est un porte-avion de taille 5");
				batLon = 5;
				break;
			case 1:
				Bateau bat1 = new Bateau(1, batDir, coor[1], coor[0]);
				joueur.ajouteBateau(bat1);
				//lbl2.setText(joueur.nomJoueur +" placer le deuxieme bateau, c'est un cargo de taille 4");
				batLon = 4;
				break;
			case 2:
				Bateau bat2 = new Bateau(2, batDir, coor[1], coor[0]);
				joueur.ajouteBateau(bat2);
				//lbl2.setText(joueur.nomJoueur +" placer le troisieme bateau, c'est un voilier de taille 3");
				batLon = 3;
				break;
			case 3:
				Bateau bat3 = new Bateau(3, batDir, coor[1], coor[0]);
				joueur.ajouteBateau(bat3);
				//lbl2.setText(joueur.nomJoueur +" placer le quatrieme bateau, c'est un voilier de taille 3");
				batLon = 3;
				break;
			case 4:
				Bateau bat4 = new Bateau(3, batDir, coor[1], coor[0]);
				joueur.ajouteBateau(bat4);
				//lbl2.setText(joueur.nomJoueur +" placer le cinquieme bateau, c'est un barque de taille 2");
				batLon = 2;
				break;
			case 5:
				Bateau bat5 = new Bateau(4, batDir, coor[1], coor[0]);
				joueur.ajouteBateau(bat5);
				//lbl1.setText("les bateaux sont mis pour "+ joueur.nomJoueur);
				if(jou==2){
					//lbl2.setText("tous les bateaux sont mis ");
					allowMouse1 = false;
					jou = 1;
					refreshMap1(j1.tabBat);
					refreshMap2(j1.tabEn);
					centre.remove(btnBateau);
					centre.add(attaque);
					mainFrame.invalidate();
					mainFrame.validate();
					mainFrame.repaint();
					allowMouse2 = true;
					break;
				}
				jou =2;
				nbBat = 0;
				mapClear(area1);
				mapIntClear(area1Int);
				batLon = 5;
				//lbl2.setText(joueur.nomJoueur + " placer le premier bateau, c'est un porte-avion de taille 5");
				if(ordi){
					nbBat = 1;
					ordiPlacerBateau();
				}
				System.out.println("changement de joueur");
				break;
			default :
				break;
		}
		nbBat++;
		System.out.println("tableau de bateaux");
		joueur.imprim(joueur.tabBat);
	}
	
	public void attaqueJou(int y,int x, int jInt){
		System.out.println("Y :" + y +" X :" + x);	
		if(jInt == 1){
			jTemp1 = j1;
			jTemp2 = j2;
		}else{
			jTemp1 = j2;
			jTemp2 = j1;
		}
		System.out.println("attaque de "+jTemp1.nomJoueur);	
		switch(jTemp2.tabBat[y][x]){
			case 0:
				//lbl1.setText("attaque ratee");
				jTemp1.tabEn[y][x] =  1;
				jTemp2.tabBat[y][x] = 5;
				tombeDansLeau.mettreSon();
				if(ordi && jou ==2){
					carteOrdi[y][x] = 0;
				}
				break;
			case 1:
				//lbl1.setText("attaque reussiee");
				jTemp1.tabEn[y][x] = 2;
				jTemp2.tabBat[y][x] = 2;
				Bateau batTemp = jTemp2.attaqueBat(y,x);
				explosion.mettreSon();
				if(batTemp.estCoule()){
					for(int n=0; n<batTemp.posBat.length; n++){
						jTemp2.tabBat[batTemp.posBat[n][0]][batTemp.posBat[n][1]] = 4; 
						jTemp1.tabEn[batTemp.posBat[n][0]][batTemp.posBat[n][1]] = 4; 
					}
					refreshCarteOrdi();
					System.out.println("carte ordi mise a jour");
				}else if(ordi && jou ==2){
					for (int i = 0; i < carteOrdi.length; ++i) {
						for (int j = 0; j < carteOrdi[0].length; ++j) {
							if(j2.tabEn[i][j]==0){
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
					carteOrdi[y][x] = 0;
				}
				break;
			default:
				break;		
		}
	} 
	
	public void jeuTermine(Joueur j){
		JFrame frameFini = new JFrame();
		frameFini.setSize(200,200);
		frameFini.setLayout(new BorderLayout());
		JPanel panelFini = new JPanel(new FlowLayout());
		JLabel lblfini = new JLabel("BRAVO " +j.nomJoueur+", vous avez gagne");
		panelFini.add(lblfini);
		frameFini.add(panelFini, BorderLayout.CENTER);
		frameFini.setLocationRelativeTo(null);
		frameFini.setVisible(true);
		mainFrame.dispose();
	}
	
	public void ordiPlacerBateau(){
		for(int n=0; n<5; n++){
			System.out.println("bateau "+ (n+1) + " mis pour l'ordi");
			loop1:
			while(true){
				batDir = (int) ( Math.random() * 2 + 1 );
				coor[0] = (int) ( Math.random() * largeur );
				coor[1] = (int) ( Math.random() * longueur );
				if(batDir == 1){
					for(int i = coor[0]; i<coor[0]+batLon; i++){
						if(i>=area1.length){
							continue loop1;
						}
						if(area1Int[i][coor[1]] == 1){
							continue loop1;
						}
					}
					for(int i = coor[0]; i<coor[0]+batLon; i++){
						if(i<area1.length && area1Int[i][coor[1]] == 0){
							area1Int[i][coor[1]] = 1;
						}
					}
				}else{
					for(int i = coor[1]; i<coor[1]+batLon; i++){
						if(i>=area1.length){
							continue loop1;
						}
						if(area1Int[coor[0]][i] == 1){
							continue loop1;
						}
					}
					for(int i = coor[1]; i<coor[1]+batLon; i++){
						if(i<area1.length && area1Int[coor[0]][i] == 0){
							area1Int[coor[0]][i] = 1;
						}
					}
				}
				placerBateau(j2);
				break loop1;
			}
		}	
	}
	
	public void ordiAttaque(){
		int grandeVal = 0;
		for(int i=0; i<carteOrdi.length; i++){
			for(int j=0; j<carteOrdi[0].length; j++){
				if(carteOrdi[i][j]>grandeVal){
					grandeVal = carteOrdi[i][j];
					coordX = j;
					coordY = i;
				}
			}
		}
		
		if(grandeVal == 0){
			while(true){
				coor[0] = (int) ( Math.random() * largeur );
				coor[1] = (int) ( Math.random() * longueur );
				if(area2Int[coor[0]][coor[1]] == 0){
					area2Int[coor[0]][coor[1]] = 3;
					coordX = coor[1];
					coordY = coor[0];
					attaque.requestFocus();
					break;
				}
			}
		}
		System.out.println("la grande valeur est "+grandeVal);
		System.out.println("attaque numero " + nomAttaque + " pour " + j2.nomJoueur);
		System.out.println("attaque faite a Y: " + coordY+ " X: " + coordX);
		attaqueJou(coordY, coordX, 2);
		System.out.println("tableau d'attaques de "+j2.nomJoueur);
		j2.imprim(j2.tabEn);
		intTemp = j1.nomBatCoul();
		System.out.println("nombre de bateaux coules : "+intTemp);
		System.out.println();
		System.out.println();
		if(intTemp ==5){
			jeuTermine(j2);
		}
		refreshMap1(j1.tabBat);
		refreshMap2(j1.tabEn);
		jou=1;
		nomAttaque++;
	}

}
