package logger.io;

import com.sun.prism.impl.Disposer;
import logger.model.Log;
import logger.model.LogType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LogsReader {
    final static String timestampRgx = "(?<timestamp>\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})";
    final static String levelRgx = "(?<level>INFO|ERROR|WARN|TRACE|DEBUG|FATAL|FINER|CONFIG|SEVER)";
    final static String classRgx = "(?<class>\\w+:\\d+)";
    final static String textRgx = "(?<text>(\\w+ )+)";
    private static Pattern PatternFullLog = Pattern.compile(timestampRgx + " " + levelRgx + " " + classRgx + " - " + textRgx, Pattern.DOTALL);
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
    private Map<LogType, List<Log>> logMap;
    private Stream<String> stream;


    public LogsReader(String fileName) throws IOException {
        this.logMap = new HashMap<>();
        this.stream = Files.lines(Paths.get(fileName));
    }

    public Map<LogType, List<Log>> getLogMap() {
        return logMap;
    }

    public void readLogs(){

        stream.forEach(line -> {
            Matcher matcher = PatternFullLog.matcher(line);
            if (matcher.find()) {
                try {
                    Log log = new Log(
                            format.parse(matcher.group("timestamp")),
                            LogType.valueOf(matcher.group("level")),
                            matcher.group("text"),
                            line
                    );
                    List<Log> logs = new ArrayList<>();
                    if (logMap.containsKey(log.getLogType())){
                        logs.addAll(logMap.get(log.getLogType()));
                    }
                    logs.add(log);
                    logMap.put(log.getLogType(), logs);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    /**
     * @param type TYPE DER Error den wir wollen
     * @return
     */
    public List<String> getByType(LogType type){
        return logMap.get(type).stream().map(Log::getLogAsString).collect(Collectors.toList());

    }

    /**findet logst die in der letzten gegeben tage gegebe wurde
     * @param days wir geben die diferentz zw den tagen
     * @return Alle logs die in der letzten gegeben tage
     */
    public List<String> getLogsByDay(long days){
        List<String> logs = new ArrayList<>();
        Date now = new Date();
        logMap.keySet().forEach(k -> logMap.get(k).stream().filter(log -> dateDiffInDays(log.getDate(), now) <= days).forEach(l -> logs.add(l.getLogAsString())));

        return logs;
    }

    /**Die funktion gibt die different von 2 daten
     * @param d1 Date 1
     * @param d2 Date 2
     * @return
     */
    public static long dateDiffInDays(Date d1, Date d2){
        long dif = d2.getTime() - d1.getTime();
        return  dif / (24 * 60 * 60 * 1000);
    }
    public String getMostCommenErrorByeType(LogType type){
        Comparator<Log> messageComparator = (m1, m2) -> m1.getMessage().compareTo(m2.getMessage());
        return logMap.get(type).stream().max(Comparator.comparing(Log::getMessage)).get().getLogAsString();
    }



}
