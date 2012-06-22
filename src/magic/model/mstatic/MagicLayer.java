package magic.model.mstatic;

import magic.model.MagicGame;
import magic.model.MagicSource;
import magic.model.MagicManaCost;

public enum MagicLayer {
    Card,        //0.  properties from the card, not formally defined in rules
    Copy,        //1.  copy 
    Control,     //2.  control changing
    Text,        //3.  text changing
    CDASubtype,  //4a. CDA subtype
    Type,        //4b. type-changing (include sub and super types)
    CDAColor,    //5a. CDA color
    Color,       //5b. color changing
    Ability,     //6.  ability adding/removing
    CDAPT,       //7a. CDA p/t
    SetPT,       //7b. set p/t to specific value
    ModPT,       //7c. modify p/t
    CountersPT,  //7d. p/t changes due to counters
    SwitchPT,    //7e. switch p/t 
    Player,      //8.  affect player, not objects
    Game,        //9.  affect game rules, not objects
    ;

    public static MagicManaCost getManaCost(final MagicSource source) {
        final MagicManaCost cost = source.getCardDefinition().getCost();
        final MagicGame game = source.getGame();

        //cost modifications due to continous effects

        return cost;
    }
}
