package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
		
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private ArrayList<Item> item = new ArrayList<Item>();
	private ArrayList<SecondEnemy> secondenemy = new ArrayList<SecondEnemy>();
	private SpaceShip v;	
	
	private Timer timer;
	
	private long score = 0;
	private double difficulty = 0.2;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
		timer = new Timer(70, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}
	
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*780), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}

	private void generateItem(){								
		Item i = new Item((int)(Math.random()*780), 30); 
		gp.sprites.add(i);
		item.add(i);
	}

	private void generateSecondEnemy(){								
		SecondEnemy se = new SecondEnemy((int)(Math.random()*780), 30); 
		gp.sprites.add(se);
		secondenemy.add(se);
	}
	
	private void process(){
		if(Math.random() < difficulty){
			generateEnemy();
		}
		if(Math.random() < 0.05){   					
			generateItem();
		}
		if(Math.random() < 0.02){   					
			generateSecondEnemy();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 100;
			}
		}

		Iterator<Item> i_iter = item.iterator();
		while(i_iter.hasNext()){
			Item i = i_iter.next();
			i.proceed();

			if(!i.isAlive()){
				i_iter.remove();
				gp.sprites.remove(i);
			}
		}
		
		Iterator<SecondEnemy> se_iter = secondenemy.iterator();
		while(se_iter.hasNext()){
			SecondEnemy se = se_iter.next();
			se.proceed();

			if(!se.isAlive()){
				se_iter.remove();
				gp.sprites.remove(se);
				score += 200;
			}
		}

		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				die();
				return;
			}
		}
		Rectangle2D.Double ir;			
		for(Item i : item){			
			ir = i.getRectangle();
			if(ir.intersects(vr)){
				score += 1000;
				i.notAlive();
				return;
			}
		}
		Rectangle2D.Double ser;			
		for(SecondEnemy se : secondenemy){			
			ser = se.getRectangle();
			if(ser.intersects(vr)){
				die();
				return;
			}
		}
	}
	
	public void die(){
		timer.stop();
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.moveLeftRight(-1);
			break;
		case KeyEvent.VK_RIGHT:
			v.moveLeftRight(1);
			break;
		case KeyEvent.VK_D:
			difficulty += 0.1;
			break;
		case KeyEvent.VK_P:  //play
		 	start();	
		 	break;						
		case KeyEvent.VK_S:   //stop
		 	die();	
		 	break;							
		case KeyEvent.VK_R:  //reset
		 	score = 0;						
		 	break; 
		case KeyEvent.VK_UP:  //up
			v.moveUpDown(-1);
			break;
		case KeyEvent.VK_DOWN: //down
			v.moveUpDown(1);
			break;

		}
	}

	public long getScore(){
		return score;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}
}
