package com.mysite.interceptor;

import com.mysite.constant.WebConst;
import com.mysite.model.po.User;
import com.mysite.utils.EncryptUtils;
import com.mysite.utils.PatternUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AuthService {

    /**
     * 返回当前登录用户
     *
     * @return
     */
    public static User getLoginUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (null == session) {
            return null;
        }
        return (User) session.getAttribute(WebConst.LOGIN_SESSION_KEY);
    }

    public String getIpAddrByRequest(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取cookie中的用户id
     *
     * @param request
     * @return
     */
    public Integer getCookieUid(HttpServletRequest request) {
        if (null != request) {
            Cookie cookie = cookieRaw(request, WebConst.USER_IN_COOKIE);
            if (cookie != null && cookie.getValue() != null) {
                try {
                    String uid = EncryptUtils.deAes(cookie.getValue(), WebConst.AES_SALT);
                    return StringUtils.isNotBlank(uid) && PatternUtils.isNumber(uid) ? Integer.valueOf(uid) : null;
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    /**
     * 从cookies中获取指定cookie
     *
     * @param request 请求
     * @param name    名称
     * @return cookie
     */
    private Cookie cookieRaw(HttpServletRequest request, String name) {
        Cookie[] servletCookies = request.getCookies();
        if (servletCookies == null) {
            return null;
        }
        for (Cookie c : servletCookies) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    /**
     * 设置记住密码cookie
     *
     * @param response
     * @param uid
     */
    public void setCookie(HttpServletResponse response, Integer uid) {
        try {
            String val = EncryptUtils.enAes(uid.toString(), WebConst.AES_SALT);
            boolean isSSL = false;
            Cookie cookie = new Cookie(WebConst.USER_IN_COOKIE, val);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 30);
            cookie.setSecure(isSSL);
            response.addCookie(cookie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置cookie
     *
     * @param name
     * @param value
     * @param maxAge
     * @param response
     */
    public void setCookie(String name, String value, int maxAge, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setSecure(false);
        response.addCookie(cookie);
    }
}
