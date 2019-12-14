package com.snake.mcf.common.ui.menu.filter;

import com.snake.mcf.common.ui.menu.MenuFilter;
import lombok.Data;

import java.util.List;

/**
 * @ClassName LayMenuFilter
 * @Author 大帅
 * @Date 2019/6/23 16:25
 */
@Data
public class LayMenuFilter implements MenuFilter {
    private String id;

    private String url;

    private String permission;

    private String name;

    private String iconfont;

    private String parentMenuid;

    private Integer lev;

    private List<LayMenuFilter> sub;

}
