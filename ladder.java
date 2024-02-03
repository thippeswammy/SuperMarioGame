import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class ladder here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ladder  extends FX
{
    GreenfootImage img = new GreenfootImage("vine.png");
    int segments;
    boolean inWorld = true;

    public ladder(int height)
    {
        segments = height;
        setImage(new GreenfootImage(2, 2));
    }

    public ladder()
    {
    }

    public void act() 
    {
        if(inWorld)
        {
            getWorld().addObject(new vine(segments), getX(), getY());
            inWorld = false;
            getWorld().removeObject(this);
            return;
        }
    }    
}
