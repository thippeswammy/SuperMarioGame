import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class rock here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class rock extends enemy
{
    int value = 10;
    
    public rock()
    {
        bCanBeAttackedFromAbove = false;
        bMeleeAttack = false;
        stunned = 99;
        usualkick = 50;
        bKillOnFall = true;
    }//rock

    public void act() 
    {
        if (bRemoved) {return;}
        stunned = 99;
        dmgdelay =0;
        // bKnockedOut = false;

        if (kickdelay > 0) {huntendboss();}

        movement();
        // deccelrate();
        xs *=0.98f; 
        gravity();

        if (!bKnockedOut) { 
            setRotation(getRotation() +(int) xs);
            getsidebg();
            try
            {
                checkbackground();
            }
            catch(IndexOutOfBoundsException ex)
            {
                if(x >= 640)
                {
                    x -= 15;
                }
                if(x <= 0)
                {
                    x += 15;
                }
            }
        }//endif

        //checkelevator();

        setLocation((int) x, (int) y);

        delays();
        limits();
        if (health < 0) { removeme();} 
    }    //act
    
    private void removeme()
    {
        bRemoved = true;
        mworld.score += this.value;
        getWorld().removeObject(this);
    }//killthis

    ///////////////////////////////////
    private void huntendboss()
    {
        endboss egod = (endboss) getOneIntersectingObject(endboss.class);

        if (egod != null
        && egod.stunned <= 0
        && !bKnockedOut
        )
        {
            egod.gethurt();
            xs = -xs;
            ys = -3;
            getWorld().addObject(new effect(), getX(),getY());
            Greenfoot.playSound("kick.wav");
            bKnockedOut = true;
        }//endif

    }//endb

    public void kickme()
    {
        //knockmeout();
        kickdelay = usualkick;
        xs *= 3;
        ys = -13;

        Greenfoot.playSound("kick.wav");
    }//kicked

    public void damageStun(int dmg)
    {
        xs = 0;
    }//damage

    private void getsidebg()
    {
        try
        {
            if (mworld.isGround(getX()+this.xoffset, getY()) )
            { x -= 15;
                if (xs > 0) {xs *= -0.5f;}
            }////endif

            if (mworld.isGround(getX()-this.xoffset, getY()) )
            { x += 15;
                if (xs < 0) {xs *= -0.5f;}
            }///endif   
        }
        catch(IndexOutOfBoundsException ex)
        {
            if(x >= 640)
            {
                x -= 15;
            }
            else if(x <= 0)
            {
                x += 15;
            }
        }
    }//sidebg

}//class
