package epam.webtech.application;

import epam.webtech.exceptions.AlreadyExistsException;
import epam.webtech.exceptions.NotEnoughMoneyException;
import epam.webtech.exceptions.NotFoundException;
import epam.webtech.exceptions.WrongPasswordException;
import epam.webtech.model.bet.Bet;
import epam.webtech.model.bet.BetRepository;
import epam.webtech.model.enums.Command;
import epam.webtech.model.horse.Horse;
import epam.webtech.model.horse.HorseRepository;
import epam.webtech.model.race.Race;
import epam.webtech.model.race.RaceRepository;
import epam.webtech.model.race.RaceService;
import epam.webtech.model.user.User;
import epam.webtech.model.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Component
public class CommandHandler {

    private boolean isRunning = true;
    private Scanner scanner = new Scanner(System.in);
    private User currentUser;

    @Autowired
    private UserService userService;

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private BetRepository betRepository;

    @Autowired
    private HorseRepository horseRepository;

    @Autowired
    private RaceService raceService;

    public void listenCommands() {
        String input;
        while (isRunning) {
            System.out.println("Enter command:");
            input = scanner.next();
            input = input.trim().toLowerCase();
            switch (input) {
                case "/register":
                    handleRegisterCommand();
                    break;
                case "/login":
                    handleLoginCommand();
                    break;
                case "/exit":
                    handleExitCommand();
                    break;
                case "/races":
                    handleRacesCommand();
                    break;
                case "/bets":
                    handleBetsCommand();
                    break;
                case "/make bet":
                    handleMakeBetCommand();
                    break;
                case "/me":
                    handleMeCommand();
                    break;
                case "/help":
                    handleHelpCommand();
                    break;
                default:
                    handleWrongCommand();
                    break;
            }
        }
    }

    private void handleRegisterCommand() {
        if (currentUser != null)
            System.out.println("Please log out before register new user");
        else {
            boolean isError = true;
            String name, password;
            while (isError) {
                System.out.println("Enter name (3-16 characters)");
                name = scanner.next();
                name = name.trim();
                if (name.equals("/exit"))
                    return;
                if ((name.length() < 3) || (name.length() > 16))
                    continue;
                System.out.println("Enter your password (6-16 characters)");
                password = scanner.next();
                password = password.trim();
                if (password.equals("/exit"))
                    return;
                if ((password.length() < 6) || (password.length() > 16))
                    continue;
                try {
                    currentUser = userService.registerNewUser(name, password);
                    System.out.println("Registration successful\nCurrent user: " + currentUser.getName());
                    isError = false;
                } catch (IOException e) {
                    //Database error;
                    //TODO log
                } catch (AlreadyExistsException e) {
                    System.out.println("User " + name + " already exists");
                }

            }
        }
    }

    private void handleLoginCommand() {
        if (currentUser != null)
            System.out.println("Please log out before log in");
        else {
            boolean isError = true;
            String name, password;
            while (isError) {
                System.out.println("Enter name (3-16 characters)");
                name = scanner.next();
                name = name.trim();
                if (name.equals("/exit"))
                    return;
                if ((name.length() < 3) || (name.length() > 16))
                    continue;
                System.out.println("Enter your password (6-16 characters)");
                password = scanner.next();
                password = password.trim();
                if (password.equals("/exit"))
                    return;
                if ((password.length() < 6) || (password.length() > 16))
                    continue;
                try {
                    currentUser = userService.logIn(name, password);
                    System.out.println("LogIn successful\nCurrent user: " + currentUser.getName());
                    isError = false;
                } catch (NotFoundException e) {
                    System.out.println("User " + name + " doesn't exist");
                } catch (WrongPasswordException e) {
                    System.out.println("Wrong password");
                }
            }

        }
    }

    private void handleExitCommand() {
        if (currentUser != null) {
            currentUser = null;
            System.out.println("Log out");
        } else {
            isRunning = false;
        }
    }

    private void handleRacesCommand() {
        List<Race> races = raceRepository.findAll();
        System.out.println("\n-----------------######   RACES   ######-----------------");
        for (Race race : races) {
            System.out.println("----------------------------------------------------------------");
            printRace(race);

        }
        System.out.println("----------------------------------------------------------------");
    }

    private void printRace(Race race) {
        System.out.println("Status: " + race.getStatus().toString() + "\nID: " + race.getId() + ". Date" + race.getDate().toString());
        System.out.println("Horses: ");
        String[] horses = race.getHorsesNames();
        float[] odds = race.getOdds();
        StringBuilder sb;
        for (int i = 0; i < horses.length; i++) {
            sb = new StringBuilder("   ");
            sb.append(horses[i]);
            for (int j = 20 - horses[i].length(); i > 0; j--) {
                sb.append(" ");
            }
            sb.append(odds[i]);
            System.out.println(sb.toString());
        }
    }

    private void handleBetsCommand() {
        List<Bet> bets = betRepository.findByUser(currentUser);
        System.out.println("\n-----------------######   YOUR BETS   ######-----------------");
        if (bets.isEmpty())
            System.out.println("You haven't active bets");
        else {
            for (Bet bet : bets) {
                System.out.println("----------------------------------------------------------------");
                System.out.println("RACE: ");
                try {
                    printRace(raceRepository.getByID(bet.getRaceId()));
                } catch (NotFoundException e) {
                    System.out.println("Race not found");
                    //TODO log
                }
                System.out.println("\nYour bet:\n   Horse: " + bet.getHorseName() + "\nAmount: " + bet.getAmount() + "\n");
            }
            System.out.println("----------------------------------------------------------------");
        }
    }

    private void handleMakeBetCommand() {
        System.out.println("\n-----------------######   ADD NEW BET   ######-----------------");

        boolean isError = true;
        int raceId, amount;
        String horseName;
        while (isError)
            try {
                System.out.println("Enter the race ID");
                String input = scanner.next();
                if (input.equals("/exit"))
                    return;
                raceId = Integer.parseInt(input);
                Race race = raceRepository.getByID(raceId);
                System.out.println("Enter the horse name");
                input = scanner.next();
                if (input.equals("/exit"))
                    return;
                horseName = input;
                Horse horse = horseRepository.getByName(horseName);
                System.out.println("Enter the amount of money");
                input = scanner.next();
                if (input.equals("/exit"))
                    return;
                amount = Integer.parseInt(input);
                raceService.addBet(currentUser, race, horse, amount);
                isError = false;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input. Please enter the number");
            } catch (NotFoundException | AlreadyExistsException | NotEnoughMoneyException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("Database error");
                //TODO log
            }
        System.out.println("Bet added successfully");
        System.out.println("----------------------------------------------------------------");
    }

    private void handleMeCommand() {

        System.out.println("\n-----------------######   YOUR PROFILE   ######-----------------");
        System.out.println("Name: " + currentUser.getName() + "\nTotal money: " + currentUser.getBank() +
                "\nActive bets: " + currentUser.getBetsId().size());
        System.out.println("----------------------------------------------------------------");
        System.out.println("----------------------------------------------------------------");
    }

    private void handleHelpCommand() {
        System.out.println("----------------------------------------------------------------");
        for (Command command : Command.values()) {
            System.out.println(command.getName() + "---->" + command.getDescription());
        }
        System.out.println("----------------------------------------------------------------");
    }

    private void handleWrongCommand() {
        System.out.println("Unknown command\n");
    }

}
