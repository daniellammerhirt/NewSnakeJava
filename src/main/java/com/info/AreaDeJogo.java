package com.info;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class AreaDeJogo extends JPanel implements ActionListener {
    private Fruta fruta;
    private Cobra cobra;
    private List<Cobra> corpo;
    private List<Cenario> cenario;
    private final Timer timer;
    private String msg = "";
    private boolean gameOver;

    public AreaDeJogo(int largura, final int altura){
        setFocusable(true);
        setSize(largura, altura);
        inicializa();
        timer = new Timer(100, this);
        timer.start();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                verificaTeclado(e.getKeyCode());
            }
        });
    }
    public void verificaTeclado(int tecla){
        switch (tecla){
            case KeyEvent.VK_UP ->
cobra.setDirecao(0, -cobra.getAltura());
            case KeyEvent.VK_DOWN ->
                cobra.setDirecao(0, cobra.getAltura());
            case KeyEvent.VK_LEFT ->
                cobra.setDirecao(-cobra.getLargura(), 0);
            case KeyEvent.VK_RIGHT ->
                cobra.setDirecao(cobra.getLargura(), 0);
            case KeyEvent.VK_P ->
                pausa();
            case KeyEvent.VK_R ->
                resetaJogo();
            case KeyEvent.VK_ESCAPE ->
                sair();
        }
    }


    private void vericaSeComeuFruta(){
        if (verificaColisao(fruta, cobra)){
            corpo.add(new Cobra(0,0));

            posicionaFruta();
        }
    }

    private void sair(){
        timer.stop();
        if (JOptionPane.showConfirmDialog(null, "Deseja realmente sair do jogo?", "Sair",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            System.exit(0);
        } else {
            if (!gameOver)
                timer.restart();
        }
    }

    private void moverCobra(){
        for (int i = corpo.size() - 1; i > 0; i--){
            corpo.get(i).setX(corpo.get(i - 1).getX());
            corpo.get(i).setY(corpo.get(i - 1).getY());
        }
        corpo.get(0).setX(cobra.getX());
        corpo.get(0).setY(cobra.getY());
        cobra.mexer();
    }
    private void pausa(){
        if (timer.isRunning()) {
            msg = "Pausa";
            repaint();
            revalidate();
            timer.stop();
        } else {
            if (!gameOver){
                timer.restart();
                msg = "";
            }
        }
    }
    private void resetaJogo(){
        inicializa();
        timer.start();
    }

    private void gameOver(){
        timer.stop();
        msg = "Game Over";
        gameOver = true;
    }

    private void posicionaFruta(){
        boolean ok = false;
        while (!ok){
            ok=true;
            int x = new Random().nextInt(getWidth());
            int y = new Random().nextInt(getHeight());
            fruta = new Fruta(x, y);
            for (Cobra parte : corpo){
                if (verificaColisao(fruta, parte)){
                    ok = false;
                    break;
                }
            }
            for (Cenario pecaCenario : cenario){
                if (verificaColisao(fruta, pecaCenario)){
                    ok = false;
                    break;
                }
            }
        }
    }

    private void inicializa(){
        int posicaoCobraX = this.getWidth() / 2;
        int posicaoCobraY = this.getHeight() / 2;
        cobra = new Cobra(posicaoCobraX, posicaoCobraY);
        cobra.setDx(-cobra.getLargura());
        corpo = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            corpo.add(new Cobra(posicaoCobraX + cobra.getLargura() + (i *
                    cobra.getLargura()), posicaoCobraY));
        }
        msg = "";
        gameOver = false;
        montaCenario();
        posicionaFruta();
    }

    private boolean verificaColisao(Object obj1, Object obj2){
        return ((Movel) obj1).getLimites().intersects(((Movel) obj2).getLimites());
    }

    private boolean verificaColisaoCorpo(){
        for (Cobra parteCorpo : corpo){
            if (verificaColisao(cobra, parteCorpo)){
                return true;
            }
        }
        return false;
    }
    private boolean verificaColisaoCenario(){
        for (Cenario pecaCenario: cenario){
            if (verificaColisao(cobra, pecaCenario)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void paint(Graphics g) {
        desenhaFundo(g);
        desenhaCobra(g);
        desenhaCenario(g);
        desenhaFruta(g);
        desenhaMensagem(g);
    }

    private void desenhaFundo(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void desenhaFruta(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(fruta.getX(), fruta.getY(), fruta.getLargura(),
                fruta.getAltura());
    }

    private void desenhaCenario(Graphics g){
        g.setColor(Color.BLUE);
        for (Cenario pecaCenario : cenario){
            g.fillRect((int) pecaCenario.getX(), (int) pecaCenario.getY(),
                    pecaCenario.getLargura(), pecaCenario.getAltura());
        }
    }

    private void desenhaCobra(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect((int) cobra.getX(), (int) cobra.getY(), cobra.getLargura(),
                cobra.getAltura());
        g.setColor(new Color(0, 150, 0));
        for (Cobra parteCorpo : corpo){
            g.fillRect((int) parteCorpo.getX(), (int) parteCorpo.getY(),
                    parteCorpo.getLargura(), parteCorpo.getAltura());
        }
    }

    private void desenhaMensagem(Graphics g){
        if (!msg.isEmpty()){
            g.setColor(Color.WHITE);
            Font f = new Font("Arial", Font.BOLD, 20);
            g.setFont(f);
            FontMetrics fm = g.getFontMetrics(f);
            Rectangle2D rect = fm.getStringBounds(msg, g);
            int textWidth = (int) rect.getWidth();
            int x = (getWidth() - textWidth) / 2;
            g.drawString(msg, x, 3 * getHeight() / 4);
        }
    }

    private void montaCenario(){
        cenario = new ArrayList<>();
        cenario.add(new Cenario(0, 0, 20, this.getHeight()));
        cenario.add(new Cenario(0, 0, this.getWidth(), 25));
        cenario.add(new Cenario(0, this.getHeight() - 20, this.getWidth(), 20));
        cenario.add(new Cenario(this.getWidth() - 20, 0, 20, this.getHeight()));
        cenario.add(new Cenario(this.getWidth() / 4, this.getHeight() / 4, 20,
                this.getHeight()/ 2));
        cenario.add(new Cenario(3 * this.getWidth() / 4, this.getHeight() / 4, 20,
                this.getHeight()/ 2));
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        moverCobra();
        vericaSeComeuFruta();
        if (verificaColisaoCorpo() || verificaColisaoCenario())
            gameOver();

        repaint();
        revalidate();
    }
}


