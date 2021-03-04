package edu.uci.main;

import edu.uci.bejeweled.Bejeweled;
import edu.uci.puzzlebobble.PuzzleBobble;
import edu.uci.tmge.GameEvent;
import edu.uci.tmge.MultiplayerGame;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainViewController extends AnchorPane {

  public MainViewController() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainView.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);
    try {
      fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  // TODO replace with game launcher and move Multiplayer game to TMGE core
  public void playBejeweled() {
    final MultiplayerGame game = new MultiplayerGame();
    final Bejeweled bejeweled1 = new Bejeweled("Some Player 1", 1);
    final Bejeweled bejeweled2 = new Bejeweled("Some Player 2", 2);

    bejeweled1.addAction(GameEvent.GAME_START, () -> {
      bejeweled1.setX(bejeweled1.getX() - 500.0);
    });

    bejeweled2.addAction(GameEvent.GAME_START, () -> {
      bejeweled2.setX(bejeweled2.getX() + 500.0);
    });

    game.addGame(bejeweled1);
    game.addGame(bejeweled2);
    game.launch();
  }

  // TODO replace with game launcher and move Multiplayer game to TMGE core
  public void playPuzzleBobble() {
    final MultiplayerGame game = new MultiplayerGame();
    final PuzzleBobble puzzle1 = new PuzzleBobble("Some Player 1", 1);
    final PuzzleBobble puzzle2 = new PuzzleBobble("Some Player 2", 2);

    puzzle1.addAction(GameEvent.GAME_START, () -> {
      puzzle1.setX(puzzle1.getX() - 500.0);
    });

    puzzle2.addAction(GameEvent.GAME_START, () -> {
      puzzle2.setX(puzzle2.getX() + 500.0);
    });

    game.addGame(puzzle1);
    game.addGame(puzzle2);
    game.launch();
  }

  public void viewPlayerProfiles() {

  }
}
