package cn.dubby.itbus.service.dto;

/**
 * Created by yangzheng03 on 2017/4/19.
 */
public enum ModifyResultEnum {
    SUCCESS(0, "修改完成"),
    NOT_EXIST(1, "未找到"),
    TICKET_ERROR(2, "凭证不正确"),
    PARAMS_ERROR(3, "参数不正确"),
    SYSTEM_EXCEPTION(-1, "系统异常");


    private ModifyResultEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;

    private String desc;

}
