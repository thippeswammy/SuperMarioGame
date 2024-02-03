import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class FireDude extends enemy
{
    ////////////////////////////
    int value = 30;
    public myWorld mworld;

    boolean bRemoved = false;

    float X,Y, XS,YS;

    static float minx = 16;
    static float miny = 16;
    static float maxx = 640-16;
    static float maxy = 480-16;
    
    boolean removed;

    static GreenfootImage[] Frames = new GreenfootImage[4];
    float curFrame;
    static GreenfootImage[] sparkFrames = new GreenfootImage[4];

    int xoffset = 14;
    int yoffset = 10;
    int extrayoffset = 8;

    int dir = 0;
    float maxrun = 3;
    float maxaccel = 0.15f;

    public FireDude()
    {
        loadFrames();
        curFrame = 0;
    }//constructor

    public void act()
    {        
        if(removed)
            return;

        gravity();
        movement();

        if(removed)
            return;
            
        if(health>=2 & !bKnockedOut)
        {
            if(removed)
            return;
            
            deccelrate(); 
            limits();

            if(dir==16)
            {
                XS -= maxaccel;
            }
            else if(dir==0)
            {
                XS += maxaccel;
            }

            setLocation((int) X, (int) Y);

            checkbackground();
            checkelevator();

            Animate(Frames);
        }
        if(removed)
            return;
            
        else
        {
            if(removed)
            return;
            setLocation((int) X, (int) Y);
            fall();
        }

    }//actend

    public void fall()
    {
        if(Y>=480)
        {
            removed = true;
            removeme();
        }
    }
    
    private void removeme()
    {
        bRemoved = true;
        mworld.score += this.value;
        getWorld().removeObject(this);
        return;
    }//killthis

    public void Animate(GreenfootImage[] myframes){
        if(cur_frame<3)
            cur_frame += 0.3;
        else{cur_frame = 0;}

        int frame = (int) cur_frame + 0;

        if (myframes[frame] != null) 
        {  
            this.setImage(myframes[frame]);
        }//endif
    }//animate
    ///////////////////////////////////////////////////////////////////////////////////////////   
    protected void addedToWorld(World world)
    {
        mworld = (myWorld) world;
        X = getX();
        Y = getY();
    } //addedtoworldend
    //////////////////////////////////////////////////////////////////////////////
    public void ai_patrol()
    {
        if(removed)
            return;
            
        if (dir == 0 && XS < maxrun)
        { XS += maxaccel;  }
        if (dir == 16 && XS > -maxrun)
        { XS -= maxaccel;  }

        if (getX() > maxx-8 ||  mworld.isGround( getX()+xoffset+2, getY() )  || mworld.isPatrol( getX()+xoffset+2, getY() ) )
        { dir = 16;}
        if (getX() < minx+8  || mworld.isGround( getX()-xoffset-2, getY() ) || mworld.isPatrol( getX()-xoffset-2, getY() ) )
        { dir = 0;}     
    }//ai patrol end

    public void gravity()
    {
        if (YS < 5 ) {YS += 0.3;}
    }//gravityend

    public void deccelrate()
    {
        XS *=  0.91;
    }//deccelrate end

    public void movement()
    {
        X += XS;
        Y += YS; 
    }//movementend

    public void limits()
    {

        if (Y < miny) {Y = miny; }
        if(Y > maxy) {Y=miny;}
        if (X > maxx) { X = maxx; dir = 16;}
        if (X < minx) {X = minx; dir = 0;}
    }//limitsend

    public void checkbackground()
    {
        if(removed)
            return;
            
        if (mworld.isJumpPad(getX(), getY()+this.yoffset) )
        { YS = -13;} //endif 

        if (mworld.isGround(getX(), getY()+this.yoffset+2) )
        {
            if (YS > 0){YS = 0;}
        }
        else
        {
        }//endif

        if (mworld.isGround(getX(), getY()+this.yoffset) )
        { Y -= 1; 
            //  if (YS > 0){YS = 0;}

        }///endif

        if (mworld.isGround(getX(), getY()-this.yoffset) )
        { Y += 1; 
            if (YS < 0){YS = 0;}
        }///endif

        if (mworld.isGround(getX()+this.xoffset, getY()) )
        { X -= 1;
            dir = 16;
            if (XS > 0) {XS = 0;}
        }////endif

        if (mworld.isGround(getX()-this.xoffset, getY()) )
        { X += 1;
            dir = 0;
            if (XS < 0) {XS = 0;}
        }///endif   
    }//checkbgend
    public void checkelevator()
    {
        if(removed)
            return;
            
        elevator mg = (elevator) getOneIntersectingObject(elevator.class);

        if (mg!= null && !mworld.isGround(getX(), getY()-yoffset-2)) 
        {

            if (YS > 0 && getY()  < (mg.getY()- mg.yOffset) )
            {
                Y = mg.getY()- mg.yOffset - extrayoffset;
                X = X + mg.cX;
                Y = Y + mg.cY;
                YS = 0;
            }//ifYS

        }//ifmgnull

    }//checkelevatorend

    private void loadFrames()
    {
        if (Frames[0] != null) {return;}
        Frames[0] = new GreenfootImage("fireframea.gif");
        Frames[1] = new GreenfootImage("fireframeb.gif");
        Frames[2] = new GreenfootImage("fireframec.gif");
        Frames[3] = new GreenfootImage("fireframed.gif");

        sparkFrames[0] = new GreenfootImage("sparkframea.gif");
        sparkFrames[1] = new GreenfootImage("sparkframeb.gif");
        sparkFrames[2] = new GreenfootImage("sparkframec.gif");
        sparkFrames[3] = new GreenfootImage("sparkframed.gif");
    }//void
}//classend