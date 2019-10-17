package epam.webtech.model.enums;

public enum RaceStatus {

    VAITING (1),
    IN_PROGRESS (0),
    FINISHED (2);

    private int priority;

    public int getPriority() {
        return priority;
    }

    RaceStatus(int priority) {
        this.priority = priority;
    }

}
