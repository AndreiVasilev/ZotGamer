package edu.uci.main;

import edu.uci.bejeweled.BejeweledFactory;
import edu.uci.bejeweled.BejeweledWindowFactory;
import edu.uci.puzzlebobble.PuzzleBobbleFactory;
import edu.uci.puzzlebobble.PuzzleBobbleWindowFactory;
import edu.uci.tmge.GameLauncher;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class ApplicationMain extends Application {

  private final GameLauncher gameLauncher;

  public ApplicationMain() {
    gameLauncher = new GameLauncher();
    gameLauncher.registerGame(GameType.BEJEWELED.name(), new BejeweledFactory(), new BejeweledWindowFactory());
    gameLauncher.registerGame(GameType.PUZZLE_BOBBLE.name(), new PuzzleBobbleFactory(), new PuzzleBobbleWindowFactory());
  }

  @Override
  public void start(Stage primaryStage) {
    final Scene mainScene = new Scene(new MainViewController(gameLauncher));
    primaryStage.setTitle("Zot Gamer");
    primaryStage.setScene(mainScene);
    primaryStage.setResizable(false);
    primaryStage.show();
  }

  public static void main(String[] args) {
    Application.launch(args);
  }
}
