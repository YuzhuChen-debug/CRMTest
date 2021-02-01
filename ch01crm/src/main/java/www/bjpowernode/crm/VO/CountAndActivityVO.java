package www.bjpowernode.crm.VO;


import java.util.List;

public class CountAndActivityVO<T> {
    private int count;
    private List<T> aList;

    public CountAndActivityVO() {
    }

    @Override
    public String toString() {
        return "CountAndActivityVO{" +
                "count=" + count +
                ", aList=" + aList +
                '}';
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getaList() {
        return aList;
    }

    public void setaList(List<T> aList) {
        this.aList = aList;
    }

    public CountAndActivityVO(int count, List<T> aList) {
        this.count = count;
        this.aList = aList;
    }
}

