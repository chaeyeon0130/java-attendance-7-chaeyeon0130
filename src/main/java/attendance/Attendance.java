package attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Attendance {
    public String nickname;
    public LocalDate date;
    public String time;
    public String status;

    public Attendance(String nickname, LocalDate date, String time, String status) {
        this.nickname = nickname;
        this.date = date;
        this.time = time;
        this.status = status;
    }
}
