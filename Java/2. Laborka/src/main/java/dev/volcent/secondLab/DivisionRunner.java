package dev.volcent.secondLab;

import java.util.ArrayList;
import java.util.List;

public class DivisionRunner implements Runnable {
    private boolean isWorking;
    final private OperationManager operationManager;
    final private ResultManager resultManager;

    public DivisionRunner(OperationManager operationManager, ResultManager resultManager) {
        this.operationManager = operationManager;
        this.resultManager = resultManager;
        this.isWorking = true;
    }

    public void stop() {
        this.isWorking = false;
    }

    @Override
    public void run() {
        while (this.isWorking) {
            long operation = this.operationManager.getOperation();
            List<Long> results = getDivisorsOf(operation);
            this.resultManager.addResults(operation, results);
        }
    }

    public static List<Long> getDivisorsOf(long number) {
        if (number == 0)
            return new ArrayList<>();
        List<Long> divisors = new ArrayList<>();
        for (long pos_divisor = 1; pos_divisor <= number; pos_divisor += 1) {
            if (number % pos_divisor == 0)
                divisors.add(pos_divisor);
        }
        return divisors;
    }
}
