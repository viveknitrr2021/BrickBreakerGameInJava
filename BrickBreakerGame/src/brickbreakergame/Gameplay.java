/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brickbreakergame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author VIVEK KUMAR SINGH
 */
public class Gameplay extends JPanel implements KeyListener, ActionListener {
    
    private boolean play = false;
    private int score = 0;
    private int totalBricks = 21;
    
    private Timer  timer;
    private int delay = 8;//to decide how fast our ball should move
    
    // properties for x-axis and y-axis for our ball and slider
    private int playerX = 310;
    
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    
    private MapGenerator map;
    
    public Gameplay() {
            map = new MapGenerator(3,7);
            addKeyListener(this);
            setFocusable(true);
            setFocusTraversalKeysEnabled(false);
            timer = new Timer(delay,this);
            timer.start();
    }
    
    //used to draw differnt shapes
    public void paint(Graphics  g){
        //the four parameters in fillRect are x,y,width,height
        
        //background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);
        
        //drawing boxes(map)
        map.draw( (Graphics2D)g);
        
        //border
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);
        
        //scores
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,25));
        g.drawString(""+score, 590, 30);
        
        //the paddle/slider
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);
        
        //the ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);
        
        if(totalBricks <=0){
             play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("You Scored Perfectly ,Your score is: "+score, 110, 300);
            
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart ", 230, 350);
            
        }
        
        //game over and restart logic
        if(ballposY >580){
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game Over Your score is: "+score, 160, 300);
            
            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("Press Enter to Restart ", 230, 350);
            
            
        }
        
        g.dispose();
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public void keyReleased(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {
       if(ke.getKeyCode() == KeyEvent.VK_RIGHT){
           if(playerX >=600){
               playerX = 600;
           }else{
               moveRight();
           }
       }
        if(ke.getKeyCode() == KeyEvent.VK_LEFT){
           if(playerX < 10){
               playerX = 10;
           }else{
               moveLeft();
           }
       }
        
         if(ke.getKeyCode() == KeyEvent.VK_ENTER){
                if(!play){
                    play = true;
                    ballposX = 120;
                    ballposY = 350;
                    ballXdir = -1;
                    ballYdir = -2;
                    playerX = 310;
                    score = 0;
                    totalBricks = 21;
                    map = new MapGenerator(3,7);
                    
                    repaint();
                }
         }
    }
    
    public void moveRight(){
        play = true;
        playerX+=20;
    }
    public void moveLeft(){
        play = true;
        playerX-=20;
    }


    @Override
    public void actionPerformed(ActionEvent ae) {
        timer.start();
        
        if(play){
            //for detecting intersection of ball and slider 
            //a new rectanle is formed using the ball's parameter to do the coparison
            //.intersects() method is called to find out if they intersect or not
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8)) ){
                    ballYdir = -ballYdir;
            }
            
            //here we are accessing 2d array "map" of MapGeneratorClass using object map of this class
            A:  for(int i=0;i<map.map.length;i++){
                    for(int j=0;j<map.map[0].length;j++){
                            if(map.map[i][j] > 0 ){
                                    int brickX = j*map.brickWidth +80;
                                    int brickY = i*map.brickHeight +50;
                                    int brickWidth = map.brickWidth;
                                    int brickHeight = map.brickHeight;
                                    
                                    //creating a rectangle around that brick 
                                    Rectangle rect = new Rectangle( brickX,  brickY, brickWidth, brickHeight );
                                    //creating a rectangle around the ball too
                                    //so as to detect the intersection
                                    Rectangle ballRect = new Rectangle( ballposX,  ballposY, 20, 20);
                                    Rectangle brickRect = rect;
                                    
                                    if(ballRect.intersects(brickRect)){
                                            map.setBrickValue(0, i, j);
                                            totalBricks--;
                                            score += 5;
                                            
                                            //for moving the ball in opposite direction when striking the rectangles
                                            if(ballposX+19 <=brickRect.x || ballposX+1 >= brickRect.x + brickRect.width){
                                                ballXdir = -ballXdir;
                                            }else{
                                                ballYdir = -ballYdir;
                                            }
                                            
                                            break A;
                                            //break label to break out of the labelled for loop
                                    }
                            }
                    }
            }
            
            ballposX += ballXdir;
            ballposY += ballYdir;
            //detecting right boundary for the ball
            if(ballposX<0){
                ballXdir = -ballXdir;
            }
            //detecting top boundary for the ball
            if(ballposY<0){
                ballYdir = -ballYdir;
            }
            //detecting right boundary  for the ball
            if(ballposX>670){
                ballXdir = -ballXdir;
            }
        }
        
        repaint();
    }
        
}
