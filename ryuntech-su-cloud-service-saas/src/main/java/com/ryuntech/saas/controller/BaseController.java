package com.ryuntech.saas.controller;

import com.ryuntech.common.constant.generator.IncrementIdGenerator;
import com.ryuntech.common.constant.generator.UniqueIdGenerator;
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
