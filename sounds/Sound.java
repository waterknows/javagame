package _08final.sounds;

import javax.sound.sampled.*;

//I have rewritten this player based on http://stackoverflow.com/questions/21128797/audioinputstream-is-not-working
// The original one will cause invalid format errors

public class Sound {

    public enum SoundEffect {
        SHOT,
        WARN,
        KILL,
        BANG,
        START,
        THEME,
    }

    /**
     * Retrieves the file path for a sound effect
     * @param effect - the file path of the sound effect to load
     */
    private static String getSoundEffectFile(SoundEffect effect) {

        String file = "";
        switch (effect) {
            case SHOT:
                file = "shot.wav";
                break;
            case WARN:
                file = "enemyFlying.wav";
                break;
            case KILL:
                file = "enemyKilled.wav";
                break;
            case BANG:
                file = "bang.wav";
                break;
            case START:
                file = "levelStart.wav";
                break;
            case THEME:
                file = "themeSong.wav";
                break;
        }
        return file;
    }

    /**
     * Plays an individual sound effect.
     * @param effect the type of sound effect to play
     * @cite: http://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
     * */
    public static synchronized void playSoundEffect(SoundEffect effect) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clp = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.getClass().getResource(getSoundEffectFile(effect)));
                    AudioFormat format = inputStream.getFormat();
                    DataLine.Info info = new DataLine.Info(Clip.class, format); //format input
                    Clip clip = (Clip)AudioSystem.getLine(info);
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());

                }
            }
        }).start();

    }


    /**
     * Continuously plays a sound effect file.
     * @param effect the path to the sound effect to load.
     * @cite: http://stackoverflow.com/questions/4875080/music-loop-in-java
     * */
    public static Clip clipForLoopFactory(SoundEffect effect){

        Clip clp = null;

        // this line caused the original exceptions
        try {
            AudioInputStream aisStream =
                    AudioSystem.getAudioInputStream(Sound.class.getResource(getSoundEffectFile(effect)));
            clp = AudioSystem.getClip();
            clp.open( aisStream );

        } catch (UnsupportedAudioFileException exp) {
            exp.printStackTrace();
        } catch(Exception exp){
            System.out.println("error");
        }

        return clp;

    }




}
