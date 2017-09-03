
package module;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BattleShipGameType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BattleShipGameType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GameType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="boardSize" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mine" type="{}mineType"/>
 *         &lt;element name="shipTypes" type="{}shipTypesType"/>
 *         &lt;element name="boards" type="{}boardsType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BattleShipGameType", propOrder = {
    "gameType",
    "boardSize",
    "mine",
    "shipTypes",
    "boards"
})
public class BattleShipGameType {

    @XmlElement(name = "GameType", required = true)
    protected String gameType;
    @XmlElement(required = true)
    protected String boardSize;
    @XmlElement(required = true)
    protected MineType mine;
    @XmlElement(required = true)
    protected ShipTypesType shipTypes;
    @XmlElement(required = true)
    protected BoardsType boards;

    /**
     * Gets the value of the gameType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGameType() {
        return gameType;
    }

    /**
     * Sets the value of the gameType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGameType(String value) {
        this.gameType = value;
    }

    /**
     * Gets the value of the boardSize property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBoardSize() {
        return boardSize;
    }

    /**
     * Sets the value of the boardSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBoardSize(String value) {
        this.boardSize = value;
    }

    /**
     * Gets the value of the mine property.
     * 
     * @return
     *     possible object is
     *     {@link MineType }
     *     
     */
    public MineType getMine() {
        return mine;
    }

    /**
     * Sets the value of the mine property.
     * 
     * @param value
     *     allowed object is
     *     {@link MineType }
     *     
     */
    public void setMine(MineType value) {
        this.mine = value;
    }

    /**
     * Gets the value of the shipTypes property.
     * 
     * @return
     *     possible object is
     *     {@link ShipTypesType }
     *     
     */
    public ShipTypesType getShipTypes() {
        return shipTypes;
    }

    /**
     * Sets the value of the shipTypes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipTypesType }
     *     
     */
    public void setShipTypes(ShipTypesType value) {
        this.shipTypes = value;
    }

    /**
     * Gets the value of the boards property.
     * 
     * @return
     *     possible object is
     *     {@link BoardsType }
     *     
     */
    public BoardsType getBoards() {
        return boards;
    }

    /**
     * Sets the value of the boards property.
     * 
     * @param value
     *     allowed object is
     *     {@link BoardsType }
     *     
     */
    public void setBoards(BoardsType value) {
        this.boards = value;
    }

}
