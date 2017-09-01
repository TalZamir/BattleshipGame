package ui.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import logic.TheGame;
import logic.enums.ErrorMessages;
import logic.exceptions.XmlContentException;
import ui.verifiers.ErrorCollector;
import ui.verifiers.IInputVerifier;
import ui.verifiers.XmlFileVerifier;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by barakm on 29/08/2017
 */
public class Controller extends JPanel implements Initializable {

    private static final String USER_INTRO = null;
    private final TheGame theGame;

    @FXML
    private Button buttonGameStatus;
    @FXML
    private Button buttonStatistics;
    @FXML
    private Button buttonMine;
    @FXML
    private Button buttonPlayMove;
    @FXML
    private Button buttonQuitMatch;
    @FXML
    private Button buttonExitGame;
    @FXML
    private TableView currentPlayerBoard;
    @FXML
    private TableView opponentPlayerBoard;
    @FXML
    private Button buttonStartGame;
    @FXML
    private Button buttonLoadXml;

    public Controller() {
        theGame = new TheGame();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonLoadXml.setOnAction(this::onLoadXmlClicked);
        buttonExitGame.setOnAction(this::onExitClicked);
        buttonPlayMove.setOnAction(this::onPlayMoveClicked);
        buttonQuitMatch.setOnAction(this::onQuitMatchClicked);
        buttonStartGame.setOnAction(this::onStartGameClicked);
        buttonGameStatus.setOnAction(this::onGameStatusClicked);
        buttonStatistics.setOnAction(this::onStatisticsClicked);
        buttonMine.setOnAction(this::onPlaceMineClicked);
    }

    private void onPlaceMineClicked(ActionEvent event) {
        if (theGame.isGameOn()) {
            if (theGame.hasMines()) {
                System.out.println("Please choose a location to place a mine:");
                onPlayMoveClicked(event);
            } else {
                System.out.println("You ran out of mines!");
            }
        } else {
            System.out.println("You must start a game before placing a mine.");
        }
    }

    private void onStatisticsClicked(ActionEvent event) {

    }

    private void onGameStatusClicked(ActionEvent event) {

    }

    //TODO: handle all system.println methods.
    private void onStartGameClicked(ActionEvent event) {
        if (!theGame.isFileLoaded()) {
            // No XML file
            System.out.println("You must load a XML file in order to start a game.");
        } else if (theGame.isGameOn()) {
            // The game is already started
            System.out.println("A game is already running.");
        } else {
            // XML loaded and game is not began yet
            try {
                theGame.startGame();
                System.out.println("The game has been started successfully!");
                //TODO: handle with this exception.
            } catch (XmlContentException e) {
                e.printStackTrace();
            }
        }
    }

    private void onQuitMatchClicked(ActionEvent event) {

    }

    private void onPlayMoveClicked(ActionEvent event) {

    }

    private void onExitClicked(ActionEvent event) {
        if (!theGame.isGameOn()) {
            theGame.exitGame();
            System.out.println("Thank you for playing! Exiting...");
        } else {
            System.out.println("You must finish the current match before exiting the game.");
        }
    }

    private void onLoadXmlClicked(ActionEvent event) {

        if (!theGame.isGameOn()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("XML Resource File");
            File file = fileChooser.showOpenDialog(null);
            ErrorCollector errorCollector = new ErrorCollector();
            IInputVerifier inputVerifier = new XmlFileVerifier();

            try {
                if (file == null
                        || !inputVerifier.isFileOk(file.getName(), errorCollector)
                        || !theGame.loadFile(file.getPath(), errorCollector)) {
                    //TODO: print errors in an ui component.
                    errorCollector.getMessages().forEach(System.out::println);
                } else {
                    //TODO: File has loaded
                    System.out.println("File has loaded. You are ready to play :)");
                }
            } catch (XmlContentException exception) {
                System.out.println(exception.getMessage());
            }
        } else {
            System.out.println(ErrorMessages.GAME_IS_ALREADY_ON.message());
        }
    }
}