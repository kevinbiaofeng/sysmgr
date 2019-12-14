package com.snake.mcf.common.web.media;

import lombok.Data;

import java.io.InputStream;
import java.io.Serializable;

/**
 * @ClassName MediaFile
 * @Author 大帅
 * @Date 2019/7/4 19:17
 */
@Data
public class MediaFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件名字
     */
    private String fileName = "";
    /**
     * 文件大小
     */
    private Long fileSize = 0L;
    /**
     * 文件类型
     */
    private String fileType = "";
    /**
     * 文件后缀
     */
    private String fileExt = "";
    /**
     * 文件字节
     */
    private byte[] fileBytes = new byte[0];

    /**
     * 文件数据流
     */
    private InputStream fileStream;


}
