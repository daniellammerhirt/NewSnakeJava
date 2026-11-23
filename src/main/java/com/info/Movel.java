package com.info;

import java.awt.*;

public class Movel {
    private int altura;
    private int largura;
    private int x;
    private int y;

    public Rectangle getLimites(){
        return new Rectangle((int) getX(), (int) getY(), getLargura(), getAltura());
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getLargura() {
        return largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
