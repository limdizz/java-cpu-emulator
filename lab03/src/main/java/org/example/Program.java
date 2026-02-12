package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Program implements Iterable<Command> {
    private List<Command> commands;

    public Program() {
        this.commands = new ArrayList<>();
    }

    public void add(Command command) {
        this.commands.add(command);
    }

    @Override
    public Iterator<Command> iterator() {
        return this.commands.iterator();
    }

    public Command[] getCommands() {
        return this.commands.toArray(new Command[0]);
    }
}
