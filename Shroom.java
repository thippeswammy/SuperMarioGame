import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class Shroom extends dynamicACTORS
{
    ////////////////////////////
    public myWorld mworld;

    boolean bRemoved = false;

    float x,y, xs,ys;

    static float minx = 16;
    static float miny = 16;
    static float maxx = 640-16;
    static float maxy = 480-16;

    int xoffset = 14;
    int yoffset = 10;
    int extrayoffset = 8;

    int dir = 0;
    float maxrun = 3;
    float maxaccel = 0.15f;

    public Shroom()
    {
    }//constructor

    public void act()
    {
        limits();
        gravity();

        if (bRemoved) {return;}

        movement();

        deccelrate(); 

        if(dir==16)
        {
            xs -= maxaccel;
        }
        else if(dir==0)
        {
            xs += maxaccel;
        }

        setLocation((int) x, (int) y);

        checkbackground();
        checkelevator();

    }//actend
    ///////////////////////////////////////////////////////////////////////////////////////////   
    protected void addedToWorld(World world)
    {
        mworld = (myWorld) world;
        x = getX();
        y = getY();
    } //addedtoworldend
    //////////////////////////////////////////////////////////////////////////////
    public void ai_patrol()
    {
        if (dir == 0 && xs < maxrun)
        { xs += maxaccel;  }
        if (dir == 16 && xs > -maxrun)
        { xs -= maxaccel;  }

        if (getX() > maxx-8 ||  mworld.isGround( getX()+xoffset+2, getY() )  || mworld.isPatrol( getX()+xoffset+2, getY() ) )
        { dir = 16;}
        if (getX() < minx+8  || mworld.isGround( getX()-xoffset-2, getY() ) || mworld.isPatrol( getX()-xoffset-2, getY() ) )
        { dir = 0;}     
    }//ai patrol end

    public void removeme()
    {
        bRemoved = true;
        getWorld().removeObject(this);
    }//killthis

    public void gravity()
    {
        if (ys < 5 ) {ys += 0.3;}
    }//gravityend

    public void deccelrate()
    {
        xs *=  0.91;
    }//deccelrate end

    public void movement()
    {
        x += xs;
        y += ys; 
    }//movementend

    public void limits()
    {

        if (y < miny) {y = miny; }
        if(y > maxy) {y=miny;}
        if (x > maxx) { x = maxx; dir = 16;}
        if (x < minx) {x = minx; dir = 0;}
    }//limitsend

    public void checkbackground()
    {
        if (mworld.isJumpPad(getX(), getY()+this.yoffset) )
        { ys = -13;} //endif 

        if (mworld.isGround(getX(), getY()+this.yoffset+2) )
        {
            if (ys > 0){ys = 0;}
        }
        else
        {
        }//endif

        if (mworld.isGround(getX(), getY()+this.yoffset) )
        { y -= 1; 
            //  if (ys > 0){ys = 0;}

        }///endif

        if (mworld.isGround(getX(), getY()-this.yoffset) )
        { y += 1; 
            if (ys < 0){ys = 0;}
        }///endif

        if (mworld.isGround(getX()+this.xoffset, getY()) )
        { x -= 1;
            dir = 16;
            if (xs > 0) {xs = 0;}
        }////endif

        if (mworld.isGround(getX()-this.xoffset, getY()) )
        { x += 1;
            dir = 0;
            if (xs < 0) {xs = 0;}
        }///endif   
    }//checkbgend
    public void checkelevator()
    {
        elevator mg = (elevator) getOneIntersectingObject(elevator.class);

        if (mg!= null && !mworld.isGround(getX(), getY()-yoffset-2)) 
        {

            if (ys > 0 && getY()  < (mg.getY()- mg.yOffset) )
            {
                y = mg.getY()- mg.yOffset - extrayoffset;
                x = x + mg.cX;
                y = y + mg.cY;
                ys = 0;
            }//ifys

        }//ifmgnull

    }//checkelevatorend
}//classend
