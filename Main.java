import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main{
	
	
	public static void main(String[] args){
		
		JFrame frame1 = new JFrame();
		frame1.setLayout(new BorderLayout());
		frame1.setSize(350,200);
		frame1.setResizable(false);
		JPanel panel1 = new JPanel(new FlowLayout());
		JPanel panel2 = new JPanel(new FlowLayout());
		JButton button1 = new JButton("commencer la bataille");
		JLabel label1 = new JLabel("nom du premier joueur : ");
		JLabel label2 = new JLabel("nom du deuxieme joueur : ");
		JLabel label3 = new JLabel("Veuillez mettre vos noms");
		JTextField textField1 = new JTextField(12);
		JTextField textField2 = new JTextField(12);
		textField1.setBounds(10, 200, 100, 10);
		textField2.setSize(label2.getPreferredSize());

		label1.setSize(label2.getPreferredSize());
		button1.setBounds(100, 500, 50, 50);
		label2.setSize(label2.getPreferredSize());
		button1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent evt){
				String nameJ1 = textField1.getText(); 
				String nameJ2 = textField2.getText();
				if(nameJ1.isEmpty()){
					nameJ1 = "Joueur 1";
				}
				if(nameJ2.isEmpty()){
					nameJ2 = "Joueur 2";
				}
				GameBoard start = new GameBoard(nameJ1, nameJ2);
				frame1.dispose();
			}
		});
		panel1.add(label1);
		panel1.add(textField1);
		panel1.add(label2);
		panel1.add(textField2);
		panel2.add(button1);
		frame1.add(panel2, BorderLayout.SOUTH);
		frame1.add(label3, BorderLayout.NORTH);
		frame1.add(panel1, BorderLayout.CENTER);
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
	}
}
