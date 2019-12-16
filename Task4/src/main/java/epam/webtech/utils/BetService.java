package epam.webtech.utils;

import epam.webtech.exceptions.NotEnoughMoneyException;
import epam.webtech.model.bet.Bet;
import epam.webtech.model.horse.Horse;
import epam.webtech.model.race.Race;
import epam.webtech.model.user.User;

public class BetService {

    public Bet makeBet(User user, Race race, Horse horse, int amount) throws NotEnoughMoneyException {
        if (user.getBank() < amount) {
            throw new NotEnoughMoneyException();
        } else {
            Bet bet = new Bet();
            bet.setUser(user);
            bet.setHorse(horse);
            bet.setRace(race);
            bet.setAmount(amount);
            return bet;
        }
    }

    public static BetService getInstance() {
        return BetService.SingletonHandler.INSTANCE;
    }

    private static class SingletonHandler {
        static final BetService INSTANCE = new BetService();
    }
}
