package entity;
// Generated 02.10.2018 21:28:39 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * SprDoljnost generated by hbm2java
 */
public class SprDoljnost  implements java.io.Serializable {


     private int id;
     private String doljnost;
     private Set mansForDoljnostId = new HashSet(0);
     private Set mansForDoljnost2Id = new HashSet(0);

    public SprDoljnost() {
    }

	
    public SprDoljnost(int id, String doljnost) {
        this.id = id;
        this.doljnost = doljnost;
    }
    public SprDoljnost(int id, String doljnost, Set mansForDoljnostId, Set mansForDoljnost2Id) {
       this.id = id;
       this.doljnost = doljnost;
       this.mansForDoljnostId = mansForDoljnostId;
       this.mansForDoljnost2Id = mansForDoljnost2Id;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getDoljnost() {
        return this.doljnost;
    }
    
    public void setDoljnost(String doljnost) {
        this.doljnost = doljnost;
    }
    public Set getMansForDoljnostId() {
        return this.mansForDoljnostId;
    }
    
    public void setMansForDoljnostId(Set mansForDoljnostId) {
        this.mansForDoljnostId = mansForDoljnostId;
    }
    public Set getMansForDoljnost2Id() {
        return this.mansForDoljnost2Id;
    }
    
    public void setMansForDoljnost2Id(Set mansForDoljnost2Id) {
        this.mansForDoljnost2Id = mansForDoljnost2Id;
    }




}


