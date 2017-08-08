package UI;

import Logic.GameManager;
import com.google.gson.Gson;
import module.BattleShipGameType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.Scanner;

public class BattleshipApp {

    public static void main(String[] args) {

        Gson gson = new Gson();

        try {
            JAXBContext context = JAXBContext.newInstance("module");
            Unmarshaller unmarshaller = context.createUnmarshaller();
            String content = new Scanner(new File("module/battleshipx.xml")).useDelimiter("\\Z").next();
            StringReader reader = new StringReader(content);
            BattleShipGameType bs = (BattleShipGameType) unmarshaller.unmarshal(reader);
            System.out.println(bs.getBoardSize());
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        GameManager gameManager = new GameManager();
        printMenu();
    }

    // **************************************************** //
    // Prints main menu
    // **************************************************** //
    private static void printMenu() {
        System.out.println("************* MAIN MENU *************");
        System.out.println("1. Load XML File");
    }
}
