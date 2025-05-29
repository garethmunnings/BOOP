package com.example.boop;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MainScreenController {
    @FXML
    private GridPane gridPane;
    @FXML Label playerTurnLabel;
    @FXML private GridPane player1GridPane;
    @FXML private GridPane player2GridPane;
    @FXML private Label winLabel;
    @FXML private VBox winVBox;
    @FXML private Button homeButton;

    Game game;

    @FXML
    private void initialize() {
        game = new Game();
        drawGrid();
        drawPlayerPools();
    }

    private void drawPlayerPools(){
        player1GridPane.getChildren().clear();
        player2GridPane.getChildren().clear();
        drawPool(1);
        drawPool(2);
    }
    private void drawPool(int playernum){

        Player player;
        if(playernum == 1)
            player = game.getPlayer1();
        else if(playernum == 2)
            player = game.getPlayer2();
        else{
            System.out.println("Invalid playernum");
            return;
        }
        int i = 0;
        for (Kitten kitten : player.getPool().getKittenPool()) {
            Pane pane = new Pane();
            pane.setPrefSize(100, 100);
            pane.getStyleClass().add("pool-tile");

            ImageView iv = kitten.getIV();
            pane.getChildren().add(iv);
            iv.setLayoutX(10);
            iv.setLayoutY(10);

            if(i < 4) {
                if (playernum == 1)
                    player1GridPane.add(pane, 0, i);
                else
                    player2GridPane.add(pane, 0, i);
            }
            else {
                if (playernum == 1)
                    player1GridPane.add(pane, 1, i - 4);
                else
                    player2GridPane.add(pane, 1, i - 4);
            }

            Tile t = new Tile(-1,-1);
            t.setFeline(kitten);
            setUpDragStartEvent(pane, t, true);
            i++;
        }
        for (Cat cat : player.getPool().getCatPool()) {
            Pane pane = new Pane();
            pane.setPrefSize(100, 100);
            pane.getStyleClass().add("pool-tile");

            ImageView iv = cat.getIV();
            pane.getChildren().add(iv);

            if(i < 4) {
                if (playernum == 1)
                    player1GridPane.add(pane, 0, i);
                else
                    player2GridPane.add(pane, 0, i);
            }
            else {
                if (playernum == 1)
                    player1GridPane.add(pane, 1, i - 4);
                else
                    player2GridPane.add(pane, 1, i - 4);
            }

            Tile t = new Tile(-1,-1);
            t.setFeline(cat);
            setUpDragStartEvent(pane, t, true);
            i++;
        }
    }

    private void changePlayerTurnLabel() {
        playerTurnLabel.setText("Player " + game.getPlayerTurn() + "'s turn");
    }

    //Gets information on what the current bed looks like
    //creates references to the panes
    private void drawGrid() {
        gridPane.getChildren().clear();

        for (int row = 0; row < game.getBed().getRows(); row++) {
            for (int col = 0; col < game.getBed().getCols(); col++) {
                //get current tile from bed
                Tile tile = game.getBed().getTile(row, col);

                //create pane
                Pane pane = new Pane();
                pane.setPrefSize(100, 100);
                pane.getStyleClass().add("tile");

                setUpDragFromBedEvents(pane, tile, row, col);

                //set up mouse click events
                int r = row; int c = col;
                pane.setOnMouseClicked(e -> handleTileClick(pane));

                //display image from tile
                if (!tile.isEmpty()) {
                    Feline feline = tile.getFeline();
                    ImageView iv = feline.getIV();
                    pane.getChildren().add(iv);
                    iv.setLayoutX(10);
                    iv.setLayoutY(10);

                    setUpDragStartEvent(pane, tile, false);
                }
                gridPane.add(pane, col, row);
            }
        }
    }
    private void setUpDragStartEvent(Pane pane, Tile tile, boolean fromPool){
        if(game.getCurrentPlayer().getNum() == tile.getFeline().getPlayer()){

            pane.setOnMouseEntered(event -> pane.setCursor(Cursor.OPEN_HAND));
            pane.setOnMousePressed(event -> pane.setCursor(Cursor.CLOSED_HAND));
            pane.setOnMouseExited(event -> pane.setCursor(Cursor.DEFAULT));

            pane.setOnDragDetected(event -> {
                //save current tile information in dragContext
                DragContext.draggedObject = tile;
                DragContext.fromPool = fromPool;

                //start the drag
                Dragboard db = pane.startDragAndDrop(TransferMode.MOVE);

                //remove feline from pool
                if(fromPool) {
                    int p = tile.getFeline().getPlayer();
                    if (p == 1) {
                        if (tile.getFeline() instanceof Kitten)
                            game.getPlayer1().getPool().removeKitten((Kitten) tile.getFeline());
                        else if(tile.getFeline() instanceof Cat)
                            game.getPlayer1().getPool().removeCat((Cat) tile.getFeline());
                    }
                    if (p == 2) {
                        if (tile.getFeline() instanceof Kitten)
                            game.getPlayer2().getPool().removeKitten((Kitten) tile.getFeline());
                        else if(tile.getFeline() instanceof Cat)
                            game.getPlayer2().getPool().removeCat((Cat) tile.getFeline());
                    }
                }

                ClipboardContent content = new ClipboardContent();
                content.putString("feline");
                db.setContent(content);

                // Optional: Show visual feedback during drag
                SnapshotParameters snapParams = new SnapshotParameters();
                snapParams.setFill(Color.TRANSPARENT);
                WritableImage snapshot = pane.getChildren().getFirst().snapshot(snapParams, null);
                db.setDragView(snapshot, snapshot.getWidth() / 2, snapshot.getHeight() / 2);

                event.consume();
            });
        }
        else {
            pane.setOnDragDetected(null); // disable dragging
        }
    }

    private void setUpDragFromBedEvents(Pane pane,Tile tile, int row, int col) {
        int r = row; int c = col;

        pane.setOnDragOver(event -> {
            if (event.getGestureSource() != pane && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        //add indicator when hovering over pane
        pane.setOnDragEntered(event -> {
            pane.getStyleClass().add("hover-indicator");
        });
        pane.setOnDragExited(event -> {
            pane.getStyleClass().remove("hover-indicator");
        });

        pane.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if(tile.isEmpty()) {

                if (db.hasString()) {
                    Tile t = (Tile) DragContext.draggedObject;
                    if (DragContext.fromPool) {//remove feline from pool

                        if (t.getFeline() instanceof Cat) {
                            if (t.getFeline().player == 1)
                                game.getPlayer1().getPool().removeCat((Cat) t.getFeline());
                            else
                                game.getPlayer2().getPool().removeCat((Cat) t.getFeline());
                        } else {
                            if (t.getFeline().player == 1)
                                game.getPlayer1().getPool().removeKitten((Kitten) t.getFeline()); //not finding kitten
                            else if (t.getFeline().player == 2)
                                game.getPlayer2().getPool().removeKitten((Kitten) t.getFeline()); //not finding kitten
                        }
                    }
                    t.getFeline().setInPlay();
                    tile.setFeline(t.getFeline());
                    if (!DragContext.fromPool) //set the tile on the beds feline to null
                        game.getBed().getTile(t.getRow(), t.getCol()).setFeline(null);

                    ArrayList<Feline> felinesBoopedOffBed;
                    felinesBoopedOffBed = game.getBed().updateTile(r, c, tile, false);

                    for (Feline f : felinesBoopedOffBed) {
                        f.setOutOfPlay();

                        if (f.getPlayer() == 1) {
                            game.getPlayer1().getPool().addFeline(f);
                        } else if (f.getPlayer() == 2) {
                            game.getPlayer2().getPool().addFeline(f);
                        }

                    }

                    drawPlayerPools();
                    pane.getChildren().add(tile.getFeline().getIV());


                    success = true;
                    if (game.getBed().threeCatsInARow()) {
                        winVBox.setVisible(true);
                        winLabel.setText("Player " + game.getPlayerTurn() + " wins!");
                        homeButton.setOnAction((actionEvent -> {
                            Main main = new Main();
                            try {
                                main.start(new Stage());

                                Stage stage = (Stage) homeButton.getScene().getWindow();
                                stage.close();
                            } catch (Exception e) {

                            }
                        }));
                    }
                    game.endTurn();

                    if (game.getBed().threeKittensInARow() != null) {
                        upgradeKittensToCats(game.getBed().threeKittensInARow());
                    }

                    drawPlayerPools();
                    changePlayerTurnLabel();
                }
                drawGrid();

                event.setDropCompleted(success);
                event.consume();
            }
        });

        pane.setOnMouseDragged(event -> pane.setCursor(Cursor.CLOSED_HAND));
    }

    private void upgradeKittensToCats(ArrayList<Feline> kittensInARow) {
        int player = kittensInARow.getFirst().getPlayer();
        if(player == 1)
            for(Feline f : kittensInARow){
                Kitten k = (Kitten)f;
                game.getBed().getTileWithFeline(k).setEmpty();

                game.getPlayer1().getPool().addCat(new Cat(player));

            }
        else if(player == 2)
        {
            for(Feline f : kittensInARow) {
                Kitten k = (Kitten)f;
                game.getBed().getTileWithFeline(k).setEmpty();
                game.getPlayer2().getPool().addCat(new Cat(player));
            }
        }
        drawPlayerPools();
    }

    private void handleTileClick(Pane pane) {

    }

}
