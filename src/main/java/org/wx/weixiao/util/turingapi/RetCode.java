package org.wx.weixiao.util.turingapi;

/**
 * Created by Daniel on 2017/4/26.
 * 返回码信息
 */
public enum RetCode {
    /**
     * 文本类
     */
    TEXT(100000),
    /**
     * 连接类
     */
    URL(200000),
    /**
     * 新闻类
     */
    NEWS(302000),
    /**
     * 菜谱类
     */
    MENU(308000),
    /**
     * 参数key错误
     */
    KEY_ERROR(40001),
    /**
     * 请求内容info为空
     */
    INFO_NULL(40002),
    /**
     * 当天请求次数已经用完
     */
    NO_TIMES(40004),
    /**
     * 数据格式错误
     */
    FORMAT_WRONG(40007);
    int code;

    RetCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }


}
