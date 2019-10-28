package epam.webtech.model;

import epam.webtech.exceptions.ValidationException;

import java.io.File;
import java.util.List;

public interface MigrationService<T> {

    List<T> validateAndMigrate(File xmlDataFile) throws ValidationException;
}
