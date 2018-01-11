package logger.io;

import logger.model.Log;
import logger.model.LogType;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class LogsReaderTest {

    @Test
    public void readLogsTest() throws Exception {
        LogsReader logsReader = new LogsReader("LogsTest.txt");
        logsReader.readLogs();
        Map<LogType, List<Log>> result = logsReader.getLogMap();

        assertEquals(8, result.size());
        assertEquals(5, result.get(LogType.DEBUG).size());
        int logs = logsReader.getLogMap().values().stream().mapToInt(List::size).sum();
        assertEquals(22, logs);
        Log debugLog = result.get(LogType.DEBUG).get(0);
        assertEquals(debugLog.getMessage(), "This is debug ");
    }


    @Test
    public void dateDiffInDaysTest() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date1 = format.parse("2014-07-02 20:52:39");
        Date date2 = format.parse("2014-07-03 20:52:39");
        long dif = LogsReader.dateDiffInDays(date1,date2);
        assertEquals(1,dif);

    }
    @Test
    public void getLogsByDayTest() throws IOException {
        LogsReader logsReader = new LogsReader("LogsTest.txt");
        logsReader.readLogs();
        List<String> result = logsReader.getLogsByDay(10);
        assertEquals(4,result.size());
        assertNotEquals(22,result.size());

    }
    @Test
    public void getByTypeTest() throws IOException {
        LogsReader logsReader = new LogsReader("LogsTest.txt");
        logsReader.readLogs();
        List<String> result = logsReader.getByType(LogType.DEBUG);
        assertEquals(5,result.size());

    }




}