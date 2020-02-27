package com.github.brunomndantas.tpl4j;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.action.link.ILinkAction;
import com.github.brunomndantas.tpl4j.task.action.retry.RetryAction;
import com.github.brunomndantas.tpl4j.task.factory.TaskFactory;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

public class IntensiveTest {

    private static class Calculation {

        private String leftNumber;
        public String getLeftNumber() { synchronized (this) { return this.leftNumber; } }

        private String rightNumber;
        public String getRightNumber() { synchronized (this) { return this.rightNumber; } }

        private char operation;
        public char getOperation() { synchronized (this) { return this.operation; } }

        private int result;
        public int getResult() { synchronized (this) { return this.result; } }
        public void setResult(int result) { synchronized (this) { this.result = result; } }



        public Calculation(String leftNumber, String rightNumber, char operation) {
            this.leftNumber = leftNumber;
            this.rightNumber = rightNumber;
            this.operation = operation;
        }



        public Calculation clone() {
            return new Calculation(this.leftNumber, this.rightNumber, this.operation);
        }

    }

    private static class CalculationAction implements IAction<Integer> {

        public static int calc(Calculation calculation) {
            int leftNumber = Integer.parseInt(calculation.getLeftNumber());
            int rightNumber = Integer.parseInt(calculation.getRightNumber());

            switch (calculation.getOperation()) {
                case '+': return leftNumber+rightNumber;
                case '-': return leftNumber+rightNumber;
                case '*': return leftNumber+rightNumber;
                case '/': return leftNumber+rightNumber;
            }

            throw new RuntimeException("Unknown operation " + calculation.getOperation() + "!");
        }



        private Calculation calculation;



        public CalculationAction(Calculation calculation) {
            this.calculation = calculation;
        }



        @Override
        public Integer run(ICancellationToken cancellationToken) throws Exception {
            cancellationToken.abortIfCancelRequested();

            if(new Random().nextBoolean())
                throw new RuntimeException("Try again later!");

            return calc(calculation);
        }

    }

    private static class SetResultAction implements ILinkAction<Calculation, Integer> {

        private Calculation calculation;



        public SetResultAction(Calculation calculation) {
            this.calculation = calculation;
        }



        @Override
        public Calculation run(Task<Integer> previousTask, ICancellationToken cancellationToken) throws Exception {
            cancellationToken.abortIfCancelRequested();

            this.calculation.setResult(previousTask.getResult());

            return this.calculation;
        }

    }

    private static class VerifyResultAction implements ILinkAction<Boolean, Calculation> {

        @Override
        public Boolean run(Task<Calculation> previousTask, ICancellationToken cancellationToken) throws Exception {
            cancellationToken.abortIfCancelRequested();

            int result = CalculationAction.calc(previousTask.getResult());

            return previousTask.getResult().getResult() == result;
        }

    }



    private static final int NUMBER_OF_CALCULATIONS = 1024 * 4;



    private static Task<Boolean> createCalculationTask(Calculation calculation, Consumer<Runnable> scheduler) {
        return TaskFactory.createAndStart(new CalculationAction(calculation), scheduler)
            .retry(RetryAction.RETRY_UNTIL_SUCCESS, scheduler)
            .then(new SetResultAction(calculation), scheduler)
            .then(new VerifyResultAction(), scheduler, Option.ATTACH_TO_PARENT);
    }

    private static Collection<Calculation> clone(Collection<Calculation> calculations) {
        Collection<Calculation> clones = new LinkedList<>();

        for(Calculation calculation : calculations)
            clones.add(calculation.clone());

        return clones;
    }

    private static Collection<Calculation> generateRandomCalculations() {
        Collection<Calculation> calculations = new LinkedList<>();

        for(int i=0; i<NUMBER_OF_CALCULATIONS; ++i)
            calculations.add(generateRandomCalculation());

        return calculations;
    }

    private static Calculation generateRandomCalculation() {
        return new Calculation(
                generateRandomNumber()+"",
                generateRandomNumber()+"",
                generateRandomOperation());
    }

    private static int generateRandomNumber() {
        return new Random().nextInt(100);
    }

    private static char generateRandomOperation() {
        int number = new Random().nextInt(3);

        if(number == 0)
            return '+';

        if(number == 1)
            return '-';

        if(number == 2)
            return '*';

        return '/';
    }



    @Test
    public void test() throws Exception {
        Collection<Calculation> calculations = generateRandomCalculations();

        Collection<Calculation> calculationsA = clone(calculations);
        Collection<Calculation> calculationsB = clone(calculations);
        Collection<Calculation> calculationsC = clone(calculations);

        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        ExecutorService multipleThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try {
            runTest(calculationsA);
            runTest(calculationsB, singleThreadPool::submit);
            runTest(calculationsC, multipleThreadPool::submit);

            long calcA = calculationsA.stream().mapToInt(Calculation::getResult).sum();
            long calcB = calculationsB.stream().mapToInt(Calculation::getResult).sum();
            long calcC = calculationsC.stream().mapToInt(Calculation::getResult).sum();

            assertEquals(calcA, calcB);
            assertEquals(calcA, calcC);
        } finally {
            singleThreadPool.shutdown();
            multipleThreadPool.shutdown();
        }
    }

    public void runTest(Collection<Calculation> calculations) {
        for(Calculation calculation : calculations)
            calculation.setResult(CalculationAction.calc(calculation));
    }

    public void runTest(Collection<Calculation> calculations, Consumer<Runnable> scheduler) throws Exception {
        Collection<Task<Void>> tasks = new LinkedList<>();
        for(Calculation calculation : calculations)
            tasks.add(new Task<>(() -> { createCalculationTask(calculation, scheduler); }, scheduler));

        for(Task<?> task : tasks)
            task.start();

        Task<?> finalTask = TaskFactory.whenAll(tasks, scheduler);
        finalTask.getResult();
    }

}
