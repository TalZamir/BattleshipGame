
package module;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the module package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BattleShipGame_QNAME = new QName("", "BattleShipGame");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: module
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BattleShipGameType }
     * 
     */
    public BattleShipGameType createBattleShipGameType() {
        return new BattleShipGameType();
    }

    /**
     * Create an instance of {@link ShipTypeType }
     * 
     */
    public ShipTypeType createShipTypeType() {
        return new ShipTypeType();
    }

    /**
     * Create an instance of {@link BoardType }
     * 
     */
    public BoardType createBoardType() {
        return new BoardType();
    }

    /**
     * Create an instance of {@link PositionType }
     * 
     */
    public PositionType createPositionType() {
        return new PositionType();
    }

    /**
     * Create an instance of {@link BoardsType }
     * 
     */
    public BoardsType createBoardsType() {
        return new BoardsType();
    }

    /**
     * Create an instance of {@link MineType }
     * 
     */
    public MineType createMineType() {
        return new MineType();
    }

    /**
     * Create an instance of {@link ShipType }
     * 
     */
    public ShipType createShipType() {
        return new ShipType();
    }

    /**
     * Create an instance of {@link ShipTypesType }
     * 
     */
    public ShipTypesType createShipTypesType() {
        return new ShipTypesType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BattleShipGameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "BattleShipGame")
    public JAXBElement<BattleShipGameType> createBattleShipGame(BattleShipGameType value) {
        return new JAXBElement<BattleShipGameType>(_BattleShipGame_QNAME, BattleShipGameType.class, null, value);
    }

}
