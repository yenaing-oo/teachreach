package comp3350.teachreach.objects;

import comp3350.teachreach.application.TRData;

public class SessionStatus {
    public static int PENDING;
    public static int ACCEPTED;
    public static int REJECTED;

    static {
        SessionStatus.PENDING = TRData.getSessionStatusEnumValue("PENDING");
        SessionStatus.ACCEPTED = TRData.getSessionStatusEnumValue("ACCEPTED");
        SessionStatus.REJECTED = TRData.getSessionStatusEnumValue("REJECTED");
    }

}
