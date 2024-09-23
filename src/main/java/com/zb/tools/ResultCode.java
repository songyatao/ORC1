package com.zb.tools;

/**
 * @auther 宋亚涛
 * @verson 1.0
 */
public enum ResultCode {
    /* 成功状态码 */
    LOGIN_SUCCESS(20000, "登录成功"),
    QUERY_SUCCESS(20000, "查询成功"),
    REGISTER_SUCCESS(20000, "注册成功"),
    SAVE_SUCCESS(20000, "新增成功"),
    ORDER_CREATE_SUCCESS(20000, "下单成功"),
    CHARGE_SUCCESS(20000, "充值成功"),
    UPDATE_SUCCESS(20000, "更新成功"),
    BACK_SUCCESS(20000, "撤销成功"),
    AUDIT_SUCCESS(20000, "审核成功"),
    DELETE_SUCCESS(20000, "删除成功"),
    UPLOAD_SUCCESS(20000, "上传成功"),
    LOGINOUT_SUCCESS(20000, "退出成功"),

    START_SUCCESS(20000, "开始服务"),


    /* 用户错误：20001-29999*/
    USER_NULL_PICTURE_ERROR(20001, "上传图片为空"),
    //    USER_NOT_LOGGED_IN(20001, "用户未登录"),
    USER_LOGIN_ERROR(20002, "账号不存在或密码错误"),
    AGGINLOGIN(50001, "登录信息已过期，请先登录"),
    ORDER_IS_EXIST(50010, "当前老人存在工单不可下单"),
    SERVER_IS_EXIST(50010, "该服务人员已经存在于本系统或者养老系统，不可添加"),
    USER_ACCOUNT_FORBIDDEN(20003, "账号已被禁用"),
    USER_NOT_EXIST(20004, "用户不存在"),
    CURRENT_ORDER_NOT_EXIST(20004, "当前没有进行中的工单"),
    USER_HAS_EXISTED(20005, "用户名已被使用，请更换"),
    OLD_PASSWORD_ERROR(20006, "旧密码错误"),
    REPASSWORD_ERROR(20007, "密码不一致"),
    CODE_ERROR(20008, "异常"),
    CODE_OVERDUE(20009, "验证码已经过期，请重新获取"),
    HEALTH_OVERDUE(20010, "请先进行健康上报，上传截图"),
    SERVICE_OVERDUE(20010, "余额不足"),


    /* 权限错误：70001-79999 */
    PERMISSION_NO_ACCESS(70001, "无访问权限");
    private String message;
    private int code;

    ResultCode(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

}
