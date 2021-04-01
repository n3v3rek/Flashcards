package timeCouting;

public class Timer implements Runnable{

    private double startTime;
    private final double timeToCountdown;                     //Seconds

    public Timer (double timeToCountdown)
    {
        this.startTime = 0;
        this.timeToCountdown = timeToCountdown * 1000;        //Konwersja na milisekundy
    }

    public boolean isEndOfTime() {
        return System.currentTimeMillis() >= this.startTime + this.timeToCountdown;
    }

    public static double replyTime(double startTime){
        return System.currentTimeMillis() - startTime;
    }

    public void printTime(double timeToBePrinted)
    {
        int minutes = 0;
        int seconds = 0;
        if ( timeToBePrinted >= 60000) {
            minutes = (int) (timeToBePrinted / 60000);
            timeToBePrinted = timeToBePrinted - (int)(timeToBePrinted / 60000) * 60000;
        }
        if ( timeToBePrinted >= 1000) {
            seconds = (int) (timeToBePrinted / 1000);
            timeToBePrinted = timeToBePrinted - (int)(timeToBePrinted / 1000) * 1000;
        }
        if (minutes > 9) {
            if (seconds > 9) {
                System.out.println(minutes + ":" +seconds);
            }
            else
            {
                System.out.println(minutes + ":0" +seconds);
            }
        }
        else
        {
            if (seconds > 9) {
                System.out.println("0" + minutes +":"+seconds);
            }
            else
            {
                System.out.println("0" + minutes + ":0"+seconds);
            }
        }
    }

    public void run() {
        this.startTime = System.currentTimeMillis();
        while (!isEndOfTime())
        {


            double timeToEnd = this.timeToCountdown - (System.currentTimeMillis() - this.startTime);
            if (timeToEnd % 1000 == 0) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

        }

    }

}

