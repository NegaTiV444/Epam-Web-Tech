package epam.webtech.application;

import epam.webtech.exceptions.AlreadyExistsException;
import epam.webtech.exceptions.NotFoundException;
import epam.webtech.model.user.User;
import epam.webtech.model.user.UserRepository;
import epam.webtech.services.HashService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Scanner;

public class CommandHandler {

    private boolean isRunning = true;
    private Scanner scanner = new Scanner(System.in);
    private User currentUser;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashService hashService;

    public void listenCommands() {
        String input;
        while (isRunning) {
            System.out.println("Enter command:");
            input = scanner.next();
            input = input.trim().toLowerCase();
            switch (input) {
                case "register":
                    handleRegisterCommand();
                    break;
                case "login":
                    handleLoginCommand();
                    break;
                case "exit":
                    handleExitCommand();
                    break;
                case "races":
                    handleRacesCommand();
                    break;
                case "race info":
                    handleRaceInfoCommand();
                    break;
                case "bets":
                    handleBetsCommand();
                    break;
                case "make bet":
                    handleMakeBetCommand();
                    break;
                case "me":
                    handleMeCommand();
                    break;
                case "help":
                    handleHelpCommand();
                    break;
                default:
                    handleWrongCommand();
                    break;
            }
        }
    }

    private void handleRegisterCommand() {
        if (currentUser == null)
            System.out.println("Please log out before register new user");
        else {
            boolean isError = true;
            User newUser = new User();
            String input;
            while (isError) {
                System.out.println("Enter name (3-16 characters)");
                input = scanner.next();
                input = input.trim();
                isError = (input.length() < 3) || (input.length() > 16);
                if (isError) {
                    System.out.println("Incorrect length");
                } else {
                    try {
                        userRepository.getByName(input);
                        System.out.println("User " + input + " already exists");
                    } catch (NotFoundException e) {
                        newUser.setName(input);
                    }
                }
            }
            isError = true;
            while (isError) {
                System.out.println("Enter your password (6-16 characters)");
                input = scanner.next();
                input = input.trim();
                isError = (input.length() < 6) || (input.length() > 16);
                if (isError) {
                    System.out.println("Incorrect length");
                } else {
                    newUser.setPasswordHash(hashService.getHash(input));
                }
            }
            newUser.setBank(10000);
            try {
                userRepository.add(currentUser);
                currentUser = newUser;
            } catch (IOException e) {
                System.out.println("Database error;");
                //TODO log
            } catch (AlreadyExistsException e) {
                System.out.println("User already exists");
            }
        }
    }

    private void handleLoginCommand() {

    }

    private void handleExitCommand() {

    }

    private void handleRacesCommand() {

    }

    private void handleRaceInfoCommand() {

    }

    private void handleBetsCommand() {

    }

    private void handleMakeBetCommand() {

    }

    private void handleMeCommand() {

    }

    private void handleHelpCommand() {

    }

    private void handleWrongCommand() {

    }

}
