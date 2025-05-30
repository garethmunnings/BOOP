package com.example.boop;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {
    private static Map<String, Clip> loadedClips = new HashMap<>();

    public static void playSound(String soundFileName) {
        try {
            Clip clip = getClip(soundFileName);
            if (clip != null) {
                clip.setFramePosition(0); // Reset to beginning
                clip.start();
            }
        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
    }

    public static void playSoundLoop(String soundFileName) {
        try {
            Clip clip = getClip(soundFileName);
            if (clip != null) {
                clip.setFramePosition(0); // Reset to beginning
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (Exception e) {
            System.err.println("Error playing looped sound: " + e.getMessage());
        }
    }

    public static void stopSound(String soundFileName) {
        Clip clip = loadedClips.get(soundFileName);
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public static void stopAllSounds() {
        for (Clip clip : loadedClips.values()) {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
        }
    }

    private static Clip getClip(String soundFileName) {
        // Check if already loaded
        if (loadedClips.containsKey(soundFileName)) {
            return loadedClips.get(soundFileName);
        }

        try {
            AudioInputStream audioInputStream = null;

            // Try multiple resource paths
            String[] resourcePaths = {
                    "/sounds/" + soundFileName,
                    "/com/example/boop/sounds/" + soundFileName,
                    "/" + soundFileName
            };

            for (String resourcePath : resourcePaths) {
                InputStream is = SoundPlayer.class.getResourceAsStream(resourcePath);
                if (is != null) {
                    BufferedInputStream bis = new BufferedInputStream(is);
                    audioInputStream = AudioSystem.getAudioInputStream(bis);
                    break;
                }
            }

            // Fallback to file system
            if (audioInputStream == null) {
                File soundFile = new File("src/main/resources/sounds/" + soundFileName);
                if (soundFile.exists()) {
                    audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                }
            }

            if (audioInputStream == null) {
                System.err.println("Sound file not found: " + soundFileName);
                return null;
            }

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            loadedClips.put(soundFileName, clip);

            return clip;

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error loading sound: " + soundFileName + " - " + e.getMessage());
            return null;
        }
    }
}