package logic.serializers;

import logic.interfaces.IGameSerializer;
import module.BattleShipGameType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Created by barakm on 08/08/2017
 */
public class XmlSerializer implements IGameSerializer {

    private static final String CONTEXT_PATH = "module";
    private static final String RELATIVE_PATH_FOR_XML_FILE = "module/battleshipx.xml";
    private final BattleShipGameType battleShipGameType;

    public XmlSerializer() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CONTEXT_PATH);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        JAXBElement unmarshalledObject = (JAXBElement) unmarshaller.unmarshal(ClassLoader.getSystemResourceAsStream(RELATIVE_PATH_FOR_XML_FILE));
        battleShipGameType = (BattleShipGameType) unmarshalledObject.getValue();
    }

    @Override
    public BattleShipGameType getBattleShipGameType() {
        return battleShipGameType;
    }
}
