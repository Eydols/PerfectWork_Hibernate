package entity;
// Generated 04.10.2018 19:07:38 by Hibernate Tools 4.3.1



/**
 * Passport generated by hbm2java
 */
public class Passport  implements java.io.Serializable {


     private String image;
     private Integer manId;
     private String dateOkonch;
     private String content;

    public Passport() {
    }

	
    public Passport(String image) {
        this.image = image;
    }
    public Passport(String image, Integer manId, String dateOkonch, String content) {
       this.image = image;
       this.manId = manId;
       this.dateOkonch = dateOkonch;
       this.content = content;
    }
   
    public String getImage() {
        return this.image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    public Integer getManId() {
        return this.manId;
    }
    
    public void setManId(Integer manId) {
        this.manId = manId;
    }
    public String getDateOkonch() {
        return this.dateOkonch;
    }
    
    public void setDateOkonch(String dateOkonch) {
        this.dateOkonch = dateOkonch;
    }
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }




}


