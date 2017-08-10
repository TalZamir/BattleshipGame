package ui;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public final class BattleshipApp {

    private BattleshipApp() {
    }

    public static void main(String[] args) throws JAXBException, FileNotFoundException {
        UiApp ui = new UiApp();
        ui.start();
    }

}
