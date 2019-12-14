package com.snake.mcf.sysmgr.enums.cash;
/**
 * 现金流状态
 */
public enum TreasureTypeEnum{
	PLATFORM_GIFT(0, "后台赠送"),
	REGISTER_GIFT(1, "注册赠送"),
	ACTIVE_TRANSFER(2, "主动转账"),
	ACCEPT_TRANSFER(3, "接收转账"),
	PURCHASE_PROPS(4, "购买道具"),
	EXCHANGE_GOLD(5, "兑换金币"),
	DEPOSIT_BANK(6, "存入银行"),
	WITHDRAW_BANK(7, "银行取出"),
	BANK_SERVICE_FEE(8, "银行服务费"),
	RECEIVE_REBATES(9, "领取返利"),
	AGENT_GIFT(10, "代理赠送"),
	GIFT_BY_THE_AGENT(11, "被代理赠送"),
	RECHARGE_GIFT(12, "充值额外赠送"),
	DAILY_SHARING(13, "每日分享"),
	SIGN_IN(14, "签到"),
	COMPETITION_REWARD(15, "比赛奖励"),
	BIND_PHONE(16, "绑定手机"),
	LEADERBOARD_REWARDS(17, "排行榜奖励"),
	CERTIFICATION_REWARDS(18, "认证奖励"),
	ARTIFICIAL_RECHARGE(19, "后台充值"),
	ARTIFICIAL_CHARGE(20, "后台扣款");
	
	public Integer code;
    public String desc;

    TreasureTypeEnum(Integer code, String desc) {
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

    public static TreasureTypeEnum getTypeByCode(Integer code) {
    	TreasureTypeEnum defaultType = TreasureTypeEnum.PLATFORM_GIFT;
        for (TreasureTypeEnum ftype : TreasureTypeEnum.values()) {
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
