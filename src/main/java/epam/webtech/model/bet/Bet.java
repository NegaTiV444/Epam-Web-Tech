package epam.webtech.model.bet;

import epam.webtech.model.Entity;
import lombok.Data;

@Data
public class Bet extends Entity implements Comparable{

    private float amount;
    private int matchID;
    private String horseName;
    private String userName;

    @Override
    public int compareTo(Object o) {
        if ((matchID == ((Bet)o).matchID) && (horseName.equals(((Bet)o).horseName))){
            return 0;
        } else
        return (int)(amount - ((Bet)o).getAmount());
    }
}
