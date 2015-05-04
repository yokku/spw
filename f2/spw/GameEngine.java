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
	private ArrayList<Light> light = new ArrayList<Light>();
	private ArrayList<Laser> laser = new ArrayList<Laser>();
	private ArrayList<Water> water = new ArrayList<Water>();
	private ArrayList<CoinSet> coinset = new ArrayList<CoinSet>();
	private ArrayList<WaterBottle> waterbottle = new ArrayList<WaterBottle>();
	
	private SpaceShip v;	
	
	private Timer timer;
	
	private long score = 0;
	private double difficulty = 0.1;
	private int life = 5;
	private int coincount = 0;
	private int watercount = 0;

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

	private void generateLight(){								
		Light l = new Light(0, (int)(Math.random()*350)); 
		gp.sprites.add(l);
		light.add(l);
	}
	
	private void generateLaser(){								
		Laser la = new Laser(v.x+v.width/2,v.y); 
		gp.sprites.add(la);
		laser.add(la);
	}

	private void generateWater(){								
		Water w = new Water(v.x+v.width/2,v.y); 
		gp.sprites.add(w);
		water.add(w);
	}

	private void generateCoinSet(){								
		CoinSet cs = new CoinSet((int)(Math.random()*750), 0); 
		gp.sprites.add(cs);
		coinset.add(cs);
	}

	private void generateWaterBottle(){								
		WaterBottle wb = new WaterBottle((int)(Math.random()*750), 0); 
		gp.sprites.add(wb);
		waterbottle.add(wb);
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
		if(Math.random() < 0.01){   					
			generateLight();
		}
		if(Math.random() < 0.01){   					
			generateCoinSet();
		}
		if(Math.random() < 0.005){   					
			generateWaterBottle();
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

		Iterator<Light> l_iter = light.iterator();
		while(l_iter.hasNext()){
			Light l = l_iter.next();
			l.proceed();
			
			if(!l.isAlive()){
				l_iter.remove();
				gp.sprites.remove(l);
			}
		}

		Iterator<Laser> la_iter = laser.iterator();
		while(la_iter.hasNext()){
			Laser la = la_iter.next();
			la.proceed();

			if(!la.isAlive()){
				la_iter.remove();
				gp.sprites.remove(la);
			}
		}

		Iterator<Water> w_iter = water.iterator();
		while(w_iter.hasNext()){
			Water w = w_iter.next();
			w.proceed();

			if(!w.isAlive()){
				w_iter.remove();
				gp.sprites.remove(w);
			}
		}

		Iterator<CoinSet> cs_iter = coinset.iterator();
		while(cs_iter.hasNext()){
			CoinSet cs = cs_iter.next();
			cs.proceed();

			if(!cs.isAlive()){
				cs_iter.remove();
				gp.sprites.remove(cs);
			}
		}
		
		Iterator<WaterBottle> wb_iter = waterbottle.iterator();
		while(wb_iter.hasNext()){
			WaterBottle wb = wb_iter.next();
			wb.proceed();

			if(!wb.isAlive()){
				wb_iter.remove();
				gp.sprites.remove(wb);
			}
		}
		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double br;
		Rectangle2D.Double er;
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
				if(life > 0){
					life--;
					e.notAlive();
				}
				else
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
				if(life > 0){
					life--;
					se.notAlive();
				}
				else
					die();
				return;
			}
		}
		Rectangle2D.Double ir;			
		for(Item i : item){			
			ir = i.getRectangle();
			if(ir.intersects(vr)){
				score += 500;
				coincount++;
				i.notAlive();					
				return;
			}
			if(coincount>=100){
					life++;
					coincount-=100;
			}
		}
		Rectangle2D.Double wr;
		Rectangle2D.Double fr;			
		for(Fireball f : fireball){	
			fr = f.getRectangle();
			for(Water w : water){			
				wr = w.getRectangle();				
				if(wr.intersects(fr)){
					score += 500;
					f.notAlive();
					return;
				}
			}
			if(fr.intersects(vr)){
				score -= 500;
				f.notAlive();
				return;
			}
		}
		Rectangle2D.Double lar;
		Rectangle2D.Double lr;			
		for(Light l : light){			
			lr = l.getRectangle();
			for(Laser la : laser){			
				lar = la.getRectangle();
				if(lar.intersects(lr)){
					score += 1000;
					l.notAlive();
					return;
				}
			}
			if(lr.intersects(vr)){
				score -= 1000;
				l.notAlive();
				return;
			}
		}
		Rectangle2D.Double csr;			
		for(CoinSet cs : coinset){			
			csr = cs.getRectangle();
			if(csr.intersects(vr)){
				score += 700;
				coincount += 10;
				cs.notAlive();					
				return;
			}
			if(coincount>=100){
					life++;
					coincount-=100;
			}
		}
		Rectangle2D.Double wbr;			
		for(WaterBottle wb : waterbottle){			
			wbr = wb.getRectangle();
			if(wbr.intersects(vr)){
				watercount += 10;
				wb.notAlive();					
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
		case KeyEvent.VK_X:	 //laser
			generateLaser();
			break;
		case KeyEvent.VK_Z:	 //water		
			if(watercount>0){
				generateWater();
				watercount--;
			}
			break;
		}
	}

	public long getScore(){
		return score;
	}

	public int getLife(){
		return life;
	}

	public int getCoinCount(){
		return coincount;
	}
	
	public int getWaterCount(){
		return watercount;
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
