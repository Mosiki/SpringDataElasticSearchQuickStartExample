package io.github.mosiki.common.utils;

import java.util.HashMap;

public class Result extends HashMap<String, Object> {
    private static final long serialVersionUID = -4964594876005675011L;

    public Result() {
        put("code", 200);
        put("msg", "success");
    }

    public static Result error() {
        return error(500, "未知异常");
    }

    public static Result error(String msg) {
        return error(500, msg);
    }

    public static Result error(int code, String msg) {
        Result result = new Result();
        result.put("code", code);
        result.put("msg", msg);
        return result;
    }

    public static Result success(String msg) {
        Result result = new Result();
        result.put("msg", msg);
        return result;
    }

    public static Result success() {
        return new Result();
    }

    /**
     * 使用示例：Result put = Result.success().put(new Date());
     * @param value
     * @return
     */
    public Result put(Object value) {
        return put("data", value);
    }

    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }

}
