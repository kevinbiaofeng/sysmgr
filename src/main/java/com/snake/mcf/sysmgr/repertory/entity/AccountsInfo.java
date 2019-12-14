package com.snake.mcf.sysmgr.repertory.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @ClassName AccountsInfo
 * @Author 大帅
 * @Date 2019/6/24 12:04
 */
@Data
@Table(name = "[WHQJAccountsDB].[dbo].[AccountsInfo]")
public class AccountsInfo implements Serializable {

    @Id
    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "GameID")
    private Integer gameId;

    @Column(name = "SpreaderID")
    private Integer spreaderId;

    @Column(name = "Accounts")
    private String accounts;

    @Column(name = "NickName")
    private String nickName;

    @Column(name = "RegAccounts")
    private String regAccounts;

    @Column(name = "UnderWrite")
    private String underWrite;

    @Column(name = "PassPortID")
    private String passPortId;

    @Column(name = "Compellation")
    private String compellation;

    @Column(name = "LogonPass")
    private String logonPass;

    @Column(name = "InsurePass")
    private String insurePass;

    @Column(name = "DynamicPass")
    private String dynamicPass;

    @Column(name = "DynamicPassTime")
    private Date dynamicPassTime;

    @Column(name = "FaceID")
    private Integer faceId;

    @Column(name = "CustomID")
    private Integer customId;

    @Column(name = "UserRight")
    private Integer userRight;

    @Column(name = "MasterRight")
    private Integer masterRight;

    @Column(name = "ServiceRight")
    private Integer serviceRight;

    @Column(name = "MasterOrder")
    private Integer masterOrder;

    @Column(name = "MemberOrder")
    private Integer memberOrder;

    @Column(name = "MemberOverDate")
    private Date memberOverDate;

    @Column(name = "MemberSwitchDate")
    private Date memberSwitchDate;

    @Column(name = "CustomFaceVer")
    private Integer customFaceVer;

    @Column(name = "Gender")
    private Integer gender;

    @Column(name = "Nullity")
    private Integer nullity;

    @Column(name = "NullityOverDate")
    private Date nullityOverDate;

    @Column(name = "StunDown")
    private Integer stunDown;

    @Column(name = "MoorMachine")
    private Integer moorMachine;

    @Column(name = "IsAndroid")
    private Integer isAndroid;

    @Column(name = "WebLogonTimes")
    private Integer webLogonTimes;

    @Column(name = "GameLogonTimes")
    private Integer gameLogonTimes;

    @Column(name = "PlayTimeCount")
    private Integer playTimeCount;

    @Column(name = "OnLineTimeCount")
    private Integer onLineTimeCount;

    @Column(name = "LastLogonIP")
    private String lastLogonIp;

    @Column(name = "LastLogonDate")
    private Date lastLogonDate;

    @Column(name = "LastLogonMobile")
    private String lastLogonMobile;

    @Column(name = "LastLogonMachine")
    private String lastLogonMachine;

    @Column(name = "RegisterIP")
    private String registerIp;

    @Column(name = "RegisterDate")
    private Date registerDate;

    @Column(name = "RegisterMobile")
    private String registerMobile;

    @Column(name = "RegisterMachine")
    private String registerMachine;

    @Column(name = "RegisterOrigin")
    private Integer registerOrigin;

    @Column(name = "ClientType")
    private Integer clientType;

    @Column(name = "PlatformID")
    private Integer platformId;

    @Column(name = "UserUin")
    private String userUin;

    @Column(name = "RankID")
    private Integer rankId;

    @Column(name = "AgentID")
    private Integer agentId;

    @Column(name = "PlaceName")
    private String placeName;

    @Column(name = "Merchant")
    private String merchant;

    private static final long serialVersionUID = 1L;
}
