package attendance;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        AttendanceController attendanceController = new AttendanceController();
        attendanceController.run();
    }
}
