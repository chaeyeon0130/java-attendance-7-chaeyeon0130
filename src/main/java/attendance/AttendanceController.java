package attendance;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.DateTimes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class AttendanceController {
    public InputView inputView = new InputView();
    public OutputView outputView = new OutputView();
    public List<Attendance> attendances = new ArrayList<>();
    public Set<String> names = new HashSet<>();

    public void run() {
        // while
            // Q를 입력받으면 while이 끝남
            // 예외가 발생하면 while이 끝남
        // 일단 프로그램이 시작하면 날짜가 정해져있음
            // 오늘 날짜 전까지 csv 파일 Attendacne 객체에 저장

        // 오늘 문구 출력
        // 기능 문구 출력

        // 1을 입력 받으면
        // 1. 출석 확인

        // 2를 입력 받으면
        // 2. 출석 수정

        // 3을 입력 받으면
        // 3. 크루별 출석 기록 확인

        // 4를 입력받으면
        // 4. 제적 위험자 확인
        boolean start = true;
        while (true) {
            try {
//                LocalDate date = DateTimes.now().toLocalDate();
                if (start) {
                    start = false;
                }
                else {
                    System.out.println();
                }
                LocalDate date = LocalDate.of(2024, 12, 13);
                DayOfWeek dayOfWeek = date.getDayOfWeek();
                System.out.printf("오늘은 %s월 %s일 %s입니다. 기능을 선택해 주세요.%n", date.getMonthValue(), date.getDayOfMonth(),
                        dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN));
                System.out.println("1. 출석 확인");
                System.out.println("2. 출석 수정");
                System.out.println("3. 크루별 출석 기록 확인");
                System.out.println("4. 제적 위험자 확인");
                System.out.println("Q. 종료");

                File file = new File("src/main/resources/attendances.csv");
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                boolean first = true;
                while ((line = br.readLine()) != null) {
                    if (first) {
                        first = false;
                        continue;
                    }
                    String[] tokens = line.split(",");
                    String nickname = tokens[0];
                    names.add(nickname);
                    String[] tokens2 = tokens[1].split(" ");
                    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate attendanceDate = LocalDate.parse(tokens2[0], dateformatter);
                    // 오늘 날짜와 같거나 이상이면 안 받음
                    if (attendanceDate.isAfter(date) || attendanceDate.isEqual(date)) {
                        continue;
                    }
                    int dayOfWeekNumber = attendanceDate.getDayOfWeek().getValue();
                    String[] tokens3 = tokens2[1].split(":");
                    int hour = Integer.parseInt(tokens3[0]);
                    int minute = Integer.parseInt(tokens3[1]);
                    String status;
                    // 출석날이 월이면 13:00~18:00
                    status = decideStatus(dayOfWeekNumber, hour, minute);
                    Attendance attendance = new Attendance(nickname, attendanceDate, tokens2[1], status);
                    attendances.add(attendance);
                }

                String selectedNumber = Console.readLine();
                if (selectedNumber.equals("1")) {
                    System.out.println();
                    System.out.println("닉네임을 입력해 주세요.");
                    String name = Console.readLine();
                    if (!names.contains(name)) throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
                    System.out.println("등교 시간을 입력해 주세요.");
                    String time = Console.readLine();
                    String[] timeTokens = time.split(":");
                    int hour1 = Integer.parseInt(timeTokens[0]);
                    int minute1 = Integer.parseInt(timeTokens[1]);
                    String status = decideStatus(date.getDayOfWeek().getValue(), hour1, minute1);
                    Attendance attendance = new Attendance(name, date, time, status);
                    System.out.println();
                    System.out.printf("%s월 %s일 %s %s (%s)%n", date.getMonthValue(), date.getDayOfMonth(),
                            dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN), time, status);

                }
                if (selectedNumber.equals("Q")) {
                    return;
                }
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    private static String decideStatus(int dayOfWeekNumber, int hour, int minute) {
        String status;
        if (dayOfWeekNumber == 1) {
            status = "결석";
            if (8 <= hour && hour <= 13) {
                if (hour == 13) {
                    if (minute >= 6 && minute <= 30) {
                        status = "지각";
                    }
                    else {
                        status = "출석";
                    }
                }
                else {
                    status = "출석";
                }
            }
        }
        else {
            status = "결석";
            if (8 <= hour && hour <= 10) {
                if (hour == 10) {
                    if (minute >= 6 && minute <= 30) {
                        status = "지각";
                    }
                    else {
                        status = "출석";
                    }
                }
                else {
                    status = "출석";
                }
            }
        }
        return status;
    }

    public void processInput() {

    }

    public void processOutput() {

    }
}
