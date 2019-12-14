package com.snake.mcf.sysmgr.repertory.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "TbSysQRCode")
public class TbSysQRCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "path")
    private String path;

    @Column(name = "code")
    private String code;

    @Column(name = "merchant")
    private String merchant;

}
