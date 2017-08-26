package ui;

import ui.javafx.JavaFxClass;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public final class BattleshipApp {

    private BattleshipApp() {
    }

    public static void main(String[] args) throws JAXBException, FileNotFoundException {
        JavaFxClass ui = new JavaFxClass();
        ui.init(args);
    }

}
