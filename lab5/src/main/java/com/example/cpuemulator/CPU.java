package com.example.cpuemulator;

import java.util.ArrayList;
import java.util.List;

public class CPU implements ICPU {
    private int[] registers = new int[4];
    private int[] memory = new int[1024];
    private List<Command> executedCommands = new ArrayList<>();

    public void execute(Command c) {
        executedCommands.add(c);

        switch (c.getName()) {
            case "init":
                init(c.getArg1(), c.getArg2());
                break;
            case "ld":
                load(c.getArg1(), c.getArg2());
                break;
            case "st":
                store(c.getArg1(), c.getArg2());
                break;
            case "add":
                add(c.getArg1(), c.getArg2(), c.getResultReg());
                break;
            case "sub":
                sub(c.getArg1(), c.getArg2(), c.getResultReg());
                break;
            case "mult":
                mult(c.getArg1(), c.getArg2(), c.getResultReg());
                break;
            case "div":
                div(c.getArg1(), c.getArg2(), c.getResultReg());
                break;
            case "mv":
                move(c.getArg1(), c.getArg2());
                break;
            default:
                System.out.println("Unknown command: " + c.getName());
        }
    }
    public Command[] getExecutedCommands() {
        return executedCommands.toArray(new Command[0]); // Возвращаем массив выполненных команд
    }

    public int[] getRegisters() {
        return registers;
    }

    public int[] getMemory() {
        return memory;
    }

    public void reset() {
        registers = new int[4];
        memory = new int[1024];
        executedCommands.clear();
    }

    private void move(int srcRegNum, int destinationRegNum) {
        if (isValidRegister(srcRegNum) && isValidRegister(destinationRegNum)) {
            registers[destinationRegNum] = registers[srcRegNum];
            System.out.println("Moved value from register " + srcRegNum + " to register " + destinationRegNum);
        } else {
            System.out.println("Error: register number is out of range");
        }
    }

    private void init(int address, int value) {
        if (address >= 0 && address < memory.length) {
            memory[address] = value;
            System.out.println("Initialized memory address " + address + " with value " + value);
        } else {
            System.out.println("Error: address is out of range");
        }
    }

    private void load(int regNum, int address) {
        if (isValidRegister(regNum) && address >= 0 && address < memory.length) {
            registers[regNum] = memory[address];
            System.out.println("Loaded value from memory address " + address + " to register " + regNum);
        } else {
            System.out.println("Error: register number or address is out of range");
        }
    }

    private void store(int regNum, int address) {
        if (isValidRegister(regNum) && address >= 0 && address < memory.length) {
            memory[address] = registers[regNum];
            System.out.println("Store value from register " + regNum + " to memory address " + address);
        } else {
            System.out.println("Error: register number or address is out of range");
        }
    }

    private void add(int reg1, int reg2, int resultReg) {
        if (isValidRegister(reg1) && isValidRegister(reg2) && isValidRegister(resultReg)) {
            registers[resultReg] = registers[reg1] + registers[reg2];
            System.out.println("Added registers " + reg1 + " and " + reg2 + ", stored in register " + resultReg + ":");
        } else {
            System.out.println("Error: register number is out of range");
        }
    }

    private void sub(int reg1, int reg2, int resultReg) {
        if (isValidRegister(reg1) && isValidRegister(reg2) && isValidRegister(resultReg)) {
            registers[resultReg] = registers[reg1] - registers[reg2];
            System.out.println("Subtracted registers " + reg1 + " and " + reg2 + ", stored in register " + resultReg+ ":");
        } else {
            System.out.println("Error: register number is out of range");
        }
    }

    private void mult(int reg1, int reg2, int resultReg) {
        if (isValidRegister(reg1) && isValidRegister(reg2) && isValidRegister(resultReg)) {
            registers[resultReg] = registers[reg1] * registers[reg2];
            System.out.println("Multiplied registers " + reg1 + " and " + reg2 + ", stored in register " + resultReg+ ":");
        } else {
            System.out.println("Error: register number is out of range");
        }
    }

    protected void div(int reg1, int reg2, int resultReg) {
        if (isValidRegister(reg1) && isValidRegister(reg2) && isValidRegister(resultReg)) {
            if (registers[reg2] != 0) {
                registers[resultReg] = registers[reg1] / registers[reg2];
                System.out.println("Divided registers " + reg1 + " by " + reg2 + ", stored in register " + resultReg + ":");
            } else {
                System.out.println("Error: division by zero");
            }
        } else {
            System.out.println("Error: register number is out of range");
        }
    }

    private boolean isValidRegister(int regNum) {
        return regNum >= 0 && regNum < registers.length;
    }
}
