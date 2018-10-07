package com.dantas.tpl4j.job;

import com.dantas.tpl4j.job.actions.CatchAction;

import java.util.function.Consumer;

public class CatchJob<T> extends Job<T> {

    private Job<T> job;
    private CatchAction<T> action;



    public CatchJob(Job<T> job, CatchAction<T> action, Consumer<Runnable> scheduler) {
        super(() -> {
            if(job.getException() != null)
                return action.run(job.getException());
            else
                return job.getResult();
        }, scheduler);

        this.job = job;
        this.action = action;
    }



    @Override
    public void schedule() {
        super.declareSchedule();
        job.events.finishedEvent.addListener(() -> {
            if(job.isCancelled())
                super.cancel();

            super.scheduler.accept(CatchJob.this);
        });
    }

}
