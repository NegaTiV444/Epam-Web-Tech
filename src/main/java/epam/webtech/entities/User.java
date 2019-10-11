package epam.webtech.entities;

import lombok.Data;

import java.util.List;

@Data
public class User extends Entity implements Comparable{

    private int bank;
    private String name;
    private List<Bet> bets;
    private String passwordHash;

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((User)o).name);
    }
}
