import org.group.project.classes.Manager;
import org.group.project.classes.Report;
import org.junit.jupiter.api.Test;

public class ReportTests {

    @Test
    public void testReportConstructor() {
        int reportId = 1;
        String reportType = "busiest-period";
        String reportData = "Lorem ipsum dolor sit amet, consectetur " +
                "adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.";
        Manager staff = new Manager("John", "Doe", "john.doe");

        Report report1 = new Report(reportId, reportType, reportData, staff);

//        System.out.println(report1.getReportId());
//        System.out.println(report1.getReportType());
//        System.out.println(report1.getReportData());
//        System.out.println(report1.getGeneratedBy());
//        System.out.println(report1.getGeneratedOnDate());
//        System.out.println(report1.getGeneratedOnTime());
    }
}
