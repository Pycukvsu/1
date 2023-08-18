import com.fasterxml.jackson.databind.ObjectMapper;
import db.CRUDUtils;
import models.Player;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        ObjectMapper objectMapper = new ObjectMapper();

        File file = new File("/Users/426/IdeaProjects/1/demo/players.json");
        Player[] players = objectMapper.readValue(file, Player[].class);        // Читаем json файл в массив игроков

        CRUDUtils.savePlayer(players);          // сохраняем массив игроков в бд

        HashMap<Integer, Player> hashMap = CRUDUtils.getPlayerData();           // выгружаем данные из бд
    }
}
