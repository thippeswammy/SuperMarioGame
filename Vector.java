/**
 * Vector - This class was written as a utility class for Greenfoot scenarios.
 * 
 * This class represents a 2-dimensional vector. 
 * 
 * The vector can be used to deal with movement information (speed and
 * direction) of actors. An actor can store a vector, and then other vectors 
 * can be added to modify the current velocity and direction.
 * 
 * @author Poul Henriksen
 * @author Michael Kolling
 * 
 * @version 1.1 (July 2007)
 */
public class Vector
{
    double dx = 0;
    double dy = 0;
    int direction = 0;
    double length;
    
    public Vector()
    {
    }

    public Vector(int direction, double length)
    {
        this.length = length;
        this.direction = direction;
        updateCartesian();
    }

//     public Vector(Location start, Location end)
//     {
//         dx = end.getX() - start.getX();
//         dy = end.getY() - start.getY();
//         updatePolar();
//     }

    /**
     * Set the direction of this vector.
     */
    public void setDirection(int direction) {
        this.direction = direction;
        updateCartesian();
    }
   
    /**
     * Add other vector to this vector.
     */
    public void add(Vector other) {
        dx += other.dx;
        dy += other.dy;
        updatePolar();
    }
    
    public void reduceLength(double d) {
        length = length - d;
        updateCartesian();
    }
    
    public void scale(double factor) {
        length = length * factor;
        updateCartesian();
    }
    
    public void setNeutral() {
        dx = 0.0;
        dy = 0.0;
        length = 0.0;
        direction = 0;
    }
    
    /**
     * Update the direction and length fom the current dx, dy.
     */
    private void updatePolar() {
        this.direction = (int) Math.toDegrees(Math.atan2(dy, dx));
        this.length = Math.sqrt(dx*dx+dy*dy);
    }   
    
    /**
     * Update dx and dy from the current direction and length.
     */
    private void updateCartesian() {
        dx = length * Math.cos(Math.toRadians(direction));
        dy = length * Math.sin(Math.toRadians(direction));   
    }   

    public double getX() {
        return dx;
    }
     
    public double getY() {
        return  dy;
    }
    
    public int getDirection() {
        return direction;
    }
    
    public double getLength() {
        return length;
    }
    
    /**
     * Create a copy of this vector.
     */
    public Vector copy() {
        Vector copy = new Vector();
        copy.dx = dx;
        copy.dy = dy;
        copy.direction = direction;
        copy.length = length;
        return copy;
    }
    
    public String toString() {
        return "[" + direction + " " + length + " / " + dx + "," + dy + "]";
    }
}
