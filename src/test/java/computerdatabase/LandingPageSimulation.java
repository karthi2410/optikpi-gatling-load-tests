package computerdatabase;


import Utils.PropertiesReader;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static Utils.LogWriter.writeToLog;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.core.CoreDsl.jsonPath;
import static io.gatling.javaapi.http.HttpDsl.*;

public class LandingPageSimulation extends Simulation {

    long Pause = 10;
    Duration pauseDuration = Duration.ofSeconds(Pause);

    // Example usage lines
    String Base_Url = PropertiesReader.get_Base_Url();
    String providers = PropertiesReader.get_providers();
    String csrf = PropertiesReader.get_csrf();
    String Login_credentials = PropertiesReader.get_Login_credentials();
    String Session = PropertiesReader.get_Session();
    String roles = PropertiesReader.get_roles();
    String user_password = PropertiesReader.get_user_password();
    String Dashboard_Engagement = PropertiesReader.get_Dashboard_Engagement();
    String DASHBOARD_BUSINESS_PERFORMANCE = PropertiesReader.get_DASHBOARD_BUSINESS_PERFORMANCE();
    String DASHBOARD_MARKETING = PropertiesReader.get_DASHBOARD_MARKETING();
    String DASHBOARD_NOTIFICATION = PropertiesReader.get_DASHBOARD_NOTIFICATION();
    String AUDIENCE_ALL = PropertiesReader.get_AUDIENCE_ALL();
    String AUDIENCE_ON_SCHEDULE = PropertiesReader.get_AUDIENCE_ON_SCHEDULE();
    String AUDIENCE_STATIC = PropertiesReader.get_AUDIENCE_STATIC();
    String AUDIENCE_CREATE = PropertiesReader.get_AUDIENCE_CREATE();
    String TAG = PropertiesReader.get_TAG();
    String CAMPAIGN_ALL = PropertiesReader.get_CAMPAIGN_ALL();
    String CAMPAIGN_ACTIVE = PropertiesReader.get_CAMPAIGN_ACTIVE();
    String CAMPAIGN_COMPLETED = PropertiesReader.get_CAMPAIGN_COMPLETED();
    String CAMPAIGN_DRAFT = PropertiesReader.get_CAMPAIGN_DRAFT();
    String WORKFLOW_ALL = PropertiesReader.get_WORKFLOW_ALL();
    String WORKFLOW_MODULE_USAGE = PropertiesReader.get_WORKFLOW_MODULE_USAGE();
    String WORKFLOW_ACTIVE = PropertiesReader.get_WORKFLOW_ACTIVE();
    String WORKFLOW_INACTIVE = PropertiesReader.get_WORKFLOW_INACTIVE();
    String WORKFLOW_DRAFT = PropertiesReader.get_WORKFLOW_DRAFT();
    String LIBRARY_ALL = PropertiesReader.get_LIBRARY_ALL();
    String LIBRARY_EMAIL = PropertiesReader.get_LIBRARY_EMAIL();
    String LIBRARY_SMS = PropertiesReader.get_LIBRARY_SMS();
    String LIBRARY_PUSH = PropertiesReader.get_LIBRARY_PUSH();
    String LIBRARY_WEBAPI = PropertiesReader.get_LIBRARY_WEBAPI();
    String DP_CUSTOMER_PROFILE = PropertiesReader.get_DP_CUSTOMER_PROFILE();
    String DP_CUSTOMER_EVENTS = PropertiesReader.get_DP_CUSTOMER_EVENTS();
    String userName = PropertiesReader.get_user_password();
    String startTs = PropertiesReader.Get_Start_Time();



    String Create_requestBody =
            "{\n" +
                    "  \\\"id\\\": null,\n" +
                    "  \\\"name\\\": \\\"" + userName + "\\\",\\n" +
                    "  \\\"tags\\\": [\\n" +
                    "    \\\"bala\\\",\\n" +
                    "    \\\"test \\\"\\n" +
                    "  ],\\n" +
                    "  \\\"audienceType\\\": {\\n" +
                    "    \\\"type\\\": \\\"\\\",\\n" +
                    "    \\\"period\\\": \\\"Day\\\",\\n" +
                    "    \\\"startDateTime\\\": \\\"" + startTs + "\\\",\\n" +
                    "    \\\"timezone\\\": \\\"UTC+05:30\\\"\\n" +
                    "  },\\n" +
                    "  \\\"groupType\\\": \\\"scratch\\\",\\n" +
                    "  \\\"type\\\": \\\"AUDIENCE_CREATED\\\",\\n" +
                    "  \\\"moduleType\\\": \\\"\\\",\\n" +
                    "  \\\"groups\\\": []\\n" +
                    "}";


    HttpProtocolBuilder httpProtocol = http
            .baseUrl(Base_Url)
            .acceptHeader("*/*")
            .acceptEncodingHeader("gzip, deflate, br, zstd")
            .acceptLanguageHeader("en-US,en;q=0.9")
            .contentTypeHeader("application/x-www-form-urlencoded")
            .disableCaching();

    ScenarioBuilder randomPagehit = scenario("login")
            .feed(csv("search.csv"))

            // Start timer
            .exec(session -> {
                long startTime = System.currentTimeMillis();
                return session.set("startTime", startTime);
            })
            .group("LoginPage to Dashboard ").on(
                    exec(http("providers")
                            .get(providers)
                            .check(status().is(200))
                    )
                            .exec(
                                    http("csrf")
                                            .get(csrf)
                                            .check(status().is(200))
                                            .check(bodyString().saveAs("RESPONSE_BODY_CSRF"))
                                            .check(jsonPath("$.csrfToken").saveAs("csrfToken"))
                            )

                            .pause(1)
                            .exec(
                                    http("Login with credentials")
                                            .post(Login_credentials)
                                            .formParam("email", "#{email}")
                                            .formParam("password", "QZAWppdGt1bWFyQDEyMw=YB=")
                                            .formParam("redirect", "false")
                                            .formParam("remember", "false")
                                            .formParam("csrfToken", "#{csrfToken}")
                                            .formParam("callbackUrl", "https://demo.optikpi.com/en")
                                            .formParam("json", "true")
                                            .check(status().is(200))
                                            .check(jsonPath("$.url").exists())
                                            .check(bodyString().saveAs("RESPONSE_BODY_AUTH"))
                            )
                            .pause(1)
                            .exec(
                                    http("Session")
                                            .get(Session)
                                            .check(status().is(200))
                                            .check(jsonPath("$.user.email").exists())
                                            .check(jsonPath("$.user.email").saveAs("email"))
                                            .check(jsonPath("$.user.workspaceId").saveAs("workspaceId"))
                            )
                            .pause(1)
                            .exec(
                                    http("Fetch Roles")
                                            .get(roles)
                                            .check(status().is(200))
                                            .check(jsonPath("$.data.role[0]").exists())
                            )
                            .pause(1)
                            .exec(
                                    http("Dashboard Engagement")
                                            .get(Dashboard_Engagement)
                                            .check(status().is(200))
                            ))

            .exec(session -> {
                //Strings
                String csrf_body = session.getString("RESPONSE_BODY_CSRF");
                String auth_body = session.getString("RESPONSE_BODY_AUTH");
                String workspaceId = session.getString("workspaceId");
                String email = session.getString("email");

                //finish time
                long startTime = session.getLong("startTime");
                long endTime = System.currentTimeMillis();
                long responseTime = endTime - startTime;

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                String startFormatted = Instant.ofEpochMilli(startTime)
                        .atZone(ZoneId.systemDefault())
                        .format(formatter);
                String endFormatted = Instant.ofEpochMilli(endTime)
                        .atZone(ZoneId.systemDefault())
                        .format(formatter);

                // === LOGGING OUTPUT ===
                System.out.println("--------------- Authentication Output ---------------");
                System.out.println("Username: " + email);
                System.out.println("csrf Response: " + csrf_body);
                System.out.println("Login Response: " + auth_body);
                System.out.println("workspaceId: " + workspaceId);
                System.out.println("Authentication Response Body: " + auth_body);
                System.out.println("Start Time: " + startFormatted);
                System.out.println("End Time: " + endFormatted);
                System.out.println("Response Time (ms): " + responseTime);
                System.out.println("========================================");

//                // Log everything including request body and headers
//                writeToLog(
//                        email,
//                        startTime,
//                        endTime,
//                        responseTime
//                );
                return session;
            })


            .group("Dashboard Pages").on(
                    exec(http("Session")
                            .get(Session)
                            .check(status().is(200))
                            .check(jsonPath("$.user.workspaceId").is(session -> session.getString("workspaceId")))
                    )
                            .exec(http("Dashboard Engagement")
                                    .get(Dashboard_Engagement)
                                    .check(status().is(200)))
                            .exec(http("DASHBOARD BUSINESS PERFORMANCE")
                                    .get(DASHBOARD_BUSINESS_PERFORMANCE)
                                    .check(status().is(200)))
                            .exec(http("DASHBOARD MARKETING")
                                    .get(DASHBOARD_MARKETING)
                                    .check(status().is(200)))
                            .exec(http("DASHBOARD NOTIFICATION")
                                    .get(DASHBOARD_NOTIFICATION)
                                    .check(status().is(200))))

            .group("AUDIENCE").on(
                    exec(http("Session")
                            .get(Session)
                            .check(status().is(200))
                            .check(jsonPath("$.user.workspaceId").is(session -> session.getString("workspaceId")))
                    )
                            .exec(http("AUDIENCE ALL")
                                    .get(AUDIENCE_ALL)
                                    .check(status().is(200)))

                            .exec(http("AUDIENCE ON SCHEDULE")
                                    .get(AUDIENCE_ON_SCHEDULE)
                                    .check(status().is(200)))

                            .exec(http("AUDIENCE STATIC")
                                    .get(AUDIENCE_STATIC)
                                    .check(status().is(200)))

//                            .group("AUDIENCE CREATE").on(
//                                    exec(http("AUDIENCE CREATE")
//                                            .post(AUDIENCE_CREATE)
//                                            .body(StringBody(Create_requestBody))
//                                            .check(status().is(200))
//                                            .check(jsonPath("$.user.workspaceId").is(session -> session.getString("workspaceId")))
//                                            .check(jsonPath("$.id").saveAs("Audience_id"))
//                                            .check(jsonPath("$.audienceType.startDateTime").saveAs("Created_Time"))
//                                    )


//                            )

            )
            .group("CAMPAIGN").on(
                    exec(http("Session")
                            .get(Session)
                            .check(status().is(200))
                            .check(jsonPath("$.user.workspaceId").is(session -> session.getString("workspaceId")))
                    )
                            .exec(http("CAMPAIGN ALL")
                                    .get(CAMPAIGN_ALL)
                                    .check(status().is(200)))

                            .exec(http("CAMPAIGN ACTIVE")
                                    .get(CAMPAIGN_ACTIVE)
                                    .check(status().is(200)))

                            .exec(http("CAMPAIGN COMPLETED")
                                    .get(CAMPAIGN_COMPLETED)
                                    .check(status().is(200)))

                            .exec(http("CAMPAIGN DRAFT")
                                    .get(CAMPAIGN_DRAFT)
                                    .check(status().is(200))))
            .group("WORKFLOW").on(
                    exec(http("Session")
                            .get(Session)
                            .check(status().is(200))
                            .check(jsonPath("$.user.workspaceId").is(session -> session.getString("workspaceId"))))
                            .exec(http("WORKFLOW ALL")
                                    .get(WORKFLOW_ALL)
                                    .check(status().is(200)))
                            .exec(http("Session")
                                    .get(Session)
                                    .check(status().is(200))
                                    .check(jsonPath("$.user.workspaceId").is(session -> session.getString("workspaceId"))))
                            .exec(http("WORKFLOW ACTIVE")
                                    .get(WORKFLOW_ACTIVE)
                                    .check(status().is(200)))

                            .exec(http("WORKFLOW INACTIVE")
                                    .get(WORKFLOW_INACTIVE)
                                    .check(status().is(200)))
                            .exec(http("WORKFLOW DRAFT")
                                    .get(WORKFLOW_DRAFT)
                                    .check(status().is(200)))
                            .exec(http("WORKFLOW MODULE USAGE")
                                    .get(WORKFLOW_MODULE_USAGE)
                                    .check(status().is(200))))

            .group("LIBRARY").on(
                    exec(http("Session")
                            .get(Session)
                            .check(status().is(200))
                            .check(jsonPath("$.user.workspaceId").is(session -> session.getString("workspaceId")))
                    )
                            .exec(http("LIBRARY ALL")
                                    .get(LIBRARY_ALL)
                                    .check(status().is(200)))
                            .exec(http("LIBRARY EMAIL")
                                    .get(LIBRARY_EMAIL)
                                    .check(status().is(200)))
                            .exec(http("LIBRARY SMS")
                                    .get(LIBRARY_SMS)
                                    .check(status().is(200)))
                            .exec(http("LIBRARY PUSH")
                                    .get(LIBRARY_PUSH)
                                    .check(status().is(200)))
                            .exec(http("LIBRARY WEBAPI")
                                    .get(LIBRARY_WEBAPI)
                                    .check(status().is(200))))
            .group("DATA PLATFORM").on(
                    exec(http("Session")
                            .get(Session)
                            .check(status().is(200))
                            .check(jsonPath("$.user.workspaceId").is(session -> session.getString("workspaceId")))
                    )
                            .exec(http("DP CUSTOMER PROFILE")
                                    .get(DP_CUSTOMER_PROFILE)
                                    .check(status().is(200)))
                            .exec(http("DP CUSTOMER EVENTS")
                                    .get(DP_CUSTOMER_EVENTS)
                                    .check(status().is(200)))
            );

    //setup
    {
        setUp(
                randomPagehit.injectOpen(atOnceUsers(1))
                        .protocols(httpProtocol));
    }

}
