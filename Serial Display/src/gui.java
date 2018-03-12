import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.print.DocFlavor.INPUT_STREAM;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class gui extends JFrame implements KeyListener{
	JTextField porta;
	JLabel status;
	JLabel temp0;
	JLabel temp1;
	
	gui(){
		this.setMaximumSize(new Dimension(200,100));
		this.setMinimumSize(new Dimension(200,100));
		

		Container input = new Container();
		porta = new JTextField(sharedResources.comPort);
		porta.setAlignmentX(input.LEFT_ALIGNMENT);
		porta.addKeyListener(this);
		status = new JLabel(sharedResources.status);
		status.setAlignmentX(input.LEFT_ALIGNMENT);
		
		Container temps = new Container();
		temp0 = new JLabel(sharedResources.temp0);
		temp0.setAlignmentX(temps.LEFT_ALIGNMENT);
		temp1 = new JLabel(sharedResources.temp1);
		temp1.setAlignmentX(temps.LEFT_ALIGNMENT);

		porta.setPreferredSize(new Dimension(100,35));
		porta.setText(sharedResources.comPort);
		
		porta.setMinimumSize(new Dimension(100,35));
		porta.setMaximumSize(new Dimension(100,35));
		status.setMinimumSize(new Dimension(100,50));
		status.setMaximumSize(new Dimension(100,50));
		
		temp0.setMinimumSize(new Dimension(100,50));
		temp0.setMaximumSize(new Dimension(100,50));
		temp1.setMinimumSize(new Dimension(100,50));
		temp1.setMaximumSize(new Dimension(100,50));
		
		input.setMinimumSize(new Dimension(100, 100));
		input.setLayout(new BoxLayout(input, BoxLayout.Y_AXIS));
		input.add(porta);
		input.add(status);
		
		temps.setMinimumSize(new Dimension(100, 100));
		temps.setLayout(new BoxLayout(temps, BoxLayout.Y_AXIS));
		temps.add(temp0);
		temps.add(temp1);
		
		this.setTitle("Sensor Temp");
		this.add(input, BorderLayout.WEST);
		this.add(temps, BorderLayout.EAST);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setFocusable(true);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getKeyCode()){
			case(KeyEvent.VK_ENTER):{
				sharedResources.comPort=porta.getText();
				sharedResources.reconnect=true;
				System.out.println("reloading port");
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getKeyCode()){
			case(KeyEvent.VK_ENTER):{
				sharedResources.reconnect=false;
				System.out.println("stop reloading port");
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
