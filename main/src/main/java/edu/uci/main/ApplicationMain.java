package edu.uci.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public final class ApplicationMain extends Application {

  @Override
  public void start(Stage primaryStage) {
    final Scene mainScene = new Scene(new AnchorPane());
    primaryStage.setHeight(800);
    primaryStage.setWidth(800);
    primaryStage.setTitle("Zot Gamer");
    primaryStage.setScene(mainScene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    Application.launch(args);
  }
}
