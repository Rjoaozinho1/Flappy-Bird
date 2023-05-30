import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

public class Bird {

    public int x;
    public int y;
    public int width;
    public int height;

    public boolean dead;

    public double yvel;
    public double gravity;

    private int jumpDelay;
    private double rotation;

    private Image image;
    private Keyboard keyboard;

    public Bird() {
        // Inicializa os atributos do pássaro
        x = 100;
        y = 150;
        yvel = 0;
        width = 45;
        height = 32;
        gravity = 0.5;
        jumpDelay = 0;
        rotation = 0.0;
        dead = false;

        keyboard = Keyboard.getInstance();
    }

    public void update() {
        yvel += gravity; // Aplica a gravidade ao pássaro

        if (jumpDelay > 0)
            jumpDelay--;

        if (!dead && keyboard.isDown(KeyEvent.VK_SPACE) && jumpDelay <= 0) {
            yvel = -10; // Faz o pássaro pular quando o jogador pressiona a tecla de espaço
            jumpDelay = 10;
        }

        y += (int)yvel; // Atualiza a posição vertical do pássaro com base na sua velocidade
    }

    public Render getRender() {
        Render r = new Render();
        r.x = x;
        r.y = y;

        if (image == null) {
            image = Util.loadImage("lib/bird.png"); // Carrega a imagem do pássaro se ainda não foi carregada
        }
        r.image = image;

        // Calcula a rotação do pássaro com base na sua velocidade vertical
        rotation = (90 * (yvel + 20) / 20) - 90;
        rotation = rotation * Math.PI / 180;

        if (rotation > Math.PI / 2)
            rotation = Math.PI / 2;

        // Cria uma transformação de afinidade para aplicar a rotação à imagem
        r.transform = new AffineTransform();
        r.transform.translate(x + width / 2, y + height / 2);
        r.transform.rotate(rotation);
        r.transform.translate(-width / 2, -height / 2);

        return r; // Retorna o objeto Render que representa o pássaro a ser desenhado na tela
    }
}
