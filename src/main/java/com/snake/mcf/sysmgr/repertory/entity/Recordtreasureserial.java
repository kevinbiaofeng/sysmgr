package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "[WHQJRecordDB].[dbo].[RecordTreasureSerial]")
public class Recordtreasureserial implements Serializable {

    @Id
    @Column(name = "SerialNumber")
    private String serialnumber;

    @Column(name = "MasterID")
    private Integer masterid;

    @Column(name = "UserID")
    private Integer userid;

    @Column(name = "TypeID")
    private Integer typeid;

    @Column(name = "CurScore")
    private Long curscore;

    @Column(name = "CurInsureScore")
    private Long curinsurescore;

    @Column(name = "ChangeScore")
    private Long changescore;

    @Column(name = "ClientIP")
    private String clientip;

    @Column(name = "CollectDate")
    private Date collectdate;

    private static final long serialVersionUID = 1L;

}