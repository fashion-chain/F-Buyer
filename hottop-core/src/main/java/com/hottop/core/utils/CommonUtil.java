package com.hottop.core.utils;

import com.hottop.core.BaseConstant;
import com.hottop.core.config.BaseConfiguration;
import com.hottop.core.model.cms.bean.TemplateComponent;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.request.argument.filter.EFilterOperator;
import com.hottop.core.utils.alioss.AliOssClient;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.Embedded;
import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URI;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.*;

public class CommonUtil {

    public static ArrayList<Field> fields(Class clazz) {
        return fields(clazz, EntityBase.class);
    }

    public static ArrayList<Field> fields(Class clazz, Class endClazz) {
        Class cls = clazz;
        ArrayList<Field> fields = new ArrayList<>();
        for (Field field: clazz.getDeclaredFields()) {
            fields.addAll(addFieldsIfFieldMarkedAsEmbedded(field));
        }
        while(cls != Object.class) {
            cls = cls.getSuperclass();
            for (Field field: cls.getDeclaredFields()) {
                fields.addAll(addFieldsIfFieldMarkedAsEmbedded(field));
            }
            if (endClazz != null && cls == endClazz) {
                break;
            }
        }
        return fields;
    }

    public static Map<Object, Object> getMapFromObj(Object obj) {
        Map<Object, Object> beanMap = new HashMap<>();
        for (Field field: obj.getClass().getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers()) || field.isEnumConstant() || field.isSynthetic()) {
                continue;
            }
            try {
                field.setAccessible(true);
                beanMap.put(field.getName(), field.get(obj));
            } catch (Exception ex) {
                LogUtil.error("error in getMapFromObj");
            }
        }
        return beanMap;
    }

    private static List<Field> addFieldsIfFieldMarkedAsEmbedded(Field field) {
        List<Field> fieldList = new ArrayList<Field>();
        if (field.getAnnotation(Embedded.class) != null) {
            Class fieldClazz = field.getType();
            List<Field> insideFields = Arrays.asList(fieldClazz.getDeclaredFields());
            for (Field insideField: insideFields) {
                fieldList.addAll(addFieldsIfFieldMarkedAsEmbedded(insideField));
            }
        } else {
            fieldList.add(field);
        }
        return fieldList;
    }

    public static URI createUri(String strUri, TemplateComponent templateComponent) {
        URI uri = createUri(strUri);
        if (StringUtils.isEmpty(uri.getHost())) {
            strUri = String.format("%s%s", BaseConfiguration.baseHost(), strUri);
        }
        if (templateComponent != null) {
            if (templateComponent.getComponentType() != null)
                strUri = setOrReplaceQueryParam(strUri, BaseConstant.Request.Argument.COMPONENT_TYPE, templateComponent.getComponentType());
            if (templateComponent.getActionType() != null)
                strUri = setOrReplaceQueryParam(strUri, BaseConstant.Request.Argument.ACTION_TYPE, templateComponent.getActionType());
        }
        return createUri(strUri);
    }

    public static String setOrReplaceQueryParam(String url, String paramKey, Object... paramValues) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(url);
        uriComponentsBuilder.replaceQueryParam(paramKey, paramValues);
        return uriComponentsBuilder.toUriString();
    }

    public static URI createUri(String uri) {
        return URI.create(urlQueryEncode(uri));
    }

    public static String urlQueryEncode(String url) {
        try {
            String[] parts = url.split("\\?");
            String[] valueParts = new String[parts.length - 1];
            System.arraycopy(parts, 1, valueParts, 0, valueParts.length);
            String query = String.join(" ", valueParts);
            String[] params = query.split("&");
            String[] targetParams = new String[params.length];
            for (int i=0; i<params.length; i++) {
                String[] paramParts = params[i].split("=");
                targetParams[i] = String.format("%s=%s", paramParts[0], urlEncode(paramParts[1]));
            }
            query = String.join("&", targetParams);
            return String.format("%s?%s", parts[0], query);
        } catch (Exception ex) {
            return url;
        }
    }

    public static String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, BaseConstant.Common.DEFAULT_ENCODE);
        } catch (UnsupportedEncodingException ex) {
            return str;
        }
    }

    public static void print(String... messages) {
        for (String message: messages) {
            System.out.print(message);
        }
    }

    public static void println(String... messages) {
        for (String message: messages) {
            System.out.println(message);
        }
    }

    public static String getClassName(Class<?> clazz) {
        return clazz.getName();
    }

    public static String getTableName(EntityManager em, Class<?> entityClass) {
        Metamodel meta = em.getMetamodel();
        EntityType<?> entityType = meta.entity(entityClass);

        Table t = entityClass.getAnnotation(Table.class);

        String tableName = (t == null)
                ? entityType.getName()
                : t.name();
        return tableName;
    }

    public static String printStackTraceElements(StackTraceElement[] elements) {
        StringBuilder stringBuilder = new StringBuilder();
        for (StackTraceElement element: elements) {
            stringBuilder.append(element.toString());
            stringBuilder.append("\\\n");
        }
        return stringBuilder.toString();
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }

    /**
     * imageUrl 添加前缀
     * /a/b -> www.fxym.com/a/b
     * @param url
     * @return
     */
    public static String imageSetUrlPrefix(String url) {
        url = url.replaceAll("(http|https)://.*?/", "");
        return AliOssClient.getAliOssUrl() + url;
    }

    /**
     * long类型的金额（分） -> （元）
     */
    public static BigDecimal fenToYuan (Long fen) {
        return BigDecimal.valueOf(fen).divide(BigDecimal.valueOf(100l), BigDecimal.ROUND_DOWN);
    }

}
