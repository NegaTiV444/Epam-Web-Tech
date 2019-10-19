package epam.webtech.model.user;

import epam.webtech.model.Entity;
import epam.webtech.model.bet.Bet;
import lombok.Data;

import java.util.List;

@Data
public class User extends Entity implements Comparable<User> {

    private int bank;
    private String name;
    private List<Integer> betsId;
    private String passwordHash;
    private int authorityLvl;

    @Override
    public int compareTo(User o) {
        return name.compareTo(o.name);
    }
}
