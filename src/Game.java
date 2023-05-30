import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.sound.sampled.Clip;

public class Game {

    public static final int PIPE_DELAY = 100;

    private Boolean paused;

    private Clip windSound;

    private int pauseDelay;
    private int restartDelay;
    private int pipeDelay;

    private Bird bird;
    private ArrayList<Pipe> pipes;
    private Keyboard keyboard;

    public int score;
    public Boolean gameover;
    public Boolean started;

    public Game() {
        keyboard = Keyboard.getInstance();
        windSound = Util.loadSound("lib/wind.wav");
        restart();
    }

    private void playWindSound() {
        if (!windSound.isRunning()) {
            windSound.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }    

    public void restart() {
        // Reinicializa o estado do jogo
        paused = false;
        started = false;
        gameover = false;

        score = 0;
        pauseDelay = 0;
        restartDelay = 0;
        pipeDelay = 0;

        bird = new Bird();
        pipes = new ArrayList<Pipe>();
    }

    public void update() {
        watchForStart(); // Verifica se o jogador pressionou a tecla de espaço para começar o jogo

        if (!started)
            return;

        watchForPause(); // Verifica se o jogador pressionou a tecla P para pausar o jogo
        watchForReset(); // Verifica se o jogador pressionou a tecla R para reiniciar o jogo

        if (paused)
            return;

        bird.update(); // Atualiza o estado do pássaro

        if (gameover)
            return;

        movePipes(); // Move os canos horizontalmente
        checkForCollisions(); // Verifica colisões entre o pássaro e os canos
        
        playWindSound(); // Reproduz o som de vento durante o jogo
    }

    public ArrayList<Render> getRenders() {
        // Retorna uma lista de objetos Render que representam os elementos a serem desenhados na tela
        ArrayList<Render> renders = new ArrayList<Render>();
        renders.add(new Render(0, 0, "lib/background.png")); // Fundo do jogo
        for (Pipe pipe : pipes)
            renders.add(pipe.getRender()); // Canos
        renders.add(new Render(0, 0, "lib/foreground.png")); // Primeiro plano
        renders.add(bird.getRender()); // Pássaro
        return renders;
    }

    private void watchForStart() {
        if (!started && keyboard.isDown(KeyEvent.VK_SPACE)) {
            started = true; // Inicia o jogo quando o jogador pressiona a tecla de espaço
        }
    }

    private void watchForPause() {
        if (pauseDelay > 0)
            pauseDelay--;

        if (keyboard.isDown(KeyEvent.VK_P) && pauseDelay <= 0) {
            paused = !paused; // Pausa ou despausa o jogo quando o jogador pressiona a tecla P
            pauseDelay = 10;
        }
    }

    private void watchForReset() {
        if (restartDelay > 0)
            restartDelay--;

        if (keyboard.isDown(KeyEvent.VK_R) && restartDelay <= 0) {
            restart(); // Reinicia o jogo quando o jogador pressiona a tecla R
            restartDelay = 10;
            return;
        }
    }

    private void movePipes() {
        pipeDelay--;

        if (pipeDelay < 0) {
            pipeDelay = PIPE_DELAY;
            Pipe northPipe = null;
            Pipe southPipe = null;

            // Procura por canos fora da tela
            for (Pipe pipe : pipes) {
                if (pipe.x - pipe.width < 0) {
                    if (northPipe == null) {
                        northPipe = pipe;
                    } else if (southPipe == null) {
                        southPipe = pipe;
                        break;
                    }
                }
            }

            // Cria um novo cano se necessário ou reseta um cano existente
            if (northPipe == null) {
                Pipe pipe = new Pipe("north");
                pipes.add(pipe);
                northPipe = pipe;
            } else {
                northPipe.reset();
            }

            if (southPipe == null) {
                Pipe pipe = new Pipe("south");
                pipes.add(pipe);
                southPipe = pipe;
            } else {
                southPipe.reset();
            }

            northPipe.y = southPipe.y + southPipe.height + 175; // Define a posição vertical do cano superior
        }

        for (Pipe pipe : pipes) {
            pipe.update(); // Move cada cano
        }
    }

    private void checkForCollisions() {
        // Verifica colisões entre o pássaro e os canos
        for (Pipe pipe : pipes) {
            if (pipe.collides(bird.x, bird.y, bird.width, bird.height)) {
                gameover = true;
                bird.dead = true;
            } else if (pipe.x == bird.x && pipe.orientation.equalsIgnoreCase("south")) {
                score++;
            }
        }

        // Verifica colisão entre o pássaro e o chão
        if (bird.y + bird.height > App.HEIGHT - 80) {
            gameover = true;
            bird.y = App.HEIGHT - 80 - bird.height;
        }
    }
}
