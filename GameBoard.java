import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

public class GameBoard extends Frame {
	
	Joueur j1 = new Joueur(8, 8, "kiro 1");
	Joueur j2 = new Joueur(8, 8, "kiro 2");
	int jou = 1;
	int batLen = 3;
	int batDir = 2;
	int longueur=8 ;
	int largeur=8;
	JFrame mainFrame = new JFrame();
	JLabel lblTop = new JLabel("Bataille navire");
	JLabel lbl1 = new JLabel();
	JLabel lbl2 = new JLabel("Appuyer sur 'bateau' pour commencer");
	JPanel top = new JPanel();
	JPanel centre = new JPanel(new BorderLayout());
	JPanel map1 = new JPanel(new GridLayout(longueur,largeur,5,5));
	JLabel[][] area1 = new JLabel[longueur][largeur];
	int[][] area1Int = new int[8][8];
	int[][] area2Int = new int[8][8];
	int[] coor = new int[2];
	JPanel map2 = new JPanel(new GridLayout(longueur,largeur,5,5));
	JLabel[][] area2 = new JLabel[longueur][largeur];
	JPanel buttons = new JPanel(new FlowLayout());
	JButton btnBateau = new JButton("bateau");
	JButton attaque = new JButton("attaque");
	boolean allowMouse1 = false;
	boolean allowMouse2 = false;
	int nbBat = 0;

	public GameBoard(){
		//set frame
		
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setTitle("GameBoard");
		mainFrame.setSize(1500,700);
		
		//maps
		//map1
		for(int i=0; i<area1.length; i++){
			for(int j =0; j<area1[0].length; j++){
				area1[i][j] = new JLabel();
				area1[i][j].addMouseListener(new mouseMvtMap1());
				area1[i][j].setOpaque(true);
				area1[i][j].setBackground(Color.WHITE);
				map1.add(area1[i][j]);
				area1[i][j].setPreferredSize(new Dimension(50, 20));
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
				area2[i][j].setBackground(Color.WHITE);
				area2[i][j].setPreferredSize(new Dimension(50, 20));
			}
		}
		map1.setBackground(Color.CYAN);
		map2.setBackground(Color.CYAN);
		map1.setSize(100,100);
		map2.setSize(100,100);
		
		//top
		top.setSize(100,50);
		top.setAlignmentX(Component.LEFT_ALIGNMENT);	
		top.add(lblTop);
		
		//centre
		Border border = BorderFactory.createLineBorder(Color.BLUE, 5);
        centre.setBorder(border);
        /*
		lbl1.setVerticalTextPosition(JLabel.BOTTOM);
		lbl1.setHorizontalTextPosition(JLabel.RIGHT);
		lbl2.setVerticalTextPosition(JLabel.BOTTOM);
		lbl2.setHorizontalTextPosition(JLabel.RIGHT);
		centre.add(lbl2);
		centre.add(lbl1);
		*/
		lbl1.setBorder(border);
		lbl2.setBorder(border);
		lbl1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lbl2.setAlignmentX(Component.CENTER_ALIGNMENT);
		centre.add(lbl1, BorderLayout.SOUTH);
		centre.add(lbl2, BorderLayout.NORTH);
		
		
		//buttons
		buttons.setSize(300,50);
		//attaque
		attaque.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
				mapClear(area2, area2Int);
			}
		});
		buttons.add(attaque);
		//bateau
		btnBateau.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
				nbBat=0;
				mapClear(area1, area1Int);
				allowMouse1= true;
				if(jou == 1){
					j1.effacerBat();
					j1.imprim();
					placerBateau(j1);
				}else{
					j2.effacerBat();
					j2.imprim();
					placerBateau(j2);
				}
			}
		});
		buttons.add(btnBateau);
		
		//add , visible
		mainFrame.add(map1, BorderLayout.EAST);
		mainFrame.add(map2, BorderLayout.WEST);
		mainFrame.add(top, BorderLayout.NORTH);
		mainFrame.add(buttons, BorderLayout.SOUTH);
		mainFrame.add(centre, BorderLayout.CENTER);
		mainFrame.setVisible(true);
	}

	public class mouseMvtMap1 implements MouseListener{
		//mouse entered
		@Override 
		public void mouseEntered(MouseEvent evt){
			if(allowMouse1){
				JLabel square = (JLabel)evt.getSource();
				coor = findIndex(area1, square);
				Color color = Color.GREEN;
				//area1[coor[0]][coor[1]].setText(String.valueOf(area1Int[coor[0]][coor[1]]));
				//lbl1.setText( coor[0] + " et " + coor[1]);
				if(batDir == 1){
					for(int i = coor[0]; i<coor[0]+batLen; i++){
						if(i>=area1.length){
							lbl1.setText(" pas d'espace ");
							color = Color.RED;
							break;
						}else if(area1Int[i][coor[1]] == 1){
							lbl1.setText(" il y a un bateau ");						
							color = Color.RED;
							break;
						}
					}
					for(int i = coor[0]; i<coor[0]+batLen; i++){
						if(i<area1.length){ 
							if(area1Int[i][coor[1]] == 0){
								area1[i][coor[1]].setBackground(color);
							}
						}
					}
				}else{
					for(int i = coor[1]; i<coor[1]+batLen; i++){
						if(i>=area1.length ){
							lbl1.setText(" pas d'espace ");
							color = Color.RED;
							break;
						}else if(area1Int[coor[0]][i] == 1){
							lbl1.setText(" il y a un bateau ");
							color = Color.RED;
							break;
						}
					}
					for(int i = coor[1]; i<coor[1]+batLen; i++){
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
				coor = findIndex(area1, square);
				if(batDir == 1){
					for(int i = coor[0]; i<coor[0]+batLen; i++){
						if(i<area1.length && area1Int[i][coor[1]] == 0){
							area1[i][coor[1]].setBackground(Color.WHITE);
						}
					}
				}else{
					for(int i = coor[1]; i<coor[1]+batLen; i++){
						if(i<area1.length && area1Int[coor[0]][i] == 0){
							area1[coor[0]][i].setBackground(Color.WHITE);
						}
					}
				}
				lbl1.setText("");
			}
		}
		//mouse clicked
		@Override 
		public void mouseClicked(MouseEvent evt) {
			if(allowMouse1){
				JLabel square = (JLabel)evt.getSource();
				coor = findIndex(area1, square);
				if(evt.getButton() == MouseEvent.BUTTON1){
					if(batDir == 1){
						for(int i = coor[0]; i<coor[0]+batLen; i++){
							if(i>=area1.length){
								return;
							}
							if(area1Int[i][coor[1]] == 1){
								return;
							}
						}
						for(int i = coor[0]; i<coor[0]+batLen; i++){
							if(i<area1.length && area1Int[i][coor[1]] == 0){
								area1[i][coor[1]].setBackground(Color.GRAY);
								area1Int[i][coor[1]] = 1;
							}
						}
					}else{
						for(int i = coor[1]; i<coor[1]+batLen; i++){
							if(i>=area1.length){
								return;
							}
							if(area1Int[coor[0]][i] == 1){
								return;
							}
						}
						for(int i = coor[1]; i<coor[1]+batLen; i++){
							if(i<area1.length && area1Int[coor[0]][i] == 0){
								area1[coor[0]][i].setBackground(Color.GRAY);
								area1Int[coor[0]][i] = 1;
							}
						}
					}
					if(jou == 1){
						placerBateau(j1);
					}else{
						placerBateau(j2);
					}
				}else if(evt.getButton() == MouseEvent.BUTTON3){
					if(batDir == 1){
						for(int i = coor[0]; i<coor[0]+batLen; i++){
							if(i<area1.length && area1Int[i][coor[1]] == 0){
								area1[i][coor[1]].setBackground(Color.WHITE);
							}
						}
					}else{
						for(int i = coor[1]; i<coor[1]+batLen; i++){
							if(i<area1.length && area1Int[coor[0]][i] == 0){
								area1[coor[0]][i].setBackground(Color.WHITE);
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
						if(area1.length-coor[0]-batLen >=0){
							color = Color.GREEN;
						}
						for(int i = coor[0]; i<coor[0]+batLen; i++){
							if(i<area1.length && area1Int[i][coor[1]] == 0){
								area1[i][coor[1]].setBackground(color);
							}
						}
					}else{
						if(area1.length-coor[1]-batLen >=0){
							color = Color.GREEN;
						}
						for(int i = coor[1]; i<coor[1]+batLen; i++){
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
			JLabel square = (JLabel)evt.getSource();
			coor = findIndex(area2, square);
			if(area2Int[coor[0]][coor[1]] == 0){
				area2[coor[0]][coor[1]].setBackground(Color.GRAY);
			}
		}
		
		@Override 
		public void mouseExited(MouseEvent evt){
				JLabel square = (JLabel)evt.getSource();
				int[] coor = findIndex(area2, square);
			if(area2Int[coor[0]][coor[1]] == 0){
				area2[coor[0]][coor[1]].setBackground(Color.WHITE);
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent evt) {
			JLabel square = (JLabel)evt.getSource();
			coor = findIndex(area2, square);
			if(area2Int[coor[0]][coor[1]] == 0){
				area2[coor[0]][coor[1]].setBackground(Color.ORANGE);
				area2Int[coor[0]][coor[1]] = 1;
			}
		}
		@Override public void mousePressed(MouseEvent evt) { }
		@Override public void mouseReleased(MouseEvent evt) { }		
	}
	
	public int[] findIndex(JLabel[][] tab, JLabel lbl){
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
	
	public void refreshBoard(int[][] tab){
		for(int i=0; i<tab.length;i++){
			for(int j=0; j<tab[0].length; j++){
				if(tab[i][j] == 0){
					area1[i][j].setBackground(Color.WHITE);
				}else if(tab[i][j] == 1){
					area1[i][j].setBackground(Color.GRAY);
				}else{
					//area1[i][j].setBackground(Color.ORANGE);
				}
			}
		}
	}
 
	public void mapClear(JLabel[][] map, int[][] mapInt){
		for(int i=0; i<map.length;i++){
			for(int j=0; j<map[0].length; j++){
				map[i][j].setBackground(Color.WHITE);
				mapInt[i][j] = 0;
			}
		}
	}
	
	public void placerBateau(Joueur joueur){
		switch(nbBat){
			case 0: 
				lbl2.setText(joueur.nomJoueur + " placer le premier bateau, c'est un porte-avion de taille 5");
				batLen = 5;
				break;
			case 1:
				Bateau bat1 = new Bateau(1, batDir, coor[1], coor[0]);
				joueur.ajouteBateau(bat1);
				lbl2.setText(joueur.nomJoueur +" placer le duexieme bateau, c'est un cargo de taille 4");
				batLen = 4;
				break;
			case 2:
				Bateau bat2 = new Bateau(2, batDir, coor[1], coor[0]);
				joueur.ajouteBateau(bat2);
				lbl2.setText(joueur.nomJoueur +" placer le troisieme bateau, c'est un voilier de taille 3");
				batLen = 3;
				break;
			case 3:
				Bateau bat3 = new Bateau(3, batDir, coor[1], coor[0]);
				joueur.ajouteBateau(bat3);
				lbl2.setText(joueur.nomJoueur +" placer le quatrieme bateau, c'est un voilier de taille 3");
				batLen = 3;
				break;
			case 4:
				Bateau bat4 = new Bateau(3, batDir, coor[1], coor[0]);
				joueur.ajouteBateau(bat4);
				lbl2.setText(joueur.nomJoueur +" placer le cinquieme bateau, c'est un barque de taille 2");
				batLen = 2;
				break;
			case 5:
				Bateau bat5 = new Bateau(4, batDir, coor[1], coor[0]);
				joueur.ajouteBateau(bat5);
				lbl2.setText("tous les bateaux sont mis pour "+ joueur.nomJoueur);
				allowMouse1 = false;
				batLen = 0;
				break;
			default :
				break;
		}
		nbBat++;
		joueur.imprim();
	}
	
	public static void main(String[] args){
		GameBoard test1 = new GameBoard();
	}
}
