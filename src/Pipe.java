import java.awt.Image;

public class Pipe {

    public int x;
    public int y;
    public int width;
    public int height;
    public int speed = 3;

    public String orientation;

    private Image image;

    public Pipe(String orientation) {
        this.orientation = orientation;
        reset(); // Reinicializa o cano
    }

    public void reset() {
        width = 66;
        height = 400;
        x = App.WIDTH + 2; // Define a posição inicial do cano fora da tela

        if (orientation.equals("south")) {
            y = -(int)(Math.random() * 120) - height / 2; // Define a posição vertical do cano superior de forma aleatória
        }
    }

    public void update() {
        x -= speed; // Move o cano para a esquerda com base na velocidade definida
    }

    public boolean collides(int _x, int _y, int _width, int _height) {
        int margin = 2;

        // Verifica se há colisão entre o cano e um retângulo definido pelas coordenadas e dimensões passadas

        if (_x + _width - margin > x && _x + margin < x + width) {

            if (orientation.equals("south") && _y < y + height) {
                return true; // Colisão com o cano superior (orientação sul)
            } else if (orientation.equals("north") && _y + _height > y) {
                return true; // Colisão com o cano inferior (orientação norte)
            }
        }

        return false; // Sem colisão
    }

    public Render getRender() {
        Render r = new Render();
        r.x = x;
        r.y = y;

        if (image == null) {
            image = Util.loadImage("lib/pipe-" + orientation + ".png"); // Carrega a imagem do cano com base na orientação
        }
        r.image = image;

        return r; // Retorna o objeto Render que representa o cano a ser desenhado na tela
    }
}
