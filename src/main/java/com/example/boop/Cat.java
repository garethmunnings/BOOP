package com.example.boop;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cat extends Feline {
    private final Image orangeCat = new Image(getClass().getResource("/com/example/boop/images/OrangeCatLower.png").toString());
    private final Image purpleCat = new Image(getClass().getResource("/com/example/boop/images/PurpleCatLower.png").toString());
    private ImageView catIV;

    public Cat(int player) {
        active = false;
        this.player = player;
        if(player == 1)
            catIV = new ImageView(orangeCat);
        else
            catIV = new ImageView(purpleCat);

        initializeIV(catIV);

    }

    @Override
    public ImageView getIV() {
        return catIV;
    }
    @Override
    public int getPlayer() {
        return player;
    }
    @Override
    public boolean getInPlay() {
        return inPlay;
    }


}
