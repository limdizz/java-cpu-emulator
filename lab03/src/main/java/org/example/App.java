package org.example;
import java.util.*;

import static org.example.CPU.*;

public class App {
    public static void main(String[] args) {
        Program prog = new Program();
        prog.add(new Command("print"));
        prog.add(new Command("init", "10", "20"));
        prog.add(new Command("init" ,"11", "25"));
        prog.add(new Command("ld", "0" ,"10"));
        prog.add(new Command("ld", "1" ,"11"));
        prog.add(new Command("ld", "2" ,"11"));
        prog.add(new Command("print"));
        prog.add(new Command("add", "0", "2", "3"));
        prog.add(new Command("print"));
        prog.add(new Command("mv", "0" ,"3"));
        prog.add(new Command("print"));
        prog.add(new Command("mult", "0", "1", "3"));
        prog.add(new Command("print"));
        prog.add(new Command("sub", "2", "0", "2"));
        prog.add(new Command("print"));
        prog.add(new Command("div", "2", "3", "1"));
        prog.add(new Command("print"));
        prog.add(new Command("st", "3", "12"));

        ICPU cpu = new CPU();
        Executor exec = new Executor(cpu);
        exec.run(prog.getCommands());

        int[] memoryRange = getMemoryRange(prog.getCommands());
        System.out.println("\nMemory range used: " + memoryRange[0] + " - " + memoryRange[1]);

        List<Map.Entry<String, Integer>> instructionsFrequency = getInstructionsFrequency(prog.getCommands());
        System.out.println("\nInstructions frequency:");
        for (Map.Entry<String, Integer> entry : instructionsFrequency) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        String popularProgram = theMostPopularProgram(prog.getCommands());
        System.out.println("\nThe most popular program: " + popularProgram);
    }
}
