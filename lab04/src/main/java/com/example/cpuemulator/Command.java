package com.example.cpuemulator;

public class Command {
    private String name;
    private Integer arg1;
    private Integer arg2;
    private Integer resultReg;

    public Command(String name) {
        this.name = name;
    }

    public Command(String name, String arg1, String arg2) {
        this.name = name;
        this.arg1 = Integer.parseInt(arg1);
        this.arg2 = Integer.parseInt(arg2);
    }

    public Command(String name, int arg1, int arg2, int resultReg) {
        this.name = name;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.resultReg = resultReg;
    }

    public Command(String name, String arg1, String arg2, String resultReg) {
        this.name = name;
        this.arg1 = Integer.parseInt(arg1);
        this.arg2 = Integer.parseInt(arg2);
        this.resultReg = Integer.parseInt(resultReg);
    }

    public String getName() {
        return name;
    }

    public Integer getArg1() {
        return arg1;
    }

    public Integer getArg2() {
        return arg2;
    }

    public Integer getResultReg() {
        return resultReg;
    }

    @Override
    public String toString() {
        return name + " " + arg1 + " " + arg2 + " " + resultReg;
    }
}
