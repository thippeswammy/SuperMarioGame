import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * Write a description of class Debris here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Debris extends SmoothMover
{
    private static final Vector GRAVITY = new Vector(90, 1.5);
    private static final int FORCE = 45;
    int fragments;
    
    public Debris(int fragments)
    {
        this.fragments = fragments;
        
        int direction = Greenfoot.getRandomNumber(360);
        int speed = Greenfoot.getRandomNumber(FORCE);
        increaseSpeed( new Vector(direction, speed));
        
        setImage(new GreenfootImage("blockDebris.png"));
        
        setRotation (Greenfoot.getRandomNumber(360));
    }
    
    public Debris()
    {
        int direction = Greenfoot.getRandomNumber(360);
        int speed = Greenfoot.getRandomNumber(FORCE);
        increaseSpeed( new Vector(direction, speed));
        
        setImage(new GreenfootImage("blockDebris.png"));
        
        setRotation (Greenfoot.getRandomNumber(360));
    }
    
    /**
     * Act - do whatever the Debris wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        while(fragments > 0)
        {
            getWorld().addObject(new Debris(), getX(), getY());
            fragments--;
        }
        increaseSpeed(GRAVITY);
        move();
    }    
}
