package com.hottop.core.cms.controller;

import com.hottop.core.cms.service.ActivityService;
import com.hottop.core.controller.EntityBaseController;
import com.hottop.core.model.cms.Activity;
import com.hottop.core.service.EntityBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity")
public class ActivityController extends EntityBaseController<Activity> {

    @Autowired
    private ActivityService activityService;

    @Override
    public Class<Activity> clazz() {
        return Activity.class;
    }

    @Override
    public EntityBaseService service() {
        return activityService;
    }
}
