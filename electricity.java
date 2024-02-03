import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class electricity extends hazard
{

    boolean bRemoved = false;
    float xs, ys;
    int health = 22;
    float xx, yy;

    public electricity(boolean noeffect)
    {
        xs =  0.1f * (float) (Greenfoot.getRandomNumber(16)-Greenfoot.getRandomNumber(16) );
        ys = 0.1f * (float) (Greenfoot.getRandomNumber(16)-Greenfoot.getRandomNumber(16) );
        health = 13;
    }//const2

    public electricity()
    {
        xs =  0.1f * (float) (Greenfoot.getRandomNumber(40)-Greenfoot.getRandomNumber(40) );
        ys = 0.1f * (float) (Greenfoot.getRandomNumber(40)-Greenfoot.getRandomNumber(40) );
    }//

    public void addedToWorld(World world) {
        yy = getY();
        xx = getX();
    }//added

    public void act() 
    {
        if (bRemoved) {return;}

        huntPlayer();
        xx += xs;
        yy += ys;

        setLocation( (int) xx,(int)yy);

        setRotation(Greenfoot.getRandomNumber(360));

        health--;

        if (health <= 0) {removeme();}

    }   //act 

    private void removeme()
    {
        if (bRemoved) {return;}
        bRemoved = true;
        getWorld().removeObject(this);
    }//rem
}//class
