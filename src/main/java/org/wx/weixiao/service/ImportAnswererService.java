package org.wx.weixiao.service;

/**
 * Created by lizhimu on 2016/12/27.
 */
public interface ImportAnswererService {
    /**
     * 将excel表格的数据导入到Answerer,Department,KeywordAndAnswer数据库中
     * @param file_dir
     * @return 错误码
     */
    //路径形如"D:/excelTest/excel/副本.xlsx"
    public int importAnswererInfo(String file_dir);

    public int importKnowledgeInfo(String file_dir);
}
