package ui.javafx;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import logic.ReplayInfo;
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
    private Label textFieldMessage;
    private Label replayFieldText;

    @FXML
    private ChoiceBox<String> choiceBoxSkin;

    private Button buttonGameStatus = new Button("Game Status");
    private Button buttonStatistics = new Button("Statistics");
    private Button buttonMine = new Button("Drag mine");
    private Button buttonQuitMatch = new Button("Quit Match");
    private Button buttonExitGame = new Button("Exit Game");
    private Button buttonStartGame = new Button("Start Game");
    private Button buttonLoadXml = new Button("Load XML");
    private Button buttonUndo = new Button("<");
    private Button buttonRedo = new Button(">");

    @FXML
    ScrollPane scrollPane;
    @FXML
    private AnchorPane contentPane;
    private Label trackingBoardLabel;
    private Label personalBoardLabel;
    //    private SkinType currentSkinType = SkinType.DEFAULT;


    private SkinBuilder skinBuilder;

    public Controller() {
        theGame = new TheGame();
        errorAlert = new Alert(Alert.AlertType.ERROR);
        informationAlert = new Alert(Alert.AlertType.INFORMATION);
        confirmationMassage = new Alert(Alert.AlertType.CONFIRMATION);
        menuButtonList = new ArrayList<>();
        textFieldMessage = new Label();
        replayFieldText = new Label();
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
        initMenu();
        initChoiceBox();
        initMenuButtonsList();
    }

    // **************************************************** //
    // Initialize menu buttons
    // **************************************************** //
    private void initMenu() {
        // Setups the main grid
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10); // Horizontal gap
        grid.setVgap(10); // Vertical gap
        // Buttons grid
        GridPane buttonsGrid = new GridPane();
        buttonsGrid.setVgap(15); // Vertical gap

        buttonLoadXml.setMinSize(130, 40);
        buttonStartGame.setMinSize(130, 40);
        buttonGameStatus.setMinSize(130, 40);
        buttonStatistics.setMinSize(130, 40);
        buttonMine.setMinSize(130, 40);
        buttonQuitMatch.setMinSize(130, 40);
        buttonExitGame.setMinSize(130, 40);

        buttonsGrid.add(buttonLoadXml, 0, 0);
        buttonsGrid.add(buttonStartGame, 0, 1);
        buttonsGrid.add(buttonGameStatus, 0, 2);
        buttonsGrid.add(buttonStatistics, 0, 3);
        buttonsGrid.add(buttonMine, 0, 4);
        buttonsGrid.add(buttonQuitMatch, 0, 5);
        buttonsGrid.add(buttonExitGame, 0, 6);

        grid.add(buttonsGrid, 1, 1);
        grid.add(createSeperator(), 2, 1);

        contentPane.getChildren().add(grid);
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
        choiceBoxSkin.setItems(FXCollections.observableArrayList("Default", "Light", "Darcula"));
        choiceBoxSkin.getSelectionModel().selectFirst();
        choiceBoxSkin.setOnAction(this::onChoiceBoxSkinItemSelected);
    }

    private void onChoiceBoxSkinItemSelected(ActionEvent event) {
        String selectedItem = ((ChoiceBox<String>) event.getSource()).getSelectionModel().getSelectedItem();
        if (skinBuilder == null) {
            skinBuilder = new SkinBuilder(buttonMine, mainPane.getWidth(), mainPane.getHeight());
        }

        if (selectedItem.equalsIgnoreCase(SkinType.DEFAULT.getValue())) {
            setDefaultSkin();
        } else if (selectedItem.equalsIgnoreCase(SkinType.LIGHT.getValue())) {
            buttonMine.getSkin();
            setFirstSkin();
        } else {
            setSecondSkin();
        }
    }

    private void setSecondSkin() {

        SkinCreator skinCreator = skinBuilder.withBackground("src/res/blue-blur-26927-2880x1800.jpg")
                                             .withBoardsButtonsBackground(0, 0, 0, 0, 1)
                                             .withMenuButtonsBackground(0, 0, 0, 0, 1)
                                             .withFontColor("#c4d8de")
                                             .withFontSize(12)
                                             .withFontStyle("Cochin")
                                             .build();
        setSkin(skinCreator);
    }

    private void setFirstSkin() {
        SkinCreator skinCreator = skinBuilder.withBackground("src/res/abstract_wavy_background_310468.jpg")
                                             .withBoardsButtonsBackground(15, 0, 0.6039, 0.8353, 1)
                                             .withMenuButtonsBackground(15, 0, 0.6039, 0.8353, 1)
                                             .withFontSize(18)
                                             .withFontStyle("Impact")
                                             .build();
        setSkin(skinCreator);
    }

    private void setSkinToBoardsButtons(BoardButton[][] board, Background imageBackground, Font font, String fontColor) {
        if (board != null) {
            for (int i = 1; i < board.length; i++) {
                for (int j = 1; j < board.length; j++) {
                    board[i][j].setBackground(imageBackground);
                    board[i][j].setFont(font);
                    board[i][j].setPrefSize(font.getSize() * 2.2, font.getSize() * 2.2);
                    board[i][j].setStyle(fontColor);
                }
            }
        }
    }

    private void setSkin(SkinCreator skinCreator) {
        mainPane.setBackground(skinCreator.getBackground());
        setSkinToBoardsButtons(trackingBoard, skinCreator.getBoardsButtonsBackground(), skinCreator.getFont(), skinCreator.getFontColor());
        setSkinToBoardsButtons(personalBoard, skinCreator.getBoardsButtonsBackground(), skinCreator.getFont(), skinCreator.getFontColor());
        menuButtonList.forEach(b -> {
            b.setBackground(skinCreator.getMenuButtonsBackground());
            b.setFont(skinCreator.getFont());
            b.setStyle(skinCreator.getFontColor());
        });

        buttonMine.setWrapText(true);
        buttonExitGame.setLayoutY(buttonMine.getLayoutY() + 65);
        if (personalBoardLabel != null) {
            personalBoardLabel.setFont(skinCreator.getFont());
            trackingBoardLabel.setFont(skinCreator.getFont());
        }
    }

    private void setDefaultSkin() {
//        SkinCreator skinCreator = skinBuilder.buildDefaultValues();
//
//        mainPane.setBackground(skinCreator.getDefaultBackground());
//
//        setSkinToBoardsButtons(trackingBoard, skinCreator.getDefaultBackground(), skinCreator.getDefaultFont(),
//                               skinCreator.getFontColor());
//        setSkinToBoardsButtons(personalBoard, skinCreator.getDefaultBackground(), skinCreator.getDefaultFont(), skinCreator.getFontColor());
//
//        menuButtonList.forEach(b -> {
//            b.setBackground(skinCreator.getDefaultBackground());
//            b.setFont(skinCreator.getDefaultFont());
//            b.setStyle(skinCreator.getDefaultStyle());
//            b.setSkin(skinCreator.getDefaultSkin());
//        });
//
//        buttonMine.setWrapText(true);
//        buttonExitGame.setLayoutY(buttonMine.getLayoutY() + 65);
//        if (personalBoardLabel != null) {
//            personalBoardLabel.setFont(skinCreator.getDefaultFont());
//            trackingBoardLabel.setFont(skinCreator.getDefaultFont());
//        }
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
                ChoiceBox<String> choiceBox = new ChoiceBox<>();
                choiceBox.setValue(choiceBoxSkin.getValue());
                onChoiceBoxSkinItemSelected(new ActionEvent(choiceBox, null));
                drawBoards();
                setButtonMineText();
                textFieldMessage.setText(getTurnMsg());
                replayFieldText.setText("");
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
        SortedMap<Integer, Integer> shipsType = theGame.getOpponentPLayerShipsTypesAndAmount();
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
    // Activates Undo-Redo
    // **************************************************** //
    private void activateUndoRedo() {
        buttonUndo.setDisable(false);
        showReplayStep(theGame.activateUndoRedo());
        buttonUndo.setDisable(theGame.shouldDisableUndo());
    }

    // **************************************************** //
    // Draw boards
    // **************************************************** //
    private void drawBoards() {
        drawSpecificBoard(personalBoard, theGame.getCurrentPlayerBoardToPrint());
        drawSpecificBoard(trackingBoard, theGame.getOpponentBoardToPrint());
    }

    // **************************************************** //
    // Draw boards
    // **************************************************** //
    private void showReplayStep(ReplayInfo replayInfo) {
        drawSpecificBoard(personalBoard, replayInfo.getPersonalBoard());
        drawSpecificBoard(trackingBoard, replayInfo.getTraceBoard());
        replayFieldText.setText(replayInfo.toString());
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

    // **************************************************** //
    // Undo button clicked
    // **************************************************** //
    private void onUndoClicked(ActionEvent event) {
        ReplayInfo replayInfo = theGame.getPrevStep();
        showReplayStep(replayInfo);
        if (!theGame.hasPrevStep()) {
            buttonUndo.setDisable(true);
        }
        buttonRedo.setDisable(false);
    }

    // **************************************************** //
    // Redo button clicked
    // **************************************************** //
    private void onRedoClicked(ActionEvent event) {
        ReplayInfo replayInfo = theGame.getNextStep();
        showReplayStep(replayInfo);
        if (!theGame.hasNextStep()) {
            buttonRedo.setDisable(true);
        }
        buttonUndo.setDisable(false);
    }

    // **************************************************** //
    // Initiates boards components
    // **************************************************** //
    private void initBoardsComponents(int convenientBoardSize) {
        contentPane = new AnchorPane();
        grid = new GridPane();
        initMenu();
        // Boards grids
        GridPane gameGrid = new GridPane();
        trackingPane = new GridPane();
        trackingPane.setId("trackingPane");
        personalPane = new GridPane();
        trackingPane.setId("personalPane");
        trackingBoard = new BoardButton[convenientBoardSize][convenientBoardSize];
        personalBoard = new BoardButton[convenientBoardSize][convenientBoardSize];
        initCells(trackingBoard, trackingPane, true);
        initCells(personalBoard, personalPane, false);
        trackingBoardLabel = new Label("Tracking Board:");
        personalBoardLabel = new Label("Personal Board:");
        textFieldMessage.autosize();
        gameGrid.setHgap(30); // Horizontal gap
        gameGrid.setVgap(10); // Vertical gap
        gameGrid.add(trackingBoardLabel, 0, 1);
        gameGrid.add(trackingPane, 0, 2);
        gameGrid.add(personalBoardLabel, 1, 1);
        gameGrid.add(personalPane, 1, 2);
        // Text grid
        GridPane textGrid = new GridPane();
        Label gameDetails = new Label("Game Details:");
        gameDetails.setFont(new Font(30));
        Label replay = new Label("Replay:");
        replay.setFont(new Font(30));
        GridPane replayButtonsGrid = new GridPane();
        replayButtonsGrid.setHgap(10); // Horizontal gap
        replayButtonsGrid.setVgap(10); // Vertical gap
        replayButtonsGrid.add(buttonUndo, 0, 0);
        replayButtonsGrid.add(buttonRedo, 1, 0);

        textGrid.setHgap(10); // Horizontal gap
        textGrid.setVgap(10); // Vertical gap
        textGrid.add(gameDetails, 0, 0);
        textGrid.add(textFieldMessage, 0, 1);
        textGrid.add(replay, 0, 2);
        textGrid.add(replayButtonsGrid, 0, 3);
        textGrid.add(replayFieldText, 0, 4);
        // Builds main grid
        grid.add(gameGrid, 4, 1);
        grid.add(createSeperator(), 5, 1);
        grid.add(textGrid, 6, 1);

        scrollPane.setContent(contentPane);
    }

    private Separator createSeperator() {
        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        return separator;
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
}