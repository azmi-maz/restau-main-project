import org.group.project.classes.FloorReport;
import org.group.project.classes.Manager;
import org.junit.jupiter.api.Test;

public class FloorReportTests {

    @Test
    public void testFloorReportConstructor() {
        int reportId = 1;
        String reportType = "busiest-period";
        String reportData = "Lorem ipsum dolor sit amet, consectetur " +
                "adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        Manager staff = new Manager("John", "Doe", "john.doe");

        FloorReport newReport = new FloorReport(reportId, reportType,
                reportData, staff);

//        System.out.println(newReport.getReportData());
    }
}
