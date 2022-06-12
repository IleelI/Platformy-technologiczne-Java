package dev.volcent.secondLab;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

public class ResultManager {
    final public Map<Long, List<Long>> resultsMap;
    private boolean workInProgress;

    public ResultManager() {
        this.resultsMap = new HashMap<>();
        this.workInProgress = false;
    }

    public Map<Long, List<Long>> getResultsMap() {
        return this.resultsMap;
    }

    public synchronized void addResults(long operation, List<Long> results) {
        while (workInProgress) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread interrupted!");
            }
        }
        this.workInProgress = true;
        this.resultsMap.put(operation, results);
        this.workInProgress = false;
        notifyAll();
    }
}
