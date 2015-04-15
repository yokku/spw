package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class SpaceShip extends Sprite{

	int step = 50;
	
	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, width, height);
		
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
		if(y > 550 - width)
			y = 550 - width;
	}
}
