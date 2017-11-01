package org.wx.weixiao.util;

/**
 * Created by Daniel on 2016/12/6.
 * 错误码信息
 */
public class ErrorCodeUtil {

    private ErrorCodeUtil(){}

    /**
     * 成功
     */
    public final static int SUCCESS = 0;
    /**
     * 未知错误
     */
    public final static int UNKNOW_FAILURE = 1;
    /**
     * 微信接口请求失败
     */
    public final static int WECHAT_INTERFACE_FAILED = 5001;
    /**
     * 公众号不存在
     */
    public final static int OFFICIAL_ACCOUNTS_NOT_EXIT = 5002;
    /**
     * 接口请求失败
     */
    public final static int INTERFACE_FAILED = 5003;
    /**
     * 签名验证不通过
     */
    public final static int SIGN_CHECK_FAILED = 5004;
    /**
     * 公众号未开启应用
     */
    public final static int APP_NOT_OPEN = 5005;
    /**
     * 请求接口参数错误
     */
    public final static int PARAMETERS_WRONG = 5006;
    /**
     * 关键词同步异常
     */
    public final static int KEYWORD_SYN_WRONG = 5007;

    /**
     * 创建问题失败
     */
    public final static int CREATE_QUESTIONDOC_WRONG = 2001;
    /*
     * 上传非excel的文件
     */
    public final static int POSTFIX_WRONG=2002;

    /**
     * excel内部文件格式出错
     */

    public final static int EXCEL_INTERNAL_FORMAT_WRONG=2003;

    /**
     * ID格式异常不符合number
     */
    public final static int NUMBER_FORMAT_ERROR = 9001;

    /**
     * 添加回答者失败
     */
    public final static int ADD_ANSWERER_FAIL = -1;

    /**
     * 问题没有在回答时间中，未被返回
     */
    public final static int QUESTION_NOT_DISPATCHED = 2701;
    /**
     * 无配置权限
     */
    public final static int CONFIGPERMISSION = 403;


}
