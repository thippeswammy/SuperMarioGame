import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Another excellent piece of code written by
 * Mutantleg
 */
public class fallift extends elevator
{
    myWorld mworld;

    float basey;
    float ys = 0;
    float yy = 0;
    boolean bFallDown = true;

    public fallift()
    {
        yOffset= 14;
    }

    public fallift(boolean falld)
    {
        yOffset= 14;
        bFallDown = falld;
    }//fallift

    public void addedToWorld(World world) {
        basey = getY();
        yy = basey;
    }//added

    public void act() 
    {
        //  pX = getX();
        pY = getY();
        if (bFallDown) {overPlayer();} else {overplb();}//endif

        setLocation(getX(),(int) yy);

        cY = getY()-pY;
    }  //act  

    private void overplb()
    {
        Player neb = (Player)  getOneIntersectingObject(Player.class);

        if ( neb!= null && ys > -5)
        {
            ys -= 0.1f; 
        }
        else
        {
            if (yy < basey ) { ys+=0.1f;}
        }

        if ( neb!= null && ys > -5)
        {
            ys -= 0.1f; 
        }
        else
        {
            if (yy < basey ) { ys+=0.1f;}
        }

        yy += ys;

        if (yy > basey) {
            ys = 0; 
            yy = basey;      
        }//endif

        if (yy < 10) { yy = 10; ys = 0;} //endif 
    }//overplb

    private void overPlayer()
    {
        Player neb = (Player)  getOneIntersectingObject(Player.class);

        if ( neb!= null && ys < 5)
        {
            ys += 0.1f; 
            if (ys < -2) { neb.ys = -5;}
        }
        else
        {
            if (yy > basey ) { ys-=0.3f;}
        }//endif

        yy += ys;

        if (yy < basey) {
            ys = 0; 
            yy = basey;      
        }//endif

        if (yy > 470) { yy = 470; ys = 0;} 

    }//overPlayer

}//fallift

