package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "RecordGameSignIn")
public class Recordgamesignin implements Serializable {

    @Id
    @Column(name = "RecordID")
    private Integer recordid;

    @Column(name = "UserID")
    private Integer userid;

    @Column(name = "SignType")
    private Integer signtype;

    @Column(name = "PackageName")
    private String packagename;

    @Column(name = "PackageGoods")
    private String packagegoods;

    @Column(name = "Probability")
    private Integer probability;

    @Column(name = "NeedDay")
    private Integer needday;

    @Column(name = "TotalDay")
    private Integer totalday;

    @Column(name = "ClinetIP")
    private String clinetip;

    @Column(name = "CollectDate")
    private Date collectdate;

    private static final long serialVersionUID = 1L;

}