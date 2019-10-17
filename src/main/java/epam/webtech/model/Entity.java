package epam.webtech.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Entity implements Serializable, Comparable {

    private int id;

    @Override
    public int compareTo(Object o) {
        return id - ((Entity) o).id;
    }
}
