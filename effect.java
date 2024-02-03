import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class effect here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class effect extends FX
{

    int health = 6;
    int dec = 1;
    boolean removed = false;

    public void act() 
    {
        // if (health <= 0) {return;}
        health--;

        setRotation(Greenfoot.getRandomNumber(350));

        if( health <= 0) {remove();}
    }  //act  

    public void remove()
    {
        if (removed) {return;}
        removed = true;
        getWorld().removeObject(this);
    }
}//class
