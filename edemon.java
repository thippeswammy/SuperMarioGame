import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * electric demon
 */
public class edemon extends enemy
{
    int value = 100;
    int goalx;
    int goaly;
    //accelration
    float xa=0;
    float ya=0;

    int attackdelay = 0; int sparkdelay = 0;
    int lookaround = 0;

    boolean bBroken = false;

    boolean bGoalset = false;

    private static GreenfootImage eframes[] = new GreenfootImage[32];

    //just experimenting a bit
    private static GreenfootImage lookanim[] = new GreenfootImage[32];

    public edemon()
    {
        loadEdemonFrames();

        bMeleeAttack = false;
        bKnockedOut = false;
        bKillOnFall = false;

        lookaround = 40;
        usualkick = 50;
        usualstun = 200;
        health = 9999999;
    }//edemon

    protected void addedToWorld(World world)
    {   
        mworld = (myWorld) world;
        x = getX();
        y = getY();
    }//added

    ///////////////////////////
    public void act() 
    {
        if (bRemoved) {return;}

        if (attackdelay > 0 ) 
        {
            setanim_sparking();
            animate(eframes);

            lookaround = 20;
            // trytoattack();

            attackdelay--;
            sparkdelay--;

            if (stunned > 0) {attackdelay = 0;}

            if (sparkdelay < 4) {  bCanBeAttackedAtAll = false;} else { bCanBeAttackedAtAll = true; }

            bMeleeAttack = false;

            slowdown();

            if (sparkdelay <= 0 )
            {
                getWorld().addObject(new electricity(), getX(), getY());
                sparkdelay= 3;
            }//endifsprkdelay
        }
        else //if tackdelay
        {
            bCanBeAttackedAtAll = true;
            bMeleeAttack = false;

            if (stunned <= 0 && kickdelay <= 0)
            {
                if (lookaround <= 0)  {
                    setgoal();   
                    followgoal(); 
                    setanim_default();
                    animate(eframes);
                }
                else//iflookaround 
                {
                    lookaround--;
                    slowdown(); 
                    setanim_looking(); 
                    animate(lookanim); 
                    if (lookaround < 80){trytoattack();}
                } //endiflookround

                move();
                hasreachedgoal();
                bKnockedOut = false;
            }
            else //ifkickdelay
            {
                if (ys < 6) {ys +=0.2;}
                xs = xs *0.98f;
                xa = 0;
                ya = 0;
                x += xs;
                y += ys;
                setanim_hurt(); animate(eframes);

                if (kickdelay <= 0 && !bBroken) {
                    checkbackground();
                    checkelevator();         
                }//endifkickdly                    
            }//endifstuffned      
        }//endif //tackdelay

        if (kickdelay > 0) {huntendboss();}
        // if (bKnockedOut) { stunned = 0; ys = -9; bKnockedOut = false; }//ifknocked

        limits();
        setLocation((int) x, (int) y);     
        if (!bBroken) {delays();}

        if (bBroken &&  mworld.isGround(getX(), getY()) )
        {
            health = -1;
            getWorld().addObject(new effect(), getX(),getY());
        }//broken

        if (bBroken) {setRotation(getRotation() + (int)xs*2);}

        //  if (bKnockedOut && getY() > 470) { health = -1; }
        if (health < 0) { removeme();}
    } //act   
    
    private void removeme()
    {
        bRemoved = true;
        mworld.score += this.value;
        getWorld().removeObject(this);
    }//killthis

    ///////////////////////////////////// 
    public void kickme()
    {
        //knockmeout();
        kickdelay = usualkick;
        xs *= 2;
        ys = -9;
    }//kicked

    ///////////////////////////
    private void trytoattack()
    {
        if (attackdelay > 0) { return;}
        //once again sacrificing readability to speed
        myWorld mg = (myWorld) getWorld();

        int aa = (getX() - mg.PlayerX);
        int bb = (getY()- mg.PlayerY);
        if (  Math.sqrt(aa*aa+bb*bb) < 120 ) { attackdelay = 90; sparkdelay = 40; }

    }//trytottack

    private void slowdown()
    {
        xs *= 0.91;
        ys *= 0.91;
        xa = 0;
        ya = 0;
    }//slowdo

    private void move()
    {
        xs += xa;
        if (xs> maxrun) {xs = maxrun;}
        if (xs<-maxrun) {xs = -maxrun;}

        ys += ya;
        if (ys > maxrun) {ys = maxrun;}
        if (ys < -maxrun) {ys = -maxrun;}

        if (xs > 0) {dir = 0;}
        if (xs < 0) {dir = 16;}

        x += xs;
        y += ys;
    }//move

    private void followgoal()
    {
        int xx = goalx - getX();
        int yy = goaly - getY();
        float length =(float) Math.sqrt((xx*xx)+(yy*yy));

        xa = ( xx / length) *0.5f;
        ya = (yy/ length) *0.5f;
    }//

    private void hasreachedgoal()
    {
        if (!bGoalset) {return;}
        if (getX() > goalx+20 || getX() < goalx-20) {return;}

        if (getY() > goaly+20 || getY() < goaly-20) {return;}

        bGoalset = false;
        lookaround = 120;
    }//reachedgoal

    private void setgoal()
    {
        if (bGoalset) {return;}
        bGoalset = true;
        goalx =  Greenfoot.getRandomNumber(500)+50;
        // if (goalx > 600) {goalx = 70;}
        goaly = Greenfoot.getRandomNumber(400)+40;
    }//setgoal
    ////////////////////

    ///////animation

    private void setanim_default()
    {
        //  if (knockedout()) {return;}
        if (animno == 0 ) {return;}
        animno = 0;
        anim_speed = 0.0f; cur_frame = 0;  start_frame = 0;  end_frame = 0;
    }//anim default

    private void setanim_looking()
    {
        if (animno == 1 ) {return;}
        animno = 1;
        anim_speed = 0.09f; cur_frame = 0;  start_frame = 0;  end_frame = 9;
    }//looking

    private void setanim_sparking()
    {
        if (animno == 2 ) {return;}
        animno = 2;
        anim_speed = 0.5f; cur_frame = 4;  start_frame = 4;  end_frame = 5;
    } //sprikng

    private void setanim_hurt()
    {
        animno = 3;
        anim_speed = 0; cur_frame = 6;  start_frame = 6;  end_frame = 6;
    } //sprikng

    private void loadEdemonFrames()
    {
        if (eframes[0] != null) {return;}

        eframes[0] = new GreenfootImage("Boo1.PNG");
        eframes[1] = new GreenfootImage("Boo2.PNG");
        eframes[2] = new GreenfootImage("Boo3.PNG");
        eframes[3] = new GreenfootImage("Boo4.PNG");
        eframes[4] = new GreenfootImage("Boo6.PNG");
        eframes[5] = new GreenfootImage("Boo6.PNG");
        eframes[6] = new GreenfootImage("Boo5.PNG");

        eframes[0+16] = new GreenfootImage("Boo1.PNG");
        eframes[1+16] = new GreenfootImage("Boo2.PNG");
        eframes[2+16] = new GreenfootImage("Boo3.PNG");
        eframes[3+16] = new GreenfootImage("Boo4.PNG");
        eframes[4+16] = new GreenfootImage("Boo6.PNG");
        eframes[5+16] = new GreenfootImage("Boo6.PNG");
        eframes[6+16] = new GreenfootImage("Boo5.PNG");

        for (int i=(0+16); i<(6+17);i++)
        {
            eframes[i].mirrorHorizontally();
        }//next i

        //set looking animation
        lookanim[0] = eframes[0];
        lookanim[1] = eframes[3];
        lookanim[2] = eframes[1];
        lookanim[3] = eframes[1];
        lookanim[4] = eframes[3];
        lookanim[5] = eframes[0];
        lookanim[6] = eframes[3];
        lookanim[7] = eframes[2];
        lookanim[8] = eframes[2];
        lookanim[9] = eframes[3];

        lookanim[0+16] = eframes[0+16];
        lookanim[1+16] = eframes[3+16];
        lookanim[2+16] = eframes[1+16];
        lookanim[3+16] = eframes[1+16];
        lookanim[4+16] = eframes[3+16];
        lookanim[5+16] = eframes[0+16];
        lookanim[6+16] = eframes[3+16];
        lookanim[7+16] = eframes[2+16];
        lookanim[8+16] = eframes[2+16];
        lookanim[9+16] = eframes[3+16];

    }//edemonframes

    //////////////////////////////
    private void huntendboss()
    {
        endboss egod = (endboss) getOneIntersectingObject(endboss.class);

        if (egod != null
        && egod.stunned <= 0
            //&& !bKnockedOut
        && !bBroken
        )
        {
            egod.gethurt();
            xs = -xs;
            ys = -3;
            getWorld().addObject(new effect(), getX(),getY());
            Greenfoot.playSound("kick.wav");
            bBroken = true;
        }//endif
    }//void

    ////
}//electric demon

