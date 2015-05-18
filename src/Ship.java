import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 2015-05-08.
 */
public class Ship extends Entity {

	private int hitPoints;
    private Point velocity = new Point(0,0);
    private ArrayList<Integer> pressedKeys;
    private long lastShot;
    
    private final double ACCELERATION_PER_SECOND = 2, ROTATION_PER_SECOND = 60,/*Degrees*/
							SHOT_COOLDOWN = 700;

    // {Up,Down,left,right,shoot}
    List<Integer> keys;

    public Ship(Point p, ArrayList<Integer> pressedKeys,List<Integer> keys){
        super(p, "../resources/pictures/ship.png");
        this.pressedKeys = pressedKeys;
        this.keys = keys;
		hitPoints  = 1000; // Initial value might change
    }
    
    public void update(int timeDiff) {
        velocity.multiply(1-0.75*(timeDiff/1000.0));
        Point acceleration = new Point(0,0);

        for(int i = 0; i < pressedKeys.size();i++) {
            int index = keys.indexOf(pressedKeys.get(i));
            if (index == 0) // Up
                acceleration.add(new Point(0,-ACCELERATION_PER_SECOND));
            if (index == 1) // Down
                acceleration.add(new Point(0,ACCELERATION_PER_SECOND));
            if (index == 2) // Left
                setAngle(getAngle()-ROTATION_PER_SECOND*timeDiff/1000.0);
            if (index == 3) // Right
                setAngle(getAngle()+ROTATION_PER_SECOND*timeDiff/1000.0);
            if (index == 4) // Shoot
				shoot();
        }
        acceleration.rotate(getAngle());
        velocity.add(acceleration,timeDiff/1000.0);
        
        Point center = getCenterPos();
        center.add(velocity);
        setCenter(center);

        resolveDamage();
	}
	
	/**
	 * Shoots a cannonball in the ships direction. A cannonball can only
	 * be shot once every 700 ms (SHOOT_COOLDOWN)
	 */
    private void shoot() {
		if(System.currentTimeMillis()-lastShot>SHOT_COOLDOWN) {
			lastShot = System.currentTimeMillis();
			Cannonball cannonball = new Cannonball(getCenterPos(),getAngle()-90);
			Pirates.addCannonball(cannonball);
		}
    }
	
	private void takeDamage() {
		hitPoints = hitPoints - 100;
	}
	
	//might be used to make damage more dynamic
	private void takeDamage(int damage) {
        hitPoints = hitPoints - damage;
    }

    public void resolveDamage() {
        for (Entity e : collidedWith()) {
            takeDamage();
        }
    }

    public boolean isColliding(Entity e) {
        //ToDo: Implement
        return false;
    }

    public ArrayList<Entity> collidedWith() {
        ArrayList<Entity> collided = new ArrayList<>();
        for (Cannonball c : Pirates.getCannonballs()) {
            if (isColliding(c)) {
                collided.add(c);
            }
        }
        return collided;
    }
    
    public void kill() {
		//TODO
    }
}
