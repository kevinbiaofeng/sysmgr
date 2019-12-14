package com.snake.mcf.sysmgr.enums.account;

public enum AccountInfoUnlockCodeEnum {
	SUCCESS(99, "解锁成功"),
	WRONG(1, "解锁密码错误，请重试！"),
	THREE_TIMES_WRONG(0, "连续输错密码3次，请在1小时之后重试！"),
	INIT_PAGE(10, "初始化页面无状态");
	
	public Integer code;
    public String desc;

    AccountInfoUnlockCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static AccountInfoUnlockCodeEnum getTypeByCode(Integer code) {
    	AccountInfoUnlockCodeEnum defaultType = AccountInfoUnlockCodeEnum.SUCCESS;
        for (AccountInfoUnlockCodeEnum ftype : AccountInfoUnlockCodeEnum.values()) {
            if (ftype.code == code) {
                return ftype;
            }
        }
        return defaultType;
    }

    public static String getDescByCode(Integer code) {
        return getTypeByCode(code).desc;
    }
}
