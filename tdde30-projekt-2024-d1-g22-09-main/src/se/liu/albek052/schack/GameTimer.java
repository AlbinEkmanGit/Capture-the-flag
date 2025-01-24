package se.liu.albek052.schack;

/**
 * The class counts down from a given time its given when initialized
 */
public class GameTimer
{
    private int minute;
    private int second;

    public GameTimer(final int minute, final int second) {
        this.minute = minute;
        this.second = second;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    /**
     * Counts down every time called and returns true if done
     * @return
     */
    public boolean isTimerDone(){
        // count down
        second--;
        if (second < 0){
            second = 59;
            minute--;
            if (minute < 0){
                minute = 0;
                second = 0;
                return true;
            }
        }
        return false;
    }
}
