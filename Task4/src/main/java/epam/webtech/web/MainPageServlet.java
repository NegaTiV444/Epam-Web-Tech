package epam.webtech.web;

import epam.webtech.exceptions.DatabaseException;
import epam.webtech.exceptions.NotFoundException;
import epam.webtech.model.race.MySqlRaceDao;
import epam.webtech.model.race.Race;
import epam.webtech.model.race.RaceDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MainPageServlet extends HttpServlet {

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
}
