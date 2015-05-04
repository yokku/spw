package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;	
	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	
	BufferedImage bg;
	BufferedImage life;
	BufferedImage coin;
	BufferedImage water;

	public GamePanel() {
		bi = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		//big.setBackground(Color.BLACK);
		try{
			bg = ImageIO.read(new File("f2/image/bg.jpg"));
		}
		catch(IOException e){

		} 

		try{
			life = ImageIO.read(new File("f2/image/Spaceship.png"));
		}
		catch(IOException e){

		}
		
		try{
			coin = ImageIO.read(new File("f2/image/coin.png"));
		}
		catch(IOException e){

		}

		try{
			water = ImageIO.read(new File("f2/image/Water2.png"));
		}
		catch(IOException e){

		}
	}

	public void updateGameUI(GameReporter reporter){
		big.clearRect(0, 0, 800, 600);
		
		big.drawImage(bg, 0, 0, 800, 600, null);
		big.setColor(Color.BLUE);
		//big.setFont("Tohoma", Font.BOLD,35);
		big.drawString(String.format("%06d", reporter.getScore()), 5, 20);

		big.drawImage(life, 750, 0, 30, 30, null);
		big.setColor(Color.RED);
		big.drawString(String.format("%02d", reporter.getLife()), 730, 25);

		big.drawImage(coin, 750, 40, 30, 30, null);
		big.setColor(Color.RED);
		big.drawString(String.format("%02d", reporter.getCoinCount()), 730, 65);

		big.drawImage(water, 755, 80, 20, 30, null);
		big.setColor(Color.RED);
		big.drawString(String.format("%02d", reporter.getWaterCount()), 730, 105);

		big.setColor(Color.BLUE);
		big.drawString(String.format("Game By.. YOK ", reporter.getScore()), 5, 570);
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}

}
