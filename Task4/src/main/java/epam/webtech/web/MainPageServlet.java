package epam.webtech.web;

import epam.webtech.exceptions.AlreadyExistsException;
import epam.webtech.exceptions.DatabaseException;
import epam.webtech.exceptions.InternalException;
import epam.webtech.exceptions.NotFoundException;
import epam.webtech.model.enums.RaceStatus;
import epam.webtech.model.horse.Horse;
import epam.webtech.model.horse.HorseDao;
import epam.webtech.model.horse.MySqlHorseDao;
import epam.webtech.model.race.MySqlRaceDao;
import epam.webtech.model.race.Race;
import epam.webtech.model.race.RaceDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainPageServlet extends HttpServlet {

    private HorseDao horseDao = MySqlHorseDao.getInstance();
    private RaceDao raceDao = MySqlRaceDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Race> races = raceDao.findAll();
            req.setAttribute("races", races);
        } catch (DatabaseException | NotFoundException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("/WEB-INF/pages/racesPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Race race = new Race();
            List<Horse> horses = horseDao.findAll();
            Map<String, String[]> paramMap = req.getParameterMap();
            for (Horse horse: horses) {
                if (paramMap.containsKey(horse.getName())) {
                    race.getHorses().add(horse);
                }
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = req.getParameter("race_date");
            Date raceDate = format.parse(dateStr);
            race.setDate(raceDate);
            race.setStatus(RaceStatus.WAITING);
            raceDao.add(race);
            resp.sendRedirect("races");
        } catch (DatabaseException | NotFoundException | ParseException | AlreadyExistsException e) {
            throw new InternalException(e.getMessage());
        }

    }
}
