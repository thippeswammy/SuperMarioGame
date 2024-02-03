import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Arcade here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Arcade  extends FX
{

    GreenfootImage image = new GreenfootImage(100, 50);

    public Arcade()
    {
        setImage(image);
    }

    /**
     * Act - do whatever the Arcade wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(Greenfoot.mouseClicked(this))
        {
            Greenfoot.playSound("42.wav");
            ((myWorld) getWorld()).Clsc=false;
            ((myWorld) getWorld()).Arcade=true;

            ((myWorld) getWorld()).setLevel(99);
        }
    }    
}
