package edu.uci.bejeweled;


import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class BejeweledViewController extends HBox {

  public BejeweledViewController() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/BejeweledView.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);
    try {
      fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }
  }

}
