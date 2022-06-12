package dev.volcent;

import java.io.Serializable;

public class Message implements Serializable {
    final static public String ready = "gotów";
    final static public String readForMessages = "gotów do odbioru";
    final static public String finished = "skończyłem";
    final static public String error = "error";
    final static public int numbersSize = 10;

    private int[] numbers;
    private String content;

    public Message(String content) {
        this.content = content;
        this.numbers = new int[numbersSize];
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setNumbers(int[] number) {
        this.numbers = number;
    }

    public int[] getNumbers() {
        return numbers;
    }

    public String getContent() {
        return content;
    }

    public void printNumbers() {
        for (int number : numbers) {
            System.out.print(number + " ");
        }
        System.out.println();
    }
}
