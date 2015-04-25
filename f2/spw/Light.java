package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Light extends Sprite{
	public static final int X_TO_FADE = 600;
	public static final int X_TO_DIE = 800;
	
	private int step = 15;
	private boolean alive = true;
	//BufferedImage image;

	public Light(int x, int y) {
		super(x, y, 400, 5);
		/*try{
			image = ImageIO.read(new File("f2/image/Light3.png"));
		}
		catch(IOException e){

		} */
	}

	@Override
	public void draw(Graphics2D g) {
		if(x < X_TO_FADE)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
		else{
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
					(float)(X_TO_DIE - x)/(X_TO_DIE - X_TO_FADE)));
		}
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);
		//g.drawImage(image, x, y, width, height, null);
	}

	public void proceed(){
		x += step;
		if(x > X_TO_DIE){
			alive = false;
		}
	}
	
	public boolean isAlive(){
		return alive;
	}

	public void notAlive(){
		alive = false;
	}
}
