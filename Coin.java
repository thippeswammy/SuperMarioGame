import greenfoot.*;
import greenfoot.GreenfootImage;

public class Coin extends FX
{
    private static final GreenfootImage[] img = {
            new GreenfootImage("Coin0.png"),
            new GreenfootImage("Coin1.png"),
            new GreenfootImage("Coin2.png"),
            new GreenfootImage("Coin3.png")
        };
    static {
        for(GreenfootImage i: img) i.scale(32, 32);
    }
    private boolean block;
    private double cur, b = -5;

    int count = 20;

    public Coin(boolean block)
    {
        this.block = block;
        setImage(img[0]);
    }

    public void act()
    {
        animate();
        countdown();
    }

    private void animate()
    {
        if(block) cur += .5;
        else cur += .1;
        if(cur >= 4) cur = 0;
        setImage(img[(int)cur]);
    }

    public void countdown()
    {
        ((myWorld) getWorld()).score++;
        count--;
        if(count<=0)
        {
            getWorld().removeObject(this);
        }
    }
}
