import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * Write a description of class killCount here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class killCount  extends FX
{
    myWorld myworld;
    /**
     * Act - do whatever the killCount wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */

    protected void addedToWorld(World world)
    {
        myWorld mworld;
        myworld = (myWorld) getWorld();
    }

    public void act() 
    {
        GreenfootImage image = new GreenfootImage(100, 50);
        image.setColor(Color.WHITE);

        image.drawString("Kills: " + myworld.kills, 10,10);
        setImage(image);
    }    
}
