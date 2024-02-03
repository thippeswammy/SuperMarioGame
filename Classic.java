import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Classic here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Classic  extends FX
{

    myWorld mworld;
    
    GreenfootImage image = new GreenfootImage(100, 50);
    
    public Classic()
    {
        setImage(image);
    }
    /**
     * Act - do whatever the Classic wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(Greenfoot.mouseClicked(this))
        {
            Greenfoot.playSound("42.wav");
            ((myWorld) getWorld()).Clsc=true;
            ((myWorld) getWorld()).Arcade=false;

            ((myWorld) getWorld()).nextLevel();
        }   
    }
}
