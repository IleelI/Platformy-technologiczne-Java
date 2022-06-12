package dev.volcent.firstLab.task;

import java.util.Comparator;

public class MageComparator implements Comparator<Mage> {
    /**
     * Implementation of compare method used to differentiate two objects of class Mage
     * The order of comparison is as follows:
     * Integer Level -> String Name -> Double Power
     * @return comparison result (negative, zero or positive value
     */
    @Override
    public int compare(Mage firstMage, Mage secondMage) {
        int levelCompare = Integer.compare(firstMage.getLevel(), secondMage.getLevel());
        if (levelCompare != 0) return levelCompare;
        int nameCompare = firstMage.getName().compareTo(secondMage.getName());
        if (nameCompare != 0) return nameCompare;
        return Double.compare(firstMage.getPower(), secondMage.getPower());
    }
}
