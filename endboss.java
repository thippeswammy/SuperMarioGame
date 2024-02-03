import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * ENDBOSS - ELECTRIC GOD
 */
public class endboss extends enemy
{
    int value = 300;
    
    boolean bDefeated = false;   
    int bosshealth = 9;
    int sparkdelay = 0;

    int falldelay = 100;

    static GreenfootImage defeatedpic = new GreenfootImage("endbossdefeated.gif");
    static GreenfootImage normalpic = new GreenfootImage("endbossframea.gif");

    static GreenfootImage hurtframea = new GreenfootImage("endbosshurta.gif");
    static GreenfootImage hurtframeb = new GreenfootImage("endbosshurtb.gif");

    public endboss()
    {
        bCanBeAttackedAtAll = false;
        //  bMeleeAttack = false;
        xoffset = 64;
        yoffset = 64;
        xs = 3;
        ys = -2;
        stunned = 40;
        bKillOnFall = true;
    }//cons

    ////
    public void act() 
    { 
        if (bRemoved) {return;}

        if (stunned <=0) { movement();}

        ////
        if (!bDefeated) {
            checkbackground();

            if (stunned == 18) {setImage(hurtframeb);}
            if (stunned == 1) {setImage(normalpic);}
            //  if (falldelay > 0) {falldelay--;}else{falldelay = 110;}
            // if (falldelay <= 9) {ys = 2;}   

            ////////////////////////
            if (!mworld.Harder && mworld.getObjects(rock.class).isEmpty())
            {
                getWorld().addObject(new rock(), Greenfoot.getRandomNumber(500)+80,300);
            }//endif

            if (mworld.Harder && mworld.getObjects(edemon.class).isEmpty())
            {
                getWorld().addObject(new edemon(), Greenfoot.getRandomNumber(500)+80,50);
                getWorld().addObject(new edemon(), Greenfoot.getRandomNumber(500)+80,60);
                getWorld().addObject(new edemon(), Greenfoot.getRandomNumber(500)+80,40);
            }//endif
            ////////////////////

            if (stunned > 0) {sparkdelay=8;}

            if (sparkdelay > 0)
            {sparkdelay--;}
            else
            {
                sparkdelay = 8;
                getWorld().addObject(new electricity(true), getX()-64, getY()-64);
                getWorld().addObject(new electricity(true), getX()+64, getY()-64);
                getWorld().addObject(new electricity(true), getX()-64, getY()+64);
                getWorld().addObject(new electricity(true), getX()+64, getY()+64);
            }//endif
        }
        else //!bdefeated
        {
            gravity();   
        }//endifbdef

        if (bosshealth <= 0 && !bDefeated){

            bDefeated = true;
            bMeleeAttack = false;
            ys = - 3;
            xs = 0;
            setImage(defeatedpic);
        }//endif

        limits();
        setLocation((int) x, (int) y);
        delays();

        if (health < 0) { removeme();}  
    } //act 
    
    private void removeme()
    {
        bRemoved = true;
        mworld.score += this.value;
        getWorld().removeObject(this);
    }//killthis
    /////////////////

    public void gethurt()
    {
        bosshealth--;
        stunned = 30;
        xs *= -1;
        ys *=-1;

        setImage(hurtframea);
    }//ghurt

    /////////////////////////////////////////
    public void checkbackground()
    {

        if (mworld.isGround(getX(), getY()+this.yoffset) )
        { y -= 1; 
            if (ys > 0){ys = -ys;}

        }///endif

        if (mworld.isGround(getX(), getY()-this.yoffset) )
        { y += 1; 
            if (ys < 0){ys = -ys;}
        }///endif

        if (mworld.isGround(getX()+this.xoffset, getY()) )
        { x -= 1;
            if (xs > 0) {xs = -xs;}
        }////endif

        if (mworld.isGround(getX()-this.xoffset, getY()) )
        { x += 1;
            if (xs < 0) {xs = -xs;}
        }///endif   
    }//checkbgend

}//class

