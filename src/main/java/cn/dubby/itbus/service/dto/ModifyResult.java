package cn.dubby.itbus.service.dto;

/**
 * Created by yangzheng03 on 2017/4/19.
 */
public class ModifyResult<T> {

    public static final ModifyResult PARAMS_ERROR = new ModifyResult(false, ModifyResultEnum.PARAMS_ERROR, null);
    public static final ModifyResult NOT_EXIST = new ModifyResult(false, ModifyResultEnum.NOT_EXIST, null);
    public static final ModifyResult TICKET_ERROR = new ModifyResult(false, ModifyResultEnum.TICKET_ERROR, null);
    public static final ModifyResult SYSTEM_EXCEPTION = new ModifyResult(false, ModifyResultEnum.SYSTEM_EXCEPTION, null);

    private boolean success;

    private ModifyResultEnum modifyResultEnum;

    private T result;

    public ModifyResult() {
    }

    public ModifyResult(boolean success, ModifyResultEnum modifyResultEnum, T result) {
        this.success = success;
        this.modifyResultEnum = modifyResultEnum;
        this.result = result;
    }

    public ModifyResult(T result) {
        this.success = true;
        this.modifyResultEnum = ModifyResultEnum.SUCCESS;
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ModifyResultEnum getModifyResultEnum() {
        return modifyResultEnum;
    }

    public void setModifyResultEnum(ModifyResultEnum modifyResultEnum) {
        this.modifyResultEnum = modifyResultEnum;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
