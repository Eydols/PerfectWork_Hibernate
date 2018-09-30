package controllers;

import db.DataHelper;
import entity.LeftMenu;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean(eager = true)
@ApplicationScoped
public class LeftMenuController implements Serializable {
    
    public List<LeftMenu> leftList;
    
    public LeftMenuController() {
        fillLeftMenu();
    }

    public List<LeftMenu> getLeftList() {
        return leftList;
    }

    private void fillLeftMenu() {
        leftList = DataHelper.getInstance().getLeftMenu();
    }
    }

