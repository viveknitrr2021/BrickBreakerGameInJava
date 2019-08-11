/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brickbreakergame;

import javax.swing.JFrame;

/**
 *
 * @author VIVEK KUMAR SINGH
 */
public class Main {
        
        public static void main(String[] args) {
            //Creating JFrame and Setting its properties
                    JFrame obj = new JFrame();
                    Gameplay gamePlay = new Gameplay();
                    obj.setBounds(10,10,700,600);
                    obj.setTitle("My Brick Breaker Game");
                    obj.setResizable(false);
                    obj.setVisible(true);
                    obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    obj.add(gamePlay);
         }
        
}
