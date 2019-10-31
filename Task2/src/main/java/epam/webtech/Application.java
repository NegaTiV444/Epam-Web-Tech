package epam.webtech;

import epam.webtech.exceptions.ValidationException;
import epam.webtech.model.HorseRacingData;
import epam.webtech.services.HorseRacingMigrationService;
import epam.webtech.model.bet.BetMigrationService;
import epam.webtech.model.horse.HorseMigrationService;
import epam.webtech.model.race.RaceMigrationService;
import epam.webtech.model.user.UserMigrationService;
import epam.webtech.services.FileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {

    private static final String XML_DATA_FILE = "src/main/resources/HorseRacingData.xml";

    private static final Logger logger = LogManager.getLogger(Application.class);

    private static HorseMigrationService horseMigrationService = HorseMigrationService.getInstance();
    private static BetMigrationService betMigrationService = BetMigrationService.getInstance();
    private static RaceMigrationService raceMigrationService = RaceMigrationService.getInstance();
    private static UserMigrationService userMigrationService = UserMigrationService.getInstance();
    private static HorseRacingMigrationService horseRacingMigrationService = HorseRacingMigrationService.getInstance();
    private static FileService fileService = FileService.getInstance();



    public static void main(String[] args) {
        System.out.println("XML to MySQL migration tool (using XSD-validation)");
        logger.debug("Application started");
        try {
            HorseRacingData data = horseRacingMigrationService.loadHorseRacingData(fileService.checkFile(XML_DATA_FILE));
            horseMigrationService.migrate(data.getHorses());
            betMigrationService.migrate(data.getBets());
            raceMigrationService.migrate(data.getRaces());
            userMigrationService.migrate(data.getUsers());
        } catch (ValidationException e) {
            System.out.println("Migration failed " + e);
            logger.debug(e.getMessage());
        }
        logger.debug("Application finished");
    }

}
