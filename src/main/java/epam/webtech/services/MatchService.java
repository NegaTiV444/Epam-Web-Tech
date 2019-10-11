package epam.webtech.services;

import epam.webtech.entities.*;
import epam.webtech.exceptions.NotEnoughMoneyException;
import epam.webtech.exceptions.NotFoundException;
import epam.webtech.repositories.XmlUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MatchService {

    @Autowired
    private XmlUserRepository userRepository;

    public Match createNewMatch(Race race) {
        Match newMatch = new Match();
        newMatch.setRace(race);
        float[] odds = new float[race.getHorses().length];
        for (int i = 0; i < odds.length; i++) {
            odds[i] = 1 + 1 / odds.length;
        }
        newMatch.setOdds(odds);
        newMatch.setBets(new ArrayList<>());
        return newMatch;
    }

    public Bet addBet(User user, Match match, Horse horse, int amount) throws NotEnoughMoneyException {
        if (user.getBank() >= amount) {
            user.setBank(user.getBank() - amount);
            Bet newBet = new Bet();
            newBet.setUserName(user.getName());
            newBet.setAmount(amount);
            newBet.setMatch(match);
            newBet.setHorse(horse);
            updateOdds(match, newBet);
            match.setBank(match.getBank() + amount);
            return newBet;
        } else
            throw new NotEnoughMoneyException();

    }

    private void updateOdds(Match match, Bet bet) {
        match.getBets().add(bet);
        Horse[] horses = match.getRace().getHorses();
        float[] odds = match.getOdds();
        int totalBets = match.getBets().size();
        for (int i = 0; i < horses.length; i++) {
            long counter;
            int finalI = i;
            counter = match.getBets().stream()
                    .filter(x -> x.getHorse().equals(horses[finalI]))
                    .count();
            odds[i] = 1 + 1 - (counter + 1) / totalBets;
        }
    }

    public void checkBets(Match match) {
        if (match.getRace().getStatus().equals(Race.RaceStatus.FINISHED)){
            Horse winner = match.getRace().getWinner();
            int winnerIndex = 0;
            for (int i = 0; i < match.getOdds().length; i++)
            {
                if (match.getRace().getHorses()[i].equals(winner)){
                    winnerIndex = i;
                    break;
                }
            }
            for (Bet bet: match.getBets()) {
                if (bet.getHorse().equals(winner)) {
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
