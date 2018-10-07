package com.dantas.tpl4j;

import com.dantas.tpl4j.factory.TaskFactory;

import java.util.Random;

public class App {

    private static class Result {
        String text;
        char letter;
        int count;
    }



    public static void main(String[] args) throws Exception {
        runCountLettersExample();
    }

    private static void runCountLettersExample() throws Exception {
        TaskFactory
            .createAndRun(App::generateText)
            .repeat(2)
            ._catch(App::handleException)
            .then((previousTask) -> {
                Result result = new Result();

                result.text = previousTask.getResult();
                result.letter = 'A';
                result.count = counterLetter(result.text, result.letter);

                return result;
            })
            .then((previousTask) -> {
                printResult(previousTask.getResult());
            }).getResult();
    }

    private static String generateText() throws Exception {
        Random random = new Random();

        if(random.nextBoolean())
            throw new Exception();

        StringBuilder text = new StringBuilder();

        for(int i=0; i<20; ++i)
            text.append((char)(random.nextInt(26) + 'A'));

        return text.toString();
    }

    private static String handleException(Exception ex) throws Exception {
        System.out.println("Bad luck! Randomness is not on your side.");
        System.out.println("Unfortunately generateText operation finished with an Exception.");
        System.out.println("We will return a default text to work with. We could also wrap this exception and throw a new one.");
        System.out.println();

        return "Default text";
    }

    private static int counterLetter(String text, char letter) {
        int counter = 0;

        for(int i=0; i<text.length(); ++i)
            if(text.charAt(i) == letter)
                counter++;

        return counter;
    }

    private static void printResult(Result result) {
        System.out.println("Text: " + result.text);
        System.out.println("Letter: " + result.letter);
        System.out.println("Counter: " + result.count);
    }

}
