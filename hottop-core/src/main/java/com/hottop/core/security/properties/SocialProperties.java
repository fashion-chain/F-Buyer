/**
 *
 */
package com.hottop.core.security.properties;

/**
 *
 *
 */
public class SocialProperties {

    private String filterProcessesUrl = "/auth";

    private QQProperties qq = new QQProperties();

    private WeixinProperties weixin = new WeixinProperties();

    private String connectionTablePrefix = "gb_";//默认值

    public QQProperties getQq() {
        return qq;
    }

    public void setQq(QQProperties qq) {
        this.qq = qq;
    }

    public String getFilterProcessesUrl() {
        return filterProcessesUrl;
    }

    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

    public WeixinProperties getWeixin() {
        return weixin;
    }

    public void setWeixin(WeixinProperties weixin) {
        this.weixin = weixin;
    }

    public String getConnectionTablePrefix() {
        return connectionTablePrefix;
    }

    public void setConnectionTablePrefix(String connectionTablePrefix) {
        this.connectionTablePrefix = connectionTablePrefix;
    }
}
