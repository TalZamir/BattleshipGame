package ui;

import logic.TheGame;
import logic.exceptions.XmlContentException;
import ui.enums.MenuItem;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public final class BattleshipApp {

    private BattleshipApp() {
    }

    public static void main(String[] args) throws JAXBException, FileNotFoundException {
        UIApp ui = new UIApp();
        ui.start();
    }

}
