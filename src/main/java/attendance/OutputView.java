package attendance;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputView {
    public static final DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("MM월 dd일");
    public static final DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("HH:mm");

    public void printStartWord() {
        LocalDateTime today = DateTimes.now();
        String day = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA);
        System.out.printf("오늘은 %s %s입니다. 기능을 선택해 주세요.%n", today.format(datePattern), day);
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");
    }

    public void printNicknameInputWord() {
        System.out.println();
        System.out.println("닉네임을 입력해 주세요.");
    }

    public void printSchoolTimeInputWord() {
        System.out.println("등교 시간을 입력해 주세요.");
    }

    public void printUpdatedAttendanceDateWord() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
    }

    public void printUpdatedAttendanceTimeWord() {
        System.out.println("언제로 변경하겠습니까?");
    }

    public void printAttendanceResult(LocalDateTime time, AttendanceStatus status) {
        System.out.println();
        DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("HH:mm");
        System.out.printf("%s %s %s (%s)%n", time.format(datePattern),
                time.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA), time.format(timePattern), status.getName());
    }

    public void printUpdatedAttendanceResult(List<Attendance> updatedResult) {
        System.out.println();
        DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime priorAttendanceTime = updatedResult.get(0).time;
        LocalDateTime updatedAttendanceTime = updatedResult.get(1).time;
        AttendanceStatus priorStatus = updatedResult.get(0).status;
        AttendanceStatus updatedStatus = updatedResult.get(1).status;
        System.out.printf("%s %s %s (%s) -> %s (%s) 수정 완료!%n",
                updatedAttendanceTime.format(datePattern),
                updatedAttendanceTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                priorAttendanceTime.format(timePattern), priorStatus.getName(),
                updatedAttendanceTime.format(timePattern), updatedStatus.getName());
    }
}
