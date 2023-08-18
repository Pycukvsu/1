package service;

import db.CRUDUtils;
import models.Currencies;
import models.Items;
import models.Progresses;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Сервис PlayerCreate для консольного приложения
 */
public class PlayerCreate {

    private static final Scanner scanner = new Scanner(System.in);

    public static List<Progresses> createProgress(int playerId) {
        System.out.print("Введите размер массива Progress: ");
        int lProgress = scanner.nextInt();
        List<Progresses> prog = new ArrayList<>();
        for (int i = 0; i < lProgress; i++) {
            System.out.print("Введите id: ");
            int idProgress = scanner.nextInt();
            if (!CRUDUtils.searchId(idProgress, "SELECT id FROM progress")) {
                System.out.print("Введите resourceId: ");
                int resourceIdProgress = scanner.nextInt();
                System.out.print("Введите score: ");
                int scoreProgress = scanner.nextInt();
                System.out.print("Введите maxScore: ");
                int maxScoreProgress = scanner.nextInt();
                prog.add(new Progresses(idProgress, playerId, resourceIdProgress, scoreProgress, maxScoreProgress));
            } else {
                System.out.println("Плеер с таким айди прогресса уже существует( \n" +
                        "Попробуйте ещё раз.");
                System.out.println();
                System.exit(0);
            }
        }
        return prog;
    }

    public static Progresses createUpdateProgress(int playerId) {
        Progresses progresses = null;
        System.out.print("Введите id: ");
        int idProgress = scanner.nextInt();
        if (!CRUDUtils.searchId(idProgress, "SELECT id FROM progress")) {
            System.out.print("Введите resourceId: ");
            int resourceIdProgress = scanner.nextInt();
            System.out.print("Введите score: ");
            int scoreProgress = scanner.nextInt();
            System.out.print("Введите maxScore: ");
            int maxScoreProgress = scanner.nextInt();
            progresses = new Progresses(idProgress, playerId, resourceIdProgress, scoreProgress, maxScoreProgress);
        } else {
            System.out.println("Плеер с таким айди прогресса уже существует( \n" +
                    "Попробуйте ещё раз.");
            System.out.println();
            System.exit(0);
        }
        return progresses;
    }

    public static List<Currencies> createCurrencies(int playerId) {
        System.out.print("Введите размер массива models.Currencies: ");
        int lcurrencies = scanner.nextInt();
        List<Currencies> curr = new ArrayList<>();
        for (int j = 0; j < lcurrencies; j++) {
            System.out.print("Введите id: ");
            int idCurr = scanner.nextInt();
            if (!CRUDUtils.searchId(idCurr, "SELECT id FROM currencies")) {
                System.out.print("Введите resourceId: ");
                int resourceIdCurr = scanner.nextInt();
                System.out.print("Введите name: ");
                String nameCurr = scanner.next();
                System.out.print("Введите count: ");
                int countCurr = scanner.nextInt();
                curr.add(new Currencies(idCurr, playerId, resourceIdCurr, nameCurr, countCurr));
            } else {
                System.out.println("Плеер с таким айди валюты уже существует( \n" +
                        "Попробуйте ещё раз.");
                System.out.println();
                System.exit(0);
            }
        }
        return curr;
    }

    public static Currencies createUpdateCurrencies(int playerId) {
        Currencies currencies = null;
        System.out.print("Введите id: ");
        int idProgress = scanner.nextInt();
        if (!CRUDUtils.searchId(idProgress, "SELECT id FROM currencies")) {
            System.out.print("Введите resourceId: ");
            int resourceIdProgress = scanner.nextInt();
            System.out.print("Введите name: ");
            String name = scanner.next();
            System.out.print("Введите count: ");
            int count = scanner.nextInt();
            currencies = new Currencies(idProgress, playerId, resourceIdProgress, name, count);
        } else {
            System.out.println("Плеер с таким айди прогресса уже существует( \n" +
                    "Попробуйте ещё раз.");
            System.out.println();
            System.exit(0);
        }
        return currencies;
    }

    public static List<Items> createItems(int playerId) {
        System.out.print("Введите размер массива models.Items: ");
        int lItems = scanner.nextInt();
        List<Items> item = new ArrayList<>();
        for (int k = 0; k < lItems; k++) {
            System.out.print("Введите id: ");
            int idItems = scanner.nextInt();
            if (!CRUDUtils.searchId(idItems, "SELECT id FROM items")) {
                System.out.print("Введите resourceId: ");
                int resourceIdItems = scanner.nextInt();
                System.out.print("Введите count: ");
                int countItems = scanner.nextInt();
                System.out.print("Введите level: ");
                int levelItems = scanner.nextInt();
                item.add(new Items(idItems, playerId, resourceIdItems, countItems, levelItems));
            } else {
                System.out.println("Плеер с таким айди предмета уже существует( \n" +
                        "Попробуйте ещё раз.");
                System.out.println();
                System.exit(0);
            }
        }
        return item;
    }

    public static Items createUpdateItems(int playerId) {
        Items item = null;
        System.out.print("Введите id: ");
        int idProgress = scanner.nextInt();
        if (!CRUDUtils.searchId(idProgress, "SELECT id FROM items")) {
            System.out.print("Введите resourceId: ");
            int resourceIdProgress = scanner.nextInt();
            System.out.print("Введите count: ");
            int countItems = scanner.nextInt();
            System.out.print("Введите level: ");
            int levelProgress = scanner.nextInt();
            item = new Items(idProgress, playerId, resourceIdProgress, countItems, levelProgress);
        } else {
            System.out.println("Плеер с таким айди прогресса уже существует( \n" +
                    "Попробуйте ещё раз.");
            System.out.println();
            System.exit(0);
        }
        return item;
    }
}