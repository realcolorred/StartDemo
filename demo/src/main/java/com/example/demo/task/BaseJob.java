package com.example.demo.task;

import org.quartz.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lenovo on 2019/9/12.
 */
public abstract class BaseJob implements Job {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

}
