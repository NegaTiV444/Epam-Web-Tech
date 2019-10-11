package epam.webtech.entities;

import lombok.Data;

@Data
public class Bet extends Entity implements Comparable{

    private float amount;
    private Match match;
    private Horse horse;
    private String userName;

    @Override
    public int compareTo(Object o) {
        return match.compareTo(((Bet)o).match);
    }
}
