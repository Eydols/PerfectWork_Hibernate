package entity;
// Generated 04.10.2018 19:07:38 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * SprFirm generated by hbm2java
 */
public class SprFirm  implements java.io.Serializable {


     private int id;
     private String firm;
     private Set mansForFirmId = new HashSet(0);
     private Set mansForFirm2Id = new HashSet(0);

    public SprFirm() {
    }

	
    public SprFirm(int id) {
        this.id = id;
    }
    public SprFirm(int id, String firm, Set mansForFirmId, Set mansForFirm2Id) {
       this.id = id;
       this.firm = firm;
       this.mansForFirmId = mansForFirmId;
       this.mansForFirm2Id = mansForFirm2Id;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getFirm() {
        return this.firm;
    }
    
    public void setFirm(String firm) {
        this.firm = firm;
    }
    public Set getMansForFirmId() {
        return this.mansForFirmId;
    }
    
    public void setMansForFirmId(Set mansForFirmId) {
        this.mansForFirmId = mansForFirmId;
    }
    public Set getMansForFirm2Id() {
        return this.mansForFirm2Id;
    }
    
    public void setMansForFirm2Id(Set mansForFirm2Id) {
        this.mansForFirm2Id = mansForFirm2Id;
    }




}


