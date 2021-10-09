import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.LongAdder;

public class Main {
    static Random random = new Random();

    public static void main(String[] args) {

        ThreadGroup threadGroup = new ThreadGroup("calculation of amount");
        int countStore = 3;
        LongAdder totalSum = new LongAdder();

        for (int i = 0; i < countStore; i++) {
            Runnable runnable = () -> {
                for (int sum : createAnArrayWithRandomNumbers(30)) {
                    totalSum.add(sum);
                    //имитация работы
                    try {
                        Thread.sleep(random.nextInt(300));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            new Thread(threadGroup, runnable).start();
        }

        Runnable mainRunnable = () -> {
            while (threadGroup.activeCount() > 0) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            DecimalFormat dF = new DecimalFormat("###,###,###,###");
            System.out.println(String.format("Общая сумма %s ₽", dF.format(totalSum.sum())));
        };

        Thread mainThread = new Thread(mainRunnable);
        mainThread.start();
    }

    private static ArrayList<Integer> createAnArrayWithRandomNumbers(int countElements) {
        ArrayList<Integer> array = new ArrayList<>();
        for (int i = 0; i < countElements; i++) {
            array.add(random.nextInt(Integer.MAX_VALUE));
        }
        return array;
    }
}
