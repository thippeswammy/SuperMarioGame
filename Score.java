import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Score here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Score  extends FX
{
    GreenfootImage image = new GreenfootImage(200, 30);
    
    public Score(int score)
    {
        image.drawString("Score: " + score, 10, 15);
        setImage(image);
    }
    
    /**
     * Act - do whatever the Score wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        image.clear();
        image.drawString("Score: " + ((myWorld) getWorld()).score, 10, 15);
    }    
}
