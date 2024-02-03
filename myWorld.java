import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.util.List;
import java.lang.Object;
import java.util.Calendar;
import javax.swing.*;
import java.util.Iterator;

public class myWorld extends World
{
    static boolean FireMode = false;
    static boolean Easy = false;
    static boolean Fire = false;

    int stored_level;

    boolean listen_for_input = false;
    int playerHealth;
    int score = 0;

    String level = "";

    int dest = 0;

    public int gravity = 1;

    public static boolean bpicked;

    public int c = 0;
    public int doorX;

    public int deaths = 0;

    boolean death = false;

    int kingHealth;

    public int t = 255;
    public int doorPos;

    public int kills = 0;

    public boolean deadPlayer = false;

    boolean left = true;

    GreenfootSound music = new GreenfootSound("level.mid");
    GreenfootSound music2 = new GreenfootSound("2.mid");
    GreenfootSound music3 = new GreenfootSound("wingcap.mid");

    GreenfootSound Music = new GreenfootSound("underground.mid");
    GreenfootSound MUSIC = new GreenfootSound("water.mid");
    GreenfootSound BossMusic = new GreenfootSound("metalcap.mid");
    GreenfootSound Boss2Music = new GreenfootSound("boss1.mid");

    public boolean moosic = true;

    public boolean Scrolling = false;

    int ThisRound = 0;

    boolean Clsc = false;
    boolean Arcade = false;

    static boolean Harder = false; 

    static GreenfootImage background;
    static GreenfootImage mask; 
    
    boolean cancelled = false;

    static GreenfootImage codes = new GreenfootImage("codes.gif");  
    static Color ground;
    static Color aicolor;
    static Color acidcolor;
    static Color hurtcolor; static Color althurtcolor;
    static Color jumpcolor;
    static Color pipecolor;
    static Color icecolor;
    static Color watercolor;

    static int ThisLevel = 0;
    int soundfile = 1;
    static int MaxLevel = 999;

    boolean ending = false;
    boolean paused = true;

    static public int PlayerX, PlayerY; 
    static public int fireX, fireY;

    public int objectX, objectY;

    static public int startX, startY;

    int time = 0;

    private myWorld mworld;

    float x = 160;
    float y = 100;
    int Trans = 0;

    int STORED_ROUND = 2;
    boolean addObjects = true;
    int waitingTime = 200;

    public myWorld()
    {    

        super(640, 480, 1, false);
        setvals();
        Greenfoot.setSpeed(50);
        setPaintOrder(Bubble.class, Score.class, HealthBar.class, FireDude.class, effect.class, thwomp.class, Fish.class, Player.class, Yoshi.class, fireball.class, firedemon.class, edemon.class, hazard.class, elevator.class, rock.class, KingBoo.class, enemy.class, barrel.class, door.class, Life.class);
        restartGame();
        FireMode=false;
        Fire=false;
        Clsc=false;
        Easy=false;
        Harder=false;
    }

    public void act()
    {
        if(!paused)
        {
            if(moosic)
            {
                music(soundfile);
            }
        }

        Scrolling();

        if(ThisLevel==27)
        {
            ending = true;
            ending();
        }

        if(listen_for_input)
        {
            listen();
        }

        if(Arcade)
            ArcadeRound();

        if(Arcade && addObjects)
        {
            pause(200);
        }

        shortcuts();
    } 

    public void started()
    {
        paused = false;
    }

    public void stopped()
    {
        paused = true;
        stopAllAudio();
    }

    private void shortcuts()
    {
        //         if(Greenfoot.isKeyDown("U") && Greenfoot.mouseClicked(this))
        //         {
        //             reverseGravity();
        //         }

        if(Greenfoot.isKeyDown("N") && Greenfoot.mouseClicked(this) && ThisLevel > 0 && ThisLevel < 99)
        {
            nextLevel();
        }
        if(Greenfoot.isKeyDown("space") && Greenfoot.mouseClicked(this))
        {
            Greenfoot.delay(20);
            try
            {
                JOptionPane pane = new JOptionPane(null);
                level = pane.showInputDialog("Enter a level number.");
                setLevel(Integer.parseInt(level));
            }
            catch(Exception ex)
            {
                if(level.equals("~"))
                {
                    stored_level = ThisLevel;
                    setLevel(-5);
                }
                else
                    return;
            }
        }
        if(Greenfoot.isKeyDown("2") && Greenfoot.mouseClicked(this))
        {
            Greenfoot.delay(20);
            try
            {
                JOptionPane pane = new JOptionPane(null);
                String difficulty = pane.showInputDialog("Select a difficulty. Level will reset upon entry.", "Easy, Hard, or Fire. (Not case sensitive)");
                difficulty.toLowerCase();
                if(difficulty.equals("hard"))
                {
                    Harder = true;
                    Fire = false;
                    Easy = false;
                }
                if(difficulty.equals("easy"))
                {
                    Harder = false;
                    Fire = false;
                    Easy = true;
                }
                if(difficulty.equals("fire"))
                {
                    Harder = false;
                    Fire = true;
                    Easy = false;
                }
            }
            catch(Exception ex)
            {
                return;
            }
            restartLevel();
        }
        if(Greenfoot.isKeyDown("R") && ThisLevel > 0)
        {
            restartLevel();
        }
        if(Greenfoot.isKeyDown("P") && Greenfoot.mouseClicked(this) && ThisLevel > -2 && ThisLevel < 99)
        {
            ThisLevel--;
            setLevel(ThisLevel);
        }
        if(ThisLevel>10 & ThisLevel != 20 & ThisLevel !=99 & Greenfoot.isKeyDown("1") && getObjects(Yoshi.class).isEmpty())
        {
            addObject(new Yoshi(), PlayerX, PlayerY);
        }

    }

    private void setvals()
    {
        ground = codes.getColorAt(1,1);
        aicolor = codes.getColorAt(22,1);
        acidcolor = codes.getColorAt(40,1);
        hurtcolor = codes.getColorAt(57,1);
        althurtcolor = new Color(255,255,0);
        jumpcolor = codes.getColorAt(68,1);
        pipecolor = new Color(128, 64, 0);
        icecolor = new Color(192, 192, 192);
        watercolor = new Color(128, 64, 0);
    }

    public boolean toggleDifficulty()
    {
        Harder = !Harder;
        return Harder;
    }

    public boolean toggleFireDifficulty()
    {
        Fire = !Fire;
        return Fire;
    }

    public void setPlayerCoords(int px, int py)
    {
        PlayerX = px;
        PlayerY = py;
    }

    public boolean radarsDestroyed()
    {
        return true;
    }

    public boolean isGround(Color mg)
    {
        if (ground.equals(mg)) 
        {
            return true;
        }
        return false;
    }

    public boolean isGround(int x, int y)
    {
        if(y<0)
        {
            y=0;
        }
        Color ez = mask.getColorAt(x, y);
        if(ground.equals(ez)) 
        {
            return true;
        }
        return false;
    }

    public boolean isWater(int x, int y)
    {
        if(y<0)
        {
            y=0;
        }
        Color ez = mask.getColorAt(x, y);
        if(watercolor.equals(ez)) 
        {
            return true;
        }
        return false;
    }

    public boolean isIce(int x, int y)
    {
        if(y<0)
        {
            y=0;
        }
        Color ez = mask.getColorAt(x, y);
        if(icecolor.equals(ez)) 
        {
            return true;
        }
        return false;
    }

    public boolean isPatrol(int x, int y)
    {
        if (x > 640-1 || x < 0 || y > 480-1 || y < 0) {return false;}

        Color ez = mask.getColorAt(x,y);
        if (aicolor.equals(ez)) 
        {return true;}
        return false;
    }//ground color

    public boolean isAcid(int x, int y)
    {
        if (x > 640-1 || x < 0 || y > 480-1 || y < 0) {return false;}

        Color ez = mask.getColorAt(x,y);
        if (acidcolor.equals(ez)) 
        {return true;}
        return false;
    }//hurt color

    public boolean isHurting(int x, int y)
    {
        if (x > 640-1 || x < 0 || y > 480-1 || y < 0) {return false;}

        Color ez = mask.getColorAt(x,y);
        if (hurtcolor.equals(ez) || althurtcolor.equals(ez)) 
        {return true;}
        return false;
    }//hurt color

    public boolean isJumpPad(int x, int y)
    {
        if (x > 640-1 || x < 0 || y > 480-1 || y < 0) {return false;}

        Color ez = mask.getColorAt(x,y);
        if (jumpcolor.equals(ez)) 
        {return true;}
        return false;
    }

    public boolean onLadder(int x, int y)
    {
        if (x > 640-1 || x < 0 || y > 480-1 || y < 0) {return false;}

        List l = getObjectsAt(x, y, ladder.class);
        Iterator i = l.iterator();

        while(i.hasNext())
        {
            Actor a = (Actor) i.next();
            if(a instanceof vine)
            {
                return true;
            }
        }

        return false;
    }

    public boolean isPipe(int x, int y)
    {
        if (x > 640-1 || x < 0 || y > 480-1 || y < 0) {return false;}

        Color ez = mask.getColorAt(x,y);
        if (pipecolor.equals(ez))
        {return true;}
        return false;
    }

    static  public void paintBg(int x, int y)
    {
        background.setColorAt(x,y, Color.RED);
        background.setColor(Color.RED);
        background.fillRect(x,y, 10,10);
    }

    public void cleanUp()
    {
        this.removeObjects(this.getObjects(elevator.class));
        this.removeObjects(this.getObjects(enemy.class));
        this.removeObjects(this.getObjects(door.class));
        this.removeObjects(this.getObjects(Player.class));
        this.removeObjects(this.getObjects(defeat.class));
        this.removeObjects(this.getObjects(hazard.class));
        this.removeObjects(this.getObjects(KoopaTroopa.class));
        this.removeObjects(this.getObjects(fireballin.class));
        this.removeObjects(this.getObjects(qBlock.class));
        this.removeObjects(this.getObjects(barrel.class));
        this.removeObjects(this.getObjects(Shroom.class));
        this.removeObjects(this.getObjects(Arcade.class));
        this.removeObjects(this.getObjects(Classic.class));
        this.removeObjects(this.getObjects(Yoshi.class));
        this.removeObjects(this.getObjects(Fish.class));
        this.removeObjects(this.getObjects(thwomp.class));
        this.removeObjects(this.getObjects(fireball.class));
        this.removeObjects(this.getObjects(HealthBar.class));
        this.removeObjects(this.getObjects(Bubble.class));
        this.removeObjects(this.getObjects(ladder.class));
    }

    public void nextLevel()
    {
        ThisLevel++;

        setLevel(ThisLevel);
    }

    public void restartLevel()
    {
        if(Arcade)
        {
            cleanUp();
            addObject(new Player(), 320, 50);
            setRound(ThisRound);
        }
        else
        {
            setLevel(ThisLevel);
        }
    }

    public void restartGame()
    {
        Harder = false;
        FireMode = false;
        ThisLevel = -2;
        soundfile = 1;
        cancelled = false;
        setLevel(ThisLevel);
        score = 0;
    }

    public void setLevel(int a)
    {
        cancelled = false;
        if (a < -5)
        {
            return;
        }
        cleanUp();
        listen_for_input = false;
        ThisLevel = a;
        switch(a)

        {
            case -5: setLevel_devLevel(); break;

            case -2: setLevel_introLevel(); break;
            case -1: setLevel_select(); break;
            case -0: setLevel_select2(); break;

            //WORLD 1//
            case 1: setLevel_lvl1(); break;
            case 2: setLevel_lvl2(); break;
            case 3: setLevel_lvl3();   break; 
            case 4: setLevel_lvl4();   break;
            case 5: setLevel_lvl5();   break;
            case 6: setLevel_lvl6();   break;
            case 7: setLevel_lvl7();   break;
            case 8: setLevel_lvl8();   break;
            case 9: setLevel_preboss1();   break;
            //WORLD 1 BOSS//
            case 10: setLevel_boss1();  break;

            case 11: setLevel_episode1to2();  break;

            //WORLD 2//
            case 12: setLevel_lvl12();  break;
            case 13: setLevel_lvl13();  break;
            case 14: setLevel_lvl14(); break;
            case 15: setLevel_lvl15();  break;
            case 16: setLevel_lvl16();  break;
            case 17: setLevel_lvl17();  break;
            case 18: setLevel_lvl18(); break;
            case 19: setLevel_preboss2();  break;
            //WORLD 2 BOSS//
            case 20: setLevel_boss2();  break;
            //WORLD 3//
            case 21: setLevel_lvl21();  break;
            case 22: setLevel_lvl22(); break; 
            case 23: setLevel_lvl23(); break;
            case 24: setLevel_lvl24();  break;
            case 25: setLevel_lvl25();  break;          
            case 26: setLevel_lvl26();  break;
            case 27: setLevel_ending(); break;
            case 28: nextLevel();break;
            case 29: nextLevel();break;
            //WORLD 2 BOSS//
            case 30: nextLevel();  break;  
            //WORLD 3//
            case 31: nextLevel();  break;
            case 32: nextLevel();  break;
            case 33:nextLevel(); break;
            case 34: nextLevel();  break;
            case 35: nextLevel();  break;
            case 36: nextLevel(); break;
            case 37: nextLevel();  break;
            case 38:  nextLevel(); break; 
            case 39:  nextLevel(); break;
            case 40:  setLevel_ending();  break;

            case 55: setLevel_secretLevel(); break;
            case 56: setLevel_secretLevel2(); break;
            case 99: ArcadeMode(); break;
        }
    }

    public void addedToWorld(World world) 
    {
        mworld = (myWorld) world;

        x = getWidth();
        y = getHeight();
    }//addedtoworld 

    public void setLevel_devLevel()
    {
        JOptionPane pane = new JOptionPane(null);

        Greenfoot.delay(20);
        String bgImage = pane.showInputDialog("Enter a background image.", "Must be located in images folder");

        try
        {
            try
            {
                background = new GreenfootImage("images/" + bgImage);
            }
            catch(IllegalArgumentException ex)
            {
                if(bgImage.indexOf(".png") >= 0)
                {
                    String[] file = bgImage.split(".png");
                    file[0] += ".PNG";
                    background = new GreenfootImage("images/" + file[0]);
                }

                if(bgImage.indexOf(".PNG") >= 0)
                {
                    String[] file = bgImage.split(".PNG");
                    file[0] += ".png";
                    background = new GreenfootImage("images/" + file[0]);
                }

            }

            setBackground(background);

            Greenfoot.delay(20);
            String maskImage = pane.showInputDialog("Enter a frame image.", "Must be located in the images folder");

            try
            {
                mask = new GreenfootImage("images/" + maskImage);
            }
            catch(IllegalArgumentException ex)
            {
                if(maskImage.indexOf(".png") >= 0)
                {
                    String[] file = maskImage.split(".png");
                    file[0] += ".PNG";
                    mask = new GreenfootImage("images/" + file[0]);
                }

                if(maskImage.indexOf(".PNG") >= 0)
                {
                    String[] file = maskImage.split(".PNG");
                    file[0] += ".png";
                    mask = new GreenfootImage("images/" + file[0]);
                }
            }

            listen_for_input = true;
        }
        catch(NullPointerException ex)
        {
            listen_for_input = false;
            setLevel(stored_level);
        }
    }

    public void setLevel_introLevel()
    {
        background = new GreenfootImage("selectmask.PNG");
        setBackground(background);
        mask = new GreenfootImage("selectframe.PNG");

        startX = 547;
        startY = 392;

        addObject(new Player(), 547, 392);  

        if(getObjects(Arcade.class).isEmpty())
        {
            addObject(new Arcade(), 120, 162);
            addObject(new Classic(), 120, 305);
        }

        addObject(new secretDoor(), 384, 414);

        addObject(new Life(), 585, 20);
        addObject(new Blaster(), 606, 457);

    }

    public void setLevel_select()
    {
        background = new GreenfootImage("Intro.PNG");
        setBackground(background);
        mask = new GreenfootImage("introframe.PNG");

        addObject(new Player(), 50, 425);

        if(getObjects(Score.class).isEmpty())
        {
            addObject(new Score(score), 103, 20);
        }

        addObject(new easydoor(), 150, 400);
        addObject(new firedoor(), 310, 400);
        addObject(new harddoor(), 480, 400);
    }

    public void setLevel_select2()
    {
        background = new GreenfootImage(1280, 480);
        setBackground(background);
        mask = new GreenfootImage("introframe.PNG");

        reload();

        addObject(new Player(), 50, 425);

        //         addObject(new onedoor(), 150, 400);
        //         addObject(new twodoor(), 310, 400);
        //         addObject(new threedoor(), 480, 400);

        addObject(new door(1), 150, 400);
        addObject(new door(11), 310, 400);
        addObject(new door(21), 480, 400);

        if(Fire)
        {
            if(getObjects(fireballin.class).isEmpty())
                addObject(new fireballin(), 110, 287);

            addObject(new FireDude(), 150, 425);
            addObject(new FireDude(), 200,425);
        }

        if(Harder)
        {
            addObject(new enemy(), 100, 425);
            addObject(new enemy(), 200, 425);
        }
    }

    public void setLevel_lvl1()
    {
        background = new GreenfootImage("lvl1mask.PNG");
        setBackground(background);
        mask = new GreenfootImage("lvl1frame.PNG");

        addObject(new door(), 399, 338);
        addObject(new Player(), 20,420);

        addObject(new barrel(), 100, 345);
        addObject(new barrel(), 135, 345);
        addObject(new barrel(), 170, 345);
        addObject(new qBlock(), 205, 345);

        addObject(new qBlock(), 100, 275);
        addObject(new qBlock(false), 395, 255);
        addObject(new barrel(), 135, 275);
        addObject(new barrel(), 170, 275);
        addObject(new barrel(), 205, 275);

        if(Harder)
        { 
            addObject(new edemon(), 100, 100);
        }
    }

    public void setLevel_lvl2()
    {
        background = new GreenfootImage("lvl2mask.PNG");
        setBackground(background); 
        mask = new GreenfootImage("lvl2frame.PNG");
        startX = 80;
        startY = 30;
        addObject(new Player(), startX,startY);

        if(Harder)
        { 
            addObject(new edemon(), 300, 300);
            addObject(new edemon(), 300, 300);
        }

        addObject(new door(), 38, 425);

    }

    public void setLevel_lvl3()
    {
        background = new GreenfootImage("lvl3mask.PNG");
        setBackground(background); 
        mask = new GreenfootImage("lvl3frame.PNG");

        startX = 80;
        startY = 30;
        addObject(new Player(), startX,startY);

        addObject(new door(), 38, 230);

        addObject(new enemy(),getWidth()/2,320); 
        addObject(new enemy(),getWidth()/2 +100,320); 

        addObject(new qBlock(), 600, 310);
        addObject(new qBlock(), 570, 310);
        addObject(new qBlock(), 540, 310);
        addObject(new qBlock(), 510, 310);

        addObject(new qBlock(false), 600, 310 - 80);
        addObject(new qBlock(false), 570, 310 - 80);

        if(Harder)
        {
            addObject(new firedemon(), 400, 300);
        }

    }//level

    public void setLevel_lvl4()
    {
        background = new GreenfootImage("lvl4mask.PNG");
        setBackground(background); 
        mask = new GreenfootImage("lvl4frame.PNG");

        startX = 80;
        startY = 30;
        addObject(new Player(), startX,startY);

        addObject(new door(), 450, 420);
        addObject(new qBlock(), 590, 95);
        addObject(new qBlock(), 530, 385);
        addObject(new qBlock(), 560, 385);
        addObject(new qBlock(), 590, 385);

        addObject(new enemy(),getWidth()/2 +100,150); 

        if(Harder)
        {
            addObject(new firedemon(5, 16), 300, 320);
            addObject(new firedemon(8, 0), 400, 80);
        }

    }//level

    public void setLevel_lvl5()
    {
        background = new GreenfootImage("lvl5mask.PNG");
        setBackground(background); 
        mask = new GreenfootImage("lvl5frame.PNG");

        startX = 384;
        startY = 350;
        addObject(new Player(), startX,startY);

        addObject(new door(), 49, 80);

        addObject(new elevator(), 199, 456); 
        addObject(new elevator(), 223, 311); 
        addObject(new elevator(), 165, 163); 

        addObject(new qBlock(), 430, 330);
        addObject(new barrel(), 462, 330);
        addObject(new barrel(), 498, 330);
        addObject(new barrel(), 533, 330);
        addObject(new qBlock(), 565, 330);

        if(Harder)
        {
            addObject(new enemy(), 165, 100);
        }

    }//level

    public void setLevel_lvl6()
    {
        background = new GreenfootImage("lvl6mask.PNG");
        setBackground(background); 
        mask = new GreenfootImage("lvl6frame.PNG");

        startX = 29;
        startY = 36;
        addObject(new Player(), startX,startY);

        addObject(new door(), 362, 377);

        if(!Harder)
        {
            addObject(new fallift(), 530, 94);
        }

        addObject(new enemy(), 123, 222);
        addObject(new enemy(), 183, 222);
        addObject(new enemy(), 233, 222);
        addObject(new enemy(), 283, 222);

        addObject(new qBlock(), 245, 195);
        addObject(new qBlock(), 275, 195);
        addObject(new qBlock(), 305, 195);
        addObject(new qBlock(), 335, 195);
        addObject(new qBlock(), 365, 195);
    }//level

    public void setLevel_lvl7()
    {
        background = new GreenfootImage("lvl7mask.PNG");
        setBackground(background); 
        mask = new GreenfootImage("lvl7frame.PNG");

        startX = getWidth()/2;
        startY = 440;
        addObject(new Player(), startX,startY);

        addObject(new door(), startX, 80);

        addObject(new rotalift(50, 2.5f, 30, 80), 166, 370);

        if(!Harder)
        {
            addObject(new qBlock(), 333, 180);
            addObject(new barrel(), 333, 266);
        }

        addObject(new qBlock(), 400, 40);

        addObject(new rotalift(100, 2.5f, 10, 80), 469, 240);

        addObject(new rotalift(38, 2.5f, 20, 130), 120, 200);

    }//level

    public void setLevel_lvl8()
    {
        background = new GreenfootImage("lvl8mask.PNG");
        setBackground(background); 
        mask = new GreenfootImage("lvl8frame.PNG");

        startX = 29;
        startY = 318;
        addObject(new Player(), startX,startY);

        addObject(new door(), getWidth()-29, startY+20);

        addObject(new qBlock(), 320, 260);
        addObject(new qBlock(), 350, 230);
        addObject(new qBlock(), 380, 200);

        addObject(new edemon(), getWidth()/2, getHeight()/2);

        if(Harder)
        {
            addObject(new firedemon(8, 0), Greenfoot.getRandomNumber(640), 300);
        }

    }//level

    public void setLevel_preboss1()
    {
        background = new GreenfootImage("preboss1mask.PNG");
        setBackground(background); 
        mask = new GreenfootImage("preboss1frame.PNG");
        startX = 20;
        startY = 270;

        addObject(new Player(), startX, startY);
        addObject(new door(), 506, 298);

        addObject(new barrel(), 264, 363);
        addObject(new barrel(), 264, 390);
        addObject(new barrel(), 231, 390);

        if(Harder)
        {
            addObject(new enemy(true), 50, 270);
            addObject(new enemy(true), 70, 270);
            addObject(new enemy(true), 90, 270);
            addObject(new enemy(true), 110, 270);
            addObject(new enemy(true), 130, 270);
            addObject(new enemy(true), 150, 270);
            addObject(new enemy(true), 170, 270);
            addObject(new enemy(true), 190, 270);
        }
    }//level
    public void setLevel_boss1()
    {
        background = new GreenfootImage("boss1mask.PNG");
        setBackground(background); 
        mask = new GreenfootImage("boss1frame.PNG");
        startX = 60;
        startY = 270;
        addObject(new Player(), startX,startY);

        addObject(new kinghammer(), getWidth()-60, 370);
        addObject(new kinghammer(), getWidth()-120, 270);

        addObject(new qBlock(), 397-31, 270);
        addObject(new qBlock(), 397, 270);
        addObject(new qBlock(), 428, 270);

        if(Harder)
        {
            addObject(new edemon(), 240, 240);
        }
    }//level

    public void setLevel_episode1to2()
    {
        background = new GreenfootImage("episode1-2mask.PNG");
        setBackground(background); 
        mask = new GreenfootImage("episode1-2frame.PNG");
        startX = 41;
        startY = 39;
        addObject(new Player(), startX,startY);

        addObject(new enemy(), 156, 112);
        addObject(new enemy(), 200, 112);
        addObject(new enemy(), 256, 112);
        addObject(new enemy(), 300, 112);
        addObject(new enemy(), 356, 112);
        addObject(new enemy(), 400, 112);
        addObject(new enemy(), 456, 112);

        addObject(new qBlock(), 195, 90);
        addObject(new qBlock(), 225, 90);
        addObject(new qBlock(), 255, 90);

        addObject(new qBlock(), 325, 240);
        addObject(new qBlock(), 355, 240);
        addObject(new qBlock(), 385, 240);

        addObject(new enemy(), 200, 262);
        addObject(new enemy(), 256, 262);
        addObject(new enemy(), 300, 262);
        addObject(new enemy(), 356, 262);
        addObject(new enemy(), 400, 262);
        addObject(new enemy(), 456, 262);
        addObject(new enemy(), 500, 262);
        addObject(new enemy(), 556, 262);

        if(Harder)
        {
            addObject(new firedemon(8, 0), 100, 100);
            addObject(new firedemon(8, 0), 50, 250);
        }

        addObject(new door(), 581, 420);
    }//level

    public void setLevel_lvl12()
    {
        background = new GreenfootImage("lvl12mask.PNG");
        setBackground(background); 
        mask = new GreenfootImage("lvl12frame.PNG");
        startX = 308;
        startY = 175;

        addObject(new falsedoor(), 35, 417);
        addObject(new door(), 571, 414);

        addObject(new qBlock(), 35, 330);

        addObject(new Player(), startX,startY);

        if(Harder)
        {
            addObject(new thwomp(), 580, 108);
            addObject(new thwomp(), 50, 108);
        }
    }

    public void setLevel_lvl13()
    {
        //         background = new GreenfootImage("lvl13mask.PNG");
        //         setBackground(background); 
        //         mask = new GreenfootImage("lvl13frame.PNG");

        background = new GreenfootImage("lvl13mask.PNG");
        setBackground(background); 
        mask = new GreenfootImage("lvl13frame.PNG");
        startX = 65;
        startY = 285;

        addObject(new door(), getWidth()-65, 307);

        addObject(new KoopaTroopa(), startX+50, startY);

        addObject(new Player(), startX,startY);

        if(Harder)
        {
            addObject(new firedemon(8,0), 100, 100);
        }
    }

    public void setLevel_lvl14()
    {
        background = new GreenfootImage("lvl14mask.png");
        setBackground(background); 
        mask = new GreenfootImage("lvl14frame.png");
        startX = 65;
        startY = 285;

        doorPos = 1000+539-330;

        t = 0;

        addObject(new door(true), doorPos, 313);

        addObject(new Player(), startX,startY);
    }

    public void setLevel_lvl15()
    {
        background = new GreenfootImage("lvl15mask.PNG");
        setBackground(background); 
        mask = new GreenfootImage("lvl15frame.PNG");
        startX = 65;
        startY = 50;

        addObject(new door(), 43, 427);

        addObject(new Player(), startX,startY);

        if(Harder)
        {
            addObject(new edemon(), 300,300);
        }
    }

    public void setLevel_lvl16()
    {
        background = new GreenfootImage("lvl16mask.PNG");
        setBackground(background); 
        mask = new GreenfootImage("lvl16frame.PNG");
        startX = 65;
        startY = 50;

        addObject(new door(), 543, 437);

        bubbles();

        addObject(new Player(), startX,startY);

        addObject(new Fish(), Greenfoot.getRandomNumber(640), Greenfoot.getRandomNumber(450));
        addObject(new Fish(), Greenfoot.getRandomNumber(640), Greenfoot.getRandomNumber(450));
        addObject(new Fish(), Greenfoot.getRandomNumber(640), Greenfoot.getRandomNumber(450));
        addObject(new Fish(), Greenfoot.getRandomNumber(640), Greenfoot.getRandomNumber(450));
        addObject(new Fish(), Greenfoot.getRandomNumber(640), Greenfoot.getRandomNumber(450));
        addObject(new Fish(), Greenfoot.getRandomNumber(640), Greenfoot.getRandomNumber(450));
        addObject(new Fish(), Greenfoot.getRandomNumber(640), Greenfoot.getRandomNumber(450));

        if(Harder)
        {
            addObject(new edemon(), 200, 200);
            addObject(new edemon(), 400, 300);
            addObject(new edemon(), 600, 400);
        }
    }

    public void setLevel_lvl17()
    {
        background = new GreenfootImage("lvl17mask.PNG");
        setBackground(background); 
        mask = new GreenfootImage("lvl17frame.PNG");
        startX = 35;
        startY = 50;

        addObject(new door(), 574, 363);

        bubbles();

        addObject(new Player(), startX,startY);

        addObject(new Fish(), Greenfoot.getRandomNumber(640), Greenfoot.getRandomNumber(450));
        addObject(new Fish(), Greenfoot.getRandomNumber(640), Greenfoot.getRandomNumber(450));
        addObject(new Fish(), Greenfoot.getRandomNumber(640), Greenfoot.getRandomNumber(450));
        addObject(new Fish(), Greenfoot.getRandomNumber(640), Greenfoot.getRandomNumber(450));
        addObject(new Fish(), Greenfoot.getRandomNumber(640), Greenfoot.getRandomNumber(450));
        addObject(new Fish(), Greenfoot.getRandomNumber(640), Greenfoot.getRandomNumber(450));
        addObject(new Fish(), Greenfoot.getRandomNumber(640), Greenfoot.getRandomNumber(450));

        if(Harder)
        {
            addObject(new edemon(), 200, 200);
            addObject(new edemon(), 200, 200);
        }
    }

    public void setLevel_lvl18()
    {
        background = new GreenfootImage("lvl18mask.PNG");
        setBackground(background); 
        mask = new GreenfootImage("lvl18frame.PNG");
        startX = 235;
        startY = 50;

        addObject(new door(), 42, 203);
        addObject(new enemy(true), 45, 203);
        addObject(new enemy(true), 56, 203);
        addObject(new enemy(true), 65, 203);

        addObject(new qBlock(), 145, 165);
        addObject(new qBlock(), 175, 165);
        addObject(new qBlock(), 205, 165);

        addObject(new Player(), startX,startY);

        if(!Harder)
        {
            addObject(new sidelift(0),200, 380);
        }
        addObject(new Yoshi(), 105, 50);

        addObject(new rotalift(19, 2.5f, 50, 100), 360, 350);

        addObject(new elevator(), 545, 100);
    }

    public void setLevel_preboss2()
    {
        background = new GreenfootImage("preboss2mask.PNG");
        setBackground(background); 
        mask = new GreenfootImage("preboss2frame.PNG");
        startX = 26;
        startY = 402;

        addObject(new door(), 589, 400);

        addObject(new Player(), startX,startY);

        addObject(new thwomp(), 105, 98);
        addObject(new thwomp(), 282, 98);
        addObject(new thwomp(), 505, 98);
        if(Harder)
        {
            addObject(new edemon(), 200, 200);
            addObject(new edemon(), 200, 200);

            addObject(new enemy(), 200, 400);
            addObject(new enemy(), 250, 400);
            addObject(new enemy(), 300, 400);
        }
    }

    public void setLevel_boss2()
    {
        background = new GreenfootImage("boss2mask.PNG");
        setBackground(background); 
        mask = new GreenfootImage("boss2frame.PNG");
        startX = 26;
        startY = 226;

        addObject(new Player(), startX,startY);

        KingBoo king = new KingBoo();

        addObject(king, 320, 240);        

        bubbles();

        if(getObjects(HealthBar.class).isEmpty())
        {
            if(!Harder)
                addObject(new HealthBar(4), 46, 49);

            else if(Harder)
                addObject(new HealthBar(6), 66, 49);
        }

        if(Harder)
        {
            addObject(new Fish(), Greenfoot.getRandomNumber(640), Greenfoot.getRandomNumber(450));
            addObject(new Fish(), Greenfoot.getRandomNumber(640), Greenfoot.getRandomNumber(450));
            addObject(new Fish(), Greenfoot.getRandomNumber(640), Greenfoot.getRandomNumber(450));
        }
    }

    public void setLevel_lvl21()
    {
        Boss2Music.stop();
        background = new GreenfootImage("lvl21mask.PNG");
        setBackground(background); 
        getBackground().setTransparency(255);
        mask = new GreenfootImage("lvl21frame.PNG");
        startX = 26;
        startY = 426;

        addObject(new qBlock(), 115, 130);

        addObject(new Player(), startX,startY);
        addObject(new door(), 507, 428);
    }

    public void setLevel_lvl22()
    {
        background = new GreenfootImage("lvl22frame.png");
        setBackground(background); 
        getBackground().setTransparency(255);
        mask = new GreenfootImage("lvl22mask.png");
        startX = 26;
        startY = 100;

        addObject(new Player(), startX,startY);
        addObject(new door(), 494, 272);

        if(Harder)
        {
            addObject(new edemon(), 200, 200);
            addObject(new edemon(), 200, 200);
        }
    }

    public void setLevel_lvl23()
    {
        background = new GreenfootImage("lvl23mask.png");
        setBackground(background); 
        mask = new GreenfootImage("lvl23frame.png");
        startX = 26;
        startY = 50;

        addObject(new Player(), startX,startY);
        addObject(new door(), 35, 289);

        addObject(new enemy(true), 200, 440);
        addObject(new enemy(true), 240, 440);
        addObject(new enemy(true), 280, 440);

        if(Harder)
        {
            addObject(new edemon(), 200, 200);
            addObject(new firedemon(4, 16), 200, 200);
        }
    }

    public void setLevel_lvl24()
    {
        background = new GreenfootImage("lvl24mask.png");
        setBackground(background); 
        mask = new GreenfootImage("lvl24frame.png");
        startX = 26;
        startY = 400;

        addObject(new Player(), startX,startY);
        addObject(new door(), 554, 460);

        if(!Harder)
        {
            for(int i = 27; i < 27 * 11; i += 27)
            {
                addObject(new Brick(), 209, i);
            }
        }

        addObject(new Brick(), 100, 385);
        addObject(new Brick(), 100, 355);

        if(Harder)
        {
            for(int i = 27; i < 27 * 9; i += 27)
            {
                addObject(new Brick(), 209, i);
            }

            addObject(new edemon(), 200, 200);
            addObject(new firedemon(4, 16), 200, 200);
        }
    }

    public void setLevel_lvl25()
    {
        background = new GreenfootImage("lvl25mask.png");
        setBackground(background); 
        mask = new GreenfootImage("lvl25frame.png");
        startX = 46;
        startY = 400;

        addObject(new Player(), startX,startY);
        addObject(new door(), 554, 359);

        addObject(new fallift(false), 46, 420);
        addObject(new sidelift(16), 400, 200);
        addObject(new rotalift(100, 2.5f, 20, 20), 300, 300);
        addObject(new barrel(), 554, 390);

        if(Harder)
        {
            addObject(new firedemon(8, 16), 300, 300);
            addObject(new edemon(), 400, 400);
        }
    }

    public void setLevel_lvl26()
    {
        background = new GreenfootImage("lvl26mask.png");
        setBackground(background); 
        mask = new GreenfootImage("lvl26frame.png");
        startX = 596;
        startY = 416;

        addObject(new Player(), startX,startY);
        addObject(new door(), 78, 412);

        addObject(new qBlock(7), 540, 370);
        addObject(new fallift(), 204, 183);

        if(Harder)
        {
            addObject(new enemy(true), 290, 140);
            addObject(new enemy(true), 310, 140);
            addObject(new enemy(true), 330, 140);
        }
    }

    public void ArcadeMode()
    {
        background = new GreenfootImage("ArcadeMask.PNG");
        setBackground(background); 
        mask = new GreenfootImage("ArcadeFrame.png");
        addObject(new Player(), 320, 50);
        setRound(ThisRound);

        addObject(new killCount(), 346, 38);
    }

    public void setLevel_secretLevel()
    {
        background = new GreenfootImage("secretMask.png");
        mask = new GreenfootImage("secretFrame.png");
        setBackground(background);

        startX = 50;
        startY = 350;

        addObject(new Player(), 50, 350);
        addObject(new door(), 377, 295);
    }

    public void setLevel_secretLevel2()
    {
        background = new GreenfootImage("sec2Mask.png");
        mask = new GreenfootImage("secret2Frame.png");
        setBackground(background);

        startX = 100;
        startY = 150;

        addObject(new Player(), startX, startY);
        addObject(new door(), 519, 386);
    }

    public void setLevel_ending()
    {
        music.stop();
        MUSIC.stop();
        Music.stop();
        Boss2Music.stop();
        background = new GreenfootImage("ENDING.png");

        setBackground(background);
        background.setTransparency(Trans);

        this.removeObjects(this.getObjects(Life.class));
    }//level

    public void setRound(int b)
    {
        ThisRound = b;
    }

    public void pause(int time)
    {        
        if(waitingTime > 0)
        {
            waitingTime--;
        }

        if(ThisRound > 0 && waitingTime <=0 & left)
        {
            addObject(new enemy(), 120,60);
            left = false;
            waitingTime = 200;
            ThisRound--;
        }

        if(ThisRound > 0 && waitingTime <=0 & !left)
        {
            addObject(new enemy(), getWidth()-120,60);
            left = true;
            waitingTime = 200;
            ThisRound--;
        }

        if(ThisRound == 0)
        {
            addObjects = false;
        }
    }

    public void ArcadeRound()
    {
        if(!addObjects & getObjects(enemy.class).isEmpty())
        {
            STORED_ROUND++;
            ThisRound = STORED_ROUND;
            addObjects = true;
        }
    }

    public void reload()
    {         
        background.drawImage(new GreenfootImage("Intro2.PNG"), dest, 0); 

        setBackground(background);
    }

    public void Scrolling()
    {
        if(ThisLevel==14 && !Arcade && ThisLevel != 55)
        {
            Scrolling = true;
        }

        else
        {
            Scrolling = false;
        }
    }

    public void ending()
    {
        if(Trans <254)
        {
            Trans++;
        }
        background.setTransparency(Trans);
    }

    public void music()
    {
        if(ThisLevel==16 || ThisLevel==17)
        {
            playWater();
        }

        if(!Arcade && ThisLevel!=16 && ThisLevel!=17 && ThisLevel !=19 && ThisLevel !=20 && ThisLevel !=9 && ThisLevel !=10)
        {
            playMusic();
        }

        if(ThisLevel==10)
        {
            playBoss();
        }

        if(ThisLevel==20)
        {
            playBoss2();
        }

        else if(Arcade || ThisLevel==9 || ThisLevel==19)
        {
            playUnderground();
        }
    }

    public void music(int spec)
    {
        if(ThisLevel==16 || ThisLevel==17)
        {
            playWater();
        }

        if(!Arcade && ThisLevel!=16 && ThisLevel!=17 && ThisLevel !=19 && ThisLevel !=20 && ThisLevel !=9 && ThisLevel !=10)
        {
            playMusic(spec);
        }

        if(ThisLevel==10)
        {
            playBoss();
        }

        if(ThisLevel==20)
        {
            playBoss2();
        }

        else if(Arcade || ThisLevel==9 || ThisLevel==19)
        {
            playUnderground();
        }
    }

    public void stopAllAudio()
    {
        Music.stop();
        music.stop();
        music2.stop();
        music3.stop();
        BossMusic.stop();
        Boss2Music.stop();
        MUSIC.stop();
    }

    public void playWater()
    {
        if(Music.isPlaying())
        {
            Music.stop();
        }
        if(BossMusic.isPlaying())
        {
            BossMusic.stop();
        }
        if(music.isPlaying())
        {
            music.stop();
        }
        if(Boss2Music.isPlaying())
        {
            Boss2Music.stop();
        }

        if(!MUSIC.isPlaying() && deadPlayer == false)
        {
            MUSIC.play();
        }

        if(MUSIC.isPlaying() && deadPlayer == false)
        {
        }

        if(MUSIC.isPlaying() && deadPlayer == true)
        {
            MUSIC.stop();
        }
    }

    public void playBoss()
    {
        if(MUSIC.isPlaying())
        {
            MUSIC.stop();
        }
        if(Music.isPlaying())
        {
            Music.stop();
        }
        if(music.isPlaying())
        {
            music.stop();
        }
        if(Boss2Music.isPlaying())
        {
            Boss2Music.stop();
        }

        if(!BossMusic.isPlaying() && deadPlayer == false && ending==false)
        {
            BossMusic.play();
        }
        else if(BossMusic.isPlaying() && deadPlayer == false)
        {
        }
        else if(BossMusic.isPlaying() && deadPlayer == true)
        {
            BossMusic.stop();
        }
    }

    public void playUnderground()
    {
        if(MUSIC.isPlaying())
        {
            MUSIC.stop();
        }
        if(BossMusic.isPlaying())
        {
            BossMusic.stop();
        }
        if(music.isPlaying())
        {
            music.stop();
        }
        if(Boss2Music.isPlaying())
        {
            Boss2Music.stop();
        }

        if(!Music.isPlaying() && deadPlayer == false)
        {
            Music.play();
        }
        else if(Music.isPlaying() && deadPlayer == false)
        {
        }
        else if(Music.isPlaying() && deadPlayer == true)
        {
            Music.stop();
        }
    }

    public void playBoss2()
    {
        if(MUSIC.isPlaying())
        {
            MUSIC.stop();
        }
        if(Music.isPlaying())
        {
            Music.stop();
        }
        if(BossMusic.isPlaying())
        {
            BossMusic.stop();
        }
        if(music.isPlaying())
        {
            music.stop();
        }

        if(!Boss2Music.isPlaying() && deadPlayer == false && ending==false)
        {
            Boss2Music.play();
        }
        else if(Boss2Music.isPlaying() && deadPlayer == false)
        {
        }
        else if(Boss2Music.isPlaying() && deadPlayer == true)
        {
            Boss2Music.stop();
        }
    }

    public void playMusic()
    {
        if(MUSIC.isPlaying())
        {
            MUSIC.stop();
        }
        if(Music.isPlaying())
        {
            Music.stop();
        }
        if(BossMusic.isPlaying())
        {
            BossMusic.stop();
        }
        if(Boss2Music.isPlaying())
        {
            BossMusic.stop();
        }
        if(!music.isPlaying() && deadPlayer == false && ending==false)
        {
            music.play();
        }
        else if(music.isPlaying() && deadPlayer == false)
        {
        }
        else if(music.isPlaying() && deadPlayer == true)
        {
            music.stop();
        }
    }

    public void playMusic(int spec)
    {
        if(MUSIC.isPlaying())
        {
            MUSIC.stop();
        }
        if(Music.isPlaying())
        {
            Music.stop();
        }
        if(BossMusic.isPlaying())
        {
            BossMusic.stop();
        }
        if(Boss2Music.isPlaying())
        {
            BossMusic.stop();
        }
        if(spec == 1)
        {
            if(!music.isPlaying() && deadPlayer == false && ending==false)
            {
                music.play();
            }
            else if(music.isPlaying() && deadPlayer == false)
            {
            }
            else if(music.isPlaying() && deadPlayer == true)
            {
                music.stop();
            }
        }
        
        if(spec == 2)
        {
            if(!music2.isPlaying() && deadPlayer == false && ending==false)
            {
                music2.play();
            }
            else if(music2.isPlaying() && deadPlayer == false)
            {
            }
            else if(music2.isPlaying() && deadPlayer == true)
            {
                music2.stop();
            }
        }
        
        if(spec == 3)
        {
            if(!music3.isPlaying() && deadPlayer == false && ending==false)
            {
                music3.play();
            }
            else if(music3.isPlaying() && deadPlayer == false)
            {
            }
            else if(music3.isPlaying() && deadPlayer == true)
            {
                music3.stop();
            }
        }
    }

    public void reverseGravity()
    {
        gravity = -gravity;
    }

    private void listen()
    {
        if(Greenfoot.mouseClicked(null))
        {
            MouseInfo m = Greenfoot.getMouseInfo();
            int cx = m.getX();
            int cy = m.getY();
            String LOCATION[];
            JOptionPane pane = new JOptionPane(null);
            String actor = pane.showInputDialog("What kind of actor would you like to add?");
            actor.toLowerCase();

            if(actor.equals("door"))
            {
                String location = pane.showInputDialog("Where? Format: xxx, yyy Last click at: ", + cx + ", " + cy);
                try
                {
                    LOCATION = location.split(", ");

                    addObject(new door(), Integer.parseInt(LOCATION[0]), Integer.parseInt(LOCATION[1]));
                }
                catch(Exception ex)
                {
                    return;
                }
            }

            if(actor.equals("lifter"))
            {
                String location = pane.showInputDialog("Where? Format: xxx, yyy Last click at: ", + cx + ", " + cy);
                try
                {
                    LOCATION = location.split(", ");

                    addObject(new Lifter(), Integer.parseInt(LOCATION[0]), Integer.parseInt(LOCATION[1]));
                }
                catch(Exception ex)
                {
                    return;
                }
            }

            if(actor.equals("sidelift"))
            {
                String location = pane.showInputDialog("Where? Format: xxx, yyy Last click at: ", + cx + ", " + cy);
                try
                {
                    LOCATION = location.split(", ");

                    addObject(new sidelift(), Integer.parseInt(LOCATION[0]), Integer.parseInt(LOCATION[1]));
                }
                catch(Exception ex)
                {
                    return;
                }
            }

            if(actor.equals("fallift"))
            {
                String location = pane.showInputDialog("Where? Format: xxx, yyy Last click at: ", + cx + ", " + cy);
                try
                {
                    LOCATION = location.split(", ");

                    addObject(new fallift(), Integer.parseInt(LOCATION[0]), Integer.parseInt(LOCATION[1]));
                }
                catch(Exception ex)
                {
                    return;
                }
            }

            if(actor.equals("barrel"))
            {
                String location = pane.showInputDialog("Where? Format: xxx, yyy Last click at: ", + cx + ", " + cy);
                try
                {
                    LOCATION = location.split(", ");

                    addObject(new barrel(), Integer.parseInt(LOCATION[0]), Integer.parseInt(LOCATION[1]));
                }
                catch(Exception ex)
                {
                    return;
                }
            }

            if(actor.equals("elevator"))
            {
                String location = pane.showInputDialog("Where? Format: xxx, yyy Last click at: ", + cx + ", " + cy);
                try
                {
                    LOCATION = location.split(", ");

                    addObject(new elevator(), Integer.parseInt(LOCATION[0]), Integer.parseInt(LOCATION[1]));
                }
                catch(Exception ex)
                {
                    return;
                }
            }

            if(actor.equals("qblock"))
            {
                String location = pane.showInputDialog("Where? Format: xxx, yyy Last click at: ", + cx + ", " + cy);
                try
                {
                    LOCATION = location.split(", ");

                    addObject(new qBlock(), Integer.parseInt(LOCATION[0]), Integer.parseInt(LOCATION[1]));
                }
                catch(Exception ex)
                {
                    return;
                }
            }

            if(actor.equals("shroom"))
            {
                String location = pane.showInputDialog("Where? Format: xxx, yyy Last click at: ", + cx + ", " + cy);
                try
                {
                    LOCATION = location.split(", ");

                    addObject(new Shroom(), Integer.parseInt(LOCATION[0]), Integer.parseInt(LOCATION[1]));
                }
                catch(Exception ex)
                {
                    return;
                }
            }

            if(actor.equals("player"))
            {
                String location = pane.showInputDialog("Where? Format: xxx, yyy Last click at: ", + cx + ", " + cy);
                try
                {
                    LOCATION = location.split(", ");

                    startX = Integer.parseInt(LOCATION[0]);
                    startY = Integer.parseInt(LOCATION[1]);

                    addObject(new Player(), Integer.parseInt(LOCATION[0]), Integer.parseInt(LOCATION[1]));
                }
                catch(Exception ex)
                {
                    return;
                }
            }

            if(actor.equals("yoshi"))
            {
                String location = pane.showInputDialog("Where? Format: xxx, yyy Last click at: ", + cx + ", " + cy);
                try
                {
                    LOCATION = location.split(", ");

                    startX = Integer.parseInt(LOCATION[0]);
                    startY = Integer.parseInt(LOCATION[1]);

                    addObject(new Yoshi(), Integer.parseInt(LOCATION[0]), Integer.parseInt(LOCATION[1]));
                }
                catch(Exception ex)
                {
                    return;
                }
            }

            if(actor.equals("enemy"))
            {
                String location = pane.showInputDialog("Where? Format: xxx, yyy Last click at: ", + cx + ", " + cy);
                try
                {
                    LOCATION = location.split(", ");

                    addObject(new enemy(), Integer.parseInt(LOCATION[0]), Integer.parseInt(LOCATION[1]));
                }
                catch(Exception ex)
                {
                    return;
                }
            }

            if(actor.equals("brick"))
            {
                String location = pane.showInputDialog("Where? Format: xxx, yyy Last click at: ", + cx + ", " + cy);
                try
                {
                    LOCATION = location.split(", ");

                    addObject(new Brick(), Integer.parseInt(LOCATION[0]), Integer.parseInt(LOCATION[1]));
                }
                catch(Exception ex)
                {
                    return;
                }
            }

            if(actor.equals("rock"))
            {
                String location = pane.showInputDialog("Where? Format: xxx, yyy Last click at: ", + cx + ", " + cy);
                try
                {
                    LOCATION = location.split(", ");

                    addObject(new rock(), Integer.parseInt(LOCATION[0]), Integer.parseInt(LOCATION[1]));
                }
                catch(Exception ex)
                {
                    return;
                }
            }

            if(actor.equals("kingboo"))
            {
                String location = pane.showInputDialog("Where? Format: xxx, yyy Last click at: ", + cx + ", " + cy);
                try
                {
                    LOCATION = location.split(", ");

                    addObject(new KingBoo(), Integer.parseInt(LOCATION[0]), Integer.parseInt(LOCATION[1]));
                }
                catch(Exception ex)
                {
                    return;
                }
            }

            if(actor.equals("kinghammer"))
            {
                String location = pane.showInputDialog("Where? Format: xxx, yyy Last click at: ", + cx + ", " + cy);
                try
                {
                    LOCATION = location.split(", ");

                    addObject(new kinghammer(), Integer.parseInt(LOCATION[0]), Integer.parseInt(LOCATION[1]));
                }
                catch(Exception ex)
                {
                    return;
                }
            }

            if(actor.equals("firedude"))
            {
                String location = pane.showInputDialog("Where? Format: xxx, yyy Last click at: ", + cx + ", " + cy);
                try
                {
                    LOCATION = location.split(", ");

                    addObject(new FireDude(), Integer.parseInt(LOCATION[0]), Integer.parseInt(LOCATION[1]));
                }
                catch(Exception ex)
                {
                    return;
                }
            }

            if(actor.equals("thwomp"))
            {
                String location = pane.showInputDialog("Where? Format: xxx, yyy Last click at: ", + cx + ", " + cy);
                try
                {
                    LOCATION = location.split(", ");

                    addObject(new thwomp(), Integer.parseInt(LOCATION[0]), Integer.parseInt(LOCATION[1]));
                }
                catch(Exception ex)
                {
                    return;
                }
            }

            if(actor.equals("koopatroopa"))
            {
                String location = pane.showInputDialog("Where? Format: xxx, yyy Last click at: ", + cx + ", " + cy);
                try
                {
                    LOCATION = location.split(", ");

                    addObject(new KoopaTroopa(), Integer.parseInt(LOCATION[0]), Integer.parseInt(LOCATION[1]));
                }
                catch(Exception ex)
                {
                    return;
                }
            }

            if(actor.equals("sidelift"))
            {
                String location = pane.showInputDialog("Where? Format: xxx, yyy Last click at: ", + cx + ", " + cy);
                try
                {
                    LOCATION = location.split(", ");

                    addObject(new sidelift(), Integer.parseInt(LOCATION[0]), Integer.parseInt(LOCATION[1]));
                }
                catch(Exception ex)
                {
                    return;
                }
            }
        }
    }

    public void bubbles()
    {
        addObject(new Bubble(), Greenfoot.getRandomNumber(640), 500 + Greenfoot.getRandomNumber(10));
        addObject(new Bubble(), Greenfoot.getRandomNumber(640), 500 + Greenfoot.getRandomNumber(100));
        addObject(new Bubble(), Greenfoot.getRandomNumber(640), 500 + Greenfoot.getRandomNumber(100));
        addObject(new Bubble(), Greenfoot.getRandomNumber(640), 500 + Greenfoot.getRandomNumber(100));
        addObject(new Bubble(), Greenfoot.getRandomNumber(640), 500 + Greenfoot.getRandomNumber(100));
        addObject(new Bubble(), Greenfoot.getRandomNumber(640), 500 + Greenfoot.getRandomNumber(100));
        addObject(new Lifter(), Greenfoot.getRandomNumber(640), 500 + Greenfoot.getRandomNumber(100));
        addObject(new Lifter(), Greenfoot.getRandomNumber(640), 500 + Greenfoot.getRandomNumber(100));
    }

    ///////////////////////    
}//class

