package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJNativeWebDB].[dbo].[CacheRankAward]")
public class Cacherankaward implements Serializable {

    @Id
    @Column(name = "DateID")
    private Integer dateid;

    @Id
    @Column(name = "UserID")
    private Integer userid;

    @Id
    @Column(name = "TypeID")
    private Integer typeid;

    @Column(name = "GameID")
    private Integer gameid;

    @Column(name = "NickName")
    private String nickname;

    @Column(name = "FaceUrl")
    private String faceurl;

    @Column(name = "FaceID")
    private Integer faceid;

    @Column(name = "RankNum")
    private Integer ranknum;

    @Column(name = "RankValue")
    private Long rankvalue;

    @Column(name = "Gold")
    private Long gold;

    @Column(name = "Diamond")
    private Integer diamond;

    @Column(name = "ReceiveState")
    private Integer receivestate;

    @Column(name = "CollectDate")
    private Date collectdate;

    @Column(name = "AwardTicket")
    private Integer awardticket;

    private static final long serialVersionUID = 1L;

}