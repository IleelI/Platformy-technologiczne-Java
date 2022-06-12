package dev.volcent.App.entities;

public class Beer {
    final private String name;
    final private int power;

    public Beer(String name, int power) {
        this.name = name;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Mage " + name + ", power " + power + "%";
    }
}
