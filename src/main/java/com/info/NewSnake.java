package com.info;

import javax.swing.*;
import java.awt.*;


public class NewSnake extends JFrame {
    public NewSnake(){
        int altura = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int largura = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        setSize(largura, altura);
        add(new AreaDeJogo(largura, altura));
        setResizable(false);
        setUndecorated(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public static void main(String[] args) {
       new NewSnake();
        }
    }