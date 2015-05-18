import java.util.ArrayList;
import java.util.List;

/**
 * Represents a cannonball entity.
 * 
 */
public class Cannonball
        extends Entity
        implements Collideable{
	
	private Point velocity;
    private Ship shotBy;
	
    public Cannonball(Point p, double angle, Ship shotBy) {
        super(p, "resources/pictures/cannonball.png");
        velocity = new Point(0.5, 0); // Length of vector is not yet decided and can change
        setAngle(angle);
        this.shotBy = shotBy;
    }

    /**
     * Sets the angle of travel.
     * 
     * @param angle The angle that the cannonball will have.
     */
    public void setAngle(double angle) {	
		super.setAngle(angle);
		velocity.rotate(angle);
	}

    public boolean isColliding(Collideable e) {

        //If distance between e's collisionspoints and cannonballs center ~<7
        List<Point> othersPoints = e.getCollisionPoints();
        for (Point p : othersPoints) {
            double dx = p.getX()-getCenterPos().getX();
            double dy = p.getY()-getCenterPos().getY();
            if (dx*dx + dy*dy < 50)
                return true;
        }

        return false;
    }

    public void update(int timeDiff) {
        Point center = getCenterPos();
        center.add(velocity, timeDiff);
		setCenter(center);
        if (center.getX()>Pirates.WINDOW_WIDTH+image.getWidth()
                || center.getX() < -image.getWidth()
                || center.getY() > Pirates.WINDOW_HEIGHT+image.getHeight()
                || center.getY() < -image.getHeight())
            Pirates.remove(this);

	}

    @Override
    public void collideWith(Collideable e) {
        //TODO
    }

    @Override
    public List<Point> getCollisionPoints() {
        List<Point> collisionPoints = new ArrayList<Point>();
        collisionPoints.add(getBottomLeftPos());
        collisionPoints.add(getBottomRightPos());
        collisionPoints.add(getTopLeftPos());
        collisionPoints.add(getTopRightPos());
        return collisionPoints;
    }

    public void kill(){}
}
