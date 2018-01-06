package logger.io;

import logger.model.Log;
import logger.model.LogType;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class LogsReaderTest {

    @Test
    public void readLogs() throws Exception {
        LogsReader logsReader = new LogsReader("C:\\Users\\Cristi\\Desktop\\Logger\\src\\main\\resources\\Logs.txt");

        logsReader.readLogs();
        Map<LogType, List<Log>> result = logsReader.getLogMap();

        assertEquals(8, result.size());
        assertEquals(5, result.get(LogType.DEBUG).size());
        int logs = logsReader.getLogMap().values().stream().mapToInt(List::size).sum();

        assertEquals(19, logs);

        Log debugLog = result.get(LogType.DEBUG).get(0);
        assertEquals(debugLog.getMessage(), "This is debug ");
    }
    @Test
    public void dateDiffInDays() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date1 = format.parse("2014-07-02 20:52:39");
        Date date2 = format.parse("2014-07-03 20:52:39");
        long dif = LogsReader.dateDiffInDays(date1,date2);
        assertEquals(1,dif);
    }

}