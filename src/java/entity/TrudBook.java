package entity;
// Generated 01.10.2018 21:54:22 by Hibernate Tools 4.3.1



/**
 * TrudBook generated by hbm2java
 */
public class TrudBook  implements java.io.Serializable {


     private int manId;
     private Man man;
     private String image;
     private String content;

    public TrudBook() {
    }

	
    public TrudBook(Man man) {
        this.man = man;
    }
    public TrudBook(Man man, String image, String content) {
       this.man = man;
       this.image = image;
       this.content = content;
    }
   
    public int getManId() {
        return this.manId;
    }
    
    public void setManId(int manId) {
        this.manId = manId;
    }
    public Man getMan() {
        return this.man;
    }
    
    public void setMan(Man man) {
        this.man = man;
    }
    public String getImage() {
        return this.image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }




}


