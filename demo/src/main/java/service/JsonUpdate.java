package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import models.Currencies;
import models.Items;
import models.Player;
import models.Progresses;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Класс JsonUpdate для работы с хешом
 */

public class JsonUpdate {

    private static HashMap<Integer, Player> playerIdAndPlayer = null;


    public static void ReadToMap(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            playerIdAndPlayer.put(players[i].getPlayerId(), players[i]);
        }
    }

    public static void addPlayerInMap(HashMap<Integer, Player> hashMap) {
        playerIdAndPlayer = hashMap;
    }

    public static void deletePlayerFromMap(int playerId) {
        playerIdAndPlayer.remove(playerId);
        System.out.println(playerIdAndPlayer.containsKey(playerId));
    }

    public static void updateNicknamePlayer(int playerId, String nickname) {
        Player player = playerIdAndPlayer.get(playerId);
        player.setNickname(nickname);
        playerIdAndPlayer.put(playerId, player);
    }

    public static void updateProgressPlayer(int id, Progresses progresses) {
        Player player = playerIdAndPlayer.get(progresses.getPlayerId());
        Progresses[] progresses2 = player.getProgresses();
        for (int i = 0; i < progresses2.length; i++) {
            if (progresses2[i].getId() == id) {
                progresses.setResourceId(progresses2[i].getResourceId());
                progresses2[i] = progresses;
            }
        }
        player.setProgresses(progresses2);
        playerIdAndPlayer.put(progresses.getPlayerId(), player);
    }

    public static void updateCurrenciesPlayer(int id, Currencies currencies) {
        Player player = playerIdAndPlayer.get(currencies.getPlayerId());
        Currencies[] currencies2 = player.getCurrencies();
        for (int i = 0; i < currencies2.length; i++) {
            if (currencies2[i].getId() == id) {
                currencies.setResourceId(currencies2[i].getResourceId());
                currencies2[i] = currencies;
            }
        }
        player.setCurrencies(currencies2);
        playerIdAndPlayer.put(currencies.getPlayerId(), player);
    }

    public static void updateItemsPlayer(int id, Items items) {
        Player player = playerIdAndPlayer.get(items.getPlayerId());
        Items[] items2 = player.getItems();
        for (int i = 0; i < items2.length; i++) {
            if (items2[i].getId() == id) {
                items.setResourceId(items2[i].getResourceId());
                items2[i] = items;
            }
        }
        player.setItems(items2);
        playerIdAndPlayer.put(items.getPlayerId(), player);
    }

    public static void addProgressesPlayer(Progresses progresses) {
        Player player = playerIdAndPlayer.get(progresses.getPlayerId());
        Progresses[] progresses2 = player.getProgresses();
        Progresses[] progresses3 = Arrays.copyOf(progresses2, progresses2.length + 1);
        progresses3[progresses3.length - 1] = progresses;
        player.setProgresses(progresses3);

        playerIdAndPlayer.put(progresses.getPlayerId(), player);
    }

    public static void addCurrenciesPlayer(Currencies currencies) {
        Player player = playerIdAndPlayer.get(currencies.getPlayerId());
        Currencies[] currencies2 = player.getCurrencies();
        Currencies[] currencies3 = Arrays.copyOf(currencies2, currencies2.length + 1);
        currencies3[currencies3.length - 1] = currencies;
        player.setCurrencies(currencies3);

        playerIdAndPlayer.put(currencies.getPlayerId(), player);
    }

    public static void addItemsPlayer(Items items) {
        Player player = playerIdAndPlayer.get(items.getPlayerId());
        Items[] items2 = player.getItems();
        Items[] items3 = Arrays.copyOf(items2, items2.length + 1);
        items3[items3.length - 1] = items;
        player.setItems(items3);

        playerIdAndPlayer.put(items.getPlayerId(), player);
    }

    public static void deleteProgressPlayer(int id, int playerId) {
        Player player = playerIdAndPlayer.get(playerId);
        Progresses[] progresses = player.getProgresses();
        Progresses[] progresses2 = new Progresses[progresses.length - 1];
        boolean a = true;
        for (int i = 0; i < progresses.length; i++) {
            if (progresses[i].getId() != id && a) {
                progresses2[i] = progresses[i];
            } else if (progresses[i].getId() != id && !a) {
                progresses2[i - 1] = progresses[i];
            } else {
                a = false;
            }
        }
        player.setProgresses(progresses2);
        playerIdAndPlayer.put(playerId, player);
    }

    public static void deleteCurrenciesPlayer(int id, int playerId) {
        Player player = playerIdAndPlayer.get(playerId);
        Currencies[] currencies = player.getCurrencies();
        Currencies[] currencies2 = new Currencies[currencies.length - 1];
        boolean a = true;
        for (int i = 0; i < currencies.length; i++) {
            if (currencies[i].getId() != id && a) {
                currencies2[i] = currencies[i];
            } else if (currencies[i].getId() != id && !a) {
                currencies2[i - 1] = currencies[i];
            } else {
                a = false;
            }
        }
        player.setCurrencies(currencies2);
        playerIdAndPlayer.put(playerId, player);
    }

    public static void deleteItemsPlayer(int id, int playerId) {
        Player player = playerIdAndPlayer.get(playerId);
        Items[] items = player.getItems();
        Items[] items2 = new Items[items.length - 1];
        boolean a = true;
        for (int i = 0; i < items.length; i++) {
            if (items[i].getId() != id && a) {
                items2[i] = items[i];
            } else if (items[i].getId() != id && !a) {
                items2[i - 1] = items[i];
            } else {
                a = false;
            }
        }
        player.setItems(items2);
        playerIdAndPlayer.put(playerId, player);
    }

    public static void JsonInFile(File file, ObjectMapper objectMapper) throws IOException {
        ArrayList<Player> players = new ArrayList<>(playerIdAndPlayer.values());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(file, players);
    }
}
