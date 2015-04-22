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
	private ArrayList<Bullet> bullet = new ArrayList<Bullet>();
	private ArrayList<Fireball> fireball = new ArrayList<Fireball>();
	private SpaceShip v;	
	
	private Timer timer;
	
	private long score = 0;
	private double difficulty = 0.1;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
		timer = new Timer(150, new ActionListener() {
			
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
		Enemy e = new Enemy((int)(Math.random()*750), 0);
		gp.sprites.add(e);
		enemies.add(e);
	}

	private void generateItem(){								
		Item i = new Item((int)(Math.random()*750), 0); 
		gp.sprites.add(i);
		item.add(i);
	}

	private void generateSecondEnemy(){								
		SecondEnemy se = new SecondEnemy((int)(Math.random()*750), 0); 
		gp.sprites.add(se);
		secondenemy.add(se);
	}

	private void generateBullet(){								
		Bullet b = new Bullet(v.x+v.width/2,v.y); 
		gp.sprites.add(b);
		bullet.add(b);
	}

	private void generateFireball(){								
		Fireball f = new Fireball((int)(Math.random()*750), 0); 
		gp.sprites.add(f);
		fireball.add(f);
	}
	
	private void process(){
		if(Math.random() < difficulty){
			generateEnemy();
		}
		if(Math.random() < 0.03){   					
			generateItem();
		}
		if(Math.random() < 0.02){   					
			generateSecondEnemy();
		}
		if(Math.random() < 0.04){   					
			generateFireball();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				//score += 100;
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
			//	score += 200;
			}
		}
		
		Iterator<Bullet> b_iter = bullet.iterator();
		while(b_iter.hasNext()){
			Bullet b = b_iter.next();
			b.proceed();

			if(!b.isAlive()){
				b_iter.remove();
				gp.sprites.remove(b);
			}
		}

		Iterator<Fireball> f_iter = fireball.iterator();
		while(f_iter.hasNext()){
			Fireball f = f_iter.next();
			f.proceed();
			
			if(!f.isAlive()){
				f_iter.remove();
				gp.sprites.remove(f);
			}
		}

		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double br;
		for(Enemy e : enemies){
			er = e.getRectangle();		
			for(Bullet b : bullet){			
				br = b.getRectangle();
				if(br.intersects(er)){
					score += 300;
					e.notAlive();
					return;
				}
			}
			if(er.intersects(vr)){
				die();
				return;
			}
		}
		Rectangle2D.Double ser;			
		for(SecondEnemy se : secondenemy){			
			ser = se.getRectangle();
			for(Bullet b : bullet){			
				br = b.getRectangle();
				if(br.intersects(ser)){
					score += 400;
					se.notAlive();
					return;
				}
			}
			if(ser.intersects(vr)){
				die();
				return;
			}
		}
		Rectangle2D.Double ir;			
		for(Item i : item){			
			ir = i.getRectangle();
			if(ir.intersects(vr)){
				score += 500;
				i.notAlive();
				return;
			}
		}

		Rectangle2D.Double fr;			
		for(Fireball f : fireball){			
			fr = f.getRectangle();
			if(fr.intersects(vr)){
				score -= 500;
				f.notAlive();
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
		case KeyEvent.VK_SPACE:	 //bullet	
			generateBullet();
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
