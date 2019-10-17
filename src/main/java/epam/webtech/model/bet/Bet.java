package epam.webtech.model.bet;

import epam.webtech.model.Entity;
import lombok.Data;

@Data
public class Bet extends Entity implements Comparable<Bet>{

    private float amount;
    private int raceId;
    private String horseName;
    private String userName;

    @Override
    public int compareTo(Bet o) {
        if ((raceId == o.raceId) && (horseName.equals(o.horseName))){
            return 0;
        } else
            return (int)(amount - o.getAmount());
    }
}
