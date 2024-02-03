import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class Pushable here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pushable  extends enemy
{
    boolean can_move;
    /**
     * Act - do whatever the Pushable wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if(x > 630 - 9)
        {
            x = 630 - 9;
            xs = 0;
        }
        if(y > 470 - 9)
        {
            y = 470 - 9;
            ys = 0;
        }
        if(x < 10 + 9)
        {
            x = 10 - 9;
            xs = 0;
        }

        Player player_left = (Player) getOneObjectAtOffset(-27, 0, Player.class);
        Player player_right = (Player) getOneObjectAtOffset(27, 0, Player.class);

        for(int i = -12; i < -6; i++)
        {
            Pushable push_left = (Pushable) getOneObjectAtOffset(i, 0, Pushable.class);
            Pushable push_right = (Pushable) getOneObjectAtOffset(-i, 0, Pushable.class);
            Pushable push_bottom = (Pushable) getOneObjectAtOffset(0, -i, Pushable.class);
            Player player_under = (Player) getOneObjectAtOffset(0, -i, Player.class);

            /**
             * Sit on a players head.
             */
            if(player_under != null)
            {
                y--;
                ys = 0;
            }

            /**
             * Get pushed by another pushable if there's no ground near.
             */
            if(!((myWorld) getWorld()).isGround((int)x+i, (int)y) && !((myWorld) getWorld()).isGround((int)x-i, (int)y))
            {
                if(push_left != null && x < 630 - 9)
                {
                    can_move = false;
                    xs = push_left.xs + 0.3f;
                    x++;
                }
                else if(push_right != null && x > 19)
                {
                    can_move = false;
                    xs = push_right.xs - 0.3f;
                    x--;
                }

                if(push_bottom != null)
                {
                    can_move = false;
                    ys = 0;
                    y--;
                }
            }
            else if(((myWorld) getWorld()).isGround((int)x-i + 20, (int)y))
            {
                xs = 0;
                x--;
            }

            else if(((myWorld) getWorld()).isGround((int)x+i - 20, (int)y))
            {
                xs = 0;
                x++;
            }
        }//end i

        /**
         * A player has started to overlap this object.
         */
        for(int i = 19; i < 24; i++)
        {
            Player p_left = (Player) getOneObjectAtOffset(-i, 0, Player.class);
            Player p_right = (Player) getOneObjectAtOffset(i, 0, Player.class);

            if(p_left != null)
            {
                p_left.x--;
            }
            else if(p_right != null)
            {
                p_right.x++;
            }
        }

        /**
         * Get pushed by a player.
         */
        if(player_left != null && x < 630 - 9)
        {
            if(player_left.xs != 0)
            {
                can_move = true;
                x++;
                xs = player_left.xs + 0.3f;
            }
        }
        else if(player_right != null && x > 19)
        {
            if(player_right.xs != 0)
            {
                can_move = true;
                x--;
                xs = player_right.xs - 0.3f;
            }
        }

        if(x > 630 - 9)
        {
            x = 630 - 9;
            xs = 0;
        }
        if(y > 470 - 9)
        {
            y = 470 - 9;
            ys = 0;
        }
        if(x < 10 + 9)
        {
            x = 10 + 9;
            xs = 0;
        }

        movement();
        gravity();
        deccelrate(); 
        try
        {
            checkbackground();
        }
        catch(IndexOutOfBoundsException ex)
        {
            y--;
            ys = 0;
        }
        checkelevator();
        setLocation((int)x, (int)y);
    }
}
