package beans;

import db.DataHelper;
import entity.Man;
import java.util.List;

public class Pager {

    public static Pager pager;

    private long totalManCount; // общее количество найденных сотрудников
    private Man selectedMan;
    private List<Man> list;
    private int from;
    private int to;

    public Pager() {  
    }

    public static Pager getInstance() {
        if (pager == null) {
            pager = new Pager();
        }
        return pager;
    }

    public long getTotalManCount() {
        return totalManCount;
    }

    public void setTotalManCount(long totalManCount) {
        this.totalManCount = totalManCount;
    }

    public Man getSelectedMan() {
        return selectedMan;
    }

    public void setSelectedMan(Man selectedMan) {
        this.selectedMan = selectedMan;
    }

    public List<Man> getList() {
        return list;
    }

    public void setList(List<Man> list) {
        this.list = list;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }



}
