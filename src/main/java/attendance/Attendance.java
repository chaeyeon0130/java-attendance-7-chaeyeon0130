package attendance;

import java.time.LocalDateTime;

public class Attendance {
    public String nickname;
    public LocalDateTime time;
    public AttendanceStatus status;

    public Attendance(String nickname, LocalDateTime time, AttendanceStatus status) {
        this.nickname = nickname;
        this.time = time;
        this.status = status;
    }
}
