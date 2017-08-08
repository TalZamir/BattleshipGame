package logic.serializers;

import logic.interfaces.ISerializer;
import module.BattleShipGameType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Created by barakm on 08/08/2017
 */
public class XmlSerializer implements ISerializer {

    private static final String CONTEXT_PATH = "module";
    private static final String RELATIVE_PATH_FOR_XML_FILE = "module/battleshipx.xml";
    private final BattleShipGameType battleShipGameType;

    public XmlSerializer() {
        JAXBContext jaxbContext;
        JAXBElement unmarshalledObject = null;
        Unmarshaller unmarshaller;
        try {
            jaxbContext = JAXBContext.newInstance(CONTEXT_PATH);
            unmarshaller = jaxbContext.createUnmarshaller();
            unmarshalledObject = (JAXBElement) unmarshaller.unmarshal(ClassLoader.getSystemResourceAsStream(RELATIVE_PATH_FOR_XML_FILE));
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        assert unmarshalledObject != null;
        battleShipGameType = (BattleShipGameType) unmarshalledObject.getValue();
    }

    @Override
    public BattleShipGameType getBattleShipGameType() {
        return battleShipGameType;
    }
}
