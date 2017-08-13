package ui.verifiers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barakm on 13/08/2017
 */
public class ErrorCollector {

    List<String> errors = new ArrayList<>();

    public void addMessage(String newMessage) {
        errors.add(newMessage);
    }

    public List<String> getMessages() {
        return errors;
    }

    public int getNumOfErrors() {
        return errors.size();
    }

    //    public void printErrors() {
    //        errors.forEach(System.out::println);
    //    }
}
