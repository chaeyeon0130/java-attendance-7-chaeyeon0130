package attendance;

public enum AttendanceStatus {
    ATTENDANCE("출석"),
    LATE("지각"),
    ABSENT("결석");

    private String name;

    private AttendanceStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
