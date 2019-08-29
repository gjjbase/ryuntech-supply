package com.ryuntech.admin.biz.controller;

import com.ryuntech.admin.api.utils.generator.IncrementIdGenerator;
import com.ryuntech.admin.api.utils.generator.UniqueIdGenerator;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class BaseController {

    protected long generateId() {
        return UniqueIdGenerator.getInstance(IncrementIdGenerator.getServiceId()).nextId();
    }




    protected HttpSession getSession() {
        return getRequest().getSession();
    }

    protected  HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

}
