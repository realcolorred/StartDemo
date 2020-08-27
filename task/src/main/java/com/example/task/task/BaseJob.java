package com.example.task.task;

import com.example.common.util.CollectionUtil;
import com.example.pubserv.util.redis.RedisLockUtil;
import com.example.pubserv.util.redis.bo.RedisLockBo;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by lenovo on 2019/9/12.
 */
@Slf4j
public abstract class BaseJob<T> {

    public abstract List<T> getTasks();

    public abstract void doTask(T task);

    protected void execute() {
        List<T> tasks = getTasks();
        if (CollectionUtil.isEmpty(tasks)) {
            return;
        }

        ExecutorService es = null;
        try {
            es = Executors.newFixedThreadPool(2);
            List<Future<?>> listFuture = new LinkedList<>();

            for (final T task : tasks) {

                Future<?> future = es.submit(() -> {
                    try {
                        lockAndDoTask(task);
                        return true;
                    } catch (Throwable t) {
                        log.error("", t);
                        return false;
                    }
                });
                listFuture.add(future);
            }

            for (Future<?> future : listFuture) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    log.error("", e);
                }
            }

        } finally {
            if (es != null) {
                try {
                    es.shutdown();
                } catch (Throwable t) {
                    log.error("", t);
                }
            }
        }
    }

    private void lockAndDoTask(T task) {
        RedisLockBo redisLockBo = null;
        try {
            String lockKey = getClass().getName() + "-" + task;
            redisLockBo = RedisLockUtil.lock(lockKey);
            if (redisLockBo.isLock()) {
                doTask(task);
            } else {
                log.debug("获取该数据：{}的锁失败，不执行定时任务。", lockKey);
            }
        } catch (Exception e) {
            log.error("加锁失败或者任务执行出错。", e);
        } finally {
            if (redisLockBo != null) {
                RedisLockUtil.ulock(redisLockBo);
            }
        }
    }
}
