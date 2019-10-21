package epam.webtech.model.user;

import epam.webtech.model.Entity;
import epam.webtech.model.bet.Bet;
import lombok.Data;

import java.util.List;

/**
 * Class representing the user.
 * @author NegaTiV444
 * @version 1.0
 */

@Data
public class User extends Entity implements Comparable<User> {

    /** The amount of money a user has */
    private int bank;

    /** User name */
    private String name;
    /** List of id of bets a user has */
    private List<Integer> betsId;
    /** Password hash */
    private String passwordHash;
    /** Level of authority (1 - simple user, 2 - admin)*/
    private int authorityLvl;

    /**
     * Compare User to another User
     * @param o - User to be compared.
     * @return a negative integer, zero, or a positive integer as this User is less than, equal to, or greater than the specified User.
     * @see User
     */

    @Override
    public int compareTo(User o) {
        return name.compareTo(o.name);
    }
}
