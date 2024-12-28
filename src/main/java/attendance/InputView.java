package attendance;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputView {
    public InputValidator inputValidator = new InputValidator();

    public String inputFunctionItem() {
        String input = Console.readLine();
        inputValidator.checkFunctionItem(input);
        return input;
    }

    public String inputNickname() {
        return Console.readLine();
    }

    public LocalDateTime inputSchoolTime() {
        try {
            DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("HH:mm");
            String input = Console.readLine();
            LocalTime time = LocalTime.parse(input, timePattern);
            return DateTimes.now().toLocalDate().atTime(time);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
        }
    }
}
