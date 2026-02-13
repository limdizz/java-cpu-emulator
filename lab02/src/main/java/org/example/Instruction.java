package org.example;

class Instruction {
    String command;
    String[] args;

    public Instruction(String command, String... args) {
        this.command = command;
        this.args = args;
    }
}