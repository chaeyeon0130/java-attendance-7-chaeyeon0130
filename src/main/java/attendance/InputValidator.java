package attendance;

public class InputValidator {
    public void checkFunctionItem(String input) {
        if (!input.equals("1") && !input.equals("2") && !input.equals("3") && !input.equals("4") && !input.equals("Q")) {
            throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
        }
    }
}
