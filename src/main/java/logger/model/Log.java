package logger.model;

import java.util.Date;

public class Log {

    private final Date date;
    private final LogType logType;
    private final String message;
    private final String logAsString;

    public Log(Date date, LogType logType, String message, String logAsString) {
        this.date = date;
        this.logType = logType;
        this.message = message;
        this.logAsString = logAsString;
    }

    public Date getDate() {
        return date;
    }

    public LogType getLogType() {
        return logType;
    }

    public String getMessage() {
        return message;
    }

    public String getLogAsString() {
        return logAsString;
    }
}
