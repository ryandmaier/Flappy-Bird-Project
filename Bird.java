import javax.swing.*;
import javax.swing.JComponent;
import java.awt.image.BufferedImage;
import java.awt.geom.*;
import java.awt.Graphics;
import java.awt.*;
import javax.swing.JComponent;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.event.ActionListener;//imports
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;
import javax.swing.Timer;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.nio.CharBuffer;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.*;

public class Bird extends JComponent implements ActionListener
{
    double w = 20;//width common factor of the bird
    double x = 250;//x value of the bird, starting at 250
    double y = 350;//y value of the bird, starting at 350
    double acc = -.04;//variable for accelleration due to gravity- rate at which the velocity changes
    double vel = 0;//variable for bird's up and down velocity- rate at which the x changes
    BufferedImage birdImg;//image of the bird used
    boolean nothover = false;//whether or not the bird is hovering
    double count = 0;//variable used to keep track of time
    double startrate = Math.cos(count);//movement of the bird while hovering, following the cosine curves
    boolean alive = true;//whether or not the bird is alive
    int score = 0;//the player's score
    int highscore = 0;//the player's highscore
    Graphics2D g2;//the graphics2D object
    FileWriter fw;//the filewriter used to save the highscore to the text writer
    BufferedWriter bw;//the bufferedwriter used to use the filewriter
    PrintWriter out;//the printwriter used to use the bufferedwriter and filewriter
    Scanner read;//the scanner used to read the text file and get the saved highscore
    public Bird()
    {
        printInstructions();//prints the game instructions
        try {
            birdImg = ImageIO.read(new File("bird.png"));//sets birdimg to the png file bird.png
            read = new Scanner(new File("out.txt"));//setsr read to a new scanner reading the text file out.txt, where the highscore is saved
            fw = new FileWriter("out.txt", true);//creates new filewriter to read out.txt and assings it to fw
            bw = new BufferedWriter(fw);//creates new bufferedwriter with fw and assigns it to bw
            out = new PrintWriter(bw);//creates new printwriter with bw and assigns it to out
        } catch (IOException e) {}
        highscore = read.nextInt();//sets the highscore to what is on the out.txt file using the scanner object
        birdImg = this.resize(birdImg,(int) w*(3),(int) w*(2));//resizes birdImg to be the right size for the game
    }

    public void paintComponent(Graphics g)
    {
        g2 = (Graphics2D) g;//sets g2 as a Graphics2D cast of g
        g2.drawImage(birdImg, null, (int) x, (int) y);//draws the birdImg with the given x and y values
    }

    public void boost()//used when space bar is pressed
    {
        vel=3;//sets velocity to set value
    }

    public int getW()//gets the width of the bird
    {
        return (int) w;
    }

    public static BufferedImage resize(BufferedImage imageIcon, double width, double height)//resizes the image to fit a space
    {
        BufferedImage bi;
        bi = new BufferedImage((int) width, (int) height, BufferedImage.TRANSLUCENT);
        //constructs new bufferedimage with specefied explicit parameters and assigns it to bi
        Graphics2D g2d = (Graphics2D) bi.createGraphics();//casts graphics of bi to graphics2D g2d
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY));
        //invokes the addrenderinghints method on g2d with specified explicit parameters
        g2d.drawImage((Image) imageIcon, 0, 0, (int) width, (int) height, null);
        //setting width/height in process
        g2d.dispose();//invokes dispose method on g2d
        return bi;//returns an imageIcon with bi
    }

    public double getBirdX()//gets the bird's x value
    {
        return x;
    }

    public double getBirdY()//gets the bird's y value
    {
        return y;
    }

    public void goFirst(boolean go)//sets the value of nothover
    {
        nothover = go;
    }

    public void die()//when the bird dies
    {
        score = 0;//resets score to 0
        alive = false;//no longer alive
        count = 0;//resets count
        y = 350;//resets y to 350
        vel = 0;//resets velocity to 0
        nothover = false;//now hovering
    }

    public int getScore()//gets the bird's score
    {
        return score;
    }

    public void addScore()//adds to the bird's score
    {
        score++;//score goes up by one
        if(score>highscore)// if the score is greater than the highscore
        {
            highscore = score;//sets new highscore
            saveScore();//saves the score
        }
    }

    public int getHighScore()//gets the highscore of the bird
    {
        return highscore;
    }

    public void saveScore()//saves the highscore
    {
        try
        (
        FileWriter fw = new FileWriter("out.txt", false);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw)//so it can save highscore to the out.txt file
        )
        {
            out.println(highscore);//uses printwriter to print highscore to out.txt
        } catch (IOException e) {
            //exception handling
        }
    }

    public void resetScore()//resets highscore
    {
        highscore = 0;//highscore is back to 0
        saveScore();//saves new highscore
    }

    public void printInstructions()
    {
        System.out.println("How to play:\n" +
            "Space bar makes you go up.\nGravity makes you go back down.\n\n"+
            "Avoid the green pipes.\nEvery time you pass a pipe, you get a point.\n\n"+
            "Hit R to reset high score.");//game instructions
    }

    public boolean getNotHover()//gets whether or not the bird is hovering
    {
        return nothover;
    }

    public void actionPerformed(ActionEvent e)
    {
        count++;//counts the milliseconds
        if(alive)//if the bird is alive
        {
            if(nothover)//if the bird is not hovering
            {
                if(y<-100)//if the bird is too much above the screen, stops the bird
                {
                    vel=0;
                }
                vel+=acc;//changes velocity by accelleration due to gravity
                y-=vel;//canges position by velocity
            }
            else//if hovering
            {
                startrate = Math.cos(.02*count);//startrate is based off of cosine, so bird goes up and down
                y+=0.1*startrate;//changes position by startrate
            }
        }
        else//if not alive
        {
            if(count==100)//after a tenth of a second
            {
                die();//kills the bird
                alive = true;//sets back to alive
                nothover = false;//back to hovering
            } else
            {
                vel = 0;//otherwise keeps the same position
            }
        }

        repaint();//repaints the bird
    }

}
