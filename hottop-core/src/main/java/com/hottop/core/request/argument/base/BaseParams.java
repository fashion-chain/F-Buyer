package com.hottop.core.request.argument.base;

import com.google.gson.annotations.SerializedName;
import com.hottop.core.BaseConstant;

public class BaseParams {
    private HeaderParam headerParam;

    public HeaderParam getHeaderParam() {
        return headerParam;
    }

    public void setHeaderParam(HeaderParam headerParam) {
        this.headerParam = headerParam;
    }

    public static class HeaderParam {
        @SerializedName(BaseConstant.Request.Header.APP_VERSION)
        private String appVersion;

        @SerializedName(BaseConstant.Request.Header.SYSTEM_VERSION)
        private String systemVersion;

        @SerializedName(BaseConstant.Request.Header.DEVICE_MODEL)
        private String deviceModel;

        @SerializedName(BaseConstant.Request.Header.DEVICE_ID)
        private String deviceId;

        @SerializedName(BaseConstant.Request.Header.NETWORK_TYPE)
        private String networkType;

        @SerializedName(BaseConstant.Request.Header.RESOLUTION_RATIO)
        private String resolutionRatio;

        @SerializedName(BaseConstant.Request.Header.ADAPTER_WIDTH)
        private String adapterWidth;

        @SerializedName(BaseConstant.Request.Header.LATITUDE_LONGITUDE)
        private String latitudeLongitude;

        @SerializedName(BaseConstant.Request.Header.USER_ID)
        private String userId;

        public String getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(String appVersion) {
            this.appVersion = appVersion;
        }

        public String getSystemVersion() {
            return systemVersion;
        }

        public void setSystemVersion(String systemVersion) {
            this.systemVersion = systemVersion;
        }

        public String getDeviceModel() {
            return deviceModel;
        }

        public void setDeviceModel(String deviceModel) {
            this.deviceModel = deviceModel;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getNetworkType() {
            return networkType;
        }

        public void setNetworkType(String networkType) {
            this.networkType = networkType;
        }

        public String getLatitudeLongitude() {
            return latitudeLongitude;
        }

        public void setLatitudeLongitude(String latitudeLongitude) {
            this.latitudeLongitude = latitudeLongitude;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getScreenHeight() {
            return Integer.valueOf(resolutionRatio.split("_")[0]);
        }

        public int getScreenWidth() {
            return Integer.valueOf(resolutionRatio.split("_")[1]);
        }

        public String getResolutionRatio() {
            return resolutionRatio;
        }

        public void setResolutionRatio(String resolutionRatio) {
            this.resolutionRatio = resolutionRatio;
        }

        public Integer getAdapterWidth() {
            return Integer.valueOf(adapterWidth);
        }

        public void setAdapterWidth(String adapterWidth) {
            this.adapterWidth = adapterWidth;
        }
    }
}
