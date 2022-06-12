package dev.volcent.secondLab;

import java.util.ArrayList;
import java.util.List;

public class OperationManager {
    final private List<Long> operations;
    private boolean workInProgress = false;

    public OperationManager() {
        this.operations = new ArrayList<>();
    }

    public synchronized void setOperation(long newOperation) {
        while (workInProgress) {
            try {
                wait();
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread has been interrupted");
                return;
            }
        }
        workInProgress = true;
        this.operations.add(newOperation);
        workInProgress = false;
        notifyAll();
    }

    public synchronized long getOperation() {
        while (operations.size() == 0 || workInProgress) {
            try {
                wait();
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread has been interrupted");
                return 0;
            }
        }
        workInProgress = true;
        long outputOperation = this.operations.remove(this.operations.size() - 1);
        workInProgress = false;
        notifyAll();
        return outputOperation;
    }
}
