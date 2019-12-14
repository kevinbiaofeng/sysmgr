package com.snake.mcf.sysmgr.service.account;

import java.util.List;

import com.snake.mcf.common.ui.paging.filter.EasyPageFilter;
import com.snake.mcf.common.web.result.PageResult;
import com.snake.mcf.sysmgr.repertory.entity.dto.ComboBoxDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TaskinfoDTO;

public interface AccountTaskService {


    PageResult<TaskinfoDTO> queryTaskWithPage(EasyPageFilter pageFilter, TaskinfoDTO dto);

    List<ComboBoxDTO> loadGameRoomComboData(Integer nullity);

    List<ComboBoxDTO> loadGameRoomInfoComboData(Integer kindid);

    boolean saveTask(TaskinfoDTO dto);

    TaskinfoDTO queryTaskById(TaskinfoDTO dto);

    boolean updateTask(TaskinfoDTO dto);

    boolean removeTaskByIds(Integer[] taskids);
}
