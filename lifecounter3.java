import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class lifecounter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class lifecounter3  extends Player
{

    int x;
    int y;

    /**
     * Act - do whatever the lifecounter wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        setImage("3Lives.PNG");

    }    

    protected void addedToWorld(World world)
    {

        myWorld mworld;
        x = getX();
        y = getY();
    }
}
