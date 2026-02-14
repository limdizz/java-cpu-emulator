package com.example.cpuemulator;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.util.*;

public class HelloController {
    DAO dao = new DAO();
    private ArrayList<String> instructions = new ArrayList<>();
    private int currentInstructionIndex = -1;
    private CPU cpu = new CPU();

    @FXML
    private Button addButton;

    @FXML
    private ListView<String> instructionListView;

    @FXML
    private TextArea memoryArea;

    @FXML
    private Button moveDownButton;

    @FXML
    private Button moveUpButton;

    @FXML
    private TextArea registerArea;

    @FXML
    private Button removeButton;

    @FXML
    private Button resetButton;

    @FXML
    private TextArea statistics;

    @FXML
    private Button stepButton;

    @FXML
    void addInstruction() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add the instruction");
        dialog.setHeaderText("Enter the instruction:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(instruction -> {
            instructions.add(instruction);
            dao.saveInstruction(instruction);
            updateInstructionList();
        });
    }

    @FXML
    void executeStep() {
        if (currentInstructionIndex < instructions.size() - 1) {
            currentInstructionIndex++;
            String currentInstruction = instructions.get(currentInstructionIndex);
            instructionListView.getItems().set(currentInstructionIndex, currentInstruction + " (current)");
            Command command = parseInstruction(currentInstruction);
            cpu.execute(command);

            updateRegistersAndMemory();
            updateStatistics();
            highlightCurrentInstruction();
        }
    }

    public static List<Map.Entry<String, Integer>> get_increasing_list(Command[] commands) {
        Map<String, Integer> frequency = new HashMap<>();
        for (Command command : commands) {
            frequency.put(command.getName(), frequency.getOrDefault(command.getName(), 0) + 1);
        }
        List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(frequency.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue());
        return sortedEntries;
    }

    private void highlightCurrentInstruction() {
        for (int i = 0; i < instructionListView.getItems().size(); i++) {
            String item = instructionListView.getItems().get(i);
            if (i == currentInstructionIndex) {
                instructionListView.getItems().set(i, item.replace(" (current)", "").toUpperCase());
            } else {
                instructionListView.getItems().set(i, item.replace(" (current)", ""));
            }
        }

        instructionListView.setCellFactory(_ -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if (getIndex() == currentInstructionIndex) {
                        setTextFill(Color.RED);
                    } else {
                        setTextFill(Color.BLACK);
                    }
                }
            }
        });
    }

    @FXML
    void moveInstruction(int direction) {
        int selectedIndex = instructionListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0 && (direction == -1 && selectedIndex > 0 || direction == 1 && selectedIndex < instructions.size() - 1)) {
            String instruction = instructions.remove(selectedIndex);
            instructions.add(selectedIndex + direction, instruction);
            updateInstructionList();
            currentInstructionIndex = -1;
            highlightCurrentInstruction();
            instructionListView.getSelectionModel().select(selectedIndex + direction);

            registerArea.clear();
            memoryArea.clear();
            statistics.clear();
            cpu.reset();
        }
    }

    private Command parseInstruction(String instruction) {
        String[] parts = instruction.split(" ");
        String commandName = parts[0];
        int arg1 = Integer.parseInt(parts[1]);
        int arg2 = parts.length > 2 ? Integer.parseInt(parts[2]) : 0;
        int resultReg = parts.length > 3 ? Integer.parseInt(parts[3]) : 0;

        return new Command(commandName, arg1, arg2, resultReg);
    }

    @FXML
    void removeInstruction() {
        if (currentInstructionIndex >= 0 && currentInstructionIndex < instructions.size()) {
            String removingInstr = instructions.get(currentInstructionIndex);
            instructions.remove(currentInstructionIndex);
            dao.deleteInstruction(removingInstr);
            if (currentInstructionIndex == instructions.size()) {
                currentInstructionIndex--;
            }
            updateInstructionList();
        }
    }

    @FXML
    void reset() {
        instructions.clear();
        currentInstructionIndex = -1;
        cpu.reset();
        dao.clearInstructions();
        updateInstructionList();

        registerArea.clear();
        memoryArea.clear();
        statistics.clear();
    }

    private void updateInstructionList() {
        instructionListView.getItems().clear();
        instructionListView.getItems().addAll(instructions);
    }

    private void updateRegistersAndMemory() {
        StringBuilder registerText = new StringBuilder();
        for (int i = 0; i < cpu.getRegisters().length; i++) {
            registerText.append("R").append(i).append(": ").append(cpu.getRegisters()[i]).append("  ");
        }
        registerArea.setText(registerText.toString());

        StringBuilder memoryText = new StringBuilder();
        for (int i = 0; i < cpu.getMemory().length; i++) {
            memoryText.append("M").append(i).append(": ").append(cpu.getMemory()[i]).append("\n");
        }
        memoryArea.setText(memoryText.toString());
    }

    private void updateStatistics() {
        List<Map.Entry<String, Integer>> commandFrequency = get_increasing_list(cpu.getExecutedCommands());
        StringBuilder statisticsText = new StringBuilder();
        for (Map.Entry<String, Integer> entry : commandFrequency) {
            statisticsText.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        statistics.setText(statisticsText.toString());
    }

    private void loadInstructionsFromDatabase() {
        List<String> loadedInstructions = dao.loadInstructions();
        if (loadedInstructions != null) {
            instructions.clear();
            instructions.addAll(loadedInstructions);
        }
    }

    @FXML
    void initialize() {
        assert addButton != null : "fx:id=\"addButton\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert instructionListView != null : "fx:id=\"instructionListView\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert memoryArea != null : "fx:id=\"memoryArea\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert moveDownButton != null : "fx:id=\"moveDownButton\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert moveUpButton != null : "fx:id=\"moveUpButton\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert registerArea != null : "fx:id=\"registerArea\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert removeButton != null : "fx:id=\"removeButton\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert resetButton != null : "fx:id=\"resetButton\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert statistics != null : "fx:id=\"statistics\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert stepButton != null : "fx:id=\"stepButton\" was not injected: check your FXML file 'hello-view.fxml'.";

        loadInstructionsFromDatabase();
        updateInstructionList();

        moveUpButton.setOnAction(_ -> moveInstruction(-1));
        moveDownButton.setOnAction(_ -> moveInstruction(1));
    }
}