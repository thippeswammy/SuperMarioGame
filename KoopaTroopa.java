import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

public class KoopaTroopa extends Actor
{
    ////////////////////////////
    public myWorld mworld;

    boolean bRemoved = false;
    boolean bKnockedOut = false;

    int health = 2;
    int dmgdelay = 0;
    int kickdelay = 0;
    int usualkick = 1000;

    int stunned = 0;
    int usualstun = 0;

    float x,y, xs,ys;
    boolean bCanJump = false;

    static float minx = 16;
    static float miny = 16;
    static float maxx = 640-16;
    static float maxy = 480-16;

    int xoffset = 14;
    int yoffset = 14;
    int extrayoffset = 8;

    int dir = 0;
    float maxrun = 3;
    float maxaccel = 0.15f;

    float cur_frame=0.0f;
    float anim_speed= 0.0f;
    float start_frame=0.0f;
    float end_frame=0.0f;
    int animno = 0;
    int animdelay = 0;

    boolean bKillOnFall = true;

    // boolean bCanBeKicked = false;
    boolean bMeleeAttack = true;
    int attackdelay = 0;

    boolean bCanBeAttackedFromAbove = true;
    boolean bCanBeAttackedFromBelow = true;
    boolean bCanBeAttackedAtAll = true;

    private static GreenfootImage frames[] = new GreenfootImage[32];

    public KoopaTroopa()
    {
        setCrabframes();     
        //   setanim_default();
    }//constructor

    public void act()
    {
        flyaway();

        if (bRemoved) {return;}

        movement();

        if (!knockedout())
        {

            if (stunned <= 0) {
                setanim_default();
                ai_patrol();
            }
            else
            {
                setanim_stunned();  
            }//endif stunted

            deccelrate(); 
            checkbackground();
            checkelevator();
        }
        //         else     
        //     {
        //         setRotation(getRotation()+8);
        //         setanim_knockedout();
        //       //  if(x > maxx-5 || x < minx+5)
        // //         {
        // //             health=-1;
        // //         }
        //         if(dir==16)
        //         {
        //             xs -= maxaccel;
        //         }
        //         else if(dir==0)
        //         {
        //             xs += maxaccel;
        //         }
        //     }//endif knocked
        //         

        animate(frames);
        delays();

        setLocation((int) x, (int) y);

        //    if (health < 1) { knockmeout();}
        //   if (health < 0) { removeme();}
    }//actend
    ///////////////////////////////////////////////////////////////////////////////////////////   
    protected void addedToWorld(World world)
    {
        mworld = (myWorld) world;
        x = getX();
        y = getY();
    } //addedtoworldend
    //////////////////////////////////////////////////////////////////////////////
    //animation

    public void animate(GreenfootImage[] myframes){
        if (animdelay > 0) {return;}

        if (anim_speed > 0) {  
            cur_frame += anim_speed;
            if (cur_frame > end_frame) { cur_frame = start_frame;}
        }

        int frame = (int) cur_frame + dir;

        if (myframes[frame] != null) 
        {  
            this.setImage(myframes[frame]);
        }//endif
    }//animate

    ///animations
    private void setanim_default()
    {
        if (knockedout()) {return;}
        if (animno == 1 ) {return;}
        animno = 1;
        anim_speed = 0.2f; cur_frame = 0;  start_frame = 0;  end_frame = 4;
    }//anim default

    private void setanim_stunned()
    {
        if (knockedout()) {return;}
        if (animno == 2) {return;}
        animno = 2; 
        anim_speed = 0.2f; cur_frame = 0;  start_frame = 0;  end_frame = 4;   
    }//anim stunned
    //ok i just realized if i wouldn't change cur_frame, i woudln't need animno, ohwell 
    private void setanim_knockedout()
    {
        if (animno == 3) {return;}
        animno = 3; 
        anim_speed = 0.2f; cur_frame = 0;  start_frame = 0;  end_frame = 4;   
    }//anim stunned

    ////////////////////////////////////////////////////
    public void ai_patrol()
    {
        if (dir == 0 && xs < maxrun)
        { xs += maxaccel;  }
        if (dir == 16 && xs > -maxrun)
        { xs -= maxaccel;  }

        if (getX() > maxx-8)// ||  mworld.isGround( getX()+xoffset+2, getY() )  || mworld.isPatrol( getX()+xoffset+2, getY() ) )
        { //dir = 16;
            x = minx+8;}
        if (getX() < minx+8)//  || mworld.isGround( getX()-xoffset-2, getY() ) || mworld.isPatrol( getX()-xoffset-2, getY() ) )
        { //dir = 0;
            x = maxx-8;}     
    }//ai patrol end

    public void delays()
    {
        if (dmgdelay > 0) {dmgdelay--;}
        if (stunned > 0) {stunned--;}
        if (animdelay > 0) {animdelay--;}
        if (kickdelay > 0) {kickdelay--;}
        if (attackdelay > 0) {attackdelay--;}
    }//delaysend

    public boolean canattack()
    {
        return (dmgdelay <= 0 && stunned <= 0 && bMeleeAttack && !knockedout() && !bRemoved );
    }//canattackend

    public boolean canbekicked()
    {
        return (dmgdelay <= 0  && kickdelay <= 0 && stunned > 0 && bCanJump && !knockedout() && !bRemoved);
    }//canbekickedend

    public boolean canbeattacked()
    {
        return (bCanBeAttackedAtAll  && dmgdelay <= 0 && !knockedout() && !bRemoved);
    }//canbeattackend

    public void damage(int dmg)
    {
        if (knockedout() || bRemoved) {return;}
        health -= dmg;
        dmgdelay = 10;
    }//damage

    public void damageStun(int dmg)
    {
        if (knockedout() || bRemoved) {return;}
        health -= dmg;
        dmgdelay = 0;
        stunned = usualstun;
    }//damage

    public void removeme()
    {
        bRemoved = true;
        getWorld().removeObject(this);
    }//killthis

    public void kickmeminus()
    {
        // knockmeout();
        kickdelay = usualkick;
        xs = xs - 5;

        knockmeout();
    }//kicked

    public void kickmeplus()
    {
        // knockmeout();
        kickdelay = usualkick;
        xs = xs + 5;

        knockmeout();
    }//kicked

    public boolean attackbelow()
    {
        return bCanBeAttackedFromBelow;
    }//attackbelow

    public boolean attackabove()
    {
        return bCanBeAttackedFromAbove;
    }//attackabove

    public boolean knockedout()
    {
        return bKnockedOut;
    }//knockedoutend

    public void knockmeout()
    {
        if (bKnockedOut) {return;}
        bKnockedOut = true;
    }//knockmeoutend

    public void gravity()
    {
        if (ys < 5 ) {ys += 2;}
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

    //     public void limits()
    //     {
    //         
    //         if (y < miny) {y = miny; }
    //         if (y > maxy) {
    //             y = maxy; 
    //             if (bKillOnFall) {health = -1;}
    //         }//endif    
    //         if (x > maxx) { x = maxx; }
    //         if (x < minx) {x = minx;}
    //     }//limitsend

    public void checkbackground()
    {
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
                bCanJump = true;
            }//ifys

        }//ifmgnull

    }//checkelevatorend

    private void setCrabframes()
    {
        if (frames[0] == null) { //so maybe we dont reload images every time we make an enemy
            //crab - abcd:walking ef:stunned gh:knockedout
            frames[0] = new GreenfootImage("trooperframe1.PNG");
            frames[1] = new GreenfootImage("trooperframe2.PNG");
            frames[2] = new GreenfootImage("trooperframe3.PNG");
            frames[3] = new GreenfootImage("trooperframe1.PNG");
            frames[4] = new GreenfootImage("trooperframe2.PNG");
            frames[5] = new GreenfootImage("trooperframe3.PNG");
            frames[6] = new GreenfootImage("trooperframe1.PNG");
            frames[7] = new GreenfootImage("trooperframe2.PNG");

            frames[0+16] = new GreenfootImage("trooperframe1.PNG");
            frames[1+16] = new GreenfootImage("trooperframe2.PNG");
            frames[2+16] = new GreenfootImage("trooperframe3.PNG");
            frames[3+16] = new GreenfootImage("trooperframe1.PNG");
            frames[4+16] = new GreenfootImage("trooperframe2.PNG");
            frames[5+16] = new GreenfootImage("trooperframe3.PNG");
            frames[6+16] = new GreenfootImage("trooperframe1.PNG");
            frames[7+16] = new GreenfootImage("trooperframe2.PNG");

            for (int i=(0+16); i<(5+17);i++)
            {
                frames[i].mirrorHorizontally();
            }//next i
        }//endif
    }////Crabframes

    public void flyaway()
    {
        if(getY() < 20)
        {
            y = 480 - 20;
        }

        if(getY() > 480-20)
        {
            y = 20;
        }

        if (getX() > maxx-8)// ||  mworld.isGround( getX()+xoffset+2, getY() )  || mworld.isPatrol( getX()+xoffset+2, getY() ) )
        { //dir = 16;
            x = minx+8;}
        if (getX() < minx+8)//  || mworld.isGround( getX()-xoffset-2, getY() ) || mworld.isPatrol( getX()-xoffset-2, getY() ) )
        { //dir = 0;
            x = maxx-8;}   
    }

}//classend