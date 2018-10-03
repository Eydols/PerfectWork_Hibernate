package enums;

public enum SearchType {

    ALL_FIRM(0,""),
    PERFECT(1,"Перфектподряд"),
    KSR(2,"КлассикСтройРемонт");

    private int id;
    private String firm_name;

    SearchType(int id, String f) {
        this.id = id;
        firm_name = f;
    }

     public int getId() {
        return id;
    }
     
    public String getFirmName() {
        return firm_name;
    }
}
