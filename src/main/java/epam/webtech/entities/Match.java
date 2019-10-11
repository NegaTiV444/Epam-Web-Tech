package epam.webtech.entities;

import lombok.Data;

import java.util.List;

@Data
public class Match extends Entity implements Comparable{

    private Race race;
    private List<Bet> bets;
    private float[] odds;
    private int bank;

    @Override
    public int compareTo(Object o) {
        return getId() - ((Match)o).getId();
    }
}
