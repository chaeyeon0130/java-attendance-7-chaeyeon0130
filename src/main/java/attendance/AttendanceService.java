package attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
            registerAttendance(nickname, attendanceTime, status);
        }
    }

    public void registerAttendance(String nickname, LocalDateTime attendanceTime, AttendanceStatus status) {
        Attendance attendance = new Attendance(nickname, attendanceTime, status);
        attendances.add(attendance);
    }

    public List<Attendance> updateAttendance(String nickname, LocalDateTime attendanceTime, AttendanceStatus status) {
        // 업데이트 이전 정보 저장
        // TODO : 등교하지 않아 결석인 경우도 새롭게 업데이트? -> ex) 12월 03일 화요일 (결석) -> 09:58 (출석) 수정 완료!
        Attendance priorAttendance;
        List<Attendance> updatedResult = new ArrayList<>();
        for (Attendance attendance : attendances) {
            if (attendance.nickname.equals(nickname) && attendance.time.toLocalDate().equals(attendanceTime.toLocalDate())) {
                priorAttendance = new Attendance(attendance.nickname, attendance.time, attendance.status);
                attendance.time = attendanceTime;
                attendance.status = status;
                updatedResult.add(priorAttendance);
                updatedResult.add(attendance);
                break;
            }
        }
        return updatedResult;
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
    
    public void checkWeekendOrHoliday(LocalDateTime date) {
        int dayOfWeekValue = date.getDayOfWeek().getValue();
        if (dayOfWeekValue == 6 || dayOfWeekValue == 7 || date.isEqual(LocalDate.of(2024, 12, 25).atStartOfDay())) {
            throw new IllegalArgumentException(String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.", date.getMonthValue(),
                    date.getDayOfMonth(), date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)));
        }
    }

    public void checkNicknameRegistration(String nickname) {
        for (Attendance attendance : attendances) {
            if (attendance.nickname.equals(nickname)) {
                return;
            }
        }
        throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
    }
    
    public void checkCampusOperationTime(LocalDateTime time) {
        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(23, 0);
        if (time.toLocalTime().isBefore(startTime) || time.toLocalTime().isAfter(endTime)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간에만 출석이 가능합니다.");
        }
    }
    
    public void checkAttendanceDuplication(String nickname, LocalDateTime time) {
        for (Attendance attendance : attendances) {
            if (attendance.nickname.equals(nickname) && attendance.time.equals(time)) {
                throw new IllegalArgumentException("[ERROR] 이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.");
            }
        }
    }

    public void checkIfUpdatedAttendanceDateIsFuture(LocalDateTime time) {
        if (time.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("[ERROR] 아직 수정할 수 없습니다.");
        }
    }
}
