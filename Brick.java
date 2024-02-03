import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Brick here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Brick  extends Pushable
{
    public Brick()
    {
        GreenfootImage image = new GreenfootImage(27, 27);
        image.fillRect(0,0,27,27);
        setImage(image);
    }
}
