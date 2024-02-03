import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Ball here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Ball  extends Pushable
{
    public Ball()
    {
        GreenfootImage image = new GreenfootImage(27, 27);
        image.fillOval(0,0,26,26);
        setImage(image);
    }    
}
