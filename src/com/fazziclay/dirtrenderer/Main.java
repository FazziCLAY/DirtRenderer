package com.fazziclay.dirtrenderer;

public class Main {
    public static void main(String[] args) {
        System.out.println("DirtRenderer: Hello!");
        Window window = new Window("DirtRenderer", (int) (1920/2.5f), (int) (1080/2.5f));
        window.run();
        window.delete();
        System.out.println("DirtRenderer: Bye!");
    }
}
