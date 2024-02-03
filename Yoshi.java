import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * More or less an exact copy of Player...
 */
public class Yoshi extends Player
{
    int deaths = 0;
    boolean attacking = false;

    int deadness = 190;

    float x = 160;
    float y = 100;
    float xs;
    float ys;

    int Y;

    boolean bpicked = false;
    boolean canDoubleJump;

    int jump_delay = 0;
    boolean second_jump = false;
    int doubleJumping = 0;
    int attack_delay = 0;

    boolean left;
    boolean right;

    float maxspeed = 3.5f;

    boolean canjump = false;

    static GreenfootImage[] frames = new GreenfootImage[32];
    //static GreenfootImage[] mframes = new GreenfootImage[9]; //mirrored frames
    float cur_frame=0;
    float anim_speed= 0.1f; //f needed or java thinks its a double
    float start_frame=0;
    float end_frame=0;
    int animno = 0;
    int dir = 0;
    int animdelay = 0;
    int hurtdelay = 0;

    public boolean added = false;

    int t = 255;

    static boolean GODMODE = false;

    boolean dead = false;
    int health = 3;

    int damage = 1;

    int h2=0;

    float jumpstr = 7;

    int f;

    private int scrollSpeed; 

    private GreenfootImage scrollingImage; 
    private GreenfootImage maskImage;
    private int scrollPosition;

    private myWorld mworld;

    public Yoshi(){
        mworld.bpicked = false;
        scrollingImage = getScrollingImage(mworld.background.getWidth(), mworld.background.getHeight()); 
        maskImage = getMaskImage(mworld.mask.getWidth(), mworld.mask.getHeight());
        getImage().scale(32,32);
        if (frames[0] == null){   //just in case
            //loading up frames
            frames[0] = new GreenfootImage("Yoshi1.PNG"); //stand
            frames[1] = new GreenfootImage("Yoshi1.PNG"); // run
            frames[2] = new GreenfootImage("Yoshi3.PNG"); // run
            frames[3] = new GreenfootImage("Yoshi2.PNG"); // run
            frames[4] = new GreenfootImage("Yoshi4.PNG"); // jump
            frames[5] = new GreenfootImage("Yoshi2.PNG"); //brake fall
            frames[6] = new GreenfootImage("Yoshi6.PNG"); //eat - after attack
            frames[7] = new GreenfootImage("Yoshi4.PNG"); //jump
            frames[8] = new GreenfootImage("Yoshi5.PNG"); 
            frames[9] = new GreenfootImage("Yoshi5.PNG");
            frames[10] = new GreenfootImage("Yoshi.PNG"); // no player

            //mirrored frames //just add 16
            frames[0+16] = new GreenfootImage("Yoshi1.PNG");
            frames[1+16] = new GreenfootImage("Yoshi1.PNG");
            frames[2+16] = new GreenfootImage("Yoshi3.PNG");
            frames[3+16] = new GreenfootImage("Yoshi2.PNG");
            frames[4+16] = new GreenfootImage("Yoshi4.PNG");
            frames[5+16] = new GreenfootImage("Yoshi2.PNG");
            frames[6+16] = new GreenfootImage("Yoshi6.PNG");
            frames[7+16] = new GreenfootImage("Yoshi4.PNG");
            frames[8+16] = new GreenfootImage("Yoshi5.PNG");
            frames[9+16] = new GreenfootImage("Yoshi5.PNG");
            frames[10+16] = new GreenfootImage("Yoshi.PNG");

            for (int i = 16; i<9+17;i++)
            {
                frames[i].mirrorHorizontally();
            } //next i
        }//endif
    }//constructor

    protected void addedToWorld(World world) 
    {
        mworld = (myWorld) world;

        mworld.playerHealth = health;

        x = getX();
        y = getY();

        added=true;

        Y = getY();
    }//addedtoworld 

    private void animate()
    {
        if (animdelay > 0) {animdelay--;}
        if (hurtdelay > 0) {hurtdelay--;}
        if (jump_delay > 0) {jump_delay--;}
        if (attack_delay > 0) {attack_delay--;}

        cur_frame += anim_speed;
        if (cur_frame > end_frame) { cur_frame = start_frame;}

        this.setImage(frames[(int)cur_frame + dir]);
    }//animate

    //animations

    private void setanim_without()
    {
        if (animno == 10 || canjump == false || animdelay > 0) {return;}
        animno = 10;
        anim_speed = 0; cur_frame = 10;  start_frame = 10;  end_frame = 10;
    }//stand

    private void setanim_stand()
    {
        if (animno == 0 || animdelay > 0) {return;}
        animno = 0;
        anim_speed = 0; cur_frame = 0;  start_frame = 0;  end_frame = 0;
    }//stand

    private void setanim_run()
    {
        if (animno == 1 ||  animdelay > 0) {return;}
        animno = 1;
        anim_speed = 0.2f; cur_frame = 1;  start_frame = 1;  end_frame = 5;
    }//run

    private void setanim_brake()
    {
        if (animno == 3 ||  animdelay > 0) {return;}
        animno = 3;
        anim_speed = 0; cur_frame = 5;  start_frame = 5;  end_frame = 5;
    }

    private void setanim_slide()
    {
        if (animno == 10 ||  animdelay > 0) {return;}
        animno = 10;
        anim_speed = 0.2f; cur_frame = 5;  start_frame = 5;  end_frame = 5;
    }

    private void setanim_jump()
    {
        if (animno == 2 || animdelay > 0) {return;}
        animno = 2;
        anim_speed = 0; cur_frame = 2;  start_frame = 2;  end_frame = 2;
    }//jump

    private void setanim_fall()
    {
        if (animno == 5 || canjump == true || animdelay > 0) {return;}
        animno = 5;
        anim_speed = 0; cur_frame = 5;  start_frame = 5;  end_frame = 5;
    }//fall

    private void setanim_kick()
    {
        if (animno == 4) {return;}
        animno = 4;
        anim_speed = 0.5f; cur_frame = 8;  start_frame = 9;  end_frame = 9;
    }//jump

    private void setanim_hurt()   
    {
        if (animno == 6) {return;}
        animno = 6;
        anim_speed = 0.4f; cur_frame = 6;  start_frame = 6;  end_frame = 8;
    }//fall

    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(mworld.ThisLevel > 20 && !mworld.Arcade)
        {
            canDoubleJump = true;
        }

        findBrick();

        if(mworld.Scrolling==true)
            Scrolling();

        if(mworld.Arcade==true)
        {
            jumpstr=8;     
        }

        if(animno!=4 && animno!=10)
        {
            getImage().scale(40,40);
        }

        if(animno==4)
        {
            getImage().scale(60,40);
        }

        if(animno==10)
        {
            getImage().scale(34, 34);
        }

        if(mworld.Fire & mworld.getObjects(fireball.class).isEmpty())
        {
            throwFire();
        }

        counter();

        if (health <= 0)
        {
            dead = true;
            mworld.deaths++;
            mworld.deadPlayer=true;

            Greenfoot.playSound("death.wav");

            getWorld().addObject(new defeat(), getX(),getY());
            getWorld().removeObject(this);
        }//health ran out
        if (dead) {return;}

        if(deadness>0 && mworld.deadPlayer==true)
        {
            deadness--;
        }
        else
        {
            mworld.deadPlayer=false;
            deadness=190;
        }

        getX();
        animate();

        if(mworld.bpicked == false)
        {
            setanim_without();
        }

        else if(mworld.bpicked == true)
        {
            if (Math.abs(xs) < 0.3) {setanim_stand();}

            if ( !Greenfoot.isKeyDown("left") && 
            !Greenfoot.isKeyDown("right") &&  
            !mworld.isIce(getX()-5, getY()+16) &&
            Math.abs(xs) > 0.3  )
            {setanim_brake();
                xs = 0;
            }

            if ( !Greenfoot.isKeyDown("left") && 
            !Greenfoot.isKeyDown("right") &&  
            mworld.isIce(getX()-5, getY()+16) &&
            dir == 0 &&
            Math.abs(xs) > 0.3  )
            {setanim_brake();
                xs += 0.4f;
            }

            if ( !Greenfoot.isKeyDown("left") && 
            !Greenfoot.isKeyDown("right") &&  
            mworld.isIce(getX()+5, getY()+16) &&
            dir == 16 &&
            Math.abs(xs) > 0.3  )
            {setanim_brake();
                xs -=0.4f;
            }
            //controls
            //run
            if(Greenfoot.isKeyDown("down") && Greenfoot.isKeyDown("left"))
            {
                dir = 16;
                setanim_stand();
            }

            if (Greenfoot.isKeyDown("left") && xs > -maxspeed && !Greenfoot.isKeyDown("down"))
            {            xs -= 0.78; setanim_run(); 
                if (animdelay <= 0 ) {dir = 16;} 
            }//endif

            if(Greenfoot.isKeyDown("down") && Greenfoot.isKeyDown("right"))
            {
                dir = 0;
                setanim_stand();
            }

            if(Greenfoot.isKeyDown("down"))
            {   
                if(xs < maxspeed && mworld.isGround(getX()-14, getY()+14))
                {
                    dir = 0;
                    setanim_slide();
                    xs += 3;
                    xs = xs+0.05f;
                }
                if(xs > -maxspeed && mworld.isGround(getX()+14, getY()+14))
                {
                    dir = 16;
                    setanim_slide();
                    xs -= 3;
                    xs = xs+0.05f;
                }
            }

            if(mworld.isIce(getX() - 5, getY()+14))
            {
                dir = 0;
                setanim_slide();
                xs += 3;
                xs = xs+0.05f;
            }
            if(mworld.isIce(getX()+5, getY()+14))
            {
                dir = 16;
                setanim_slide();
                xs -= 3;
                xs = xs+0.05f;
            }

            if (Greenfoot.isKeyDown("right") && xs < maxspeed && !Greenfoot.isKeyDown("down"))
            {            xs += 0.78; setanim_run(); 
                if (animdelay <= 0 ) {dir = 0;}
            }//endif

            //jump
            if(!mworld.onLadder((int)x, (int)y+12))
            {
                if(!canDoubleJump)
                {
                    if (Greenfoot.isKeyDown("up") && canjump)//&& ys > -4 )
                    {
                        if(mworld.gravity > 0){
                            ys = -jumpstr; canjump = false;}
                        else if(mworld.gravity < 0)  
                            ys = +jumpstr; canjump = false;
                        setanim_jump();
                        if(!mworld.isWater(getX(), getY()))
                        {
                            Greenfoot.playSound("40.wav");
                        }
                    }
                }

                else if(canDoubleJump)
                {
                    if(Greenfoot.isKeyDown("up") && doubleJumping == 0)//&& ys > -4 )
                    {         
                        ys = -jumpstr;
                        canjump = false;
                        setanim_jump();

                        doubleJumping = 1;
                        jump_delay = 15;

                        if(!mworld.isWater(getX(), getY()))
                        {
                            Greenfoot.playSound("40.wav");
                        }
                    }
                    if(!Greenfoot.isKeyDown("up") && doubleJumping != 0)
                    {
                        second_jump = true;
                    }
                    if(Greenfoot.isKeyDown("up") && second_jump && doubleJumping == 1)
                    {
                        ys = -jumpstr;
                        canjump = false;
                        setanim_jump();

                        if(jump_delay <= 0)
                        {
                            doubleJumping = 2;
                            jump_delay = 15;

                            if(!mworld.isWater(getX(), getY()))
                            {
                                Greenfoot.playSound("40.wav");
                            }
                        }
                    }
                }
            }
            else
            {
                if (Greenfoot.isKeyDown("up") && canhop)//&& ys > -4 )
                {
                    if(doubleJumping == 0)//&& ys > -4 )
                    {         
                        ys = -jumpstr;
                        canjump = false;
                        setanim_jump();

                        doubleJumping = 1;
                        jump_delay = 15;

                        if(!mworld.isWater(getX(), getY()))
                        {
                            Greenfoot.playSound("40.wav");
                        }
                    }
                    if(doubleJumping != 0)
                    {
                        second_jump = true;
                    }
                    if(second_jump && doubleJumping == 1)
                    {
                        ys = -jumpstr;
                        canjump = false;
                        setanim_jump();

                        if(jump_delay <= 0)
                        {
                            doubleJumping = 2;
                            jump_delay = 15;

                            if(!mworld.isWater(getX(), getY()))
                            {
                                Greenfoot.playSound("40.wav");
                            }
                        }
                    }
                }
                if(!Greenfoot.isKeyDown("up") && doubleJumping == 2 && jump_delay <= 0)
                {
                    doubleJumping = 0;
                    second_jump = false;
                    canhop = true;
                }

                if(!Greenfoot.isKeyDown("up") && !Greenfoot.isKeyDown("down"))
                {
                    ys = 0;
                    setanim_stand();

                    if(mworld.onLadder((int)x, (int)y+11))
                    {
                        canhop = false;
                    }
                    else
                    {
                        doubleJumping = 0;
                        second_jump = false;
                        canhop = true;
                    }
                }

                if(Greenfoot.isKeyDown("up") && !canhop)
                {
                    if(mworld.onLadder((int)x, (int)y+11))
                    {
                        canhop = false;
                        ys = -1;
                    }
                    else
                    {
                        ys = 0;
                        doubleJumping = 0;
                        second_jump = false;
                        canhop = true;
                    }
                    setanim_run();
                }
                if(Greenfoot.isKeyDown("down"))
                {
                    canhop = false;
                    ys = 1;
                    setanim_run();
                }
            }

            if(Greenfoot.isKeyDown("space") && attack_delay <= 0 && !attacking)
            {
                tongueAttack();
            }

            if(Greenfoot.isKeyDown("space") && attack_delay <= 20 && attack_delay > 0)
            {
                attacking = false;
            }

            if(!Greenfoot.isKeyDown("space") && attack_delay <= 5)
            {
                attack_delay = 0;
                attacking = false;
            }

            if(canjump)
            {
                doubleJumping = 0;
                second_jump = false;
            }
        }

        //movement physics etc
        x += xs;
        y += ys;

        xs *=  0.91;

        if (ys > 2 ) {setanim_fall(); canjump = false;}
        if (ys < 5 ) {ys += 0.3;}

        if (y > 460)
        {
            scrollPosition = 0;
            paint(-scrollPosition);
            x = mworld.startX;
            y = mworld.startY;
            //   y = 460; ys = 0; canjump = true;
        }//endif

        if(mworld.Arcade==false)
        {
            if (y < 30) {y=30;}

            if (x > (640-25)) { x = (640-25); }
            if (x < 25) {x = 25;}
        }
        else if(mworld.Arcade==true)
        {
            if(y < 25)
            {
                y = 25;
            }

            if(x > (640-25))
            {
                x = 25;
            }
            if(x < 25)
            {
                x = 640-25;
            }
        }

        up();
        findElevator();

        experiment();

        if(mworld.bpicked==true)
        {
            attack();
        }

        setLocation((int)x,(int)y-8);

        if(mworld.bpicked==true)
        {
            mworld.setPlayerCoords(getX(),getY() );
            gethurt();
        }

        LevelNine();

        if(attacking)
        {
            setanim_kick();
        }
    }//act
    ///////////////////////////////////////   

    /////////////////////////////////////////////

    public void hurtPlayer()
    {
        if (hurtdelay > 0 && animdelay > 0 ) {return;}   

        ys = 0;
        setanim_hurt();
        hurtdelay = 60;
        animdelay = 30;
        health--;
        mworld.playerHealth = health;
        getWorld().addObject(new effect(), getX(),getY());
        Greenfoot.playSound("shock.wav");
    }//hurtPlayer

    private void gethurt()
    {
        enemy mutantleg = (enemy) getOneIntersectingObject(enemy.class);
        // KoopaTroopa ducky = (KoopaTroopa) getOneIntersectingObject(KoopaTroopa.class);
        KoopaTroopa duckeh = (KoopaTroopa) getOneObjectAtOffset(14, -1, KoopaTroopa.class);

        if(!attacking)
        {
            if(mutantleg instanceof Pushable)
            {
            }

            else
            {
                if (mutantleg != null
                    //&& mutantleg.stunned <= 0
                    //&& !mutantleg.bKnockedOut
                && hurtdelay <= 0
                    //&& mutantleg.dmgdelay <= 0
                && animdelay <= 0 
                && mutantleg.canattack()
                && ys > -2
                && getY() >= mutantleg.getY()
                )

                { 
                    ys = 0;
                    setanim_hurt();
                    hurtdelay = 60;
                    animdelay = 30;
                    health--;
                    mworld.playerHealth = health;
                    getWorld().addObject(new effect(), getX(),getY());
                    Greenfoot.playSound("shock.wav");
                }//endif

                if (mutantleg != null  
                && hurtdelay <= 0
                && animdelay <= 0   
                && mutantleg.canattack()
                && !mutantleg.attackbelow()
                && ys < 0
                && getY() >= mutantleg.getY()
                )
                {
                    ys = 0;
                    setanim_hurt();
                    hurtdelay = 60;
                    animdelay = 30;
                    health--;
                    mworld.playerHealth = health;
                    getWorld().addObject(new effect(), getX(),getY());
                    Greenfoot.playSound("shock.wav");
                }//hurtb
            }
        }

        else if(attacking)
        {
            if(mutantleg instanceof Pushable)
            {
            }
            else
            {
                if (mutantleg != null 
                && canjump
                && !mutantleg.bKnockedOut
                )

                {
                    mutantleg.health = 1;
                    hurtdelay = 17;

                    mutantleg.xs = this.xs *1.3f;
                    //  mutantleg.ys = -6;
                    if(dir==0)
                    {
                        mutantleg.kickmeplus();
                    }
                    else if(dir==16)
                    {
                        mutantleg.kickmeminus();
                    }
                    getWorld().addObject(new effect(), mutantleg.getX(),getY());
                    Greenfoot.playSound("kick.wav");
                    //  ys = -2;
                }
            }
        }

        if (mworld.isHurting(getX(),getY()) && hurtdelay <= 0 && animdelay <= 0)
        {
            setanim_hurt(); 

            health--;
            mworld.playerHealth = health;

            getWorld().addObject(new effect(), getX(),getY());
            hurtdelay = 60;
            animdelay = 30;
            Greenfoot.playSound("shock.wav");
        }
        ////////
        if(duckeh != null)
        {
        }

        if (mworld.isHurting(getX(),getY()) && hurtdelay <= 0 && animdelay <= 0)
        {
            setanim_hurt(); 

            health--;
            mworld.playerHealth = health;

            getWorld().addObject(new effect(), getX(),getY());
            hurtdelay = 60;
            animdelay = 30;
            Greenfoot.playSound("shock.wav");
        }
    }//gethurt////////

    private void up()
    {       
        Shroom mutantleg = (Shroom) getOneIntersectingObject(Shroom.class);

        if (mutantleg != null)
        {
            Greenfoot.playSound("keycollect.wav");
            health++;
            mworld.playerHealth = health;
            mworld.removeObject(mutantleg);
        }
    }//attack

    private void attack()
    {
        if (hurtdelay > 5) {return;}

        enemy mutantleg = (enemy) getOneIntersectingObject(enemy.class);

        if (mutantleg != null
        && ys > 0
        && mutantleg.canbeattacked()
        && mutantleg.attackabove()
        && getY() < mutantleg.getY()
        && !mutantleg.knockedout()
        )
        {
            ys = -5;
            mutantleg.ys = 0;
            hurtdelay = 17;
            mutantleg.damageStun(damage);
            getWorld().addObject(new effect(), getX(),getY()+16);
            Greenfoot.playSound("knock.wav");
        }

        barrel Barrel = (barrel) getOneObjectAtOffset(0,-25,barrel.class);
        if (Barrel != null)
        {
            ys = 1;
            hurtdelay = 17;
            Barrel.byebye = 1;
            getWorld().addObject(new effect(), getX(),getY()-16);
            Greenfoot.playSound("knock.wav");
        }

        mutantleg = (enemy) getOneIntersectingObject(enemy.class);
        if (mutantleg != null
        && ys < 0 
        && getY() > mutantleg.getY()
        && mutantleg.canbeattacked()
        && mutantleg.attackbelow()
        && !mutantleg.bKnockedOut
        )
        {
            ys = 1;
            hurtdelay = 17;
            mutantleg.ys = -5;
            mutantleg.damageStun(1);
            getWorld().addObject(new effect(), getX(),getY()-16);
            Greenfoot.playSound("knock.wav");
        }

        kinghammer Chicken = (kinghammer) getOneIntersectingObject(kinghammer.class);
        if (Chicken != null 
        && canjump
        && Chicken.canbekicked()
        )

        {
            animdelay = 30;
            setanim_kick();
            //   mutantleg.health = 1;
            hurtdelay = 17;

            Chicken.xs = this.xs *1.3f;

            if(dir==0)
            {
                Chicken.kickmeplus();
            }
            else if(dir==16)
            {
                Chicken.kickmeminus();
            }
        }

        rock Mc = (rock) getOneIntersectingObject(rock.class);

        if (Mc != null 
        && canjump
        && Mc.canbekicked()
        )

        {
            animdelay = 30;
            setanim_kick();
            //   mutantleg.health = 1;
            hurtdelay = 17;

            Mc.xs = this.xs *1.3f;

            Mc.kickme();
        }

        mutantleg = (enemy) getOneIntersectingObject(enemy.class);
        if (mutantleg != null 
        && canjump
        && mutantleg.canbekicked()
        )

        {
            animdelay = 30;
            setanim_kick();
            //   mutantleg.health = 1;
            hurtdelay = 17;

            mutantleg.xs = this.xs *1.3f;
            //  mutantleg.ys = -6;
            if(dir==0)
            {
                mutantleg.kickmeplus();
            }
            else if(dir==16)
            {
                mutantleg.kickmeminus();
            }
            getWorld().addObject(new effect(), mutantleg.getX(),getY());
            Greenfoot.playSound("kick.wav");
            //  ys = -2;
        }
        ///////
        if (hurtdelay > 5) {return;}

        KoopaTroopa ducky = (KoopaTroopa) getOneIntersectingObject(KoopaTroopa.class);

        if (ducky != null
        && ys > 0
        && ducky.canbeattacked()
        && ducky.attackabove()
        && getY() < ducky.getY()
        && !ducky.knockedout()
        )
        {
            ys = -10;
            //            ducky.ys = 0;
            hurtdelay = 0;
            ducky.damageStun(0);
            getWorld().addObject(new effect(), getX(),getY()+16);
            Greenfoot.playSound("knock.wav");
            //            ducky.gravity();
        }

        ducky = (KoopaTroopa) getOneIntersectingObject(KoopaTroopa.class);
        if (ducky != null
        && ys < 0 
        && getY() > ducky.getY()
        && ducky.canbeattacked()
        && ducky.attackbelow()
        && !ducky.bKnockedOut
        )
        {
            ys = 0.05f;
            hurtdelay = 0;
            //            ducky.ys = -5;
            getWorld().addObject(new effect(), getX(),getY()-16);
            Greenfoot.playSound("knock.wav");
            //             ducky.gravity();
        }

        ducky = (KoopaTroopa) getOneIntersectingObject(KoopaTroopa.class);
        if (ducky != null 
        && canjump
        && ducky.canbekicked()
        )

        {
            animdelay = 30;
            setanim_kick();
            //   ducky.health = 1;
            hurtdelay = 17;

            ducky.xs = this.xs *1.3f;
            //  ducky.ys = -6;
            if(dir==0)
            {
                ducky.kickmeplus();
            }
            else if(dir==16)
            {
                ducky.kickmeminus();
            }
            getWorld().addObject(new effect(), ducky.getX(),getY());
            Greenfoot.playSound("kick.wav");
            //  ys = -2;
        }

    }//attack
    /////////////////////////////////////// 

    public void LevelNine()
    {
        if(mworld.ThisLevel==9)
        {
            String key = Greenfoot.getKey();
            if(key == ("down"))
            {
                mworld.nextLevel();
            }
        }
    }

    public void tongueAttack()
    {
        attacking = true;
        setanim_kick();
        attack_delay = 30;
    }

    private void experiment()
    {

        if (mworld.isJumpPad(getX(), getY()+14) )
        { ys = -13;}///
        if (mworld.isGround(getX(), getY()+16) || mworld.isIce(getX(), getY()+16))//ground.equals(Color.BLACK))
        { ys = 0; canjump = true; }//x =320; y = 240;}
        //ys = 0; y -= 1; }
        //myWorld.paintBg(getX(), getY());

        if(mworld.isWater(getX(), getY()) && !mworld.isGround(getX(), getY()+16))
        {
            ys -= 0.2;

            canjump = true;
            jumpstr = 2;

            if(xs>0)
            {
                setRotation(10);
            }
            if(xs<0)
            {
                setRotation(-10);
            }
            else if(xs == 0)
            {
                setRotation(0);
            }

            maxspeed = 1;
        }

        if(!mworld.isWater(getX(), getY()) && !mworld.Arcade)
        {
            jumpstr = 6;
            setRotation(0);
        }

        if(mworld.isWater(getX(), getY()) && mworld.isGround(getX(), getY()+16))
        {
            maxspeed = 1;
            setRotation(0);
        }

        if(!mworld.isWater(getX(), getY()))
        {
            maxspeed = 3;
        }

        if (mworld.isGround(getX(), getY()+14) || mworld.isIce(getX(), getY()+14))
        { y -= 1;}///

        if (mworld.isGround(getX(), getY()-14) || mworld.isIce(getX(), getY()-14))
        { y += 1; 
            if (ys < 0){ys = 0;}
        }///

        if (mworld.isGround(getX()+14, getY()) || mworld.isIce(getX()+14, getY()))
        { x -= 1;
            if (xs > 0) {xs = 0;}
        }////

        if (mworld.isGround(getX()-14, getY()) || mworld.isIce(getX()-14, getY()))
        { x += 1;
            if (xs < 0) {xs = 0;}
        }///

    }//experiment

    private void findBrick()
    {
        barrel mg = null;
        Pushable mh = null;

        mg = (barrel) getOneIntersectingObject(barrel.class);
        mh = (Pushable) getOneIntersectingObject(Pushable.class);

        if (mg!= null && !mworld.isGround(getX(), getY()-14) && !mworld.isIce(getX(), getY()-14)&& !mworld.isGround(getX(), getY()+14) ){
            // mworld.paintBg(mg.getX(),mg.getY());

            if (
            (getY())  < (mg.getY()- mg.yOffset) 
            )
            {
                y = mg.getY() - mg.yOffset;
                ys = 0;
                canjump = true;

            }//ys

        }//mgnull

        if (mh!= null && !mworld.isGround(getX(), getY()-14) && !mworld.isIce(getX(), getY()-14)&& !mworld.isGround(getX(), getY()+14) ){
            // mworld.paintBg(mg.getX(),mg.getY());

            if (
            (getY())  < (mh.getY()- mh.getImage().getHeight() /2 - 5) 
            )
            {
                y = mh.getY() - mh.getImage().getHeight() /2 -5;
                ys = 0;
                canjump = true;

            }//ys

        }//mgnull
    }//void

    private void findElevator()
    {
        elevator mg = null;

        mg = (elevator) getOneIntersectingObject(elevator.class);

        if (mg!= null && !mworld.isGround(getX(), getY()-14) && !mworld.isGround(getX(), getY()+14) && y>30){
            // mworld.paintBg(mg.getX(),mg.getY());

            if(y<=30)
            {
            }
            else if (
            //   ys > 0  
            mg.isActive()
            && ys > -1
            && (getY())  < (mg.getY()- mg.yOffset) 
            )
            {
                y = mg.getY()- mg.yOffset;
                x = x + mg.cX;
                y = y + mg.cY;
                ys = 0;
                canjump = true;

            }//ys

        }//mgnull
    }//void

    public void counter()
    {   
        if(health>=3 && mworld.getObjects(lifecounter3.class).isEmpty())
        {
            mworld.addObject(new lifecounter3(), 607, 22);
            mworld.removeObjects(mworld.getObjects(lifecounter0.class));
        }

        if(health==2 && mworld.getObjects(lifecounter2.class).isEmpty())
        {
            mworld.addObject(new lifecounter2(), 607, 22);
            mworld.removeObjects(mworld.getObjects(lifecounter3.class));
        }

        if(health==1 && mworld.getObjects(lifecounter1.class).isEmpty())
        {
            mworld.addObject(new lifecounter1(), 607, 22);
            mworld.removeObjects(mworld.getObjects(lifecounter2.class));
        }

        if(health==0 && mworld.getObjects(lifecounter0.class).isEmpty())
        {
            mworld.addObject(new lifecounter0(), 607, 22);
            mworld.removeObjects(mworld.getObjects(lifecounter1.class));
        }
    }

    public void throwFire()
    {
        if(Greenfoot.isKeyDown("space") && dir==0)
        {
            mworld.addObject(new fireball(0), getX()+17, getY() +3);
        }
        if(Greenfoot.isKeyDown("space") && dir==16)
        {
            mworld.addObject(new fireball(16), getX()-17, getY() +3);
        }
    }

    /** 
     * Paints an image. 
     */

    private void paint(int position) 
    { 
        mworld.background.drawImage(scrollingImage, position, 0); 
        mworld.mask.drawImage(maskImage, position, 0);
        if(position > 0) { 
            mworld.background.drawImage(scrollingImage, position - scrollingImage.getWidth(), 0);
            mworld.mask.drawImage(maskImage, position - maskImage.getWidth(), 0);
        } 
        else { 
            mworld.background.drawImage(scrollingImage, position + scrollingImage.getWidth(), 0);
            mworld.mask.drawImage(maskImage, position + maskImage.getWidth(), 0);
        } 
    } 

    /** 
     * Returns a background image with the given dimensions. 
     */ 
    private GreenfootImage getScrollingImage(int width, int height) 
    { 
        GreenfootImage Image = new GreenfootImage(width, height);

        for(int l = 0; l < width; l += mworld.background.getWidth()) { 
            for(int m = 0; m < height; m += mworld.background.getHeight()) { 
                Image.drawImage(mworld.background, l, m); 
            } 
        } 

        return Image; 
    }

    /** 
     * Returns a mask image with the given dimensions. 
     */
    private GreenfootImage getMaskImage(int width, int height) 
    { 
        GreenfootImage image = new GreenfootImage(width, height);

        for(int n = 0; n < width; n += mworld.mask.getWidth()) { 
            for(int o = 0; o < height; o += mworld.mask.getHeight()) { 
                image.drawImage(mworld.mask, n, o); 
            } 
        }
        return image; 
    }

    public void Scrolling()
    {
        if(mworld.Scrolling==true & x >= 640/2+50 & xs >0 && !mworld.isGround(getX()+14, getY()) && scrollPosition < mworld.background.getWidth())
        {
            x = x - xs;
            scrollSpeed = (int)xs*2;
            scrollPosition += scrollSpeed; 

            paint(-scrollPosition);
            mworld.doorPos -= scrollSpeed;
        }

        /////////////////////MOVING LEFT SCROlL

        if(mworld.Scrolling==true & x <= 640/2-50 & xs <0 && !mworld.isGround(getX()-14, getY()) && scrollPosition > 3)
        {
            x = x + -xs;
            scrollSpeed = -((int)xs*2);
            scrollPosition -= scrollSpeed; 

            paint(-scrollPosition);
            mworld.doorPos +=scrollSpeed;
        }
    }

    public void DoorScroll()
    {
        if(mworld.ThisLevel==14)
        {
            //             if(scrollPosition >=1270)
            //             {
            //                 mworld.t=255;
            //             }
            //             else
            //             {
            //                 mworld.t=0;
            //             }
        }
    }

    public void Thwomped()
    {
        setanim_hurt();
        hurtdelay = 60;
        animdelay = 30;
        health--;
        getWorld().addObject(new effect(), getX(),getY());
        Greenfoot.playSound("shock.wav");
    }
}