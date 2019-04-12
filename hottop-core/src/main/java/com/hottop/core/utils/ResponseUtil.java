package com.hottop.core.utils;

import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;

import java.util.Map;

/**
 * 生成response
 *
 * {@link com.hottop.core.response.Response}
 * Response Util
 */
public class ResponseUtil {

    public static Response createErrorResponse(Map map) {
        return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).data(map).create();
    }

    public static Response createErrorResponse(String reason) {
        return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).data(reason).create();
    }

    public static Response createOKResponse() {
        return Response.ResponseBuilder.result(EResponseResult.OK).create();
    }

    public static Response createOKResponse(String msg){
        return Response.ResponseBuilder.result(EResponseResult.OK).create();
    }
}
