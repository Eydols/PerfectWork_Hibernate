package entity;
// Generated 28.09.2018 20:38:17 by Hibernate Tools 4.3.1



/**
 * DiplomSsuz generated by hbm2java
 */
public class DiplomSsuz  implements java.io.Serializable {


     private String image;
     private Integer manId;
     private String content;

    public DiplomSsuz() {
    }

	
    public DiplomSsuz(String image) {
        this.image = image;
    }
    public DiplomSsuz(String image, Integer manId, String content) {
       this.image = image;
       this.manId = manId;
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
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }




}


