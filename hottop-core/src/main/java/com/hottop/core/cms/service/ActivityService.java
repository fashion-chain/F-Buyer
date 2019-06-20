package com.hottop.core.cms.service;

import com.hottop.core.model.cms.Activity;
import com.hottop.core.repository.EntityBaseRepository;
import com.hottop.core.repository.cms.ActivityRepository;
import com.hottop.core.service.EntityBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService extends EntityBaseService<Activity, Long> {
    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public EntityBaseRepository<Activity, Long> repository() {
        return activityRepository;
    }

}
