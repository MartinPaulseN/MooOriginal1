package org.example;

public class SimpleWindowUI implements UserInterface{
    private final SimpleWindow window;

    public SimpleWindowUI(String title) {
        window = new SimpleWindow(title);

    }

    @Override
    public String prompt(String message) {
        window.addString(message);
        return window.getString();
    }

    @Override
    public void showMessage(String message) {
        window.addString(message);

    }

    @Override
    public boolean askYesNo(String message) {
        return window.yesNo(message);

    }

    @Override
    public void clear() {
        window.clear();

    }

    @Override
    public void exit() {
        window.exit();

    }
}
