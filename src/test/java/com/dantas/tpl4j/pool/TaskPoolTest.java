package com.dantas.tpl4j.pool;

import com.dantas.tpl4j.task.Task;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TaskPoolTest {

    private static void sleep(long milliseconds) {
        try{
            Thread.sleep(milliseconds);
        } catch (Exception e) { }
    }



    @Test
    public void fixedPoolSizeTest() {
        boolean[] running = new boolean[Runtime.getRuntime().availableProcessors()*2];

        TaskPool pool = new TaskPool(running.length);

        try{
            for(int i=0; i<running.length; ++i){
                int idx = i;

                pool.createAndRun(() -> {
                    running[idx] = true;
                    sleep(4000);
                    running[idx] = false;
                });
            }
        }finally {
            pool.close();
        }

        sleep(1000);

        for(boolean b : running)
            assertTrue(b);

        sleep(6000);

        for(boolean b : running)
            assertFalse(b);
    }

    @Test
    public void defaultPoolSizeTest() {
        boolean[] running = new boolean[Runtime.getRuntime().availableProcessors()];

        TaskPool pool = new TaskPool(running.length);

        try{
            for(int i=0; i<running.length; ++i){
                int idx = i;

                pool.createAndRun(() -> {
                    running[idx] = true;
                    sleep(4000);
                    running[idx] = false;
                });
            }
        }finally {
            pool.close();
        }

        sleep(1000);

        for(boolean b : running)
            assertTrue(b);

        sleep(6000);

        for(boolean b : running)
            assertFalse(b);
    }

    @Test
    public void thenOnActionReceivedFromFixedPoolSizeTest() {
        boolean[] runningFirstLevel = new boolean[Runtime.getRuntime().availableProcessors()];
        boolean[] runningSecondLevel = new boolean[Runtime.getRuntime().availableProcessors()];
        boolean[] runningThirdLevel = new boolean[Runtime.getRuntime().availableProcessors()];

        TaskPool pool = new TaskPool(runningFirstLevel.length);

        try{
            for(int i=0; i<runningFirstLevel.length; ++i){
                int idx = i;

                pool.createAndRun(() -> {
                    runningFirstLevel[idx] = true;
                    sleep(4000);
                    runningFirstLevel[idx] = false;
                })
                .then((previous) -> {
                    runningSecondLevel[idx] = true;
                    sleep(4000);
                    runningSecondLevel[idx] = false;
                })
                .then((previous) -> {
                    runningThirdLevel[idx] = true;
                    sleep(4000);
                    runningThirdLevel[idx] = false;
                });
            }
        }finally {
            pool.close();
        }

        sleep(1000);

        for(boolean b : runningFirstLevel)
            assertTrue(b);

        sleep(4000);

        for(boolean b : runningSecondLevel)
            assertFalse(b);

        sleep(4000);

        for(boolean b : runningThirdLevel)
            assertFalse(b);
    }

    @Test
    public void whenAllWithFixedPoolSizeTest() throws Exception {
        boolean[] running = new boolean[16];

        TaskPool pool = new TaskPool(4);

        Collection<Task<Void>> tasks = new LinkedList<>();

        try{
            for(int i=0; i<running.length; ++i){
                int idx = i;

                tasks.add(
                    pool.createAndRun(() -> {
                        running[idx] = true;
                        sleep(2000);
                        running[idx] = false;
                    })
                );
            }

            long finishedTime = System.currentTimeMillis() + 2000*(running.length/4);

            Task<?> task = pool.whenAll(tasks);
            task.getResult();

            assertTrue(finishedTime <= System.currentTimeMillis());
        }finally {
            pool.close();
        }
    }

}

