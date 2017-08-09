package logic.exceptions;

import logic.enums.ExceptionsMeassage;

/**
 * Created by xozh4v on 8/9/2017.
 */
public class XmlContentException extends Exception {

    public XmlContentException(ExceptionsMeassage message) {
        super(message.message());
    }
}
