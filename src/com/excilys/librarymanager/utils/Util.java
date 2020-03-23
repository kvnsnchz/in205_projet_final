package com.excilys.librarymanager.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Util
 */
public interface Util {

    public static void setAttributesWithSession(HttpServletRequest request, String[] fields) {
        HttpSession session = request.getSession(true);
        for (String field : fields) {
            request.setAttribute(field, session.getAttribute(field));
            session.removeAttribute(field);
        }
    }

}