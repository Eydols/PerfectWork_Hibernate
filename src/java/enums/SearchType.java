package enums;

public enum SearchType {

    ALL_FIRM(0,""),
    PERFECT(1,"Перфектподряд"),
    KSR(2,"КлассикСтройРемонт");

    private long id;
    private String firm_name;

    SearchType(long id, String f) {
        this.id = id;
        firm_name = f;
    }

     public long getId() {
        return id;
    }
     
    public String getFirmName() {
        return firm_name;
    }
}
