package com.iris.blog.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * sql注入处理工具类
 */
@Slf4j
public class SqlInjectionUtils {
    /**
     * sign 用于sql加签的盐值【SQL漏洞】
     * （上线修改值 TABLE_DICT_SIGN_SALT，同步修改前端的盐值）
     */
    private static final String xssStr = "'|and |exec |insert |select |delete |update |drop |count |chr |mid |master |truncate |char |declare |;|or |+|sleep";

    private static final String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"

            + "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";


    /**
     * 表示忽略大小写
     */
    private static final Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);



    /**
     * sql注入过滤处理，遇到注入关键字抛异常
     *
     * @param value
     * @return
     */
    public static void filterContent(String value) {
        if (value == null || "".equals(value)) {
            return;
        }
        // 统一转为小写
        value = value.toLowerCase();
        String[] xssArr = xssStr.split("\\|");
        for (int i = 0; i < xssArr.length; i++) {
            if (value.indexOf(xssArr[i]) > -1) {
                log.info("请注意，存在SQL注入关键词---> {}", xssArr[i]);
                log.info("请注意，值可能存在SQL注入风险!---> {}", value);
                throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + value);
            }
        }
        return;
    }

    /**
     * sql注入过滤处理，遇到注入关键字抛异常
     *
     * @param values
     * @return
     */
    public static void filterContent(String[] values) {
        String[] xssArr = xssStr.split("\\|");
        for (String value : values) {
            if (value == null || "".equals(value)) {
                return;
            }
            // 统一转为小写
            value = value.toLowerCase();
            for (int i = 0; i < xssArr.length; i++) {
                if (value.indexOf(xssArr[i]) > -1) {
                    log.info("请注意，存在SQL注入关键词---> {}", xssArr[i]);
                    log.info("请注意，值可能存在SQL注入风险!---> {}", value);
                    throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + value);
                }
            }
        }
        return;
    }

    /**
     * 注入过滤
     * @param value
     * @return
     */
    @Deprecated
    public static void specialFilterContent(String value) {
        String specialXssStr = " exec | insert | select | delete | update | drop | count | chr | mid | master | truncate | char | declare |;|+|";
        String[] xssArr = specialXssStr.split("\\|");
        if (value == null || "".equals(value)) {
            return;
        }
        // 统一转为小写
        value = value.toLowerCase();
        for (int i = 0; i < xssArr.length; i++) {
            if (value.indexOf(xssArr[i]) > -1 || value.startsWith(xssArr[i].trim())) {
                log.info("请注意，存在SQL注入关键词---> {}", xssArr[i]);
                log.info("请注意，值可能存在SQL注入风险!---> {}", value);
                throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + value);
            }
        }
        return;
    }


    /**
     * 注入过滤
     * @param value
     * @return
     */
    @Deprecated
    public static void specialFilterContentForOnlineReport(String value) {
        String specialXssStr = " exec | insert | delete | update | drop | chr | mid | master | truncate | char | declare |";
        String[] xssArr = specialXssStr.split("\\|");
        if (value == null || "".equals(value)) {
            return;
        }
        // 统一转为小写
        value = value.toLowerCase();
        for (int i = 0; i < xssArr.length; i++) {
            if (value.indexOf(xssArr[i]) > -1 || value.startsWith(xssArr[i].trim())) {
                log.info("请注意，存在SQL注入关键词---> {}", xssArr[i]);
                log.info("请注意，值可能存在SQL注入风险!---> {}", value);
                throw new RuntimeException("请注意，值可能存在SQL注入风险!--->" + value);
            }
        }
        return;
    }


    /**
     * 参数校验
     * @param str ep: "or 1=1"
     */
    public static boolean isSqlValid(String str) {
        Matcher matcher = sqlPattern.matcher(str);
        if (matcher.find()) {
            //获取非法字符：or
            log.info("参数存在非法字符，请确认："+matcher.group());
            return false;
        }
        return true;
    }

}


