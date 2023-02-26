import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CRUDUtils {
    private static Connection connection = DBUtils.getConnection();

    private static final Scanner scanner = new Scanner(System.in);
    private static final String[] insertPlayers = new String[]{"INSERT INTO players(playerId, nickname) VALUES (?, ?);",
            "INSERT INTO progress(id, playerId, resourceId, score, maxScore) VALUES (?, ?, ?, ?, ?);",
            "INSERT INTO currencies(id, playerId, resourceId, name, count) VALUES (?, ?, ?, ?, ?);",
            "INSERT INTO items(id, playerId, resourceId, count, level) VALUES (?, ?, ?, ?, ?);"};


    /**
     * Сохранение массива плееров в Бд
     */

    public static Connection getConnection() {
        return connection;
    }

    public static void savePlayer(Player[] player) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertPlayers[0]);
             PreparedStatement preparedStatement2 = connection.prepareStatement(insertPlayers[1]);
             PreparedStatement preparedStatement3 = connection.prepareStatement(insertPlayers[2]);
             PreparedStatement preparedStatement4 = connection.prepareStatement(insertPlayers[3])) {
            for (int i = 0; i < player.length; i++) {
                preparedStatement.setInt(1, player[i].getPlayerId());
                preparedStatement.setString(2, player[i].getNickname());
                Progresses[] prog = player[i].getProgresses();
                for (int j = 0; j < prog.length; j++) {
                    preparedStatement2.setInt(1, prog[j].getId());
                    preparedStatement2.setInt(2, prog[j].getPlayerId());
                    preparedStatement2.setInt(3, prog[j].getResourceId());
                    preparedStatement2.setInt(4, prog[j].getScore());
                    preparedStatement2.setInt(5, prog[j].getMaxScore());
                    preparedStatement2.executeUpdate();
                }
                Currencies[] curr = player[i].getCurrencies();
                for (int j = 0; j < curr.length; j++) {
                    preparedStatement3.setInt(1, curr[j].getId());
                    preparedStatement3.setInt(2, curr[j].getPlayerId());
                    preparedStatement3.setInt(3, curr[j].getResourceId());
                    preparedStatement3.setString(4, curr[j].getName());
                    preparedStatement3.setInt(5, curr[j].getCount());
                    preparedStatement3.executeUpdate();

                }
                Items[] it = player[i].getItems();
                for (int j = 0; j < it.length; j++) {
                    preparedStatement4.setInt(1, it[j].getId());
                    preparedStatement4.setInt(2, it[j].getPlayerId());
                    preparedStatement4.setInt(3, it[j].getResourceId());
                    preparedStatement4.setInt(4, it[j].getCount());
                    preparedStatement4.setInt(5, it[j].getLevel());
                    preparedStatement4.executeUpdate();
                }
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
    }

    /**
     * Сохранение плеера в Бд
     */

    public static void savePlayer(Player player) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertPlayers[0]);
             PreparedStatement preparedStatement2 = connection.prepareStatement(insertPlayers[1]);
             PreparedStatement preparedStatement3 = connection.prepareStatement(insertPlayers[2]);
             PreparedStatement preparedStatement4 = connection.prepareStatement(insertPlayers[3])) {
            preparedStatement.setInt(1, player.getPlayerId());
            preparedStatement.setString(2, player.getNickname());
            Progresses[] prog = player.getProgresses();
            for (int j = 0; j < prog.length; j++) {
                preparedStatement2.setInt(1, prog[j].getId());
                preparedStatement2.setInt(2, prog[j].getPlayerId());
                preparedStatement2.setInt(3, prog[j].getResourceId());
                preparedStatement2.setInt(4, prog[j].getScore());
                preparedStatement2.setInt(5, prog[j].getMaxScore());
                preparedStatement2.executeUpdate();
            }
            Currencies[] curr = player.getCurrencies();
            for (int j = 0; j < curr.length; j++) {
                preparedStatement3.setInt(1, curr[j].getId());
                preparedStatement3.setInt(2, curr[j].getPlayerId());
                preparedStatement3.setInt(3, curr[j].getResourceId());
                preparedStatement3.setString(4, curr[j].getName());
                preparedStatement3.setInt(5, curr[j].getCount());
                preparedStatement3.executeUpdate();

            }
            Items[] it = player.getItems();
            for (int j = 0; j < it.length; j++) {
                preparedStatement4.setInt(1, it[j].getId());
                preparedStatement4.setInt(2, it[j].getPlayerId());
                preparedStatement4.setInt(3, it[j].getResourceId());
                preparedStatement4.setInt(4, it[j].getCount());
                preparedStatement4.setInt(5, it[j].getLevel());
                preparedStatement4.executeUpdate();
            }
            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Получение списка плееров из Бд
     */

    public static HashMap<Integer, Player> getPlayerData() {
        HashMap<Integer, Player> playerHashMap = new HashMap<>();

        try (PreparedStatement allPlayer = connection.prepareStatement("SELECT * FROM players");
             PreparedStatement allProgress = connection.prepareStatement("SELECT * FROM progress");
             PreparedStatement allCurrencies = connection.prepareStatement("SELECT * FROM currencies");
             PreparedStatement allItems = connection.prepareStatement("SELECT * FROM items")
        ) {
            ResultSet rs = allPlayer.executeQuery();
            ResultSet rs2 = allProgress.executeQuery();
            ResultSet rs3 = allCurrencies.executeQuery();
            ResultSet rs4 = allItems.executeQuery();

            HashMap<Integer, List<Progresses>> playerIdAndProgress = getProgress(rs2);
            HashMap<Integer, List<Currencies>> playerIdAndCurrencies = getCurr(rs3);
            HashMap<Integer, List<Items>> playerIdAndItems = getItems(rs4);

            while (rs.next()) {
                int playerId = rs.getInt("playerId");
                String nickname = rs.getString("nickname");
                Progresses[] prog;
                Currencies[] currr;
                Items[] itemm;
                if (playerIdAndProgress.get(playerId) != null) {
                    prog = ProgresToArray(playerIdAndProgress.get(playerId));
                } else {
                    prog = new Progresses[0];
                }
                if (playerIdAndCurrencies.get(playerId) != null) {
                    currr = CurrToArray(playerIdAndCurrencies.get(playerId));
                } else {
                    currr = new Currencies[0];
                }
                if (playerIdAndItems.get(playerId) != null) {
                    itemm = ItemToArray(playerIdAndItems.get(playerId));
                } else {
                    itemm = new Items[0];
                }
                playerHashMap.put(playerId, new Player(playerId, nickname, prog, currr, itemm));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return playerHashMap;
    }

    public static HashMap<Integer, List<Items>> getItems(ResultSet rs4) throws SQLException {
        HashMap<Integer, List<Items>> playerIdAndItems = new HashMap<>();
        while (rs4.next()) {
            int playerId4 = rs4.getInt("playerId");
            int id = rs4.getInt("id");
            int resourceId = rs4.getInt("resourceId");
            int count = rs4.getInt("count");
            int level = rs4.getInt("level");
            if (!playerIdAndItems.containsKey(playerId4)) {
                List<Items> item = new ArrayList<>();
                item.add(new Items(id, playerId4, resourceId, count, level));
                playerIdAndItems.put(playerId4, item);
            } else {
                List<Items> item = playerIdAndItems.get(playerId4);
                item.add(new Items(id, playerId4, resourceId, count, level));
                playerIdAndItems.put(playerId4, item);
            }
        }
        return playerIdAndItems;
    }

    public static HashMap<Integer, List<Currencies>> getCurr(ResultSet rs3) throws SQLException {
        HashMap<Integer, List<Currencies>> playerIdAndCurrencies = new HashMap<>();
        while (rs3.next()) {
            int playerId3 = rs3.getInt("playerId");
            int id = rs3.getInt("id");
            int resourceId = rs3.getInt("resourceId");
            String name = rs3.getString("name");
            int count = rs3.getInt("count");
            if (!playerIdAndCurrencies.containsKey(playerId3)) {
                List<Currencies> curr = new ArrayList<>();
                curr.add(new Currencies(id, playerId3, resourceId, name, count));
                playerIdAndCurrencies.put(playerId3, curr);
            } else {
                List<Currencies> curr = playerIdAndCurrencies.get(playerId3);
                curr.add(new Currencies(id, playerId3, resourceId, name, count));
                playerIdAndCurrencies.put(playerId3, curr);
            }
        }
        return playerIdAndCurrencies;
    }

    public static HashMap<Integer, List<Progresses>> getProgress(ResultSet rs2) throws SQLException {
        HashMap<Integer, List<Progresses>> playerIdAndProgress = new HashMap<>();
        while (rs2.next()) {
            int playerId2 = rs2.getInt("playerId");
            int id = rs2.getInt("id");
            int resourceId = rs2.getInt("resourceId");
            int score = rs2.getInt("score");
            int maxScore = rs2.getInt("maxScore");
            if (!playerIdAndProgress.containsKey(playerId2)) {
                List<Progresses> prog = new ArrayList<>();
                prog.add(new Progresses(id, playerId2, resourceId, score, maxScore));
                playerIdAndProgress.put(playerId2, prog);
            } else {
                List<Progresses> prog = playerIdAndProgress.get(playerId2);
                prog.add(new Progresses(id, playerId2, resourceId, score, maxScore));
                playerIdAndProgress.put(playerId2, prog);
            }
        }
        return playerIdAndProgress;
    }

    /**
     * Добавление плеера в Бд
     */

    public static Player addPlayer() {
        System.out.print("Введите playerId: ");
        Player player = null;
        int playerId = scanner.nextInt();
        if (!searchPlayerId(playerId)) {
            System.out.print("Введите nickname: ");
            String name = scanner.next();

            List<Progresses> prog = PlayerCreate.createProgress(playerId);
            List<Currencies> curr = PlayerCreate.createCurrencies(playerId);
            List<Items> item = PlayerCreate.createItems(playerId);

            player = new Player(playerId, name, ProgresToArray(prog), CurrToArray(curr), ItemToArray(item));
            savePlayer(player);
        } else {
            System.out.println("Плеер с таким playerId уже существует");
            System.exit(0);
        }

        return player;
    }

    public static void updateNickname(int playerId, String nickname) throws SQLException {
        String updatePlayers = "UPDATE players SET nickname = ? WHERE playerId  = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updatePlayers);
        preparedStatement.setString(1, nickname);
        preparedStatement.setInt(2, playerId);
        preparedStatement.executeUpdate();
    }


    public static void updatePlayer() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        String updatePlayers;
        String str = "1 - изменить | 2 - добавить | 3 - удалить";
        System.out.println("Введите playerId игрока, которого хотите изменить: ");
        int playerId = scanner.nextInt();

        if (searchPlayerId(playerId)) {
            System.out.println("Что хотите изменить?");
            System.out.println("1 - nickname | 2 - progress | 3 - currencies | 4 - items");
            int n = scanner.nextInt();

            if (n == 1) {
                System.out.print("Введите nickname: ");
                String nickname = scanner.next();

                JsonUpdate.updateNicknamePlayer(playerId, nickname);

                updatePlayers = "UPDATE players SET nickname = ? WHERE playerId  = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(updatePlayers);
                preparedStatement.setString(1, nickname);
                preparedStatement.setInt(2, playerId);
                preparedStatement.executeUpdate();
            } else if (n == 2) {
                System.out.println(str);
                int h = scanner.nextInt();
                if (h == 1) {
                    updateProgress(playerId);
                } else if (h == 2) {
                    addProgress(playerId);
                } else {
                    deleteProgress(playerId);
                }
            } else if (n == 3) {
                System.out.println(str);
                int h = scanner.nextInt();
                if (h == 1) {
                    updateCurrencies(playerId);
                } else if (h == 2) {
                    addCurrencies(playerId);
                } else {
                    deleteCurrencies(playerId);
                }
            } else if (n == 4) {
                System.out.println(str);
                int h = scanner.nextInt();
                if (h == 1) {
                    updateItems(playerId);
                } else if (h == 2) {
                    addItems(playerId);
                } else {
                    deleteItems(playerId);
                }
            }
        } else {
            System.out.println("Игрока с таким playerId не существует. Попробуйте ещё раз.");
            System.out.println();
            System.exit(0);
        }
    }

    public static void updateProgress(int id, int score, int maxscore) throws SQLException {
        String updatePlayers = "UPDATE progress SET score = ?, maxScore = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(updatePlayers);
        preparedStatement.setInt(1, score);
        preparedStatement.setInt(2, maxscore);
        preparedStatement.setInt(3, id);
        preparedStatement.executeUpdate();
    }

    public static void updateProgress(int playerId) throws SQLException {
        System.out.print("Введите id прогресса: ");
        int id = scanner.nextInt();

        if (searchId(id, "SELECT id FROM progress")) {
            System.out.print("Введите score: ");
            int score = scanner.nextInt();
            System.out.print("Введите maxScore: ");
            int maxscore = scanner.nextInt();
            Progresses progresses = new Progresses(id, playerId, score, maxscore);
            String updatePlayers = "UPDATE progress SET score = ?, maxScore = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updatePlayers);
            preparedStatement.setInt(1, score);
            preparedStatement.setInt(2, maxscore);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            JsonUpdate.updateProgressPlayer(id, progresses);
        } else {
            System.out.println("Что то пошло не так. Попробуйте ещё раз)");
            System.exit(0);
        }
    }

    public static void addProgress(int playerId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(insertPlayers[1]);
        Progresses progresses = PlayerCreate.createUpdateProgress(playerId);
        JsonUpdate.addProgressesPlayer(progresses);
        preparedStatement.setInt(1, progresses.getId());
        preparedStatement.setInt(2, playerId);
        preparedStatement.setInt(3, progresses.getResourceId());
        preparedStatement.setInt(4, progresses.getScore());
        preparedStatement.setInt(5, progresses.getMaxScore());
        preparedStatement.executeUpdate();
    }

    public static void addProgress(Progresses progresses) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(insertPlayers[1]);
        preparedStatement.setInt(1, progresses.getId());
        preparedStatement.setInt(2, progresses.getPlayerId());
        preparedStatement.setInt(3, progresses.getResourceId());
        preparedStatement.setInt(4, progresses.getScore());
        preparedStatement.setInt(5, progresses.getMaxScore());
        preparedStatement.executeUpdate();
    }

    public static void deleteProgres(int id) throws SQLException {
        String zapros = "DELETE FROM progress WHERE id = " + id;
        PreparedStatement preparedStatement = connection.prepareStatement(zapros);
        preparedStatement.executeUpdate();
    }

    public static void deleteProgress(int playerId) throws SQLException {
        System.out.println("Введите id прогресса: ");
        int id = scanner.nextInt();
        JsonUpdate.deleteProgressPlayer(id, playerId);
        String zapros = "DELETE FROM progress WHERE id = " + id;
        PreparedStatement preparedStatement = connection.prepareStatement(zapros);
        preparedStatement.executeUpdate();
    }

    public static void updateCurrencies(int playerId) throws SQLException {
        System.out.print("Введите id валюты: ");
        int id = scanner.nextInt();

        if (searchId(id, "SELECT id FROM currencies")) {
            System.out.print("Введите name: ");
            String name = scanner.next();
            System.out.print("Введите count: ");
            int count = scanner.nextInt();
            Currencies currencies = new Currencies(id, playerId, name, count);
            String updatePlayers = "UPDATE currencies SET name = ?, count = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updatePlayers);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, count);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            JsonUpdate.updateCurrenciesPlayer(id, currencies);
        } else {
            System.out.println("Что то пошло не так. Попробуйте ещё раз)");
            System.exit(0);
        }
    }

    public static void deleteCurrencies(int playerId) throws SQLException {
        System.out.println("Введите id прогресса: ");
        int id = scanner.nextInt();
        JsonUpdate.deleteCurrenciesPlayer(id, playerId);
        String zapros = "DELETE FROM currencies WHERE id = " + id;
        PreparedStatement preparedStatement = connection.prepareStatement(zapros);
        preparedStatement.executeUpdate();
    }

    public static void addCurrencies(int playerId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(insertPlayers[2]);
        Currencies currencies = PlayerCreate.createUpdateCurrencies(playerId);
        JsonUpdate.addCurrenciesPlayer(currencies);
        preparedStatement.setInt(1, currencies.getId());
        preparedStatement.setInt(2, playerId);
        preparedStatement.setInt(3, currencies.getResourceId());
        preparedStatement.setString(4, currencies.getName());
        preparedStatement.setInt(5, currencies.getCount());
        preparedStatement.executeUpdate();
    }

    public static void updateItems(int playerId) throws SQLException {
        System.out.print("Введите id предмета: ");
        int id = scanner.nextInt();

        if (searchId(id, "SELECT id FROM items")) {
            System.out.print("Введите count: ");
            int count = scanner.nextInt();
            System.out.print("Введите level: ");
            int level = scanner.nextInt();
            Items items = new Items(id, playerId, count, level);
            String updatePlayers = "UPDATE items SET count = ?, level = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updatePlayers);
            preparedStatement.setInt(1, count);
            preparedStatement.setInt(2, level);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            JsonUpdate.updateItemsPlayer(id, items);
        } else {
            System.out.println("Что то пошло не так. Попробуйте ещё раз)");
            System.exit(0);
        }
    }

    public static void addItems(int playerId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(insertPlayers[3]);
        Items item = PlayerCreate.createUpdateItems(playerId);
        JsonUpdate.addItemsPlayer(item);
        preparedStatement.setInt(1, item.getId());
        preparedStatement.setInt(2, playerId);
        preparedStatement.setInt(3, item.getResourceId());
        preparedStatement.setInt(4, item.getCount());
        preparedStatement.setInt(5, item.getLevel());
        preparedStatement.executeUpdate();
    }

    public static void deleteItems(int playerId) throws SQLException {
        System.out.println("Введите id предмета: ");
        int id = scanner.nextInt();
        JsonUpdate.deleteItemsPlayer(id, playerId);
        String zapros = "DELETE FROM items WHERE id = " + id;
        PreparedStatement preparedStatement = connection.prepareStatement(zapros);
        preparedStatement.executeUpdate();
    }

    public static boolean searchPlayerId(int plId) {
        HashMap<Integer, Integer> map = new HashMap<>();
        try (PreparedStatement allPlayer = connection.prepareStatement("SELECT playerId FROM players")) {
            ResultSet rs = allPlayer.executeQuery();
            int n = 0;
            while (rs.next()) {
                int playerId = rs.getInt("playerId");
                map.put(playerId, n);
                n++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return map.containsKey(plId);
    }

    public static Player searchPlayer(int plId) {
        Player player = null;
        try (PreparedStatement allPlayer = connection.prepareStatement("SELECT playerId FROM players");
             PreparedStatement allProgress = connection.prepareStatement("SELECT playerId FROM progress");
             PreparedStatement allCurr = connection.prepareStatement("SELECT playerId FROM currencies");
             PreparedStatement allItem = connection.prepareStatement("SELECT playerId FROM items")) {
            ResultSet rs = allPlayer.executeQuery();
            ResultSet rs2 = allProgress.executeQuery();
            ResultSet rs3 = allCurr.executeQuery();
            ResultSet rs4 = allItem.executeQuery();
            while (rs.next()) {
                int playerId = rs.getInt("playerId");
                if (plId == playerId) {
                    String nickname = rs.getString("nickname");
                    player = new Player(playerId, nickname);
                }
            }

            List<Progresses> progresses = new ArrayList<>();
            while (rs2.next()) {
                int playerId = rs2.getInt("playerId");
                if (plId == playerId) {
                    int id = rs2.getInt("id");
                    int resourceid = rs2.getInt("resourceid");
                    int score = rs2.getInt("score");
                    int maxscore = rs2.getInt("maxscore");
                    progresses.add(new Progresses(id, playerId, resourceid, score, maxscore));
                }
            }
            player.setProgresses(ProgresToArray(progresses));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return player;
    }

    public static int deletePlayer() throws SQLException {
        System.out.println("Введите playerId игрока, которого хотите удалить: ");
        int playerid = scanner.nextInt();
        String zapros = "DELETE FROM players WHERE playerId = " + playerid;
        String zaprosProgresses = "DELETE FROM progress WHERE playerId = " + playerid;
        String zaprosCurrencies = "DELETE FROM currencies WHERE playerId = " + playerid;
        String zaprosItems = "DELETE FROM items WHERE playerId = " + playerid;

        PreparedStatement preparedStatement = connection.prepareStatement(zapros);
        preparedStatement.executeUpdate();
        PreparedStatement preparedStatement2 = connection.prepareStatement(zaprosProgresses);
        preparedStatement2.executeUpdate();
        PreparedStatement preparedStatement3 = connection.prepareStatement(zaprosCurrencies);
        preparedStatement3.executeUpdate();
        PreparedStatement preparedStatement4 = connection.prepareStatement(zaprosItems);
        preparedStatement4.executeUpdate();

        return playerid;
    }

    public static int deletePlayer(int playerid) throws SQLException {
        String zapros = "DELETE FROM players WHERE playerId = " + playerid;
        String zaprosProgresses = "DELETE FROM progress WHERE playerId = " + playerid;
        String zaprosCurrencies = "DELETE FROM currencies WHERE playerId = " + playerid;
        String zaprosItems = "DELETE FROM items WHERE playerId = " + playerid;

        PreparedStatement preparedStatement = connection.prepareStatement(zapros);
        preparedStatement.executeUpdate();
        PreparedStatement preparedStatement2 = connection.prepareStatement(zaprosProgresses);
        preparedStatement2.executeUpdate();
        PreparedStatement preparedStatement3 = connection.prepareStatement(zaprosCurrencies);
        preparedStatement3.executeUpdate();
        PreparedStatement preparedStatement4 = connection.prepareStatement(zaprosItems);
        preparedStatement4.executeUpdate();

        return playerid;
    }

    public static boolean searchIdProg(int id) {
        HashMap<Integer, Integer> map = new HashMap<>();
        try (PreparedStatement all = connection.prepareStatement("SELECT id FROM progress")) {
            ResultSet rs = all.executeQuery();
            int n = 0;
            while (rs.next()) {
                int idd = rs.getInt("id");
                map.put(idd, n);
                n++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return map.containsKey(id);
    }

    public static boolean searchId(int id, String zapros) {
        HashMap<Integer, Integer> map = new HashMap<>();
        try (PreparedStatement all = connection.prepareStatement(zapros)) {
            ResultSet rs = all.executeQuery();
            int n = 0;
            while (rs.next()) {
                int idd = rs.getInt("id");
                map.put(idd, n);
                n++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return map.containsKey(id);
    }

    public static Progresses[] ProgresToArray(List<Progresses> list) {
        Progresses[] pl1 = new Progresses[list.size()];
        Iterator itr = list.iterator();
        int i = 0;
        while (itr.hasNext()) {
            pl1[i] = (Progresses) itr.next();
            i++;
        }
        return pl1;
    }


    public static Currencies[] CurrToArray(List<Currencies> list) {
        Currencies[] pl1 = new Currencies[list.size()];
        Iterator itr = list.iterator();
        int i = 0;
        while (itr.hasNext()) {
            pl1[i] = (Currencies) itr.next();
            i++;
        }
        return pl1;
    }

    public static Items[] ItemToArray(List<Items> list) {
        Items[] pl1 = new Items[list.size()];
        Iterator itr = list.iterator();
        int i = 0;
        while (itr.hasNext()) {
            pl1[i] = (Items) itr.next();
            i++;
        }
        return pl1;
    }
}