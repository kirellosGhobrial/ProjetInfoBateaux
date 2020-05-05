import java.awt.*;
import java.awt.event.*;

public class GameBoard extends Frame implements MouseListener{
	
		private int batLen = 3;
		private int batDir = 2;
		int longueur = 8;
		int largeur = 8;
		Label lbl1 = new Label("Bataille navire");
		Panel top = new Panel(new FlowLayout());
		Panel map = new Panel(new GridLayout(longueur,largeur,5,5));
		Label[][] area = new Label[longueur][largeur];
		Panel buttons = new Panel(new FlowLayout());
		Button placeBateau = new Button("bateau");
		Button attaque = new Button("attaque");






		
	public GameBoard(){
		//set frame
		setLayout(new BorderLayout());
		setTitle("GameBoard");
		setSize(500,500);
		
		//map
		
		for(int i=0; i<area.length; i++){
			for(int j =0; j<area[0].length; j++){
				area[i][j] = new Label();
				area[i][j].addMouseListener(this);
				area[i][j].setBackground(Color.WHITE);
				map.add(area[i][j]);
			}
		}
		map.setBackground(Color.CYAN);
		//top
		
		top.setSize(100,50);
		top.add(lbl1, BorderLayout.CENTER);
		
		
		
		//buttons
		
		buttons.setSize(300,50);
		attaque.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
			
			}
		});
		buttons.add(attaque);
		
		placeBateau.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
			
			}
		});
		buttons.add(placeBateau);
		
		//add , visible
		add(map, BorderLayout.CENTER);
		add(top, BorderLayout.NORTH);
		add(buttons, BorderLayout.SOUTH);
		setVisible(true);
	}


	@Override public void mouseEntered(MouseEvent evt){
		Label square = (Label)evt.getSource();
		int[] coor = findIndex(area, square);
		Color color = Color.RED;
		lbl1.setText( coor[0] + " et " + coor[1]);
		if(batDir == 1){
			if(area.length-coor[0]-batLen >=0){
				color = Color.GREEN;
			}
				for(int i = coor[0]; i<coor[0]+batLen; i++){
					if(i<area.length){
						area[i][coor[1]].setBackground(color);
					}
				}
		}else{
			if(area.length-coor[1]-batLen >=0){
				color = Color.GREEN;
			}
				for(int i = coor[1]; i<coor[1]+batLen; i++){
					if(i<area.length){
						area[coor[0]][i].setBackground(color);
					}
				}
		}
		
		
	}
	@Override public void mouseExited(MouseEvent evt){
		Label square = (Label)evt.getSource();
		int[] coor = findIndex(area, square);
		if(batDir == 1){
			for(int i = coor[0]; i<coor[0]+batLen; i++){
				if(i<area.length){
					area[i][coor[1]].setBackground(Color.WHITE);
				}
			}
		}else{
			for(int i = coor[1]; i<coor[1]+batLen; i++){
				if(i<area.length){
					area[coor[0]][i].setBackground(Color.WHITE);
				}
			}
		}
	}
	@Override public void mousePressed(MouseEvent evt) { }
	@Override public void mouseReleased(MouseEvent evt) { }
	@Override public void mouseClicked(MouseEvent evt) { }
	
	public int[] findIndex(Label[][] tab, Label lbl){
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




	
	public static void main(String[] args){
		GameBoard test1 = new GameBoard();
		System.out.println(test1.largeur);
		
	}
	

}
