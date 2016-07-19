package cn.libery.knots.api;


public class ApiException extends RuntimeException {

    public ApiException(int code, String msg) {
        this(getApiExceptionMessage(code, msg));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     *
     * @param code 服务器错误码
     * @param msg  服务器错误信息
     * @return 错误信息
     */
    private static String getApiExceptionMessage(int code, String msg) {
        return code + "\n" + msg;
    }

}
