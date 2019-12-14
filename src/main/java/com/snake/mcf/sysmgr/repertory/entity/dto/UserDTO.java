package com.snake.mcf.sysmgr.repertory.entity.dto;

import com.snake.mcf.sysmgr.repertory.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ClassName UserDTO
 * @Author 大帅
 * @Date 2019/6/20 11:00
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserDTO extends User {

    private static final long serialVersionUID = 1L;

    private String start;


}
