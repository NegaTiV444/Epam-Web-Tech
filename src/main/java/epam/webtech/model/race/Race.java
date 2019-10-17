package epam.webtech.model.race;

import epam.webtech.model.Entity;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Data
public class Race extends Entity implements Comparable {

    public enum RaceStatus {
        VAITING,
        IN_PROGRESS,
        FINISHED
    }

    private String[] horsesNames;
    private String winnerHorseName;
    private RaceStatus status;
    private Date date;

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
