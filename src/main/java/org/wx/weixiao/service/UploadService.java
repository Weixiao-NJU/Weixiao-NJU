package org.wx.weixiao.service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by lizhimu on 2017/1/8.
 */
public interface UploadService {
    /**
     * 管理员上传回答者信息的.xlsx文件后根据文件内容将文件存到服务器本地并将文件内容写到数据库重
     * @param request
     * @param path 希望文件存的位置，path格式形如"D:\\excelTest
     */

    public int saveAnswererFileInDatabase(HttpServletRequest request,String path,String mediaId);

    /**
     * 管理员上传知识库内容的.xlsx文件后根据文件内容将文件存到服务器本地并将文件内容写到数据库重
     * @param request
     * @param path 希望文件存的位置，path格式形如"D:\\excelTest"
     */
    public int saveKnowledgeFileInDatabase(HttpServletRequest request,String path,String mediaId);
}
