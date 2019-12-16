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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AddRacePageServlet extends HttpServlet {

    private HorseDao horseDao = MySqlHorseDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer authorityLvl = (Integer) req.getSession().getAttribute("authorityLvl");
        if (2 != authorityLvl) {
            resp.sendRedirect("races");
        } else {
            try {
                req.setAttribute("horses", horseDao.findAll());
                req.getRequestDispatcher("/WEB-INF/pages/addRacePage.jsp").forward(req, resp);
            } catch (DatabaseException | NotFoundException e) {
                throw new InternalException(e.getMessage());
            }
        }
    }


}
