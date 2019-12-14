package com.snake.mcf.sysmgr.enums.merchant;

public enum DomainTypeEnum {

    MARKET("1", "推广"),
    API("2", "API"),
    Main("3", "主域名");

    private String code;
    private String name;

    DomainTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }


    public static String getPayTypeName(String code) {
        for (DomainTypeEnum type: DomainTypeEnum.values()) {
            if (code.equals(type.getCode())) {
                return type.getName();
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
