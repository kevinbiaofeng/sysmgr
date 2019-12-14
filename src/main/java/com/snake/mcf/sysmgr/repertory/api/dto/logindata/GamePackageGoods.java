package com.snake.mcf.sysmgr.repertory.api.dto.logindata;

import java.io.Serializable;

import lombok.Data;

@Data
public class GamePackageGoods implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer goodsID;
	private Integer PackageID;
	private Integer TypeID;
	private Integer PackageTypeID;
	private String ResourceURL;
	private String Name;
	private Integer SortID;
}
