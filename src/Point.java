/**
 * Created by Alexander on 2015-05-10.
 */
public class Point extends Vector {
    public Point(double x, double y) {
        super(new double[]{x,y});
    }

    public Point(Matrix m) {
        this(m.getElem(0,0),m.getElem(1,0));
    }

    public double getX() {
        return get(0);
    }

    public double getY() {
        return get(1);
    }

    public void add(Point p) {
        add(p, 1);
    }

    /**
     * Rotates the vector counter-clockwise.
     * @param angle The angle for the vector to be rotated in degrees.
     */
    public void rotate(double angle) {
        angle = Math.toRadians(angle);
        Matrix rotate = new Matrix(new double[][] {
                {Math.cos(angle),-Math.sin(angle)},
                {Math.sin(angle),Math.cos(angle)}
        });
        setData(rotate.multiply(this).getData());
    }
    
    public double norm() {
		return Math.sqrt(getX()*getX()+getY()*getY());
	}

    public Point toBasis(Point p1, Point p2) {
        Matrix transMatrix = new Matrix(new double[][] {
                {p1.getX(), p2.getX()},
                {p1.getY(), p2.getY()}
        });
        return new Point(transMatrix.multiply(this));
    }

    public void normalize() {
        setData(multiply(1/norm()).getData());
    }

    @Override
    public String toString() {
        return "Point: (" + getX() + ", " + getY() + ")";
    }
}
