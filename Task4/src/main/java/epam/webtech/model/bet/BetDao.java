package epam.webtech.model.bet;

import epam.webtech.model.Dao;
import epam.webtech.model.horse.Horse;
import epam.webtech.model.race.Race;
import epam.webtech.model.user.User;

import java.util.List;

public interface BetDao extends Dao<Bet> {

    List<Bet> findByUser(User user);
    List<Bet> findByRace(Race race);
    List<Bet> findByHorse(Horse horse);
}
