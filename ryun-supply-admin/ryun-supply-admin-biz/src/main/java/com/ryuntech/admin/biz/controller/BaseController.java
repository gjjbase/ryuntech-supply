package com.ryuntech.admin.biz.controller;

import com.ryuntech.admin.api.utils.generator.IncrementIdGenerator;
import com.ryuntech.admin.api.utils.generator.UniqueIdGenerator;

public class BaseController {
    protected long generateId() {
        return UniqueIdGenerator.getInstance(IncrementIdGenerator.getServiceId()).nextId();
    }

}
