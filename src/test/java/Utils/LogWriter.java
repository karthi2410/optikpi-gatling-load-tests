package Utils;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class LogWriter {

    private static boolean isFileCleared = false; // Flag to ensure we only clear once

    public static void writeToLog(
            String email,
            long startTimeMillis,
            long endTimeMillis,
            long responseTime
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        String startTimeFormatted = Instant.ofEpochMilli(startTimeMillis)
                .atZone(ZoneId.systemDefault())
                .format(formatter);

        String endTimeFormatted = Instant.ofEpochMilli(endTimeMillis)
                .atZone(ZoneId.systemDefault())
                .format(formatter);

        String projectRoot = System.getProperty("user.dir");
        String logFile = projectRoot + "/src/test/resources/Logs/OptiKPI_log.txt";

        try {
            // Clear file only on first call
            if (!isFileCleared) {
                new PrintWriter(logFile).close(); // This clears the file
                isFileCleared = true;
                System.out.println("Previous Logs Cleared");
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(logFile, true))) {
                String logMessage =
                        "=========================\n" +
                                "email: " + email + "\n" +
                                "Start Time: " + startTimeFormatted + "\n" +
                                "End Time: " + endTimeFormatted + "\n" +
                                "Response Time: " + responseTime + " ms\n" +
                                "=========================";

                writer.println(logMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
