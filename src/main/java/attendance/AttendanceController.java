package attendance;


public class AttendanceController {
    public AttendanceService attendanceService = new AttendanceService();

    public void run() {
        attendanceService.processData();
    }
}
