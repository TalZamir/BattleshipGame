package ui.javafx;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by barakm on 08/09/2017
 */
public final class SkinBuilder {

    private final double mainPaneWidth;
    private final double mainPaneHeight;
    private final Background defaultBackground;
    private final String defaultStyle;
    private final ObservableList<String> defaultStyleClass;
    private final Skin<?> defaultSkin;
    private final Font defaultFont;
    private String imagePath;
    private double fontSize;
    private String fontStyle;
    private Background boardsButtonsBackground;
    private String fontColor;
    private Background menuButtonsBackground;
    private Background background;

    public SkinBuilder(Button defaultButton, double mainPaneWidth, double mainPaneHeight) {
        this.mainPaneWidth = mainPaneWidth;
        this.mainPaneHeight = mainPaneHeight;
        defaultBackground = defaultButton.getBackground();
        defaultStyle = defaultButton.getStyle();
        defaultStyleClass = defaultButton.getStyleClass();
        defaultSkin = defaultButton.getSkin();
        defaultFont = defaultButton.getFont();
        reset();
    }

    public SkinBuilder withBackground(String imagePath) {
        this.imagePath = imagePath;
        return this;
    }

    public SkinBuilder withFontSize(double fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public SkinBuilder withFontStyle(String fontStyle) {
        this.fontStyle = fontStyle;
        return this;
    }

    public SkinBuilder withFontColor(String fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    public SkinBuilder withMenuButtonsBackground(double cornerRadios,
                                                 double red,
                                                 double green,
                                                 double blue,
                                                 int opacity) {
        menuButtonsBackground = createBackground(cornerRadios, red, green, blue, opacity);
        return this;
    }

    public SkinBuilder withBoardsButtonsBackground(double cornerRadios,
                                                   double red,
                                                   double green,
                                                   double blue,
                                                   int opacity) {
        boardsButtonsBackground = createBackground(cornerRadios, red, green, blue, opacity);
        return this;
    }

    public SkinCreator buildDefaultValues() {
        SkinCreator skinCreator = new SkinCreator(defaultBackground,
                                                  defaultSkin,
                                                  defaultStyle,
                                                  defaultStyleClass,
                                                  defaultFont,
                                                  fontColor);

        reset();
        return skinCreator;
    }

    public SkinCreator build() {
        Font font = new Font(fontStyle, fontSize);

        if (!imagePath.isEmpty()) {

            Image image = null;
            try {
                image = new Image(new FileInputStream(imagePath),
                                  mainPaneWidth,
                                  mainPaneHeight,
                                  false,
                                  false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            assert image != null;
            background = new Background(new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat
                    .REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT));
        }

        fontColor = "-fx-text-fill: " + fontColor + ";";

        SkinCreator skinCreator = new SkinCreator(background,
                                                  menuButtonsBackground,
                                                  boardsButtonsBackground,
                                                  font,
                                                  fontColor);
        reset();

        return skinCreator;
    }

    private void reset() {
        fontSize = 13;
        fontStyle = "System";
        fontColor = "#000000";
    }

    private Background createBackground(double cornerRadios, double red, double green, double blue, int opacity) {
        CornerRadii cornerRadii = new CornerRadii(cornerRadios);
        Paint paint = new Color(red, green, blue, opacity);

        return new Background(new BackgroundFill(paint, cornerRadii, Insets.EMPTY));
    }
}
