/*
 * Copyright (c) 2006-2017, Yunnan Sanjiu Network technology Co., Ltd.
 *
 * All rights reserved.
 */
package com.gjp.sms.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 前端控制器返回封装类，除了第三方调用（cloud和http等请求）外，
 * 本系统内部尽量在controller层使用，不建议传递到service层（复用麻烦）
 *
 * @param <T>
 */
public class Result<T> {

    public static final int SUCCESS = 0;// 成功

    public static final int UNLOGIN = 1000;// 鉴权失败,需要登录

    public static final int DANGER_CHAR = 1001;// 存在危险字符

    public static final int NO_PERMISSIONS = 1002;// 当前用户没有权限

    public static final int NEED_VERIFY = 1003;// 需要账号验证

    public static final int SHOW_DIALOG = 1004;//需要弹出对话框并跳转到某个链接

    public static final int PARAM_VALIDATE_FAILED = 2000;// 参数校验失败

    public static final int PARAM_ERROR = 2001;// 参数错误

    public static final int NO_DATA = 2002;// 暂无数据

    public static final int SERVER_ERROR = 5000;// 内部出错

    public static final int SERVER_RETURN_NULL = 2002;// 调用接口返回空

    public static final int WEBSERVER_ERROR = 5001;// 调用webservice出错

    public static final int RETURN_ERROR = 2003;// 调用服务出错

    private int code = 0;

    private String msg = "";

    private String userMsg = "";

    private DialogInfo dialog;

    //    @JsonIgnoreProperties({ "isdel", "createTime", "updateTime", "updateUserId" })
    private T data = null;

    public Result() {
    }

    /**
     * {@link #success()}
     *
     * @param code
     */
    //@Deprecated
    public Result(int code) {
        this.code = code;
    }

    /**
     * {@link #success(Object)}
     *
     * @param data
     */
    //@Deprecated
    public Result(T data) {
        this.data = data;
    }

    /**
     * {@link #success()}
     * {@link #error()}
     *
     * @param code
     * @param msg
     * @param userMsg
     * @param data
     */
    //@Deprecated
    public Result(int code, String msg, String userMsg, T data) {
        this.code = code;
        this.msg = msg;
        this.userMsg = userMsg;
        this.data = data;
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg, String userMsg) {
        this.code = code;
        this.msg = msg;
        this.userMsg = userMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(String userMsg) {
        this.userMsg = userMsg;
    }

    public DialogInfo getDialog() {
        return dialog;
    }

    public void setDialogInfo(DialogInfo dialog) {
        this.dialog = dialog;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    @JsonIgnore
    public boolean isSuccess() {
        return this.code == SUCCESS;
    }

    public static <T> Result<T> success(String msg, String userMsg, T t) {
        return new Result<>(Result.SUCCESS, msg, userMsg, t);
    }

    public static <T> Result<T> success(T t) {
        return new Result<>(Result.SUCCESS, "SUCCESS", "请求成功", t);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> error(String msg, String userMsg, T t) {
        return new Result<>(Result.SERVER_ERROR, msg, userMsg, t);
    }

    public static <T> Result<T> error(String msg, String userMsg) {
        return new Result<>(Result.SERVER_ERROR, msg, userMsg, null);
    }

    public static <T> Result<T> error(T t) {
        return new Result<>(Result.SERVER_ERROR, "", "", t);
    }

    public static <T> Result<T> error() {
        return new Result<>(Result.SERVER_ERROR, "", "", null);
    }

    public static <T> Result<T> paramError(String msg, String userMsg) {
        return new Result<>(Result.PARAM_ERROR, msg, userMsg, null);
    }

    public static <T> Result<T> paramError(String msg, String userMsg, T t) {
        return new Result<>(Result.PARAM_ERROR, msg, userMsg, t);
    }

    public static <T> Result<T> paramError(T t) {
        return new Result<>(Result.PARAM_ERROR, "参数错误", "", t);
    }

    public static <T> Result<T> noData() {
        return new Result<>(Result.NO_DATA, "", "暂无数据", null);
    }


    /**
     *
     * <p>
     * 全部改用拦截器
     */
    @Deprecated
    public static <T> Result<T> unlogin(String msg, String userMsg) {
        return new Result<>(Result.UNLOGIN, msg, userMsg, null);
    }

    /**
     *
     * <p>
     * 全部改用拦截器
     */
    @Deprecated
    public static <T> Result<T> unlogin(String msg, String userMsg, T t) {
        return new Result<>(Result.UNLOGIN, msg, userMsg, t);
    }


    /**
     *
     * <p>
     * 全部改用拦截器
     */
    @Deprecated
    public static <T> Result<T> unlogin(T t) {
        return new Result<>(Result.UNLOGIN, "", "", t);
    }

    public static <T> Result<T> needVerify() {
        return new Result<>(Result.NEED_VERIFY, "", "检测到您最近修改过密码，请重新登录", null);
    }


    public static <T> Result<T> showDialog(DialogInfo dialogInfo) {
        Result<T> result = new Result<>(Result.SHOW_DIALOG, "", "", null);
        result.setDialogInfo(dialogInfo);
        result.setUserMsg(dialogInfo.getMessage());
        return result;
    }


    /**
     * 弹窗详情
     */
    public static class DialogInfo {
        private String title; // 标题
        private String message; // 文案
        private String confirmBtnText = "确认"; // 确认按钮文案
        private String confirmLink; // 确认按钮跳转链接
        private String cancelBtnText = "取消"; // 取消按钮文案
        private String cancelLink; // 取消按钮跳转链接
        private Boolean showCancelBtn = false; // 是否显示取消按钮

        public DialogInfo() {
        }

        public DialogInfo(String title, String message, String confirmBtnText, String confirmLink, String cancelBtnText, String cancelLink, Boolean showCancelBtn) {
            this.title = title;
            this.message = message;
            this.confirmBtnText = confirmBtnText;
            this.confirmLink = confirmLink;
            this.cancelBtnText = cancelBtnText;
            this.cancelLink = cancelLink;
            this.showCancelBtn = showCancelBtn;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getConfirmBtnText() {
            return confirmBtnText;
        }

        public void setConfirmBtnText(String confirmBtnText) {
            this.confirmBtnText = confirmBtnText;
        }

        public String getConfirmLink() {
            return confirmLink;
        }

        public void setConfirmLink(String confirmLink) {
            this.confirmLink = confirmLink;
        }

        public String getCancelBtnText() {
            return cancelBtnText;
        }

        public void setCancelBtnText(String cancelBtnText) {
            this.cancelBtnText = cancelBtnText;
        }

        public String getCancelLink() {
            return cancelLink;
        }

        public void setCancelLink(String cancelLink) {
            this.cancelLink = cancelLink;
        }

        public Boolean getShowCancelBtn() {
            return showCancelBtn;
        }

        public void setShowCancelBtn(Boolean showCancelBtn) {
            this.showCancelBtn = showCancelBtn;
        }
    }

}
