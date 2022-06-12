package dev.volcent.firstLab.task;

import java.util.*;

public class Mage implements Comparable<Mage> {
    final static public MageComparator mageComparator = new MageComparator();
    static public boolean hashMode = false;
    private String name;
    private int level;
    private double power;
    private Set<Mage> apprentices;

    public Mage(String name, int level, double power) {
        this.name = name;
        this.level = level;
        this.power = power;
        this.apprentices = new HashSet<Mage>();
    }
    public Mage() {
        this.name = "Mage";
        this.level = 1;
        this.power = 10.0;
        this.apprentices = new HashSet<Mage>();
    }

    public String getName() {
        return name;
    }
    public int getLevel() {
        return level;
    }
    public double getPower() {
        return power;
    }
    public Set<Mage> getApprentices() {
        return apprentices;
    }

    private int getAllChildrenCount(Mage mage) {
        if (mage.getApprentices().size() == 0)
            return 0;
        int counter = 0;
        for (Mage apprentice : mage.getApprentices())
            counter += apprentice.getApprentices().size() + 1;
        return counter;
    }
    public Map<String, Integer> getStatistics() {
        Map<String, Integer> mageMap = Mage.hashMode ? new HashMap<>() : new TreeMap<>();
        mageMap.put(this.getName(), getAllChildrenCount(this));
        for (Mage apprentice : this.getApprentices())
            mageMap.put(apprentice.getName(), getAllChildrenCount(apprentice));
        return mageMap;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public void setPower(double power) {
        this.power = power;
    }
    public void setApprentices(Set<Mage> apprentices) { this.apprentices = apprentices; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mage mage = (Mage) o;
        return  name.equals(mage.name) && level == mage.level
                && power == mage.power && apprentices.equals(mage.apprentices);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, level, power, apprentices);
    }
    @Override
    public String toString() {
        return "Mage: " +
                "name: '" + name + '\'' +
                ", level: " + level +
                ", power: " + power +
                ", apprentices: " + apprentices.size();
    }
    @Override
    public int compareTo(Mage mage) {
        int nameCompare = name.compareTo(mage.getName());
        if (nameCompare != 0) return nameCompare;
        int levelCompare = Integer.compare(level, mage.level);
        if (levelCompare != 0) return levelCompare;
        return Double.compare(power, mage.power);
    }
}
