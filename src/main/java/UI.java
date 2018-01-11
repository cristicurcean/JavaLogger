import logger.io.LogsReader;
import logger.model.LogType;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class UI {

    static Scanner scanner = new Scanner(System.in);
    LogsReader logsReader;

    public UI(String filename) throws IOException {
        this.logsReader = new LogsReader(filename);
        logsReader.readLogs();
    }


    public static void printMenu() {
        System.out.println("1 -  Anzeige die Logs bei Typ");
        System.out.println("2 -  Anzeige der am häufigsten Log Meldungen für Type = SEVERE");
        System.out.println("3 -  Anzeige die Logs, die in den letzten 10 Tagen geschrieben wurden;\n");
    }

    public void start() {
        printMenu();

        while (true) {
            System.out.println("Optiune:");
            int opt = scanner.nextInt();

            if (opt == 1) {
                System.out.println("Bitte den Typ der Error wahlen :");
                System.out.println("INFO, ERROR, DEBUG, SEVER, WARN, CONFIG, FINE, FINER, FATAL");
                String logType = scanner.next();
                List<String> logs = logsReader.getByType(LogType.valueOf(logType));
                System.out.println(String.join("\n", logs));

            } else if (opt == 2) {
                String log = logsReader.getMostCommenErrorByeType(LogType.SEVER);
                System.out.println(log);

            } else if (opt == 3) {
                List<String> logs = logsReader.getLogsByDay(10);
                System.out.println(String.join("\n", logs));

            }
        }

    }
}