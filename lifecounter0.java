import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class lifecounter0 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class lifecounter0  extends Player
{

    int x;
    int y;
    /**
     * Act - do whatever the lifecounter0 wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        setImage("0Lives.PNG");
    }    

    protected void addedToWorld(World world)
    {

        myWorld mworld;
        x = getX();
        y = getY();
    }
}
