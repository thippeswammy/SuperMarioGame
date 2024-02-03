import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * so
 * its a rotating lift
 * 
 * code is put together from sidelift and fire
 * 
 * DonaldDuck (me) really can't do math...
 * More credits to Mutantleg
 * without whom, this wouldn't be here
 * 
 */

public class rotalift extends elevator
{

    int basex;
    int basey;

    private float xdist;
    private float ydist;

    private float angle = 0;
    private float rotspeed = 0.1f;

    public rotalift(float ang, float rot, int disx, int disy)
    {
        yOffset= 14;
        angle = ang;
        rotspeed = rot;
        xdist = disx;
        ydist = disy;
    }//const

    public rotalift()
    {
        yOffset= 14;
        xdist = 64;
        ydist = 0;
    }//const

    public void addedToWorld(World world) {
        basex = getX();
        basey = getY();
    }//added

    private void rotate()
    {
        angle += rotspeed;
        if (angle > 360) {angle = 0;}
        if (angle < 0) {angle = 360;}

        int yy = (int) (basey + (Math.sin(Math.toRadians(angle))*ydist));
        int xx = (int) (basex + (Math.cos(Math.toRadians(angle))*xdist));

        setLocation(xx,yy);

    }//rotate

    public void act() 
    {
        pX = getX();// x and y before moving
        pY = getY();

        rotate();

        cX = getX()-pX;
        cY = getY()-pY;
    }    //act
}//class

