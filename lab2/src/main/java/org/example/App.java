package org.example;

public class App {
    public static void main(String[] args) {
        CPU cpu = new CPU();
        cpu.exe(new Instruction("init", "0", "10")); // memory[0] = 10
        cpu.exe(new Instruction("init", "1", "20")); // memory[1] = 20
        cpu.exe(new Instruction("init", "2", "30")); // memory[2] = 30
        cpu.exe(new Instruction("print"));

        cpu.exe(new Instruction("load", "0", "0")); // R0 = 10
        cpu.exe(new Instruction("load", "1", "1")); // R1 = 20
        cpu.exe(new Instruction("load", "2", "2")); // R2 = 30
        cpu.exe(new Instruction("print"));

        cpu.exe(new Instruction("sub", "0", "1", "3"));
        cpu.exe(new Instruction("print"));

        cpu.exe(new Instruction("add", "3", "2", "0"));
        cpu.exe(new Instruction("print"));

        cpu.exe(new Instruction("mult", "0", "1", "2"));
        cpu.exe(new Instruction("print"));

        cpu.exe(new Instruction("div", "2", "1", "3"));
        cpu.exe(new Instruction("print"));

        cpu.exe(new Instruction("move", "2", "1"));
        cpu.exe(new Instruction("print"));

        cpu.exe(new Instruction("store", "0", "4"));
        System.out.println("Address 4 value: " + cpu.memory[4]);
    }
}