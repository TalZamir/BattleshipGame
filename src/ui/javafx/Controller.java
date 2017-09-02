package ui.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import logic.TheGame;
import logic.enums.ErrorMessages;
import logic.exceptions.XmlContentException;
import ui.UserMoveInput;
import ui.verifiers.ErrorCollector;
import ui.verifiers.IInputVerifier;
import ui.verifiers.XmlFileVerifier;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.System.exit;

/**
 * Created by barakm on 29/08/2017
 */
public class Controller extends JPanel implements Initializable {

    private final TheGame theGame;
    private final Alert errorAlert;
    private final Alert informationAlert;
    private final Alert confirmationMassage;
    private BoardButton[][] trackingBoard;
    private BoardButton[][] personalBoard;
    @FXML
    private TextField textFieldMessage;
    @FXML
    private Button buttonGameStatus;
    @FXML
    private Button buttonStatistics;
    @FXML
    private Button buttonMine;
    @FXML
    private Button buttonQuitMatch;
    @FXML
    private Button buttonExitGame;
    @FXML
    private Button buttonStartGame;
    @FXML
    private Button buttonLoadXml;
    @FXML
    private Pane trackingPane;
    @FXML
    private Pane personalPane;

    public Controller() {
        theGame = new TheGame();
        errorAlert = new Alert(Alert.AlertType.ERROR);
        informationAlert = new Alert(Alert.AlertType.INFORMATION);
        confirmationMassage = new Alert(Alert.AlertType.CONFIRMATION);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buttonLoadXml.setOnAction(this::onLoadXmlClicked);
        buttonExitGame.setOnAction(this::onExitClicked);
        buttonQuitMatch.setOnAction(this::onQuitMatchClicked);
        buttonStartGame.setOnAction(this::onStartGameClicked);
        buttonGameStatus.setOnAction(this::onGameStatusClicked);
        buttonStatistics.setOnAction(this::onStatisticsClicked);
        buttonMine.setOnAction(this::onPlaceMineClicked);
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
                    errorCollector.getMessages().forEach(errorAlert::setContentText);
                    errorAlert.show();
                } else {
                    informationAlert.setContentText("File has loaded. You are ready to play :)");
                    informationAlert.show();
                }
            } catch (XmlContentException exception) {
                errorAlert.setContentText(exception.getMessage());
                errorAlert.show();
            }
        } else {
            errorAlert.setContentText(ErrorMessages.GAME_IS_ALREADY_ON.message());
            errorAlert.show();
        }
    }

    private void onStartGameClicked(ActionEvent event) {
        if (!theGame.isFileLoaded()) {
            // No XML file
            errorAlert.setContentText("You must load a XML file in order to start a game.");
            errorAlert.show();
        } else if (theGame.isGameOn()) {
            // The game is already started
            errorAlert.setContentText("A game is already running.");
            errorAlert.show();
        } else {
            // XML loaded and game is not began yet
            try {
                theGame.startGame();
                initBoardsComponents(theGame.getBoardSize());
                drawBoards();
                informationAlert.setContentText("The game has been started successfully!");
                informationAlert.show();
            } catch (XmlContentException e) {
                errorAlert.setContentText(e.getMessage());
                errorAlert.show();
            }
        }
    }

    private void onGameStatusClicked(ActionEvent event) {
        if (theGame.isGameOn()) {
            String turnMsg = getTurnMsg();
            textFieldMessage.setText(turnMsg);
        } else {
            errorAlert.setContentText("You must start a game in order to display a status.");
            errorAlert.show();
        }
    }

    private String getTurnMsg() {
        return "~~~~  " +
                theGame.getCurrentPlayerName() +
                " Turn (Score: " +
                theGame.getCurrentPlayerScore() +
                ")  ~~~~~";
    }

    private void onPlayMoveClicked(ActionEvent event) {

        if (theGame.isGameOn()) {
            BoardButton boardButton = (BoardButton) event.getTarget();
            UserMoveInput userMoveInput = new UserMoveInput(boardButton.getRow(),
                                                            boardButton.getColumn());
            try {
                theGame.playMove(userMoveInput, boardButton.getParent().getId().equalsIgnoreCase(trackingPane.getId()));
            } catch (XmlContentException e) {
                errorAlert.setContentText(e.getMessage());
            }

            if (theGame.isPlayerWon()) {
                try {
                    finishMatch();
                } catch (XmlContentException e) {
                    errorAlert.setContentText(e.getMessage());
                }
            } else {
                textFieldMessage.setText(getTurnMsg()); // Indicates who's turn is it
            }
        } else {
            errorAlert.setContentText("You must start a new game before performing a move.");
        }
    }

    private void onStatisticsClicked(ActionEvent event) {
        if (theGame.isGameOn()) {
            textFieldMessage.setText(theGame.getStatistics());
        } else {
            errorAlert.setContentText("You must start a game in order to show game statistics.");
            errorAlert.show();
        }
    }

    private void onPlaceMineClicked(ActionEvent event) {
        if (theGame.isGameOn()) {
            if (theGame.hasMines()) {
                onPlayMoveClicked(event);
            } else {
                textFieldMessage.setText("You ran out of mines!");
            }
        } else {
            errorAlert.setContentText("You must start a game before placing a mine.");
        }
    }

    private void onQuitMatchClicked(ActionEvent event) {
        if (theGame.isGameOn()) {
            confirmationMassage.setContentText(theGame.quitMatch());
            if (confirmationMassage.showAndWait().get() == ButtonType.OK) {
                try {
                    finishMatch();
                } catch (XmlContentException e) {
                    errorAlert.setContentText(e.getMessage());
                }
            }
        } else {
            errorAlert.setContentText("You must start a game in order to quit.");
        }
    }

    private void finishMatch() throws XmlContentException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(theGame.getStatistics());
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("-  The game will restart now...");
        theGame.resetGame();
    }

    private void onExitClicked(ActionEvent event) {
        confirmationMassage.setContentText("Are you sure you want to exit?");
        if (!theGame.isGameOn() && confirmationMassage.showAndWait().get() == ButtonType.OK) {
            theGame.exitGame();
            informationAlert.setContentText("Thank you for playing! Exiting...");
            informationAlert.showAndWait();
            exit(0);
        } else {
            errorAlert.setContentText("You must finish the current match before exiting the game.");
        }
    }

    // **************************************************** //
    // Initiates boards components
    // **************************************************** //
    private void initBoardsComponents(int convenientBoardSize) {
        trackingBoard = new BoardButton[convenientBoardSize][convenientBoardSize];
        personalBoard = new BoardButton[convenientBoardSize][convenientBoardSize];
        setSize(500, 500);
        initCells(trackingBoard, trackingPane, true);
        initCells(personalBoard, personalPane, false);
    }

    // **************************************************** //
    // Initiates boards components
    // **************************************************** //
    private void initCells(BoardButton[][] boardComponent, Pane pane, boolean isClickable) {
        int space = 40;
        int rowSpace = 0;
        int colSpace = 0;
        for (int i = 1; i < boardComponent.length; i++) {
            for (int j = 1; j < boardComponent.length; j++) {
                BoardButton button = new BoardButton(i, j);
                button.setLayoutX(rowSpace);
                button.setLayoutY(colSpace);
                button.setText("~");
                if (isClickable) {
                    button.setOnAction(this::onPlayMoveClicked);
                } else {
                    button.setDisable(true);
                }
                boardComponent[i][j] = button;
                pane.getChildren().add(button);
                rowSpace += space;
            }
            rowSpace = 0;
            colSpace += space;
        }
    }

    // **************************************************** //
    // Draw boards
    // **************************************************** //
    private void drawBoards() {
        drawSpecificBoard(personalBoard, theGame.getCurrentPlayerBoardToPrint());
        drawSpecificBoard(trackingBoard, theGame.getOpponentBoardToPrint());
    }

    // **************************************************** //
    // Drawing a given UI board
    // **************************************************** //
    private void drawSpecificBoard(BoardButton[][] physicalboard, char[][] logicalBoard) {
        for (int i = 1; i < physicalboard.length; i++) {
            for (int j = 1; j < physicalboard.length; j++) {
                physicalboard[i][j].setText(String.valueOf(logicalBoard[i][j]));
            }
        }
    }
}