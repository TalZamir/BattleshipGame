package logic.serializers;

import logic.interfaces.ISerializer;
import module.BattleShipGameType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XmlSerializer implements ISerializer {

    private static final String CONTEXT_PATH = "module";
    private final BattleShipGameType battleShipGameType;

    public XmlSerializer(String xmlPath) throws JAXBException {
        File file = new File(xmlPath);
        JAXBContext jaxbContext;
        JAXBElement unmarshalledObject;
        Unmarshaller unmarshaller;
        jaxbContext = JAXBContext.newInstance(CONTEXT_PATH);
        unmarshaller = jaxbContext.createUnmarshaller();
        unmarshalledObject = (JAXBElement) unmarshaller.unmarshal(file);
        assert unmarshalledObject != null;
        battleShipGameType = (BattleShipGameType) unmarshalledObject.getValue();
    }

    @Override
    public BattleShipGameType getBattleShipGameType() {
        return battleShipGameType;
    }
}
