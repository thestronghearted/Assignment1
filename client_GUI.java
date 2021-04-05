
import java.awt.Dimension;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class client_GUI extends JFrame {

		JTextField txtInput;
		JTextArea txtOutput;
		JButton sendBtn; //button.addActionListener(e -> System.out.println();)
		client_GUI(){
			
		
			this.setTitle("Client GUI");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			
			JPanel northP= new JPanel();
			northP.setBounds(0,0,250,250);
			
			JPanel southP = new JPanel();
			southP.setBounds(0,250, 250, 250);
			southP.setLayout(new BorderLayout());
			
			//OUTPUT TEXT AREA
			txtOutput= new JTextArea();
			txtOutput.setEditable(false); 
			txtOutput.setPreferredSize(new Dimension(750,200));
			northP.add(txtOutput);
			
			//INPUT TEXT AREA
			txtInput = new JTextField();
			txtInput.setText("type here");
			txtInput.setPreferredSize(new Dimension(600,100));
			txtInput.setHorizontalAlignment(JLabel.LEFT);
			southP.add(txtInput,BorderLayout.WEST);
			
			//send button
			sendBtn = new JButton("send");
			sendBtn.setPreferredSize(new Dimension(150,100));
			southP.add(sendBtn, BorderLayout.EAST);			

			// add all components to the frame
			this.add(northP,BorderLayout.NORTH);
			this.add(southP,BorderLayout.SOUTH);
			this.pack();
			this.setVisible(true);
			
		}
	
	

}
