import java.awt.Image;
import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Util {

    private static HashMap<String, Image> cache = new HashMap<String, Image>();
	private static HashMap<String, Clip> soundCache = new HashMap<>();

    public static Image loadImage(String path) {
        Image image = null;

        if (cache.containsKey(path)) {
            return cache.get(path); // Retorna a imagem do cache se já foi carregada anteriormente
        }

        try {
            image = ImageIO.read(new File(path)); // Carrega a imagem do arquivo

            if (!cache.containsKey(path)) {
                cache.put(path, image); // Armazena a imagem no cache para uso futuro
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }

        return image; // Retorna a imagem carregada
    }

    public static Clip loadSound(String path) {
        Clip sound = soundCache.get(path);
        if (sound == null) {
            try {
                sound = AudioSystem.getClip();
                sound.open(AudioSystem.getAudioInputStream(new File(path)));
                soundCache.put(path, sound);
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }
        return sound;
    }
}