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
import java.util.NoSuchElementException;
import java.util.Set;

public class AttendanceController {
    public InputView inputView = new InputView();
    public OutputView outputView = new OutputView();
    public List<Attendance> attendances = new ArrayList<>();
    public Set<String> names = new HashSet<>();
    public LocalDate date;

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
                date = DateTimes.now().toLocalDate();
                if (start) {
                    start = false;
                }
                else {
                    System.out.println();
                }
//                date = LocalDate.of(2024, 12, 13);
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
                    if (date.getDayOfWeek().getValue() >= 6) {
                        String day;
                        if (date.getDayOfWeek().getValue() == 6) {
                            day = "토요일";
                        }
                        else {
                            day = "일요일";
                        }
                        throw new IllegalArgumentException(String.format("[ERROR] %s월 %s일 %s은 등교일이 아닙니다.", date.getMonthValue(),
                                date.getDayOfMonth(), day));
                    }
                    System.out.println("닉네임을 입력해 주세요.");
                    String name = Console.readLine();
                    if (!names.contains(name)) throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
                    System.out.println("등교 시간을 입력해 주세요.");
                    String time = Console.readLine();
                    String[] timeTokens = time.split(":");
                    int hour1 = Integer.parseInt(timeTokens[0]);
                    int minute1 = Integer.parseInt(timeTokens[1]);
                    if (hour1 > 24 || hour1 < 0) {
                        throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
                    }
                    else if (minute1 > 59 || minute1 < 0) {
                        throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
                    }
                    String status = decideStatus(date.getDayOfWeek().getValue(), hour1, minute1);
                    Attendance attendance = new Attendance(name, date, time, status);
                    System.out.println();
                    System.out.printf("%s월 %s일 %s %s (%s)%n", date.getMonthValue(), date.getDayOfMonth(),
                            dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN), time, status);
                }
                else if (selectedNumber.equals("2")) {
                    System.out.println();
                    System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
                    String name = Console.readLine();
                    System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
                    String updatedDate = Console.readLine();
                    if (updatedDate.length() == 1) {
                        updatedDate = "0" + updatedDate;
                    }
                    System.out.println("언제로 변경하겠습니까?");
                    String updatedTime = Console.readLine();
                    String[] timeTokens = updatedTime.split(":");
                    DateTimeFormatter dateformatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate updatedAttendanceDate = LocalDate.parse("2024-12"+"-"+updatedDate, dateformatter1);
                    String updatedStatus = decideStatus(updatedAttendanceDate.getDayOfWeek().getValue(),
                            Integer.parseInt(timeTokens[0]),Integer.parseInt(timeTokens[1]));
                    String originalTime = "";
                    String originalStatus = "";
                    for (int i = 0; i < attendances.size(); i++) {
                        Attendance attendance = attendances.get(i);
                        if (attendance.date.isEqual(updatedAttendanceDate) && attendance.nickname.equals(name)) {
                            originalTime = attendance.time;
                            originalStatus = attendance.status;
                            attendance.time = updatedTime;
                            attendance.status = updatedStatus;
                            break;
                        }
                    }
                    System.out.println();
                    System.out.printf("%s월 %s일 %s %s (%s) -> %s (%s) 수정 완료!%n", updatedAttendanceDate.getMonthValue(),
                            updatedAttendanceDate.getDayOfMonth(),
                            updatedAttendanceDate.getDayOfWeek().getDisplayName(TextStyle.FULL,
                                    Locale.KOREAN), originalTime, originalStatus, updatedTime, updatedStatus);
                }
                else if (selectedNumber.equals("3")) {
                    System.out.println();
                    System.out.println("닉네임을 입력해 주세요.");
                    String name = Console.readLine();
                    System.out.println();
                    System.out.println("이번 달 빙티의 출석 기록입니다.");
                    System.out.println();
                    int save = 0;
                    int perception = 0;
                    int absence = 0;
                    List<String> result = new ArrayList<>();
                    result.add(""); result.add(""); result.add(""); result.add("");
                    result.add(""); result.add(""); result.add(""); result.add("");
                    result.add(""); result.add(""); result.add(""); result.add("");
                    result.add(""); result.add(""); result.add(""); result.add("");
                    for (int i = 0; i < attendances.size(); i++) {
                        Attendance attendance = attendances.get(i);
                        if (!attendance.nickname.equals(name)) {
                            continue;
                        }
                        int dateIndex = attendance.date.getDayOfMonth();
                        result.add(dateIndex, String.format("%s월 %s일 %s %s (%s)", attendance.date.getMonthValue(),
                                attendance.date.getDayOfMonth(), attendance.date.getDayOfWeek().getDisplayName(TextStyle.FULL,
                                        Locale.KOREAN), attendance.time, attendance.status));
                        if (attendance.status.equals("출석")) {
                            save += 1;
                        }
                        else if (attendance.status.equals("지각")) {
                            perception += 1;
                        }
                        else if (attendance.status.equals("결석")) {
                            absence += 1;
                        }
                    }
                    for (int i = 2; i < 14; i++) {
                        if (result.get(i).equals("") && !(i == 7 || i == 8)) {
                            absence += 1;
                            String date = String.valueOf(i);
                            if (i >= 2 && i <= 9) {
                                date = "0" + date;
                            }
                            if (i == 2) {
                                System.out.println("12월 02일 월요일 --:-- (결석)");
                            }
                            if (i == 3) {
                                System.out.println("12월 03일 화요일 --:-- (결석)");
                            }
                            if (i == 4) {
                                System.out.println("12월 04일 수요일 --:-- (결석)");
                            }
                            if (i == 5) {
                                System.out.println("12월 05일 목요일 --:-- (결석)");
                            }
                            if (i == 6) {
                                System.out.println("12월 06일 금요일 --:-- (결석)");
                            }
                            if (i == 9) {
                                System.out.println("12월 09일 월요일 --:-- (결석)");
                            }
                            if (i == 10) {
                                System.out.println("12월 10일 화요일 --:-- (결석)");
                            }
                            if (i == 11) {
                                System.out.println("12월 11일 수요일 --:-- (결석)");
                            }
                            if (i == 12) {
                                System.out.println("12월 12일 목요일 --:-- (결석)");
                            }
                        }
                        else {
                            System.out.println(result.get(i));
                        }
                    }
                    System.out.println();
                    System.out.printf("출석: %d회%n", save);
                    System.out.printf("지각: %d회%n", perception);
                    System.out.printf("출석: %d회%n", absence);
                    System.out.println();

                }
                if (selectedNumber.equals("Q")) {
                    return;
                }
            } catch (NoSuchElementException e) {
                return;
            }
            catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    private String decideStatus(int dayOfWeekNumber, int hour, int minute) {
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
