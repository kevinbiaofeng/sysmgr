package com.snake.mcf.common.utils;

import com.snake.mcf.common.constant.UniversalConstant;
import com.snake.mcf.common.exception.BusinessException;
import com.snake.mcf.common.service.ConfigResource;
import com.snake.mcf.common.web.media.MediaFile;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * 文件操作
 *
 * @ClassName:  FileUtils
 * @author: hengoking
 * @date:   2018年12月29日 下午1:41:43
 *
 * @Copyright: 2018 www.payplatform.com Inc. All rights reserved.
 * 注意：本内容仅限于 内部传阅，禁止外泄以及用于其他的商业目
 */
@Slf4j
@Data
//@Configuration
//@PropertySource("classpath:pps/config.properties")
public class FileUtils extends org.apache.commons.io.FileUtils{

//    @Value("${local.user.face.path}")
//    private static String userFacePath;

    @Autowired
    private ConfigResource configResource;

    //static/Upload/Initialize/3.png
    private static final String UPLOAD_PATH = "static/Upload";

    private static final String UPLOAD_RESP_DIR = "Initialize";

    private static final String UPLOAD_RESP_ADVERT_DIR = "Advert";

    private static final String UPLOAD_DIR = "Upload";

    /**
     * 保存文件到指定路径
     *
     * @param file 要保存的文件
     * @param path suffixPath 指定的路径  默认是 classpath下的 static/Upload 文件下
     *           比如传入 path = "Initialize";
     *             就是将图片传入 static/Upload/Initialize 文件夹下
     *
     * @return   返回值就是 图片穿存入的地址
     *         比如: /Upload/Initialize/1.jpg
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String uploadFile(MediaFile file,String path) throws FileNotFoundException,IOException{
        if(file == null){
            String description = "MediaFile resource [" + file + "]";
            throw new FileNotFoundException(description + " cannot be resolved to URL because it does not exist");
        }
        //获取classpath 路径  路径前缀
        if(StringUtils.isBlank(path)){
            /*String description = "class path resource [" + userFacePath + "]";
            throw new FileNotFoundException(description + " cannot be resolved to URL because it does not exist");*/
            throw new BusinessException("保存路径前缀不存在");
        }
        // static/Upload
        /*File uploadRoot = new File(path);
        String absolutePath = uploadRoot.getAbsolutePath();
        log.info("absolutePath:{}",absolutePath);*/
        File upload = new File(path);
        if(!upload.exists()) {
            upload.mkdirs();
        }

        //数据源
        InputStream in = file.getFileStream();
        String sid = DateUtils.format(new Date(), "yyyyMMddHHmmssSSS");
        String ext = file.getFileExt();
        String fileName = sid + "." + ext;
        File destFile = new File(upload,fileName);
        //拷贝文件
        copyToFile(in,destFile);

        //获取路径
        /*String fileAbsolutePath = destFile.getAbsolutePath();
        log.info("destFile : {}" , fileAbsolutePath);
        // E:\workspace\sysmgr\target\classes\static\Upload\Initialize\20190708165151094.jpg
        fileAbsolutePath = fileAbsolutePath.replace("\\","/");
        log.info("destFile : {}" , fileAbsolutePath);
        int index = fileAbsolutePath.indexOf(UPLOAD_DIR);
        log.info("index : {}" , index);
        fileAbsolutePath = fileAbsolutePath.substring(index,fileAbsolutePath.length());
        log.info("destFile : {}" , fileAbsolutePath);*/
        String[] strs = path.split("img");
        for (int i = 0; i < strs.length; i++) {
            if (i == strs.length-1){
                path = strs[i];
            }
        }
        return path + UniversalConstant.SEPARATOR_SYMBOL_DIAGONAL + fileName;
    }

    /**
     * 保存文件到指定路径
     *
     * @return
     */
    public static String uploadFile(MediaFile file) throws FileNotFoundException,IOException {
        return uploadFile(file,null);
    }

    /**
     * 获取 MediaFile
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static MediaFile getMediaFile(MultipartFile file) throws IOException{
        if(file == null){
            String description = "MultipartFile resource [" + file + "]";
            throw new FileNotFoundException(description + " cannot be resolved MultipartFile because it does not exist");
        }
        MediaFile mediaFile = new MediaFile();
        String filename = file.getOriginalFilename();
        log.info("filename = {}" , filename);
        if(StringUtils.isBlank(filename)){
            return null;
        }
        mediaFile.setFileName(filename);
        long size = file.getSize();
        log.info("size = {}" , size);
        if(size == 0){
            return null;
        }
        mediaFile.setFileSize(Long.valueOf(size));
        String extension = org.springframework.util.StringUtils.getFilenameExtension(filename);
        log.info("extension = {}" , extension);
        if(StringUtils.isBlank(extension)){
            return null;
        }
        if (!"png".equalsIgnoreCase(extension) && !"jpg".equalsIgnoreCase(extension)) {
            throw new BusinessException("只支持上传图片png/jpg");
        }
        mediaFile.setFileExt(extension);
        InputStream inputStream = file.getInputStream();
        log.info("inputStream = {}" , inputStream);
        mediaFile.setFileStream(inputStream);
        byte[] bytes = file.getBytes();
        log.info("bytes = {}" , bytes);
        mediaFile.setFileBytes(bytes);
        String contentType = file.getContentType();
        log.info("contentType = {}" , contentType);
        mediaFile.setFileType(contentType);
        return mediaFile;
    }

    /**
     * 获取静态资源位置
     *
     * @param resourceurl
     * @return
     */
    public static String getUploadUrl(String resourceurl){
        return UniversalConstant.SEPARATOR_SYMBOL_DIAGONAL + UPLOAD_DIR + resourceurl;
    }

    /**
     * 校验是否是图片
     *
     * @param ext
     * @return true 是 false 否
     */
    public static boolean isImg(String ext){
        return !(!"jpg".equals(ext) && !"jpeg".equals(ext) && !"png".equals(ext) && !"bmp".equals(ext) && !"gif".equals(ext));
    }

    /**
     * 判断文件是否存在，不存在创建该文件
     * @param file
     */
    public static boolean judeFileExists(File file) {
    	 if (file.exists()) {
    		 return true;
    	 } else {
    		 return false;
    	}
    }

    public static int getFolderFilesCount(String filePath) {
    	File file = new File(filePath);
    	return file.list().length;
    }

    //删除指定文件下的文件
    public static Boolean deleteFile(String path) {
        try {
            String prefixPath = ResourceUtils.getURL("classpath:" + UPLOAD_PATH).getPath();
            String resultPath = prefixPath + path;
            String finalPath = resultPath.replace("\\", "/");
            File file = new File(finalPath);
            return file.delete();
        } catch (FileNotFoundException e) {
            log.error("删除失败");
            return false;
        }
    }


    public static void main(String[] args) {
        try {
        	File f = new File("D:/img/upload/head/");
            int bo = FileUtils.getFolderFilesCount("D:/img/upload/head/");
            System.out.println(bo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
