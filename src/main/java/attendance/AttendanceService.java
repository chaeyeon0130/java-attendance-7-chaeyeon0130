package attendance;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceService {
    public DataLoader dataLoader = new DataLoader();
    public List<Attendance> attendances = new ArrayList<>();

    public void processData() {
        List<String> data = dataLoader.loadData("src/main/resources/attendances.csv");
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (int i = 0; i < data.size(); i++) {
            String[] tokens = data.get(i).split(",");
            String nickname = tokens[0];
            LocalDateTime attendanceTime = LocalDateTime.parse(tokens[1], pattern);
            if (attendanceTime.isAfter(LocalDateTime.now()) || attendanceTime.isEqual(LocalDateTime.now())) {
                continue;
            }
            AttendanceStatus status = decideAttendanceStatus(attendanceTime);
            Attendance attendance = new Attendance(nickname, attendanceTime, status);
            attendances.add(attendance);
        }
    }

    public AttendanceStatus decideAttendanceStatus(LocalDateTime time) {
        // 전제
            // 주말 또는 공휴일이 아님
            // 캠퍼스 운영 시간임
        LocalDateTime mondayLate = time.toLocalDate().atTime(13, 6);
        LocalDateTime mondayAbsent = time.toLocalDate().atTime(13, 31);
        LocalDateTime restLate = time.toLocalDate().atTime(10, 6);
        LocalDateTime restAbsent = time.toLocalDate().atTime(10, 31);
        int dayOfWeekNumber = time.getDayOfWeek().getValue();
        // 월요일 경우
        if (dayOfWeekNumber == 1) {
            if (time.isBefore(mondayLate)) {
                return AttendanceStatus.ATTENDANCE;
            }
            else if (time.isBefore(mondayAbsent)) {
                return AttendanceStatus.LATE;
            }
            else  {
                return AttendanceStatus.ABSENT;
            }
        }
        // 나머지일 경우
        if (dayOfWeekNumber >= 2 && dayOfWeekNumber <= 5) {
            if (time.isBefore(restLate)) {
                return AttendanceStatus.ATTENDANCE;
            } else if (time.isBefore(restAbsent)) {
                return AttendanceStatus.LATE;
            } else {
                return AttendanceStatus.ABSENT;
            }
        }
        return AttendanceStatus.ABSENT;
    }
}
