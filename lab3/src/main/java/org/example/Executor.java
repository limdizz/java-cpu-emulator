package org.example;

class Executor {
    private ICPU cpu;

    public Executor(ICPU cpu) {
        this.cpu = cpu;
    }

    public void run(Command[] commands) {
        for (Command command : commands) {
            cpu.execute(command);
        }
    }
}
