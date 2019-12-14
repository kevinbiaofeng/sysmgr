package com.snake.mcf.sysmgr.enums.cash;

public enum PayTypeEnum {

    BANK(1, "银行卡"),
    ALI_PAY(2, "支付宝");

    private Integer code;
    private String name;

    PayTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }


    public static String getPayTypeName(Integer code) {
        for (PayTypeEnum type: PayTypeEnum.values()) {
            if (code == type.getCode()) {
                return type.getName();
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
