package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpaceShip extends Sprite{

	int step = 50;
	BufferedImage image;

	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		try{
			image = ImageIO.read(new File("f2/image/Spaceship.png"));
		}
		catch(IOException e){

		}
	}

	@Override
	public void draw(Graphics2D g) {
		//g.setColor(Color.BLUE);
		//g.fillRect(x, y, width, height);
		g.drawImage(image, x, y, width, height, null);
	}

	public void moveLeftRight(int direction){
		x += (step * direction);
		if(x < 0)
			x = 0;
		if(x > 780 - width)
			x = 780 - width;
	}
	
	public void moveUpDown(int direction){
		y += (step * direction);
		if(y < 0)
			y = 0;
		if(y > 600 - width)
			y = 600 - width;
	}
}
