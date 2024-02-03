import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.util.Random;
/**
 * Write a description of class Bubble here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Bubble  extends FX
{
    float x, y;
    float speed;

    public Bubble()
    {
        int circle = Greenfoot.getRandomNumber(20) + 10;

        final Random rnd = new Random();

        int low = 1;
        int high = 2;
        int decpl = 2;
        speed = getRandomValue(rnd, low, high, decpl);

        GreenfootImage image = new GreenfootImage("bubble.png");
        image.scale(circle, circle);
        image.setTransparency(100);
        setImage(image);
    }

    public static float getRandomValue(Random random, int lowerBound, int upperBound, int decimalPlaces)
    {
        final double dbl = ((random == null ? new Random() : random).nextDouble() * (upperBound - lowerBound))+ lowerBound;
        //        return String.format("%." + decimalPlaces + "f", dbl);
        return (float) dbl;

    }

    protected void addedToWorld(World world)
    {
        x = getX();
        y = getY();
    }

    /**
     * Act - do whatever the Bubble wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(y > -50)
        {
            x += 0.02;
            y -= speed;
        }

        else
        {
            getWorld().addObject(new Bubble(), Greenfoot.getRandomNumber(640), 600);
            getWorld().removeObject(this);
            return;
        }

        setLocation((int) x, (int) y);
    }    
}
