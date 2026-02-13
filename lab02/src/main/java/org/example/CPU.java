package org.example;

class CPU {
    private int[] registers = new int[4];
    protected int[] memory = new int[1024];

    public void exe(Instruction instruction) {
        switch (instruction.command) {
            case "move":
                move(instruction.args);
                break;
            case "store":
                store(instruction.args);
                break;
            case "load":
                load(instruction.args);
                break;
            case "add":
                add(instruction.args);
                break;
            case "sub":
                sub(instruction.args);
                break;
            case "mult":
                mult(instruction.args);
                break;
            case "div":
                div(instruction.args);
                break;
            case "print":
                print();
                break;
            case "init":
                init(instruction.args);
                break;
            default:
                System.out.println("Unknown command: " + instruction.command);
        }
    }
    private void store(String[] args) {
        int reg = Integer.parseInt(args[0]);
        int address = Integer.parseInt(args[1]);
        memory[address] = registers[reg];
        System.out.println("Stored value from register " + reg + " to memory address " + address);
    }

    private void load(String[] args) {
        int reg = Integer.parseInt(args[0]);
        int address = Integer.parseInt(args[1]);
        registers[reg] = memory[address];
        System.out.println("Loaded value from memory address " + address + " to register " + reg);
    }

    private void add(String[] args) {
        int reg1 = Integer.parseInt(args[0]);
        int reg2 = Integer.parseInt(args[1]);
        int reg3 = Integer.parseInt(args[2]);
        registers[reg3] = registers[reg1] + registers[reg2];
        System.out.println("Added registers " + reg1 + " and " + reg2 + ", stored in register " + reg3);
    }

    private void sub(String[] args) {
        int reg1 = Integer.parseInt(args[0]);
        int reg2 = Integer.parseInt(args[1]);
        int reg3 = Integer.parseInt(args[2]);
        registers[reg3] = registers[reg1] - registers[reg2];
        System.out.println("Subtracted register " + reg2 + " from register " + reg1 + ", stored in register " + reg3);
    }

    private void mult(String[] args) {
        int reg1 = Integer.parseInt(args[0]);
        int reg2 = Integer.parseInt(args[1]);
        int reg3 = Integer.parseInt(args[2]);
        registers[reg3] = registers[reg1] * registers[reg2];
        System.out.println("Multiplied registers " + reg1 + " and " + reg2 + ", stored in register " + reg3);
    }

    private void div(String[] args) {
        int reg1 = Integer.parseInt(args[0]);
        int reg2 = Integer.parseInt(args[1]);
        int reg3 = Integer.parseInt(args[2]);
        if (registers[reg2] != 0) {
            registers[reg3] = registers[reg1] / registers[reg2];
            System.out.println("Divided register " + reg1 + " by register " + reg2 + ", stored in register " + reg3);
        } else {
            System.out.println("Error: Division by zero");
        }
    }

    private void print() {
        System.out.print("Registers: ");
        for (int i = 0; i < registers.length; i++) {
            System.out.print("R" + i + "=" + registers[i] + " ");
        }
        System.out.println();
    }

    private void init(String[] args) {
        int address = Integer.parseInt(args[0]);
        int value = Integer.parseInt(args[1]);
        memory[address] = value;
        System.out.println("Initialized memory address " + address + " with value " + value);
    }

    private void move(String[] args) {
        int reg1 = Integer.parseInt(args[0]);
        int reg2 = Integer.parseInt(args[1]);
        registers[reg2] = registers[reg1];
        System.out.println("Moved value from register " + reg1 + " to register " + reg2);
    }
}
