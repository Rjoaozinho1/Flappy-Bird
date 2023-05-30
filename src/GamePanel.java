import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    private Game game;

    public GamePanel() {
        game = new Game(); // Cria uma instância do jogo
        new Thread(this).start(); // Inicia uma nova thread para executar o método run()
    }

    public void update() {
        game.update(); // Atualiza o estado do jogo
        repaint(); // Redesenha o painel
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;

        // Desenha os elementos do jogo
        for (Render r : game.getRenders()) {
            if (r.transform != null) {
                g2D.drawImage(r.image, r.transform, null); // Desenha a imagem com transformação
            } else {
                g.drawImage(r.image, r.x, r.y, null); // Desenha a imagem nas coordenadas (x, y)
            }
        }

        g2D.setColor(Color.BLACK);

        if (!game.started) {
            g2D.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g2D.drawString("Press SPACE to start", 150, 240); // Exibe uma mensagem de instrução para iniciar o jogo
        } else {
            g2D.setFont(new Font("TimesRoman", Font.PLAIN, 24));
            g2D.drawString(Integer.toString(game.score), 10, 465); // Exibe a pontuação atual do jogo
        }

        if (game.gameover) {
            g2D.setFont(new Font("TimesRoman", Font.PLAIN, 20));
            g2D.drawString("Press R to restart", 150, 240); // Exibe uma mensagem para reiniciar o jogo após o game over
        }
    }

    public void run() {
        try {
            while (true) {
                update(); // Atualiza o jogo e redesenha o painel
                Thread.sleep(25); // Aguarda um curto intervalo de tempo para limitar a taxa de atualização do jogo
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
