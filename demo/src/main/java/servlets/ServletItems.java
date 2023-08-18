package servlets;

import db.CRUDUtils;
import models.Items;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Сервлет для работы с таблицей items
 */
@WebServlet("/item")
public class ServletItems extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        PrintWriter pw = resp.getWriter();
        Items items = CRUDUtils.searchIdItems(id);
        if (items != null){
            pw.println(items);
        } else {
            pw.println("Заданный предмет не найден");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        int id = Integer.parseInt(req.getParameter("id"));
        int count = Integer.parseInt(req.getParameter("score"));
        int level = Integer.parseInt(req.getParameter("maxscore"));

        if (CRUDUtils.searchIdItem(id)){
            try {
                CRUDUtils.updateItems(id, count, level);
                pw.println(CRUDUtils.searchIdItems(id));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            pw.println("Такого предмета нет в БД");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        int playerId = Integer.parseInt(req.getParameter("playerid"));
        int resourceid = Integer.parseInt(req.getParameter("resourceid"));
        int count = Integer.parseInt(req.getParameter("score"));
        int level = Integer.parseInt(req.getParameter("maxscore"));
        PrintWriter pw = resp.getWriter();
        try {
            if (!CRUDUtils.searchIdItem(id)) {
                if (CRUDUtils.searchPlayerId(playerId)) {
                    CRUDUtils.addItems(new Items(id, playerId, resourceid, count, level));
                    pw.println(CRUDUtils.searchIdItems(id));
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
            CRUDUtils.deleteItems(id);
            pw.println("Валюта успешно удалена");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
