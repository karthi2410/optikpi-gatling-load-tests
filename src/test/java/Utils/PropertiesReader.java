package Utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class PropertiesReader {
    private static final Properties properties = new Properties();
    private static final String PATH = "src/test/resources/OptiKPI.properties";
    private static AtomicInteger counter;
    private static final Object ioLock = new Object();
    static {
        try {
            FileInputStream file = new FileInputStream(PATH);
            properties.load(file);
            file.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file!", e);
        }
        // initialize atomic counter from file
        int start = Integer.parseInt(properties.getProperty("user.next", "1"));
        counter = new AtomicInteger(start);
    }

    public static String nextUserName() {
        String base = properties.getProperty("user.base", "Testuser");
        int n = counter.getAndIncrement();             // atomic increment
        return base + n;                                // e.g., Testuser1, Testuser2
    }

    public static void persistNext() {
        // Save the next unused value so the next run continues from there
        synchronized (ioLock) {                         // synchronize I/O writes
            properties.setProperty("user.next", Integer.toString(counter.get()));
            try (FileOutputStream fos = new FileOutputStream(PATH)) {
                properties.store(fos, "Updated by Gatling run");
            } catch (IOException e) {
                throw new RuntimeException("Failed to persist OptiKPI.properties!", e);
            }
        }
    }
    public static String Get_Start_Time() {
        final ZoneOffset  tzOffset = ZoneOffset.of("+05:30");
        final DateTimeFormatter  iso = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return OffsetDateTime.now(tzOffset).format(iso);
    }
//    public static String Get_CREATE_START_TIME() {
//        final ZoneOffset  tzOffset = ZoneOffset.of("+05:30");
//        final DateTimeFormatter  iso = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
//        return OffsetDateTime.now(tzOffset).plusHours(1).format(iso);
//    }
    public static String get_Base_Url() {
        return properties.getProperty("Base_Url");
    }

    public static String get_Landing_Page_Hit() {
        return properties.getProperty("Landing_Page_Hit");
    }

    public static String get_providers() {
        return properties.getProperty("providers");
    }

    public static String get_csrf() {
        return properties.getProperty("csrf");
    }
    public static String get_Login_credentials() {
        return properties.getProperty("Login_credentials");
    }
    public static String get_Session() {
        return properties.getProperty("Session");
    }
    public static String get_roles() {
        return properties.getProperty("roles");
    }

    public static String get_user_password() {
        return properties.getProperty("user_password");
    }
    public static String get_Dashboard_Engagement() {
        return properties.getProperty("Dashboard_Engagement");
    }

    public static String get_DASHBOARD_BUSINESS_PERFORMANCE() {
        return properties.getProperty("DASHBOARD_BUSINESS_PERFORMANCE");
    }

    public static String get_DASHBOARD_MARKETING() {
        return properties.getProperty("DASHBOARD_MARKETING");
    }

    public static String get_DASHBOARD_NOTIFICATION() {
        return properties.getProperty("DASHBOARD_NOTIFICATION");
    }

    public static String get_AUDIENCE_ALL() {
        return properties.getProperty("AUDIENCE_ALL");
    }

    public static String get_AUDIENCE_ON_SCHEDULE() {
        return properties.getProperty("AUDIENCE_ON_SCHEDULE");
    }

    public static String get_AUDIENCE_STATIC() {
        return properties.getProperty("AUDIENCE_STATIC");
    }
    public static String get_AUDIENCE_CREATE() {
        return properties.getProperty("AUDIENCE_CREATE");
    }
    public static String get_TAG() {
        return properties.getProperty("TAG");
    }

    public static String get_CAMPAIGN_ALL() {
        return properties.getProperty("CAMPAIGN_ALL");
    }

    public static String get_CAMPAIGN_ACTIVE() {
        return properties.getProperty("CAMPAIGN_ACTIVE");
    }

    public static String get_CAMPAIGN_COMPLETED() {
        return properties.getProperty("CAMPAIGN_COMPLETED");
    }

    public static String get_CAMPAIGN_DRAFT() {
        return properties.getProperty("CAMPAIGN_DRAFT");
    }

    public static String get_WORKFLOW_ALL() {
        return properties.getProperty("WORKFLOW_ALL");
    }

    public static String get_WORKFLOW_MODULE_USAGE() {
        return properties.getProperty("WORKFLOW_MODULE_USAGE");
    }

    public static String get_WORKFLOW_ACTIVE() {
        return properties.getProperty("WORKFLOW_ACTIVE");
    }

    public static String get_WORKFLOW_INACTIVE() {
        return properties.getProperty("WORKFLOW_INACTIVE");
    }

    public static String get_WORKFLOW_DRAFT() {
        return properties.getProperty("WORKFLOW_DRAFT");
    }

    public static String get_LIBRARY_ALL() {
        return properties.getProperty("LIBRARY_ALL");
    }

    public static String get_LIBRARY_EMAIL() {
        return properties.getProperty("LIBRARY_EMAIL");
    }

    public static String get_LIBRARY_SMS() {
        return properties.getProperty("LIBRARY_SMS");
    }

    public static String get_LIBRARY_PUSH() {
        return properties.getProperty("LIBRARY_PUSH");
    }

    public static String get_LIBRARY_WEBAPI() {
        return properties.getProperty("LIBRARY_WEBAPI");
    }

    public static String get_DP_CUSTOMER_PROFILE() {
        return properties.getProperty("DATA_PLATFORM_CUSTOMER_PROFILE");
    }

    public static String get_DP_CUSTOMER_EVENTS() {
        return properties.getProperty("DATA_PLATFORM_CUSTOMER_EVENTS");
    }


}
