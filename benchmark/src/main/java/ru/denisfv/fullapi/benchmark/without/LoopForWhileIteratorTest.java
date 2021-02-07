package ru.denisfv.fullapi.benchmark.without;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class LoopForWhileIteratorTest {

    private static List<String> DATA_FOR_TESTING = Arrays.asList(createArray());

    public static void main(String[] argv) {
        LoopForWhileIteratorTest obj = new LoopForWhileIteratorTest();

        obj.loopIterator();
        obj.loopFor();
        obj.loopWhile();
    }

    public void loopFor() {
        System.out.println("\n--------- For Loop -------\n");
        long startTime = new Date().getTime();

        StringBuilder s = new StringBuilder();
        for (int i = 0; i < DATA_FOR_TESTING.size(); i++) {
            s.append(DATA_FOR_TESTING.get(i));
        }
        System.out.println(s.length());

        long endTime = new Date().getTime();
        long difference = endTime - startTime;
        System.out.println("For - Elapsed time in milliseconds: " + difference);
    }

    public void loopWhile() {
        System.out.println("\n--------- While Loop -------\n");
        long startTime = new Date().getTime();

        StringBuilder s = new StringBuilder();
        int i = 0;
        while (i < DATA_FOR_TESTING.size()) {
            s.append(DATA_FOR_TESTING.get(i));
            i++;
        }
        System.out.println(s.length());

        long endTime = new Date().getTime();
        long difference = endTime - startTime;
        System.out.println("While - Elapsed time in milliseconds: " + difference);
    }

    public void loopIterator() {
        System.out.println("\n--------- Iterator Loop -------\n");
        long startTime = new Date().getTime();

        StringBuilder next = new StringBuilder();
        Iterator<String> iterator = DATA_FOR_TESTING.iterator();
        while (iterator.hasNext()) {
            next.append(iterator.next());
        }
        System.out.println(next.length());

        long endTime = new Date().getTime();
        long difference = endTime - startTime;
        System.out.println("Iterator - Elapsed time in milliseconds: " + difference);
    }

    private static String[] createArray() {
        String sArray[] = new String[15_000_000];
        for (int i = 0; i < 15_000_000; i++) {
            sArray[i] = "Array " + i;
        }
        return sArray;
    }

}
