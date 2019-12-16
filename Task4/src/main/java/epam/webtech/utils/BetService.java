package epam.webtech.utils;

import epam.webtech.exceptions.AlreadyExistsException;
import epam.webtech.exceptions.DatabaseException;
import epam.webtech.exceptions.NotEnoughMoneyException;
import epam.webtech.exceptions.NotFoundException;
import epam.webtech.model.bet.Bet;
import epam.webtech.model.bet.BetDao;
import epam.webtech.model.bet.MySqlBetDao;
import epam.webtech.model.horse.Horse;
import epam.webtech.model.race.Race;
import epam.webtech.model.user.MySqlUserDao;
import epam.webtech.model.user.User;
import epam.webtech.model.user.UserDao;

public class BetService {

    private BetDao betDao = MySqlBetDao.getInstance();
    private UserDao userDao = MySqlUserDao.getInstance();

    public void makeBet(User user, Race race, Horse horse, int amount) throws NotEnoughMoneyException, DatabaseException, AlreadyExistsException, NotFoundException {
        if (user.getBank() < amount) {
            throw new NotEnoughMoneyException();
        } else {
            Bet bet = new Bet();
            bet.setUser(user);
            bet.setHorse(horse);
            bet.setRace(race);
            bet.setAmount(amount);
            user.setBank(user.getBank() - amount);
            userDao.update(user);
            betDao.add(bet);
        }
    }

    public static BetService getInstance() {
        return BetService.SingletonHandler.INSTANCE;
    }

    private static class SingletonHandler {
        static final BetService INSTANCE = new BetService();
    }
}
