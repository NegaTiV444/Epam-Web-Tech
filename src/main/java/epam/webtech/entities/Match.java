package epam.webtech.entities;

import lombok.Data;

import java.util.List;

@Data
public class Match extends Entity implements Comparable{

    private int raceId;
    private List<Integer> betsId;
    private float[] odds;
    private int bank;

    @Override
    public int compareTo(Object o) {
        return getId() - ((Match)o).getId();
    }
}
