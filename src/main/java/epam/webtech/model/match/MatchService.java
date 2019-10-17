package epam.webtech.model.match;

import epam.webtech.exceptions.NotEnoughMoneyException;
import epam.webtech.exceptions.NotFoundException;
import epam.webtech.model.horse.Horse;
import epam.webtech.model.race.Race;
import epam.webtech.model.bet.Bet;
import epam.webtech.model.user.User;
import epam.webtech.model.bet.XmlBetRepository;
import epam.webtech.model.race.XmlRaceRepository;
import epam.webtech.model.user.XmlUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MatchService {

    @Autowired
    private XmlBetRepository betRepository;

    @Autowired
    private XmlRaceRepository raceRepository;

    @Autowired
    private XmlUserRepository userRepository;

    public Match createNewMatch(Race race) {
        Match newMatch = new Match();
        newMatch.setRaceId(race.getId());
        float[] odds = new float[race.getHorsesNames().length];
        for (int i = 0; i < odds.length; i++) {
            odds[i] = 1 + 1 / odds.length;
        }
        newMatch.setOdds(odds);
        newMatch.setBetsId(new ArrayList<>());
        return newMatch;
    }

    public Bet addBet(User user, Match match, Horse horse, int amount) throws NotEnoughMoneyException {
        if (user.getBank() >= amount) {
            user.setBank(user.getBank() - amount);
            Bet newBet = new Bet();
            newBet.setUserName(user.getName());
            newBet.setAmount(amount);
            newBet.setMatchID(match.getId());
            newBet.setHorseName(horse.getName());
            updateOdds(match, newBet);
            match.setBank(match.getBank() + amount);
            return newBet;
        } else
            throw new NotEnoughMoneyException();

    }

    private void updateOdds(Match match, Bet bet) {
        betRepository.add(bet);
        match.getBetsId().add(bet.getId());
        Race race = raceRepository.getByID(match.getRaceId());
        String[] horsesNames = race.getHorsesNames();
        float[] odds = match.getOdds();
        int totalBets = match.getBetsId().size();
        for (int i = 0; i < horsesNames.length; i++) {
            long counter;
            int finalI = i;
            counter = match.getBetsId().stream()
                    .filter(x -> betRepository.getByID(x).getHorseName().equals(horsesNames[finalI]))
                    .count();
            odds[i] = 1 + 1 - (counter + 1) / totalBets;
        }
    }

    public void checkBets(Match match) {
        Race race = raceRepository.getByID(match.getRaceId());
        if (race.getStatus().equals(Race.RaceStatus.FINISHED)){
            String winnerHorseName = race.getWinnerHorseName();
            int winnerIndex = 0;
            for (int i = 0; i < match.getOdds().length; i++)
            {
                if (race.getHorsesNames()[i].equals(winnerHorseName)){
                    winnerIndex = i;
                    break;
                }
            }
            for (int id: match.getBetsId()) {
                Bet bet = betRepository.getByID(id);
                if (bet.getHorseName().equals(winnerHorseName)) {
                    int prize = (int)Math.floor(match.getOdds()[winnerIndex] * bet.getAmount());
                    try {
                        User user = userRepository.getByName(bet.getUserName());
                        user.setBank(user.getBank() + prize);
                        match.setBank(match.getBank() - prize);
                    } catch (NotFoundException e) {
                        //TODO log
                    }
                }
            }
        }
    }

}
