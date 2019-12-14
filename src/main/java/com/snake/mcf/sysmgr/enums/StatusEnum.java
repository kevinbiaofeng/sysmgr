package com.snake.mcf.sysmgr.enums;

public enum StatusEnum {
	DELETE(1, "删除"),
	SUCCESS(0, "成功"),
	DRAFT(2, "草稿");
	
	public Integer code;
    public String desc;

    StatusEnum(Integer code, String desc) {
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

    public static StatusEnum getTypeByCode(Integer code) {
    	StatusEnum defaultType = StatusEnum.SUCCESS;
        for (StatusEnum ftype : StatusEnum.values()) {
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
