package attendance;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class OutputView {
    public void printStartWord() {
        LocalDateTime today = DateTimes.now();
        String day = today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA);
        System.out.printf("오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.%n", today.getMonthValue(), today.getDayOfMonth(), day);
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

    public void printAttendanceWord(LocalDateTime time, AttendanceStatus status) {
        System.out.println();
        DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("HH:mm");
        System.out.printf("%d월 %d일 %s %s (%s)%n", time.getMonthValue(), time.getDayOfMonth(),
                time.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA), time.format(timePattern), status.getName());
    }
}
