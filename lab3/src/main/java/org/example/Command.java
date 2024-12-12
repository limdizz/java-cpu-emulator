package org.example;

public class Command {
    private String[] args;
    private String name;
    private Integer arg1;
    private Integer arg2;
    private Integer resultReg;

    public Command(String name, String... args) {
        this.name = name;
        this.args = args;
    }

    public Command(String name, String arg1, String arg2, String resultReg) {
        this.name = name;
        this.arg1 = Integer.parseInt(arg1);
        this.arg2 = Integer.parseInt(arg2);
        this.resultReg = Integer.parseInt(resultReg);
    }

    public String[] getArgs() {
        return args;
    }

    public Integer getArg1() {
        return arg1;
    }

    public Integer getArg2() {
        return arg2;
    }

    public String getName() {
        return name;
    }

    public Integer getResultReg() {
        return resultReg;
    }
}
