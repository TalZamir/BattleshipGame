package ui.javafx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import logic.TheGame;
import logic.enums.ErrorMessages;
import logic.exceptions.XmlContentException;
import ui.UserMoveInput;
import ui.components.BoardButton;
import ui.enums.SkinType;
import ui.verifiers.ErrorCollector;
import ui.verifiers.IInputVerifier;
import ui.verifiers.XmlFileVerifier;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.SortedMap;

import static java.lang.System.exit;
import static java.lang.System.lineSeparator;

/**
 * Created by barakm on 29/08/2017
 */
public class Controller extends JPanel implements Initializable {

    private final TheGame theGame;
    private final Alert errorAlert;
    private final Alert informationAlert;
    private final Alert confirmationMassage;
    private final List<Button> menuButtonList;
    public AnchorPane mainPane;
    private BoardButton[][] trackingBoard;
    private BoardButton[][] personalBoard;
    private GridPane grid;
    private GridPane personalPane;
    private GridPane trackingPane;

    @FXML
    private ChoiceBox<String> choiceBoxSkin;
    @FXML
    private TextArea textFieldMessage;
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
    private AnchorPane contentPane;
    @FXML
    private Button buttonUndo;
    @FXML
    private Button buttonRedo;

    public Controller() {
        theGame = new TheGame();
        errorAlert = new Alert(Alert.AlertType.ERROR);
        informationAlert = new Alert(Alert.AlertType.INFORMATION);
        confirmationMassage = new Alert(Alert.AlertType.CONFIRMATION);
        menuButtonList = new ArrayList<>();
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
        buttonMine.setOnDragDetected(this::mineOnDragDetected);
        buttonMine.setOnDragDone(this::mineOnDragDone);
        buttonUndo.setOnAction(this::onUndoClicked);
        buttonRedo.setOnAction(this::onRedoClicked);
        buttonUndo.setDisable(true);
        buttonRedo.setDisable(true);
        initChoiceBox();
        initMenuButtonsList();
    }

    private void initMenuButtonsList() {
        menuButtonList.add(buttonExitGame);
        menuButtonList.add(buttonGameStatus);
        menuButtonList.add(buttonLoadXml);
        menuButtonList.add(buttonMine);
        menuButtonList.add(buttonQuitMatch);
        menuButtonList.add(buttonStartGame);
        menuButtonList.add(buttonStatistics);
    }

    private void initChoiceBox() {
        choiceBoxSkin.setItems(FXCollections.observableArrayList("Default", "First", "Second"));
        choiceBoxSkin.getSelectionModel().selectFirst();
        choiceBoxSkin.setOnAction(this::onChoiceBoxSkinItemSelected);
    }

    private void onChoiceBoxSkinItemSelected(ActionEvent event) {
        String selectedItem = ((ChoiceBox<String>) event.getTarget()).getSelectionModel().getSelectedItem();

        if (selectedItem.equalsIgnoreCase(SkinType.DEFAULT.getValue())) {
            setDefaultSkin();
        } else if (selectedItem.equalsIgnoreCase(SkinType.FIRST.getValue())) {
            setFirstSkin();
        } else {

            setSecondSkin();
        }
    }

    private void setSecondSkin() {
    }

    private void setFirstSkin() {

        Background menuButtonsBackground = createMenuButtonsBackground(15, 0, 0.6039, 0.8353, 1);
        Background boardButtonsBackground = createMenuButtonsBackground(100, 0, 0.6039, 0.8353, 1);
        Background background = createMenuButtonsBackgroundWithImage("src/res/abstract_wavy_background_310468.jpg");

        mainPane.setBackground(background);
        setSkin(background, boardButtonsBackground, menuButtonsBackground);
    }

    private Background createMenuButtonsBackgroundWithImage(String imageLocation) {
        Image image = null;
        try {
            image = new Image(new FileInputStream(imageLocation),
                              mainPane.getWidth(),
                              mainPane.getHeight(),
                              false,
                              false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat
                .REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT));
    }

    private Background createMenuButtonsBackground(double cornerRadios,
                                                   double red,
                                                   double green,
                                                   double blue,
                                                   int opacity) {
        CornerRadii cornerRadii = new CornerRadii(cornerRadios);
        Paint paint = new Color(red, green, blue, opacity);
        Insets insets = new Insets(0);
        return new Background(new BackgroundFill(paint, cornerRadii, insets));
    }

    private void setSkinToBoardsButtons(BoardButton[][] board, Background imageBackground) {
        if (board != null) {
            for (int i = 1; i < board.length; i++) {
                for (int j = 1; j < board.length; j++) {
                    board[i][j].setBackground(imageBackground);
                }
            }
        }
    }

    private void setSkin(Background background, Background boardButtonsBackground, Background menuButtonsBackground) {
        mainPane.setBackground(background);
        setSkinToBoardsButtons(trackingBoard, boardButtonsBackground);
        setSkinToBoardsButtons(personalBoard, boardButtonsBackground);
        menuButtonList.forEach(b -> b.setBackground(menuButtonsBackground));
    }

    private void setDefaultSkin() {
    }

    private void mineOnDragDone(DragEvent event) {
        event.consume();
    }

    private void myBoardCellOnDragDropped(DragEvent event) {
        ActionEvent actionEvent = new ActionEvent(event.getSource(), event.getTarget());

        onPlayMoveClicked(actionEvent);
        event.setDropCompleted(true);

        event.consume();
    }

    private void myBoardCellOnDragExit(DragEvent event) {
        ((BoardButton) event.getSource()).setStyle("");
        event.consume();
    }

    private void myBoardCellOnDragEntered(DragEvent event) {
        ((BoardButton) event.getSource()).setStyle("-fx-background-color: #e4c7c8;");

        event.consume();
    }

    private void myBoardCellOnDragOver(DragEvent event) {
        event.acceptTransferModes(TransferMode.MOVE);

        event.consume();
    }

    private void mineOnDragDetected(MouseEvent event) {
        Dragboard db = buttonMine.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();

        Image image = null;
        try {
            image = new Image(new FileInputStream("src/res/mineImage.png"), 30, 30, false, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        content.putImage(image);
        content.put(DataFormat.IMAGE, db.setContent(content));

        event.consume();
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
                    if (!errorCollector.getMessages().isEmpty()) {
                        errorCollector.getMessages().forEach(errorAlert::setContentText);
                        errorAlert.show();
                    }
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
                buttonUndo.setDisable(true);
                buttonRedo.setDisable(true);
                theGame.startGame();
                initBoardsComponents(theGame.getBoardSize());
                drawBoards();
                setButtonMineText();
                textFieldMessage.setText(getTurnMsg());
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
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("~~~~  ")
                     .append(theGame.getCurrentPlayerName())
                     .append(" Turn (Score: ")
                     .append(theGame.getCurrentPlayerScore())
                     .append(")  ~~~~~")
                     .append(lineSeparator())
                     .append(theGame.getStatistics())
                     .append(lineSeparator());

        appendBattleships(stringBuilder);
        return stringBuilder.toString();
    }

    private void appendBattleships(StringBuilder stringBuilder) {
        SortedMap<Integer, Integer> shipsType = theGame.getCurrentPlayerShipsTypesAndAmount();
        appendBattleshipsList(stringBuilder, shipsType, "Your remain battleships:");
        shipsType = theGame.getOpponentPLayerShipsTypesAndAmount();
        appendBattleshipsList(stringBuilder, shipsType, "Opponent remain battleships:");
    }

    private void appendBattleshipsList(StringBuilder stringBuilder,
                                       SortedMap<Integer, Integer> shipsType,
                                       String battleshipsPlayerMessage) {
        stringBuilder.append(battleshipsPlayerMessage);
        stringBuilder.append(lineSeparator());
        shipsType.forEach((k, v) -> stringBuilder.append("From ship type ")
                                                 .append(k)
                                                 .append(" left ")
                                                 .append(v)
                                                 .append(" ships")
                                                 .append(lineSeparator()));
    }

    private void onPlayMoveClicked(ActionEvent event) {
        StringBuilder turnMessage = new StringBuilder();
        if (theGame.isGameOn()) {
            BoardButton boardButton = (BoardButton) event.getSource();
            UserMoveInput userMoveInput = new UserMoveInput(boardButton.getRow(),
                                                            boardButton.getColumn());
            try {
                turnMessage.append(theGame.playMove(userMoveInput,
                                                    boardButton.getParent().getId()
                                                               .equalsIgnoreCase(trackingPane.getId())));
                turnMessage.append(lineSeparator());
            } catch (XmlContentException e) {
                errorAlert.setContentText(e.getMessage());
                errorAlert.show();
            }
            drawBoards();
            setButtonMineText();

            if (theGame.isPlayerWon()) {
                try {
                    finishMatch();
                    informationAlert.setContentText(theGame.playerWonMatchMessage());
                    informationAlert.show();
                } catch (XmlContentException e) {
                    errorAlert.setContentText(e.getMessage());
                    errorAlert.show();
                }
            } else {
                turnMessage.append(getTurnMsg());
                textFieldMessage.setText(turnMessage.toString()); // Indicates who's turn is it
            }
        } else {
            errorAlert.setContentText("You must start a new game before performing a move.");
            errorAlert.show();
        }
    }

    private void setButtonMineText() {
        String[] playersStatistics = theGame.getStatistics().split(lineSeparator());
        String numOfMines = "";
        String buttonText = buttonMine.getText();
        StringBuilder buttonMineText = new StringBuilder();
        int i;

        int begin = buttonText.indexOf("Drag");
        int end = buttonText.indexOf("mine") + 4;
        buttonMineText.append(buttonMine.getText().substring(begin, end));

        for (i = 0; i < playersStatistics.length; i++) {
            if (playersStatistics[i].contains(theGame.getCurrentPlayerName())) {
                numOfMines = playersStatistics[i];
            }
        }

        begin = numOfMines.indexOf("Mines");
        end = numOfMines.indexOf("Average");
        String mines = numOfMines.substring(begin, end - 2);

        buttonMineText.append(lineSeparator());
        buttonMineText.append(mines);
        buttonMine.setText(buttonMineText.toString());
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
            errorAlert.show();
        }
    }

    private void onQuitMatchClicked(ActionEvent event) {
        if (theGame.isGameOn()) {
            confirmationMassage.setContentText("Are you sure you want to quit?");
            if (confirmationMassage.showAndWait().get() == ButtonType.OK) {
                informationAlert.setContentText(theGame.quitMatch());
                informationAlert.showAndWait();
                try {
                    finishMatch();
                } catch (XmlContentException e) {
                    errorAlert.setContentText(e.getMessage());
                    errorAlert.show();
                }
            }
        } else {
            errorAlert.setContentText("You must start a game in order to quit.");
            errorAlert.show();
        }
    }

    private void finishMatch() throws XmlContentException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(theGame.getStatistics());
        stringBuilder.append(lineSeparator());
        stringBuilder.append("-  The game will restart now...");
        textFieldMessage.setText(stringBuilder.toString());
        theGame.resetGame();
        activateUndoRedo();
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
            errorAlert.show();
        }
    }

    // **************************************************** //
    // Initiates boards components
    // **************************************************** //
    private void initBoardsComponents(int convenientBoardSize) {
        contentPane.getChildren().clear();
        grid = new GridPane();
        trackingPane = new GridPane();
        personalPane = new GridPane();
        trackingBoard = new BoardButton[convenientBoardSize][convenientBoardSize];
        personalBoard = new BoardButton[convenientBoardSize][convenientBoardSize];
        trackingPane.setId("trackingBoard");
        personalPane.setId("personalBoard");
        initCells(trackingBoard, trackingPane, true);
        initCells(personalBoard, personalPane, false);
        Label lab = new Label("Tracking Board:");
        lab.setAlignment(Pos.CENTER);

        grid.setHgap(50); // Horizontal gap
        grid.setVgap(10); // Vertical gap
        grid.add(lab, 0, 0);
        grid.add(trackingPane, 0, 1);
        grid.add(new Label("Personal Board:"), 1, 0);
        grid.add(personalPane, 1, 1);
        contentPane.getChildren().add(grid);
    }

    // **************************************************** //
    // Initiates boards components
    // **************************************************** //
    private void initCells(BoardButton[][] boardComponent, GridPane pane, boolean isClickable) {
        pane.setHgap(5); // Horizontal gap
        pane.setVgap(5); // Vertical gap
        for (int i = 1; i < boardComponent.length; i++) {
            for (int j = 1; j < boardComponent.length; j++) {
                BoardButton button = new BoardButton(i, j);
                button.setPrefWidth(35);
                if (isClickable) {
                    button.setOnAction(this::onPlayMoveClicked);
                } else {
                    button.setOnAction(null);
                    button.setOnDragOver(this::myBoardCellOnDragOver);
                    button.setOnDragEntered(this::myBoardCellOnDragEntered);
                    button.setOnDragExited(this::myBoardCellOnDragExit);
                    button.setOnDragDropped(this::myBoardCellOnDragDropped);
                }
                boardComponent[i][j] = button;
                pane.add(button, j, i);
            }
        }
        // Column characters
        char colVal = 'A';
        for (int i = 1; i < boardComponent.length; i++) {
            Label label = new Label(String.valueOf(colVal));
            pane.add(label, i, 0);
            pane.setHalignment(label, HPos.CENTER);
            colVal++;
        }
        // Row numbers
        for (int i = 1; i < boardComponent.length; i++) {
            Label label = new Label(String.valueOf(i));
            pane.add(new Label(String.valueOf(i)), 0, i);
            pane.setHalignment(label, HPos.CENTER);
        }
    }

    // **************************************************** //
    // Activates Undo-Redo
    // **************************************************** //
    private void activateUndoRedo() {
        buttonUndo.setDisable(false);
        textFieldMessage.clear();
        theGame.activateUbdoRedo();
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

    private void onUndoClicked(ActionEvent event) {
        textFieldMessage.setText(theGame.getPrevStep());
        if (!theGame.hasPrevStep()) {
            buttonUndo.setDisable(true);
        }
        buttonRedo.setDisable(false);
    }

    private void onRedoClicked(ActionEvent event) {
        textFieldMessage.setText(theGame.getNextStep());
        if (!theGame.hasNextStep()) {
            buttonRedo.setDisable(true);
        }
        buttonUndo.setDisable(false);
    }

}