package com.snake.mcf.sysmgr.enums.account;

public enum AccountInfoTradeTypeEnum{
	DEPOSIT(1, "银行存款"),
	WITHDRAWAL(2, "银行取款"),
	TRANSFER(3, "银行转账"),
	RECEIPT(199, "银行转账"),   //该表未包含该状态，用userId判断
	COMMISSION(4, "代理抽成结算");
	
	
	
	public Integer code;
    public String desc;

    AccountInfoTradeTypeEnum(Integer code, String desc) {
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

    public static AccountInfoTradeTypeEnum getTypeByCode(Integer code) {
    	AccountInfoTradeTypeEnum defaultType = AccountInfoTradeTypeEnum.DEPOSIT;
        for (AccountInfoTradeTypeEnum ftype : AccountInfoTradeTypeEnum.values()) {
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
