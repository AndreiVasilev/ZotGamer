package edu.uci.main;

import edu.uci.tmge.Scoreboard;
import edu.uci.tmge.ScoreboardEntry;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ScoreBoardController extends HBox {

  @FXML private TableView<ScoreboardEntry> bejeweledScores;
  @FXML private TableView<ScoreboardEntry> puzzleBobbleScores;

  public ScoreBoardController() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ScoreboardView.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);
    try {
      fxmlLoader.load();
    } catch (IOException exception) {
      throw new RuntimeException(exception);
    }

    final Scoreboard scoreboard = new Scoreboard();
    final Map<String, List<ScoreboardEntry>> scores = scoreboard.getHighScores();
    initializeScoreboard(bejeweledScores, scores.getOrDefault(GameType.BEJEWELED.toString(), Collections.emptyList()));
    initializeScoreboard(puzzleBobbleScores, scores.getOrDefault(GameType.PUZZLE_BOBBLE.toString(), Collections.emptyList()));
  }

  private void initializeScoreboard(final TableView<ScoreboardEntry> table, final List<ScoreboardEntry> entries) {
    final TableColumn<ScoreboardEntry, String> nameColumn = new TableColumn<>("Player");
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));

    final TableColumn<ScoreboardEntry, String> scoreColumn = new TableColumn<>("Score");
    scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

    table.getColumns().addAll(List.of(nameColumn, scoreColumn));
    table.getItems().addAll(entries);
    table.setPlaceholder(new Label("No scores recorded yet."));
  }
}
