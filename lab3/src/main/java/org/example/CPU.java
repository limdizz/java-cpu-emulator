package org.example;

import java.util.*;

public class CPU implements ICPU {
    private int[] registers = new int[4];
    private int[] memory = new int[1024];

    @Override
    public void execute(Command c) {
        switch (c.getName()) {
            case "init":
                if (c.getArgs().length == 2) {
                    init(Integer.parseInt(c.getArgs()[0]), Integer.parseInt(c.getArgs()[1]));
                } else {
                    System.out.println("Error: incorrect number of arguments for init.");
                }
                break;
            case "ld":
                if (c.getArgs().length == 2) {
                    load(Integer.parseInt(c.getArgs()[0]), Integer.parseInt(c.getArgs()[1]));
                } else {
                    System.out.println("Error: incorrect number of arguments for ld.");
                }
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
                if (c.getArgs().length == 2) {
                    move(Integer.parseInt(c.getArgs()[0]), Integer.parseInt(c.getArgs()[1]));
                } else {
                    System.out.println("Error: incorrect number of arguments for move.");
                }
                break;
            case "st":
                if (c.getArgs().length == 2) {
                    store(Integer.parseInt(c.getArgs()[0]), Integer.parseInt(c.getArgs()[1]));
                } else {
                    System.out.println("Error: incorrect number of arguments for store.");
                }
                break;
            case "print":
                print();
                break;
            default:
                System.out.println("Error: unknown command.");
        }
    }

    private void init(int address, int value) {
        if (address >= 0 && address < memory.length) {
            memory[address] = value;
            System.out.println("Initialized memory address " + address + " with value " + value);
        } else {
            System.out.println("Error: the address is out of range.");
        }
    }

    private void load(int regNum, int address) {
        if (isValidRegister(regNum) && address >= 0 && address < memory.length) {
            registers[regNum] = memory[address];
            System.out.println("Loaded value from memory address " + address + " to register " + regNum);
        } else {
            System.out.println("Error: the register number or address is out of range.");
        }
    }
    private void move(int srcRegNum, int destinationRegNum) {
        if (isValidRegister(srcRegNum) && isValidRegister(destinationRegNum)) {
            registers[destinationRegNum] = registers[srcRegNum];
            System.out.println("Moved value from register " + srcRegNum + " to register " + destinationRegNum + ":");
        } else {
            System.out.println("Error: The register number is out of range.");
        }
    }

    private void store(int regNum, int address) {
        if (isValidRegister(regNum) && address >= 0 && address < memory.length) {
            memory[address] = registers[regNum];
            System.out.println("Stored value from register " + regNum + " to memory address " + address);
        } else {
            System.out.println("Error: the register number or address is out of range.");
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

    public void print() {
        for (int i = 0; i < registers.length; i++) {
            System.out.println("R" + i + ": " + registers[i]);
        }
    }

    private boolean isValidRegister(int regNum) {
        return regNum >= 0 && regNum < registers.length;
    }

    private static List<Integer> getAddresses(Command[] commands) {
        List<Integer> addresses = new ArrayList<>();

        for (Command command : commands) {
            if ("init".equals(command.getName())) {
                try {
                    addresses.add(Integer.parseInt(command.getArgs()[0]));
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            } else if ("ld".equals(command.getName())) {
                try {
                    addresses.add(Integer.parseInt(command.getArgs()[1]));
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            } else if ("st".equals(command.getName())) {
                try {
                    addresses.add(Integer.parseInt(command.getArgs()[1]));
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return addresses;
    }

    public static List<Map.Entry<String, Integer>> getInstructionsFrequency(Command[] commands) {
        Map<String, Integer> frequency = new HashMap<>();
        for (Command command: commands) {
            frequency.put(command.getName(), frequency.getOrDefault(command.getName(), 0) + 1);
        }
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(frequency.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue());
        return sortedEntries;
    }

    public static int[] getMemoryRange(Command[] commands) {
        List<Integer> addresses = getAddresses(commands);
        int minAddress = addresses.stream().min(Integer::compare).orElse(0);
        int maxAddress = addresses.stream().max(Integer::compare).orElse(0);
        return new int[]{minAddress, maxAddress};
    }

    public static String theMostPopularProgram(Command[] commands) {
        Map<String, Integer> frequency = new HashMap<>();
        for (Command command: commands) {
            frequency.put(command.getName(), frequency.getOrDefault(command.getName(), 0) + 1);
        }
        return Collections.max(frequency.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
