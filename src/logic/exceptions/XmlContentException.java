package logic.exceptions;

import logic.enums.ErrorMessages;

/**
 * Created by xozh4v on 8/9/2017.
 */
public class XmlContentException extends Exception {

    public XmlContentException(ErrorMessages message) {
        super(message.message());
    }
}
