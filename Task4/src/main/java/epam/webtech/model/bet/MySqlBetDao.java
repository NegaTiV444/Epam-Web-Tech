package epam.webtech.model.bet;

import epam.webtech.exceptions.DatabaseException;
import epam.webtech.exceptions.NotFoundException;
import epam.webtech.model.horse.Horse;
import epam.webtech.model.horse.HorseDao;
import epam.webtech.model.horse.MySqlHorseDao;
import epam.webtech.model.race.Race;
import epam.webtech.model.user.MySqlUserDao;
import epam.webtech.model.user.User;
import epam.webtech.model.user.UserDao;
import epam.webtech.utils.JdbcService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MySqlBetDao implements BetDao{

    private static final String TABLE = "bets";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM " + TABLE + " WHERE bet_id = ? ;";
    private static final String ADD_QUERY = "INSERT INTO " + TABLE
            + " (bet_id, bet_amount, bet_race_id, bet_horse_id, bet_user_id) VALUES (?, ?, ?, ?, ?)";

    private JdbcService jdbcService = epam.webtech.utils.JdbcService.getInstance();
    private UserDao userDao = MySqlUserDao.getInstance();
    private HorseDao horseDao = MySqlHorseDao.getInstance();

    private static class SingletonHandler {
        static final MySqlBetDao INSTANCE = new MySqlBetDao();
    }

    public static MySqlBetDao getInstance() {
        return MySqlBetDao.SingletonHandler.INSTANCE;
    }

    @Override
    public List<Bet> findByUser(User user) {
        return null;
    }

    @Override
    public List<Bet> findByRace(Race race) {
        return null;
    }

    @Override
    public List<Bet> findByHorse(Horse horse) {
        return null;
    }

    @Override
    public void add(Bet object) {

    }

    @Override
    public Bet findById(int id) throws DatabaseException, NotFoundException {
        Bet bet;
        try (PreparedStatement preparedStatement = jdbcService.getConnection().prepareStatement(FIND_BY_ID_QUERY)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.isFirst()) {
                    bet = getBetFromResultSet(resultSet);
                } else {
                    throw new NotFoundException("Bet with id " + id + " not found");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database error");
        }
        return bet;
    }

    @Override
    public List<Bet> findAll() throws DatabaseException {
        return null;
    }

    @Override
    public void update(Bet object) throws DatabaseException, NotFoundException {

    }

    @Override
    public void delete(Bet object) throws DatabaseException, NotFoundException {

    }

    private Bet getBetFromResultSet(ResultSet resultSet) throws SQLException {
        return null;
    }

}
