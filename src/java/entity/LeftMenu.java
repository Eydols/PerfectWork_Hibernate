package entity;
// Generated 02.10.2018 21:28:39 by Hibernate Tools 4.3.1

import javax.faces.context.FacesContext;




/**
 * LeftMenu generated by hbm2java
 */
public class LeftMenu  implements java.io.Serializable {


     private Integer id;
     private String nameRu;
     private String nameEn;
     private String page;

    public LeftMenu() {
    }

	
    public LeftMenu(String nameRu) {
        this.nameRu = nameRu;
    }
    public LeftMenu(String nameRu, String nameEn, String page) {
       this.nameRu = nameRu;
       this.nameEn = nameEn;
       this.page = page;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNameRu() {
        return this.nameRu;
    }
    
    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }
    public String getNameEn() {
        return this.nameEn;
    }
    
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    public String getPage() {
        return this.page;
    }
    
    public void setPage(String page) {
        this.page = page;
    }
    
    public String getName() {
        
        if (FacesContext.getCurrentInstance().getViewRoot().getLocale().toString().equals("en")) {
            return nameEn;
        } else {
            return nameRu;
        }
    }




}


