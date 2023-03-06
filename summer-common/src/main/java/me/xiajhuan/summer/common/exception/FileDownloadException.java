package me.xiajhuan.summer.common.exception;

import me.xiajhuan.summer.common.utils.MessageUtil;

/**
 * 文件下载异常
 *
 * @author xiajhuan
 * @date 2022/11/26
 */
public class FileDownloadException extends Exception {

    /**
     * 构造文件下载异常<br>
     * note：只有作为原始异常抛出时使用，不要丢弃原始异常！
     *
     * @param message 消息
     */
    private FileDownloadException(String message) {
        super(message);
    }

    /**
     * 构造文件下载异常<br>
     * note：捕获其他异常需要再抛出时使用，包装原始异常
     *
     * @param message 消息
     * @param cause   原始异常
     */
    private FileDownloadException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构建文件下载异常（作为原始异常），消息国际化
     *
     * @param code  错误编码 {@link ErrorCode}
     * @param param 消息填充参数
     * @return 文件下载异常
     */
    public static FileDownloadException of(int code, String... param) {
        return of(MessageUtil.getI18nMessage(code, param));
    }

    /**
     * 构建文件下载异常（包装原始异常），消息国际化
     *
     * @param cause 原始异常
     * @param code  错误编码 {@link ErrorCode}
     * @param param 消息填充参数
     * @return 文件下载异常
     */
    public static FileDownloadException of(Throwable cause, int code, String... param) {
        return of(cause, MessageUtil.getI18nMessage(code, param));
    }


    /**
     * 构建文件下载异常（作为原始异常）
     *
     * @param message 消息
     * @return 文件下载异常
     */
    public static FileDownloadException of(String message) {
        return new FileDownloadException(message);
    }

    /**
     * 构建文件下载异常（包装原始异常）
     *
     * @param cause   原始异常
     * @param message 消息
     * @return 文件下载异常
     */
    public static FileDownloadException of(Throwable cause, String message) {
        return new FileDownloadException(message, cause);
    }

}
