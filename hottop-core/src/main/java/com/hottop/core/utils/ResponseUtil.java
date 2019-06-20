package com.hottop.core.utils;

import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.response.EResponseResult;
import com.hottop.core.response.Response;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * 生成response
 *
 * {@link com.hottop.core.response.Response}
 * Response Util
 */
public class ResponseUtil {


    public static Response createErrorResponse(Map map) {
        return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).message(map).create();
    }

    public static Response createErrorResponse(String reason) {
        return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).simpleMessage(reason).create();
    }

    public static Response createOKResponse(EResponseResult result){
        return Response.ResponseBuilder.result(result).message().create();
    }

    public static Response createErrorResponse(EResponseResult result) {
        return Response.ResponseBuilder.result(result).message().create();
    }

    public static Response addSuccessResponse(String name) {
        return Response.ResponseBuilder.result(EResponseResult.OK).simpleMessage(
                String.format(BaseConfiguration.getMessage("common.add.success"),
                        BaseConfiguration.getMessage(name))
        ).create();
    }

    public static Response addSuccessResponse(String name, Object data) {
        return Response.ResponseBuilder.result(EResponseResult.OK).data(data).simpleMessage(
                String.format(BaseConfiguration.getMessage("common.add.success"),
                        BaseConfiguration.getMessage(name))
        ).create();
    }

    public static Response addFailResponse(String name) {
        return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).simpleMessage(
                String.format(BaseConfiguration.getMessage("common.add.fail"),
                        BaseConfiguration.getMessage(name))
        ).create();
    }

    public static Response updateSuccessResponse(String name) {
        return Response.ResponseBuilder.result(EResponseResult.OK).simpleMessage(
                String.format(BaseConfiguration.getMessage("common.update.success"),
                        BaseConfiguration.getMessage(name))
        ).create();
    }

    public static Response updateSuccessResponse(String name, Object data) {
        return Response.ResponseBuilder.result(EResponseResult.OK).data(data).simpleMessage(
                String.format(BaseConfiguration.getMessage("common.update.success"),
                        BaseConfiguration.getMessage(name))
        ).create();
    }

    public static Response updateFailResponse(String name) {
        return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).simpleMessage(
                String.format(BaseConfiguration.getMessage("common.update.fail"),
                        BaseConfiguration.getMessage(name))
        ).create();
    }

    public static Response deleteSuccessResponse(String name) {
        return Response.ResponseBuilder.result(EResponseResult.OK).simpleMessage(
                String.format(BaseConfiguration.getMessage("common.delete.success"),
                        BaseConfiguration.getMessage(name))
        ).create();
    }

    public static Response deleteFailResponse(String name) {
        return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).simpleMessage(
                String.format(BaseConfiguration.getMessage("common.delete.fail"),
                        BaseConfiguration.getMessage(name))
        ).create();
    }

    public static Response detailSuccessResponse(String name, Object data) {
        return Response.ResponseBuilder.result(EResponseResult.OK).data(data).simpleMessage(
                String.format(BaseConfiguration.getMessage("common.detail.success"),
                        BaseConfiguration.getMessage(name))
        ).create();
    }

    public static Response detailFailResponse(String name) {
        return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).simpleMessage(
                String.format(BaseConfiguration.getMessage("common.detail.fail"),
                        BaseConfiguration.getMessage(name))
        ).create();
    }

    public static Response notExistResponse(String name) {
        return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).simpleMessage(
                String.format(BaseConfiguration.getMessage("common.notExists"),
                        BaseConfiguration.getMessage(name))
        ).create();
    }

    public static Response existResponse(String name) {
        return Response.ResponseBuilder.result(EResponseResult.ERROR_INTERVAL).simpleMessage(
                String.format(BaseConfiguration.getMessage("common.exists"),
                        BaseConfiguration.getMessage(name))
        ).create();
    }
}
