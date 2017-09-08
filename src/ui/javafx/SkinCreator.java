package ui.javafx;

import javafx.collections.ObservableList;
import javafx.scene.control.Skin;
import javafx.scene.layout.Background;
import javafx.scene.text.Font;

/**
 * Created by barakm on 08/09/2017
 */
public class SkinCreator {

    private final Background background;
    private final Skin<?> defaultSkin;
    private final String defaultStyle;
    private final Font defaultFont;
    private final Background menuButtonsBackground;
    private final Background boardsButtonsBackground;
    private final Font font;
    private final String fontColor;
    private final Background defaultBackground;

    public SkinCreator(Background background, Background menuButtonsBackground, Background boardsButtonsBackground, Font font, String fontColor) {

        this.background = background;
        this.menuButtonsBackground = menuButtonsBackground;
        this.boardsButtonsBackground = boardsButtonsBackground;
        this.font = font;
        this.fontColor = fontColor;
        defaultSkin = null;
        defaultStyle = null;
        defaultBackground = null;
        defaultFont = null;
    }

    public SkinCreator(Background defaultBackground, Skin<?> defaultSkin, String defaultStyle, ObservableList<String> defaultStyleClass, Font defaultFont, String defaultFontColor) {
        this.defaultBackground = defaultBackground;
        this.defaultSkin = defaultSkin;
        this.defaultStyle = defaultStyle;
        this.defaultFont = defaultFont;
        background = null;
        menuButtonsBackground = null;
        boardsButtonsBackground = null;
        font = null;
        fontColor = null;
    }

    public Font getDefaultFont() {
        return defaultFont;
    }

    public Skin<?> getDefaultSkin() {
        return defaultSkin;
    }

    public String getDefaultStyle() {
        return defaultStyle;
    }

    public Background getDefaultBackground() {
        return defaultBackground;
    }

    public Background getBackground() {
        return background;
    }

    public Background getMenuButtonsBackground() {
        return menuButtonsBackground;
    }

    public Background getBoardsButtonsBackground() {
        return boardsButtonsBackground;
    }

    public Font getFont() {
        return font;
    }

    public String getFontColor() {
        return fontColor;
    }
}
