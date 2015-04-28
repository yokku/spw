package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Water extends Sprite{
	public static final int Y_TO_FADE = 600;
	public static final int Y_TO_DIE = 20;
	
	private int step = 15;
	private boolean alive = true;
	BufferedImage image;

	public Water(int x, int y) {
		super(x, y, 15, 30);
		try{
			image = ImageIO.read(new File("f2/image/Water.png"));
		}
		catch(IOException e){

		}
	}

	@Override
	public void draw(Graphics2D g) {
		if(y < Y_TO_FADE)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
		else{
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
					(float)(Y_TO_DIE - y)/(Y_TO_DIE - Y_TO_FADE)));
		} 
		//g.setColor(Color.YELLOW);
		//g.fillRect(x, y, width, height);
		g.drawImage(image, x, y, width, height, null);
	}

	public void proceed(){
		y -= step;
		if(y < Y_TO_DIE){
			alive = false;
		}
	}
	
	public boolean isAlive(){
		return alive;
	}
}