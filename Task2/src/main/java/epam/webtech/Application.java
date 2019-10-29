package epam.webtech;

import epam.webtech.exceptions.ValidationException;
import epam.webtech.model.bet.BetMigrationService;
import epam.webtech.model.horse.HorseMigrationService;
import epam.webtech.model.race.RaceMigrationService;
import epam.webtech.model.user.UserMigrationService;
import epam.webtech.services.FileService;

public class Application {

    private static HorseMigrationService horseMigrationService = HorseMigrationService.getInstance();
    private static BetMigrationService betMigrationService = BetMigrationService.getInstance();
    private static RaceMigrationService raceMigrationService = RaceMigrationService.getInstance();
    private static UserMigrationService userMigrationService = UserMigrationService.getInstance();
    private static FileService fileService = FileService.getInstance();

    public static void main(String[] args) {
        System.out.println("XML to MySQL migration tool (using XSD-validation)");
        try {
            horseMigrationService.validateAndMigrate(fileService.checkFile("horses.xml"));
            betMigrationService.validateAndMigrate(fileService.checkFile("bets.xml"));
            raceMigrationService.validateAndMigrate(fileService.checkFile("races.xml"));
            userMigrationService.validateAndMigrate(fileService.checkFile("users.xml"));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

}
