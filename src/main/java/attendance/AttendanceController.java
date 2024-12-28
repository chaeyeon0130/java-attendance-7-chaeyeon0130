package attendance;


import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.util.List;

public class AttendanceController {
    public AttendanceService attendanceService = new AttendanceService();
    public OutputView outputView = new OutputView();
    public InputView inputView = new InputView();

    public void run() {
        // 사용자가 Q를 입력하거나 오류가 발생하기 전까지 반복
        attendanceService.processData();
        outputView.printStartWord();
        String input = inputView.inputFunctionItem();
        if (input.equals("1")) {
            attendanceService.checkWeekendOrHoliday(DateTimes.now());
            outputView.printNicknameInputWord();
            String nickname = inputView.inputNickname();
            attendanceService.checkNicknameRegistration(nickname);
            outputView.printSchoolTimeInputWord();
            LocalDateTime time = inputView.inputSchoolTime();
            attendanceService.checkCampusOperationTime(time);
            attendanceService.checkAttendanceDuplication(nickname, time);
            AttendanceStatus status = attendanceService.decideAttendanceStatus(time);
            attendanceService.registerAttendance(nickname, time, status);
            outputView.printAttendanceResult(time, status);
        }
        else if (input.equals("2")) {
            attendanceService.checkWeekendOrHoliday(DateTimes.now());
            outputView.printNicknameInputWord();
            String nickname = inputView.inputNickname();
            attendanceService.checkNicknameRegistration(nickname);
            outputView.printUpdatedAttendanceDateWord();
            LocalDateTime time = inputView.inputUpdatedAttendanceDate();
            attendanceService.checkIfUpdatedAttendanceDateIsFuture(time);
            outputView.printUpdatedAttendanceTimeWord();
            time = inputView.inputUpdatedAttendanceTime(time);
            attendanceService.checkCampusOperationTime(time);
            AttendanceStatus status = attendanceService.decideAttendanceStatus(time);
            List<Attendance> updatedResult = attendanceService.updateAttendance(nickname, time, status);
            outputView.printUpdatedAttendanceResult(updatedResult);
        }
        else if (input.equals("3")) {
            // 크루별 출석 기록 확인
        }
        else if (input.equals("4")) {
            // 제적 위험자 확인
        }
        else {
            // 종료
        }
    }
}
