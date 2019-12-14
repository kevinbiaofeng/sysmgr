package com.snake.mcf.sysmgr.repertory.api.vo.wealth;

import java.io.Serializable;
import com.snake.mcf.sysmgr.repertory.api.vo.BaseAPIVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
public class UserWealthVO extends BaseAPIVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long score;
	private Long insure;
	private Long diamond;
	private Long awardTicket;
}
