package www.bjpowernode.crm.VO;


public class CountAndActivityVO<T> {
    private int count;
    private T t ;

    public CountAndActivityVO(int count, T t) {
        this.count = count;
        this.t = t;
    }

    public CountAndActivityVO() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "CountAndActivityVO{" +
                "count=" + count +
                ", t=" + t +
                '}';
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
