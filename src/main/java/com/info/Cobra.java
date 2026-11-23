package com.info;

public class Cobra extends Movel{
    private int dx;
    private int dy;

    public Cobra(int x, int y){
        setX(x);
        setY(y);
        setAltura(20);
        setLargura(20);
    }

    public void mexer(){
        setX(getX() + dx);
        setY(getY() + dy);
    }

    public void setDirecao(int dx, int dy){
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }
}
