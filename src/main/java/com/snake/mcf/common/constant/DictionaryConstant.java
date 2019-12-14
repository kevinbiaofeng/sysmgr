package com.snake.mcf.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DictionaryConstant
 * @Author 大帅
 * @Date 2019/6/24 15:13
 */
public class DictionaryConstant {

    /**
     *  性别
     *  0 保密
     *  1 男
     *  2 女
     */
    private static Map<Integer,String> DICTIONARY_GENDER_MAP = new HashMap<>();

    /**
     * 注册来源
     * 0：PC
     * 1：模拟器
     * 17：Android
     * 32：iTouch
     * 50：iPhone
     * 66：iPad
     * 80：web
     */
    private static Map<Integer,String> DICTIONARY_REGISTERORIGIN_MAP = new HashMap<>();

    /**
     * 是否禁用
     * 0 启用
     * 1 禁用
     */
    private static Map<Integer,String> DICTIONARY_NULLITY_MAP = new HashMap<>();

    /**
     * 平台类型
     *  1 H5
     *  2 U3D
     *  3 LUA
     */
    private static Map<Integer,String> DICTIONARY_PLATFORMTYPE_MAP = new HashMap<>();

    /**
     * 任务类型
     *  0 其他任务
     *  1 总局任务
     *  2 胜局任务
     *  4 首胜任务
     *  8 连胜任务
     */
    private static Map<Integer,String> DICTIONARY_TASKTYPE_MAP = new HashMap<>();

    /**
     * 用户类型
     * 0 所有用户
     * 1 普通用户
     * 2 会员用户
     */
    private static Map<Integer,String> DICTIONARY_USERTYPE_MAP = new HashMap<>();

    /**
     * 时间类型
     * 1 一次任务
     * 2 每日任务
     * 4 每周任务
     * 8 每月任务
     */
    private static Map<Integer,String> DICTIONARY_TIMETYPE_MAP = new HashMap<>();

    /**
     * 奖励类型
     * 0 其他
     * 1 金币
     * 2 钻石
     * 3 奖券
     */
    private static Map<Integer,String> DICTIONARY_AWARDTYPE_MAP = new HashMap<>();

    /**
     * 充值类型
     * 0 普通充值
     * 1 苹果内购
     */
    private static Map<Integer,String> DICTIONARY_PAYTYPE_MAP = new HashMap<>();

    /**
     * 首冲类型
     *  0 普通
     *  1 首充
     *  2 每日
     *  3 热卖
     *  4 人气
     */
    private static Map<Integer,String> DICTIONARY_PAYIDENTITY_MAP = new HashMap<>();

    /**
     * 充值货币类型
     * 0 游戏币
     * 1 钻石
     */
    private static Map<Integer,String> DICTIONARY_SCORETYPE_MAP = new HashMap<>();

    /**
     * 标签类型
     * 0 普通
     * 1 人气
     */
    private static Map<Integer,String> DICTIONARY_TAGID_MAP = new HashMap<>();

    /**
     * 订单状态
     * 0 未支付
     * 1 已支付
     */
    private static Map<Integer,String> DICTIONARY_ORDERSTATUS_MAP = new HashMap<>();

    /**
     * 充值类型
     *  101 手机微信充值
     *  102 H5微信充值
     *  201 手机支付宝充值
     *  301 手机零钱充值
     *  302 竣付通微信充值
     *  303 竣付通支付宝充值
     *  800 手机苹果充值
     */
    private static Map<Integer,String> DICTIONARY_SHAREID_MAP = new HashMap<>();

    /**
     * 消息范围
     * 1 游戏
     * 2 房间
     * 3 全部
     */
    private static Map<Integer,String> DICTIONARY_MESSAGETYPE_MAP = new HashMap<>();

    /**
     * 排行榜类型
     * 1 财富排行榜
     * 2 胜局排行榜
     */
    private static Map<Integer,String> DICTIONARY_RANK_TYPEID_MAP = new HashMap<>();

    /**
     * 排行榜排名
     *
     * 1 第一名
     * 2 第二名
     * 3 第三名
     * 4 第4-10名
     * 5 第11-20名
     * 6 第21-50名
     * 7 第51-100名
     */
    private static Map<Integer,String> DICTIONARY_RANK_RANKTYPE_MAP = new HashMap<>();

    /**
     * 购买类型
     *
     * 0 钻石购买
     * 1 游戏币购买
     */
    private static Map<Integer,String> DICTIONARY_EXCHANGETYPE_MAP = new HashMap<>();

    /**
     * 使用范围
     *
     * 1 大厅
     * 2 房间
     * 4 游戏
     */
    private static Map<Integer,String> DICTIONARY_USEAREA_MAP = new HashMap<>();

    /**
     * 作用范围
     * 1 自己
     * 2 玩家
     * 4 旁观
     */
    private static Map<Integer,String> DICTIONARY_SERVICEAREA_MAP = new HashMap<>();

    /**
     * 是否推荐
     * 0 否
     * 1 是
     */
    private static Map<Integer,String> DICTIONARY_RECOMMEND_MAP = new HashMap<>();

    /**
     * 平台类型
     * 0 通用
     * 1 LUA
     * 2 H5
     * 3 U3D
     */
    private static Map<Integer,String> DICTIONARY_PLATFORMKIND_MAP = new HashMap<>();

    /**
     * 礼包类型
     * 0 抽奖签到礼包
     * 1 累计签到礼包
     */
    private static Map<Integer,String> DICTIONARY_SIGN_TYPEID_MAP = new HashMap<>();

    /**
     * 物品类型
     * 0 游戏币
     * 1 钻石
     * 2 道具
     * 3 奖券
     */
    private static Map<Integer,String> DICTIONARY_GOODS_TYPEID_MAP = new HashMap<>();

    /**
     * 道具
     * 0 无
     * 306 大喇叭
     */
    private static Map<Integer,String> DICTIONARY_GOODS_PROPERTYID_MAP = new HashMap<>();

    /**
     * 签到类型
     *  0 每日签到
     *  1 累计签到
     */
    private static Map<Integer,String> DICTIONARY_SIGNTYPE_MAP = new HashMap<>();

    /**
     * 图片类型
     * 1 横屏
     * 2 竖屏
     */
    private static Map<Integer,String> DICTIONARY_TYPE_MAP = new HashMap<>();

    /**
     * 广告位类型
     *  0 首页轮播广告
     *  1 新闻公告广告
     *  2 联系我们广告
     *  3 H5竖版弹出广告
     *  4 手机横版弹出广告
     *  5 游戏下载广告
     *  6 活动公告
     */
    private static Map<Integer,String> DICTIONARY_AD_TYPE_MAP = new HashMap<>();

    /**
     * 广告平台类型
     * 1 三端
     * 2 LUA
     * 3 U3D
     * 4 H5
     */
    private static Map<Integer,String> DICTIONARY_AD_PLATFORMTYPE_MAP = new HashMap<>();

    /**
     * 交易类型
     * 1 存款
     * 2 取款
     * 3 转账
     */
    private static Map<Integer,String> DICTIONARY_TRADETYPE_MAP = new HashMap<>();

    /**
     * 离开原因
     *  0 常规离开
     *  1 系统原因
     *  2 用户冲突
     *  3 网络原因
     *  4 房间人满）
     */
    private static Map<Integer,String> DICTIONARY_LEAVEREASON_MAP = new HashMap<>();

    /**
     * 是否是安卓
     * 0 否  用户
     * 1 是  机器人
     */
    private static Map<Integer,String> DICTIONARY_ISANDROID_MAP = new HashMap<>();

    /**
     * 房间状态
     * 0 等待中
     * 1 游戏中
     * 2 已解散
     */
    private static Map<Integer,String> DICTIONARY_ROOMSTATUS_MAP = new HashMap<>();

    /**
     * 亲友圈状态
     * 1：可用
     * 2：不可用
     */
    private static Map<Integer,String> DICTIONARY_GROUPSTATUS_MAP = new HashMap<>();

    /**
     * 绑定方式
     *
     * 1 银行卡
     * 2 支付宝
     *
     */
    private static Map<Integer,String> DICTIONARY_BANKCHOICE_MAP = new HashMap<>();

    /**
     * 审核状态
     *
     * 0 未审核
     * 1 审核通过
     * 2 审核未通过
     *
     */
    private static Map<Integer,String> DICTIONARY_REVIEWSTATUS_MAP = new HashMap<>();

    static {

        /**
         * 审核状态
         *
         * 0 未审核
         * 1 审核通过
         * 2 审核未通过
         *
         */
        DICTIONARY_REVIEWSTATUS_MAP.put(0,"未审核");
        DICTIONARY_REVIEWSTATUS_MAP.put(1,"审核通过");
        DICTIONARY_REVIEWSTATUS_MAP.put(2,"审核未通过");

        /**
         * 卡类型
         *
         */
        DICTIONARY_BANKCHOICE_MAP.put(0,"未知");
        DICTIONARY_BANKCHOICE_MAP.put(1,"中国银行");
        DICTIONARY_BANKCHOICE_MAP.put(2,"农行");
        DICTIONARY_BANKCHOICE_MAP.put(3,"建行");
        DICTIONARY_BANKCHOICE_MAP.put(4,"工行");
        DICTIONARY_BANKCHOICE_MAP.put(5,"交行");
        DICTIONARY_BANKCHOICE_MAP.put(6,"招行");
        DICTIONARY_BANKCHOICE_MAP.put(7,"光大");
        DICTIONARY_BANKCHOICE_MAP.put(8,"浦东发展");
        DICTIONARY_BANKCHOICE_MAP.put(9,"广发");
        DICTIONARY_BANKCHOICE_MAP.put(10,"北京");
        DICTIONARY_BANKCHOICE_MAP.put(11,"兴业");
        DICTIONARY_BANKCHOICE_MAP.put(12,"邮政");
        DICTIONARY_BANKCHOICE_MAP.put(13,"上海");
        DICTIONARY_BANKCHOICE_MAP.put(14,"平安");
        DICTIONARY_BANKCHOICE_MAP.put(15,"华夏");

        /**
         * 亲友圈状态
         * 1：开放
         * 2：冻结
         */
        DICTIONARY_GROUPSTATUS_MAP.put(1,"开放");
        DICTIONARY_GROUPSTATUS_MAP.put(2,"冻结");

        /**
         * 房间状态
         * 0 等待中
         * 1 游戏中
         * 2 已解散
         */
        DICTIONARY_ROOMSTATUS_MAP.put(0,"等待中");
        DICTIONARY_ROOMSTATUS_MAP.put(1,"游戏中");
        DICTIONARY_ROOMSTATUS_MAP.put(2,"已解散");

        /**
         * 是否是安卓
         * 0 否  用户
         * 1 是  机器人
         */
        DICTIONARY_ISANDROID_MAP.put(0,"否");
        DICTIONARY_ISANDROID_MAP.put(1,"是");

        /**
         * 离开原因
         *  0 常规离开
         *  1 系统原因
         *  2 用户冲突
         *  3 网络原因
         *  4 房间人满
         */
        DICTIONARY_LEAVEREASON_MAP.put(0,"常规离开");
        DICTIONARY_LEAVEREASON_MAP.put(1,"系统原因");
        DICTIONARY_LEAVEREASON_MAP.put(2,"用户冲突");
        DICTIONARY_LEAVEREASON_MAP.put(3,"网络原因");
        DICTIONARY_LEAVEREASON_MAP.put(4,"房间人满");

        /**
         * 交易类型
         * 1 存款
         * 2 取款
         * 3 转账
         */
        DICTIONARY_TRADETYPE_MAP.put(1,"存款");
        DICTIONARY_TRADETYPE_MAP.put(2,"取款");
        DICTIONARY_TRADETYPE_MAP.put(3,"转账");


        /**
         * 广告平台类型
         * 1 三端
         * 2 LUA
         * 3 U3D
         * 4 H5
         */
        DICTIONARY_AD_PLATFORMTYPE_MAP.put(1,"三端");
        DICTIONARY_AD_PLATFORMTYPE_MAP.put(2,"LUA");
        DICTIONARY_AD_PLATFORMTYPE_MAP.put(3,"U3D");
        DICTIONARY_AD_PLATFORMTYPE_MAP.put(4,"H5");

        /**
         * 广告位类型
         *  0 首页轮播广告
         *  1 新闻公告广告
         *  2 联系我们广告
         *  3 H5竖版弹出广告
         *  4 手机横版弹出广告
         *  5 游戏下载广告
         *  6 活动公告
         */
        DICTIONARY_AD_TYPE_MAP.put(0,"首页轮播广告");
        DICTIONARY_AD_TYPE_MAP.put(1,"新闻公告广告");
        DICTIONARY_AD_TYPE_MAP.put(2,"联系我们广告");
        DICTIONARY_AD_TYPE_MAP.put(3,"H5竖版弹出广告");
        DICTIONARY_AD_TYPE_MAP.put(4,"手机横版弹出广告");
        DICTIONARY_AD_TYPE_MAP.put(5,"游戏下载广告");
        DICTIONARY_AD_TYPE_MAP.put(6,"活动公告");

        /**
         * 图片类型
         * 1 横屏
         * 2 竖屏
         */
        DICTIONARY_TYPE_MAP.put(1,"横屏");
        DICTIONARY_TYPE_MAP.put(2,"竖屏");

        /**
         * 签到类型
         *  0 每日签到
         *  1 累计签到
         */
        DICTIONARY_SIGNTYPE_MAP.put(0,"每日签到");
        DICTIONARY_SIGNTYPE_MAP.put(1,"累计签到");

        /**
         * 道具
         * 0 无
         * 306 大喇叭
         */
        DICTIONARY_GOODS_PROPERTYID_MAP.put(0,"无");
        DICTIONARY_GOODS_PROPERTYID_MAP.put(306,"大喇叭");

        /**
         * 物品类型
         * 0 游戏币
         * 1 钻石
         * 2 道具
         * 3 奖券
         */
        DICTIONARY_GOODS_TYPEID_MAP.put(0,"游戏币");
        DICTIONARY_GOODS_TYPEID_MAP.put(1,"钻石");
        DICTIONARY_GOODS_TYPEID_MAP.put(2,"道具");
        DICTIONARY_GOODS_TYPEID_MAP.put(3,"奖券");

        /**
         * 礼包类型
         * 0 抽奖签到礼包
         * 1 累计签到礼包
         */
        DICTIONARY_SIGN_TYPEID_MAP.put(0,"抽奖签到礼包");
        DICTIONARY_SIGN_TYPEID_MAP.put(1,"累计签到礼包");

        /**
         * 平台类型
         * 0 通用
         * 1 LUA
         * 2 H5
         * 3 U3D
         */
        DICTIONARY_PLATFORMKIND_MAP.put(0,"通用");
        DICTIONARY_PLATFORMKIND_MAP.put(1,"LUA");
        DICTIONARY_PLATFORMKIND_MAP.put(2,"H5");
        DICTIONARY_PLATFORMKIND_MAP.put(3,"U3D");

        /**
         * 是否推荐
         * 0 否
         * 1 是
         */
        DICTIONARY_RECOMMEND_MAP.put(0,"否");
        DICTIONARY_RECOMMEND_MAP.put(1,"是");

        /**
         * 作用范围
         * 1 自己
         * 2 玩家
         * 4 旁观
         */
        DICTIONARY_SERVICEAREA_MAP.put(1,"自己");
        DICTIONARY_SERVICEAREA_MAP.put(2,"玩家");
        DICTIONARY_SERVICEAREA_MAP.put(4,"旁观");

        /**
         * 使用范围
         * 1 大厅
         * 2 房间
         * 4 游戏
         */
        DICTIONARY_USEAREA_MAP.put(1,"大厅");
        DICTIONARY_USEAREA_MAP.put(2,"房间");
        DICTIONARY_USEAREA_MAP.put(4,"游戏");

        /**
         * 购买类型
         * 0 钻石购买
         * 1 游戏币购买
         */
        DICTIONARY_EXCHANGETYPE_MAP.put(0,"钻石购买");
        DICTIONARY_EXCHANGETYPE_MAP.put(1,"游戏币购买");

        /**
         * 排行榜排名
         *
         * 1 第一名
         * 2 第二名
         * 3 第三名
         * 4 第4-10名
         * 5 第11-20名
         * 6 第21-50名
         * 7 第51-100名
         */
        DICTIONARY_RANK_RANKTYPE_MAP.put(1,"第一名");
        DICTIONARY_RANK_RANKTYPE_MAP.put(2,"第二名");
        DICTIONARY_RANK_RANKTYPE_MAP.put(3,"第三名");
        DICTIONARY_RANK_RANKTYPE_MAP.put(4,"第4-10名");
        DICTIONARY_RANK_RANKTYPE_MAP.put(5,"第11-20名");
        DICTIONARY_RANK_RANKTYPE_MAP.put(6,"第21-50名");
        DICTIONARY_RANK_RANKTYPE_MAP.put(7,"第51-100名");

        /**
         * 排行榜类型
         * 1 财富排行榜
         * 2 胜局排行榜
         */
        DICTIONARY_RANK_TYPEID_MAP.put(1,"财富排行榜");
        DICTIONARY_RANK_TYPEID_MAP.put(2,"胜局排行榜");

        /**
         * 消息范围
         * 1 游戏
         * 2 房间
         * 3 全部
         */
        DICTIONARY_MESSAGETYPE_MAP.put(1,"游戏");
        DICTIONARY_MESSAGETYPE_MAP.put(2,"房间");
        DICTIONARY_MESSAGETYPE_MAP.put(3,"全部");

        /**
         * 充值类型
         *  101 手机微信充值
         *  102 H5微信充值
         *  201 手机支付宝充值
         *  301 手机零钱充值
         *  302 竣付通微信充值
         *  303 竣付通支付宝充值
         *  800 手机苹果充值
         */
        DICTIONARY_SHAREID_MAP.put(101,"手机微信充值");
        DICTIONARY_SHAREID_MAP.put(102,"H5微信充值");
        DICTIONARY_SHAREID_MAP.put(201,"手机支付宝充值");
        DICTIONARY_SHAREID_MAP.put(301,"手机零钱充值");
        DICTIONARY_SHAREID_MAP.put(302,"竣付通微信充值");
        DICTIONARY_SHAREID_MAP.put(303,"竣付通支付宝充值");
        DICTIONARY_SHAREID_MAP.put(800,"手机苹果充值");

        /**
         * 订单状态
         * 0 未支付
         * 1 已支付
         */
        DICTIONARY_ORDERSTATUS_MAP.put(0,"未支付");
        DICTIONARY_ORDERSTATUS_MAP.put(1,"已支付");

        /**
         * 标签类型
         * 0 普通
         * 1 人气
         */
        DICTIONARY_TAGID_MAP.put(0,"普通");
        DICTIONARY_TAGID_MAP.put(1,"人气");

        /**
         * 充值货币类型
         * 0 游戏币
         * 1 钻石
         */
        DICTIONARY_SCORETYPE_MAP.put(0,"游戏币");
        DICTIONARY_SCORETYPE_MAP.put(1,"钻石");

        /**
         * 首冲类型
         *  0 普通
         *  1 首充
         *  2 每日
         *  3 热卖
         *  4 人气
         */
        DICTIONARY_PAYIDENTITY_MAP.put(0,"普通");
        DICTIONARY_PAYIDENTITY_MAP.put(1,"首充");
        DICTIONARY_PAYIDENTITY_MAP.put(2,"每日");
        DICTIONARY_PAYIDENTITY_MAP.put(3,"热卖");
        DICTIONARY_PAYIDENTITY_MAP.put(4,"人气");

        /**
         * 充值类型
         * 0 普通充值
         * 1 苹果内购
         */
        DICTIONARY_PAYTYPE_MAP.put(0,"普通充值");
        DICTIONARY_PAYTYPE_MAP.put(1,"苹果内购");

        /**
         * 奖励类型
         * 0 其他
         * 1 金币
         * 2 钻石
         * 3 奖券
         */
        DICTIONARY_AWARDTYPE_MAP.put(0,"其他");
        DICTIONARY_AWARDTYPE_MAP.put(1,"金币");
        DICTIONARY_AWARDTYPE_MAP.put(2,"钻石");
        DICTIONARY_AWARDTYPE_MAP.put(3,"奖券");

        /**
         * 时间类型
         * 1 一次任务
         * 2 每日任务
         * 4 每周任务
         * 8 每月任务
         */
        DICTIONARY_TIMETYPE_MAP.put(1,"一次任务");
        DICTIONARY_TIMETYPE_MAP.put(2,"每日任务");
        DICTIONARY_TIMETYPE_MAP.put(4,"每周任务");
        DICTIONARY_TIMETYPE_MAP.put(8,"每月任务");

        /**
         * 用户类型
         * 0 所有用户
         * 1 普通用户
         * 2 会员用户
         */
        DICTIONARY_USERTYPE_MAP.put(0,"所有用户");
        DICTIONARY_USERTYPE_MAP.put(1,"普通用户");
        DICTIONARY_USERTYPE_MAP.put(2,"会员用户");

        /**
         * 任务类型
         *  0 其他任务
         *  1 总局任务
         *  2 胜局任务
         *  4 首胜任务
         *  8 连胜任务
         */
        DICTIONARY_TASKTYPE_MAP.put(0,"其他任务");
        DICTIONARY_TASKTYPE_MAP.put(1,"总局任务");
        DICTIONARY_TASKTYPE_MAP.put(2,"胜局任务");
        DICTIONARY_TASKTYPE_MAP.put(4,"首胜任务");
        DICTIONARY_TASKTYPE_MAP.put(8,"连胜任务");

        /**
         * 平台类型
         *  1 H5
         *  2 U3D
         *  3 LUA
         */
        DICTIONARY_PLATFORMTYPE_MAP.put(1,"H5");
        DICTIONARY_PLATFORMTYPE_MAP.put(2,"U3D");
        DICTIONARY_PLATFORMTYPE_MAP.put(3,"LUA");

        /**
         *  性别
         *  0 保密
         *  1 男
         *  2 女
         */
        DICTIONARY_GENDER_MAP.put(0,"保密");
        DICTIONARY_GENDER_MAP.put(1,"男");
        DICTIONARY_GENDER_MAP.put(2,"女");

        /**
         * 注册来源
         * 0：PC
         * 1：模拟器
         * 16 17 18：Android
         * 32：iTouch
         * 48 49 50：iPhone
         * 64 65 66：iPad
         * 81 83 84：WEB推广页
         * 82 WEB约战页
         * 90 H5
         */
        DICTIONARY_REGISTERORIGIN_MAP.put(0,"PC");
        DICTIONARY_REGISTERORIGIN_MAP.put(1,"模拟器");
        DICTIONARY_REGISTERORIGIN_MAP.put(16,"Android");
        DICTIONARY_REGISTERORIGIN_MAP.put(17,"Android");
        DICTIONARY_REGISTERORIGIN_MAP.put(18,"Android");
        DICTIONARY_REGISTERORIGIN_MAP.put(32,"iTouch");
        DICTIONARY_REGISTERORIGIN_MAP.put(48,"iPhone");
        DICTIONARY_REGISTERORIGIN_MAP.put(49,"iPhone");
        DICTIONARY_REGISTERORIGIN_MAP.put(50,"iPhone");
        DICTIONARY_REGISTERORIGIN_MAP.put(64,"iPad");
        DICTIONARY_REGISTERORIGIN_MAP.put(65,"iPad");
        DICTIONARY_REGISTERORIGIN_MAP.put(66,"iPad");
        DICTIONARY_REGISTERORIGIN_MAP.put(81,"WEB推广页");
        DICTIONARY_REGISTERORIGIN_MAP.put(83,"WEB推广页");
        DICTIONARY_REGISTERORIGIN_MAP.put(84,"WEB推广页");
        DICTIONARY_REGISTERORIGIN_MAP.put(82,"WEB约战页");
        DICTIONARY_REGISTERORIGIN_MAP.put(90,"H5");

        /**
         * 是否禁用
         * 0 启用
         * 1 禁用
         */
        DICTIONARY_NULLITY_MAP.put(0,"启用");
        DICTIONARY_NULLITY_MAP.put(1,"禁用");

    }


    /**
     * 风控审核状态
     *
     * @param code
     * @return
     */
    public static String getReviewstatusDesc(Integer code){
        String value = DICTIONARY_REVIEWSTATUS_MAP.get(code);
        return value;
    }

    /**
     * 财务审核状态
     *
     * @param code
     * @return
     */
    public static String getFinancialStatusDesc(Integer code){
        String value = DICTIONARY_REVIEWSTATUS_MAP.get(code);
        return value;
    }

    /**
     * 绑定卡类型
     *
     * @param code
     * @return
     */
    public static String getBankchoiceDesc(Integer code){
        String value = DICTIONARY_BANKCHOICE_MAP.get(code);
        return value;
    }

    /**
     * 获取所有银行名称
     */
    public static Map<Integer, String> getBankNameMap(){
        return DICTIONARY_BANKCHOICE_MAP;
    }

    /**
     *  亲友圈状态
     *
     * @param code
     * @return
     */
    public static String getGroupstatusDesc(Integer code){
        String value = DICTIONARY_GROUPSTATUS_MAP.get(code);
        return value;
    }

    /**
     *  房间状态
     *
     * @param code
     * @return
     */
    public static String getRoomstatusDesc(Integer code){
        String value = DICTIONARY_ROOMSTATUS_MAP.get(code);
        return value;
    }

    /**
     *  是否是机器人
     *
     * @param code
     * @return
     */
    public static String getIsAndroidDesc(Integer code){
        String value = DICTIONARY_ISANDROID_MAP.get(code);
        return value;
    }

    /**
     *  离开原因
     *
     * @param code
     * @return
     */
    public static String getLeavereasonDesc(Integer code){
        String value = DICTIONARY_LEAVEREASON_MAP.get(code);
        return value;
    }

    /**
     *  交易类型
     *
     * @param code
     * @return
     */
    public static String getTradetypeDesc(Integer code){
        String value = DICTIONARY_TRADETYPE_MAP.get(code);
        return value;
    }

    /**
     *  广告平台类型
     *
     * @param code
     * @return
     */
    public static String getAdPlatformtypeDesc(Integer code){
        String value = DICTIONARY_AD_PLATFORMTYPE_MAP.get(code);
        return value;
    }


    /**
     *  广告位类型
     *
     * @param code
     * @return
     */
    public static String getAdTypeDesc(Integer code){
        String value = DICTIONARY_AD_TYPE_MAP.get(code);
        return value;
    }


    /**
     *  图片类型
     *
     * @param code
     * @return
     */
    public static String getTypeDesc(Integer code){
        String value = DICTIONARY_TYPE_MAP.get(code);
        return value;
    }

    /**
     *  签到类型
     *
     * @param code
     * @return
     */
    public static String getSigntypeDesc(Integer code){
        String value = DICTIONARY_SIGNTYPE_MAP.get(code);
        return value;
    }

    /**
     *  道具
     *
     * @param code
     * @return
     */
    public static String getGoodsPropertyiddDesc(Integer code){
        String value = DICTIONARY_GOODS_PROPERTYID_MAP.get(code);
        return value;
    }

    /**
     *  物品类型
     *
     * @param code
     * @return
     */
    public static String getGoodsTypeidDesc(Integer code){
        String value = DICTIONARY_GOODS_TYPEID_MAP.get(code);
        return value;
    }

    /**
     *  礼包类型
     *
     * @param code
     * @return
     */
    public static String getSignTypeidDesc(Integer code){
        String value = DICTIONARY_SIGN_TYPEID_MAP.get(code);
        return value;
    }

    /**
     *  平台类型
     *
     * @param code
     * @return
     */
    public static String getPlatformkindDesc(Integer code){
        String value = DICTIONARY_PLATFORMKIND_MAP.get(code);
        return value;
    }

    /**
     * 是否推荐
     *
     * @param code
     * @return
     */
    public static String getRecommendDesc(Integer code){
        String value = DICTIONARY_RECOMMEND_MAP.get(code);
        return value;
    }

    /**
     * 作用范围
     *
     * @param code
     * @return
     */
    public static String getServiceareaDesc(Integer code){
        String value = DICTIONARY_SERVICEAREA_MAP.get(code);
        return value;
    }

    /**
     * 使用范围
     *
     * @param code
     * @return
     */
    public static String getUseareaDesc(Integer code){
        String value = DICTIONARY_USEAREA_MAP.get(code);
        return value;
    }

    /**
     * 购买类型
     *
     * @param code
     * @return
     */
    public static String getExchangetypeDesc(Integer code){
        String value = DICTIONARY_EXCHANGETYPE_MAP.get(code);
        return value;
    }

    /**
     * 排行榜排名
     *
     * @param code
     * @return
     */
    public static String getRankRanktypeDesc(Integer code){
        String value = DICTIONARY_RANK_RANKTYPE_MAP.get(code);
        return value;
    }

    /**
     * 排行榜类型
     *
     * @param code
     * @return
     */
    public static String getRankTypeidDesc(Integer code){
        String value = DICTIONARY_RANK_TYPEID_MAP.get(code);
        return value;
    }

    /**
     * 消息类型
     *
     * @param code
     * @return
     */
    public static String getMessagetypeDesc(Integer code){
        String value = DICTIONARY_MESSAGETYPE_MAP.get(code);
        return value;
    }


    /**
     * 充值类型
     *
     * @param code
     * @return
     */
    public static String getShareidDesc(Integer code){
        String value = DICTIONARY_SHAREID_MAP.get(code);
        return value;
    }

    /**
     * 订单状态
     *
     * @param code
     * @return
     */
    public static String getOrderstatusDesc(Integer code){
        String value = DICTIONARY_ORDERSTATUS_MAP.get(code);
        return value;
    }


    /**
     * 标签类型
     *
     * @param code
     * @return
     */
    public static String getTagidDesc(Integer code){
        String value = DICTIONARY_TAGID_MAP.get(code);
        return value;
    }

    /**
     * 充值货币类型
     *
     * @param code
     * @return
     */
    public static String getScoretypeDesc(Integer code){
        String value = DICTIONARY_SCORETYPE_MAP.get(code);
        return value;
    }

    /**
     * 首冲类型
     *
     * @param code
     * @return
     */
    public static String getPayidentityDesc(Integer code){
        String value = DICTIONARY_PAYIDENTITY_MAP.get(code);
        return value;
    }

    /**
     * 充值类型
     *
     * @param code
     * @return
     */
    public static String getPaytypeDesc(Integer code){
        String value = DICTIONARY_PAYTYPE_MAP.get(code);
        return value;
    }

    /**
     * 奖励类型
     *
     * @param code
     * @return
     */
    public static String getAwardtypeDesc(Integer code){
        String value = DICTIONARY_AWARDTYPE_MAP.get(code);
        return value;
    }

    /**
     * 时间类型
     *
     * @param code
     * @return
     */
    public static String getTimetypeDesc(Integer code){
        String value = DICTIONARY_TIMETYPE_MAP.get(code);
        return value;
    }

    /**
     * 用户类型
     *
     * @param code
     * @return
     */
    public static String getUsertypeDesc(Integer code){
        String value = DICTIONARY_USERTYPE_MAP.get(code);
        return value;
    }

    /**
     * 任务类型
     *
     * @param code
     * @return
     */
    public static String getTasktypeDesc(Integer code){
        String value = DICTIONARY_TASKTYPE_MAP.get(code);
        return value;
    }

    /**
     * 获取平台类型
     *
     * @return
     */
    public static String getPlatformtypeDesc(Integer code){
        String value = DICTIONARY_PLATFORMTYPE_MAP.get(code);
        return value;
    }

    /**
     * 是否禁用
     *
     * @param code
     * @return
     */
    public static String getNullityDesc(Integer code){
        String value = DICTIONARY_NULLITY_MAP.get(code);
        return value;
    }

    /**
     * 获取注册来源
     *
     * @return
     */
    public static String getRegisterOriginDesc(Integer code){
        String value = DICTIONARY_REGISTERORIGIN_MAP.get(code);
        return value;
    }

    /**
     * 获取性别别
     * @param code
     * @return
     */
    public static String getGenderDesc(Integer code){
        String value = DICTIONARY_GENDER_MAP.get(code);
        return value;
    }









}
