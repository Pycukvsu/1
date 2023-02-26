import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/prog")
public class ServletProgress extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        int id = Integer.parseInt(req.getParameter("id"));
        int score = Integer.parseInt(req.getParameter("score"));
        int maxscore = Integer.parseInt(req.getParameter("maxscore"));

        if (CRUDUtils.searchIdProg(id)){
            try {
                CRUDUtils.updateProgress(id, score, maxscore);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            pw.println("Такого прогресса нет в БД");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        int playerId = Integer.parseInt(req.getParameter("playerid"));
        int resourceid = Integer.parseInt(req.getParameter("resourceid"));
        int score = Integer.parseInt(req.getParameter("score"));
        int maxscore = Integer.parseInt(req.getParameter("maxscore"));
        try {
            CRUDUtils.addProgress(new Progresses(id,playerId,resourceid,score,maxscore));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            CRUDUtils.deleteProgres(id);
            /*if (CRUDUtils.searchId(id)){

            }else {
                pw.println("Такого прогресса нет в БД");
            }*/
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
