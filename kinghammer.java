import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
BOSS - KING BOAR FROG
 */
public class kinghammer extends enemy
{
    int value = 30;
    boolean bDefeated = false;

    public int bosshealth = 3;

    private static GreenfootImage hammerframes[] = new GreenfootImage[32];

    public kinghammer()
    {
        sethammerframes();
        usualstun = 120;
        kickdelay = 30;
        usualkick = 60;
        attackdelay = 120;
        health = 200;
        dir = 16;
        xs = 0;
        ys = 1;
        xoffset = 32;
        yoffset = 25;
        maxaccel = 2;
        bCanBeAttackedFromBelow = false;
        bKillOnFall = true;

        if (mworld.Harder) {
            bosshealth = 10;
            usualstun = 100;
            maxrun = 3.75f;
        }//endif

    }//constructor

    public void act() 
    {
        if (bRemoved) {return;}

        if (!bDefeated) {  
            if (!knockedout())
            {    
                if (stunned <= 0 && kickdelay <= 0) {
                    yoffset = 25;
                    if (bCanJump && ys >= 0) {setanim_default();}
                    if (!bCanJump) { ai_patrol();}
                    ai_hammerJump();
                }
                else
                {
                    if (kickdelay <= 0) {setanim_stunned();}
                }//endifstunned

            }//ifknockedout
            else
            {
                setanim_kicked();
                stunned = 0;
                bosshealth--;
                bKnockedOut = false;
            }//elseknockedout

        }//endif

        animate(hammerframes);

        deccelrate();
        movement();
        gravity();
        deccelrate();
        limits();

        if (!bDefeated) { checkbg(); } 

        delays();    
        setLocation((int) x, (int) y);

        if(bosshealth <=0)
        {
            setRotation(getRotation()+5);
        }

        if (bosshealth <= 0 && !bDefeated)
        {
            getWorld().addObject(new Coin(true), getX(), getY());
            mworld.c++;
            bDefeated = true;
            bMeleeAttack = false;
            bCanBeAttackedAtAll = false;
        }

        if(mworld.c==2)
        {
            mworld.addObject(new door(), 300, 327);
            mworld.addObject(new Yoshi(), 240, 300);
            mworld.c=0;
        }

        if (health < 0) { removeme();}
    }    //actend
    //////////////////////////////////////////////
    
    public void checkbg()
    {
        int xoff = xoffset;
        int yoff = yoffset;

        for(yoff = yoffset; yoff > yoffset-2; yoff--)
        {
            for(xoff = xoffset; xoff > xoffset-2; xoff--)
            {

                if (mworld.isJumpPad(getX(), getY()+yoff) )
                { ys = -13;} //endif 

                if (mworld.isGround(getX(), getY()+yoff+2) )
                {
                    if (ys > 0){ys = 0;}
                    bCanJump = true;
                }
                else
                {
                    bCanJump = false;
                }//endif

                if (mworld.isGround(getX(), getY()+yoff) )
                { y -= 1; 
                    //  if (ys > 0){ys = 0;}
                }///endif

                if (mworld.isGround(getX(), getY()-yoff) )
                { y += 1; 
                    if (ys < 0){ys = 0;}
                }///endif

                if (mworld.isGround(getX()+xoff, getY()-2) )
                { x -= 1;
                    if (xs > 0) {xs = 0;}
                }////endif

                if (mworld.isGround(getX()-xoff, getY()-2) )
                { x += 1;
                    if (xs < 0) {xs = 0;}
                }///endif   
            }
        }
    }//checkbgend
    
    private void ai_hammerJump()
    {
        if((xs <= -maxaccel || xs >= maxaccel) && mworld.isGround(getX(), getY()+yoffset+3) && kickdelay <= 0)
        {
            xs = 0;
        }
        if (attackdelay <= 0)// && bCanJump)
        {
            attackdelay = 120;
            ys = -8;
            setanim_attack();
            bCanJump = false;
        }

    }//hammerjump

    ///////////////////////////
    private void sethammerframes()
    {
        if (hammerframes[0] != null) {return;}

        hammerframes[0] = new GreenfootImage("Hammer1.PNG");
        hammerframes[1] = new GreenfootImage("Hammer2.PNG");
        hammerframes[2] = new GreenfootImage("Hammer4.PNG"); //kicked
        hammerframes[3] = new GreenfootImage("Hammer4.PNG");
        hammerframes[4] = new GreenfootImage("Hammer3.PNG");

        hammerframes[0+16] = new GreenfootImage("Hammer1.PNG");
        hammerframes[1+16] = new GreenfootImage("Hammer2.PNG");
        hammerframes[2+16] = new GreenfootImage("Hammer4.PNG"); //kicked
        hammerframes[3+16] = new GreenfootImage("Hammer4.PNG");
        hammerframes[4+16] = new GreenfootImage("Hammer3.PNG"); 

        for (int i=(0); i<(3+1);i++)
        {
            hammerframes[i].mirrorHorizontally();
        }//next i

        hammerframes[5] = hammerframes[2];
        hammerframes[5+16] = hammerframes[2+16];
    }//hammerframes

    private void setanim_default()
    {
        if (!bCanJump) {return;}
        if (animno == 0) {return;}
        animno = 0; 
        cur_frame = 0;
        anim_speed= 0.0f;
    }//void
    
    private void removeme()
    {
        bRemoved = true;
        mworld.score += this.value;
        getWorld().removeObject(this);
    }//killthis

    public void kickmeplus()
    {
        // knockmeout();
        kickdelay = usualkick;
        ys = -7;
        xs = 15;
        getWorld().addObject(new effect(), getX(),getY());
        Greenfoot.playSound("kick.wav");

        bosshealth--;
    }//kicked

    public void kickmeminus()
    {
        // knockmeout();
        kickdelay = usualkick;
        ys = -7;
        xs = -15;
        getWorld().addObject(new effect(), getX(),getY());
        Greenfoot.playSound("kick.wav");

        bosshealth--;
    }//kicked

    public void damageStun(int dmg)
    {
        if (knockedout() || bRemoved) {return;}
        health -= dmg;
        kickdelay = 0;
        dmgdelay = 0;
        yoffset=16;
        stunned = usualstun;
    }//damage
    
    public boolean canbekicked()
    {
        if(yoffset == 16)
        {return true;}
        return false;
    }

    private void setanim_kicked()
    {
        if (animno == 1) {return;}
        animno = 1; 
        cur_frame = 4; 
        start_frame=5;
        end_frame= 5;
        anim_speed= 0.15f;
    }//void

    private void setanim_attack()
    {
        if (animno == 2) {return;}
        bMeleeAttack = true;
        animno = 2; 
        cur_frame = 1; 
        anim_speed= 0.0f;
    }//void

//     public void limits()
//     {
//         if(x > 620)
//         {
//             x = 620;
//         }
//         if(x < 20)
//         {
//             x = 20;
//         }
//     }

    private void setanim_stunned()
    {
        if (animno == 3) {return;}
        bMeleeAttack = false;
        animno = 3; 
        cur_frame = 3; 
        anim_speed= 0.0f;
    }//void
    ////////////////////////////////////////////    
}//classend
