package cn.dubby.itbus.service.dto;

/**
 * Created by teeyoung on 17/5/5.
 */
public class RegisterDto {

    public static final RegisterDto EMAIL_EXIST = new RegisterDto(-1, null);

    public static final RegisterDto INVITATION_CODE_ERROR = new RegisterDto(-2, null);

    public static final RegisterDto DB_ERROR = new RegisterDto(-999, null);

    public RegisterDto() {
    }

    public RegisterDto(int errorCode, Integer userId) {
        this.errorCode = errorCode;
        this.userId = userId;
    }

    private int errorCode;

    private Integer userId;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
