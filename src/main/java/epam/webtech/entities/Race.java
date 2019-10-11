package epam.webtech.entities;

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

    private Horse[] horses;
    private Horse winner;
    private RaceStatus status;
    private Date date;

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
