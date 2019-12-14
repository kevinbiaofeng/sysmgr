package com.snake.mcf.sysmgr.repertory.api.vo.logindata;

import java.io.Serializable;
import lombok.Data;

@Data
public class CustomerService implements Serializable{
	private static final long serialVersionUID = 4061239869302071136L;
	
	private String phone;
	
	private String weiXin;
	
	private String qq;
	
	private String link;
}
