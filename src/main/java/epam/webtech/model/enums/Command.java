package epam.webtech.model.enums;

public enum Command {

    REGISTER("/register", "Register new User"),
    LOGIN("/login", "Log in to the system"),
    EXIT("/exit", "Log out of the system / stop application"),
    RACES("/races", "Show all races"),
    BETS("/bets", "Show your bets"),
    MAKE_BET("/make bet", "Make a new bet"),
    ME("/me", "Show your account's information"),
    HELP("/help", "Show available command");

    Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    private String name;
    private String description;

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

}
