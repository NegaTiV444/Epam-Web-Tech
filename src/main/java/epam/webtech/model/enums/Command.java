package epam.webtech.model.enums;

public enum Command {

    REGISTER("register"),
    LOGIN("login"),
    EXIT("exit"),
    RACES("races"),
    RACE_INFO("race info"),
    BETS("bets"),
    MAKE_BET("make bet"),
    ME("me"),
    HELP("help");

    Command(String string) {
        this.string = string;
    }

    private String string;

    public String getString() {
        return string;
    }

}
