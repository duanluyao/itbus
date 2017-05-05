package cn.dubby.itbus.service.dto;

/**
 * Created by teeyoung on 17/4/26.
 */
public class UserDto {

    private Integer userId;

    private String nickName;

    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
