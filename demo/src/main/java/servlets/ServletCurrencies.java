package servlets;

import db.CRUDUtils;
import models.Currencies;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Сервлет для работы с таблицей currencies
 */
@WebServlet("/curr")
public class ServletCurrencies extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        PrintWriter pw = resp.getWriter();
        Currencies currencies = CRUDUtils.searchIdCurrencies(id);
        if (currencies != null) {
            pw.println(currencies);
        } else {
            pw.println("Заданная валюта не найдена");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        int count = Integer.parseInt(req.getParameter("count"));

        if (CRUDUtils.searchIdCurr(id)) {
            try {
                CRUDUtils.updateCurrencies(id, name, count);
                pw.println(CRUDUtils.searchIdCurrencies(id));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            pw.println("Такой валюты нет в БД");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        int id = Integer.parseInt(req.getParameter("id"));
        int playerId = Integer.parseInt(req.getParameter("playerid"));
        int resourceid = Integer.parseInt(req.getParameter("resourceid"));
        String name = req.getParameter("name");
        int count = Integer.parseInt(req.getParameter("count"));
        try {
            if (!CRUDUtils.searchIdCurr(id)) {
                if (CRUDUtils.searchPlayerId(playerId)) {
                    CRUDUtils.addCurrencies(new Currencies(id, playerId, resourceid, name, count));
                    pw.println(CRUDUtils.searchIdCurrencies(id));
                }else {
                    pw.println("Такого playerId нет в БД");
                }
            }else {
                pw.println("Такой id уже есть в бд");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            CRUDUtils.deleteCurrencies(id);
            pw.println("Валюта успешно удалена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
