package dev.volcent.firstLab.task;
import java.util.*;

public class Main {
    public static void main(String [] args) {
        Set<Mage> mageSet = switch (args[0]) {
            case (SortingOptions.DefaultSort) -> new TreeSet<Mage>();
            case (SortingOptions.AlternateSort) -> new TreeSet<Mage>(new MageComparator());
            default -> new HashSet<Mage>();
        };
        // Deciding if the data structures will be hash or tree.
        Mage.hashMode = args[0].equals(SortingOptions.NoSort);

        Mage m1 = new Mage("Jnnon", 1, 5.0);
        Mage m2 = new Mage("Ialon", 2, 7.5);
        Mage m3 = new Mage("Hulen", 5, 25.0);
        Mage m4 = new Mage("Garon", 3, 12.5);
        Mage m5 = new Mage("Furon", 50, 9000);
        Mage m6 = new Mage("Einneas", 30, 4250.0);
        Mage m7 = new Mage("Dweneth", 8, 60.0);
        Mage m8 = new Mage("Cenriett", 45, 7600.0);
        Mage m9 = new Mage("Bsabell", 25, 2800.0);
        Mage m10 = new Mage("Austine", 1, 6.5);

        m1.getApprentices().add(m2);
        m1.getApprentices().add(m3);
        m1.getApprentices().add(m4);
        m8.getApprentices().add(m6);
        m8.getApprentices().add(m7);
        m8.getApprentices().add(m9);
        m8.getApprentices().add(m10);
        m5.getApprentices().add(m1);
        m5.getApprentices().add(m8);
        mageSet.add(m1);
        mageSet.add(m2);
        mageSet.add(m3);
        mageSet.add(m4);
        mageSet.add(m5);
        mageSet.add(m6);
        mageSet.add(m7);
        mageSet.add(m8);
        mageSet.add(m9);
        mageSet.add(m10);

       displayMageSet(mageSet, 0);
       Map<String, Integer> statistics2 = m5.getStatistics();
       System.out.println(statistics2);
    }
    static public void displayMageSet(Set<Mage> mageSet, int indentationLevel) {
        if (mageSet == null) return;
        String indentationString = createIndentationString(indentationLevel);
        for (Mage currentMage : mageSet) {
            Set<Mage> currentMageApprentices = currentMage.getApprentices();
            System.out.println(indentationString + currentMage);
            displayMageSet(currentMageApprentices, indentationLevel + 1);
        }
    }
    static public String createIndentationString(int indentationLevel) {
        String outputString = indentationLevel == 0 ? "⌘\t" : "\t";
        for (int i = 0; i < indentationLevel; i++)
            outputString = outputString.concat("\t");
        return outputString;
    }
}
