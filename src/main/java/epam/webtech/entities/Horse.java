package epam.webtech.entities;

import lombok.Data;

@Data
public class Horse extends Entity implements Comparable{

    private String name;
    private int winsCounter;

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((Horse) o).getName());
    }
}
