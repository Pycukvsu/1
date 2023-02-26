import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


@WebServlet("/create")
public class ServletCreatePlayer extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();

        int playerId = Integer.parseInt(request.getParameter("playerId"));
        String nickname = request.getParameter("nickname");
        response.setStatus(HttpServletResponse.SC_OK);

        try {
            CRUDUtils.updateNickname(playerId, nickname);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nickname = req.getParameter("nickname");
        int playerId = Integer.parseInt(req.getParameter("playerId"));

        Player player = new Player(playerId, nickname, new Progresses[0], new Currencies[0], new Items[0]);

        CRUDUtils.savePlayer(player);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int playerId = Integer.parseInt(req.getParameter("playerId"));
        try {
            CRUDUtils.deletePlayer(playerId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
