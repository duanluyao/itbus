package cn.dubby.itbus.service.dto;

/**
 * Created by teeyoung on 17/5/5.
 */
public class ResetPasswordDto {

    public static final ResetPasswordDto EMAIL_NOT_EXIST = new ResetPasswordDto(-1, null);

    public static final ResetPasswordDto DB_ERROR = new ResetPasswordDto(-999, null);

    public ResetPasswordDto() {
    }

    public ResetPasswordDto(int errorCode, Integer userId) {
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
