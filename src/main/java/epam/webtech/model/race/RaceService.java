package epam.webtech.model.race;

import epam.webtech.exceptions.NotEnoughMoneyException;
import epam.webtech.exceptions.NotFoundException;
import epam.webtech.model.enums.RaceStatus;
import epam.webtech.model.horse.Horse;
import epam.webtech.model.bet.Bet;
import epam.webtech.model.user.User;
import epam.webtech.model.bet.XmlBetRepository;
import epam.webtech.model.user.XmlUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class RaceService {

    @Autowired
    private XmlBetRepository betRepository;

    @Autowired
    private XmlUserRepository userRepository;

    public Race createRace(String[] horsesNames, Date date) {
        Race newRace = new Race();
        newRace.setHorsesNames(horsesNames);
        newRace.setDate(date);
        float[] odds = new float[horsesNames.length];
        for (int i = 0; i < odds.length; i++) {
            odds[i] = 1 + 1 / odds.length;
        }
        newRace.setOdds(odds);
        newRace.setBetsId(new ArrayList<>());
        return newRace;
    }

    public Bet addBet(User user, Race race, Horse horse, int amount) throws NotEnoughMoneyException {
        if (user.getBank() >= amount) {
            user.setBank(user.getBank() - amount);
            Bet newBet = new Bet();
            newBet.setUserName(user.getName());
            newBet.setAmount(amount);
            newBet.setRaceId(race.getId());
            newBet.setHorseName(horse.getName());
            updateOdds(race, newBet);
            race.setBank(race.getBank() + amount);
            return newBet;
        } else
            throw new NotEnoughMoneyException();

    }

    private void updateOdds(Race race, Bet bet) {
        betRepository.add(bet);
        race.getBetsId().add(bet.getId());
        String[] horsesNames = race.getHorsesNames();
        float[] odds = race.getOdds();
        int totalBets = race.getBetsId().size();
        for (int i = 0; i < horsesNames.length; i++) {
            long counter;
            int finalI = i;
            counter = race.getBetsId().stream()
                    .filter(x -> betRepository.getByID(x).getHorseName().equals(horsesNames[finalI]))
                    .count();
            odds[i] = 1 + 1 - (counter + 1) / totalBets;
        }
    }

    public void checkBets(Race race) {
        if (race.getStatus().equals(RaceStatus.FINISHED)){
            String winnerHorseName = race.getWinnerHorseName();
            int winnerIndex = 0;
            for (int i = 0; i < race.getOdds().length; i++)
            {
                if (race.getHorsesNames()[i].equals(winnerHorseName)){
                    winnerIndex = i;
                    break;
                }
            }
            for (int id: race.getBetsId()) {
                Bet bet = betRepository.getByID(id);
                if (bet.getHorseName().equals(winnerHorseName)) {
                    int prize = (int)Math.floor(race.getOdds()[winnerIndex] * bet.getAmount());
                    try {
                        User user = userRepository.getByName(bet.getUserName());
                        user.setBank(user.getBank() + prize);
                        race.setBank(race.getBank() - prize);
                    } catch (NotFoundException e) {
                        //TODO log
                    }
                }
            }
        }
    }

}
