package servlets;

import db.CRUDUtils;
import models.Currencies;
import models.Items;
import models.Player;
import models.Progresses;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Сервлет для работы с таблицей players
 */
@WebServlet("/player")
public class ServletPlayer extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int playerId = Integer.parseInt(request.getParameter("playerId"));

        PrintWriter pw = response.getWriter();
        Player player = CRUDUtils.searchPlayer(playerId);
        if (player != null) {
            pw.println(player);
        } else {
            pw.println("Заданный игрок не найден");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();

        int playerId = Integer.parseInt(request.getParameter("playerId"));
        String nickname = request.getParameter("nickname");
        response.setStatus(HttpServletResponse.SC_OK);

        if (CRUDUtils.searchPlayerId(playerId)) {
            try {
                CRUDUtils.updateNickname(playerId, nickname);
                pw.println(CRUDUtils.searchPlayer(playerId));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            pw.println("Такого игрока нет в бд");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nickname = req.getParameter("nickname");
        int playerId = Integer.parseInt(req.getParameter("playerId"));
        PrintWriter pw = resp.getWriter();

        Player player = new Player(playerId, nickname, new Progresses[0], new Currencies[0], new Items[0]);
        if (!CRUDUtils.searchPlayerId(playerId)) {
            CRUDUtils.savePlayer(player);
            pw.println(CRUDUtils.searchPlayer(playerId));
        } else {
            pw.println("Такой playerId уже есть в БД");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int playerId = Integer.parseInt(req.getParameter("playerId"));
        PrintWriter pw = resp.getWriter();
        try {
            CRUDUtils.deletePlayer(playerId);
            pw.println("Игрок успешно удален");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
