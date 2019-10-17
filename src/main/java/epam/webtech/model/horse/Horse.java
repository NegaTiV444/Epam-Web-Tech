package epam.webtech.model.horse;

import epam.webtech.model.Entity;
import lombok.Data;

@Data
public class Horse extends Entity implements Comparable<Horse>{

    private int id;
    private String name;
    private int winsCounter;

    @Override
    public int compareTo(Horse o) {
        return name.compareTo(o.getName());
    }
}
