import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

public class Render {
    public int x;
    public int y;
    public Image image;
    public AffineTransform transform;

    public Render() {
        // Construtor vazio
    }

    public Render(int x, int y, String imagePath) {
        Toolkit.getDefaultToolkit().sync();
        this.x = x;
        this.y = y;
        this.image = Util.loadImage(imagePath); // Carrega a imagem especificada pelo caminho do arquivo
    }
}
