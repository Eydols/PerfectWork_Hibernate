package controllers;

import beans.Pager;
import entity.Man;
import db.DataHelper;
import enums.SearchType;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@ManagedBean(eager = true)
@SessionScoped
public class ManListController implements Serializable {

    // критерии поиска
    private String currentSearchString; // хранит поисковую строку
    private SearchType selectedSearchType = SearchType.ALL_FIRM; // хранит выбранный тип поиска, по умолчанию - все организации
    private Pager<Man> pager = new Pager<Man>();

    private boolean editModeView = false; // отображение режима редактирования

    public ManListController() {
        fillManAll();
    }

//<editor-fold defaultstate="collapsed" desc="запросы в БД">
    private void fillManAll() {
        DataHelper.getInstance().getAllMan(pager);
    }

    public String fillManBySearch() {

        if (selectedSearchType == SearchType.KSR || selectedSearchType == SearchType.PERFECT) { // потом можно попробовать использовать оператор switch
            DataHelper.getInstance().getManByString(currentSearchString, selectedSearchType, pager);
        } else {
            DataHelper.getInstance().getManBySurname(currentSearchString, pager);
        }
        return "man";
    }

    public void updateMan() { // обновляет измененные данные сотудников после редактирования
        DataHelper.getInstance().update();
        cancelEditMode();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="режим редактирования">
    public void showEdit() {
        editModeView = true;
    }

    public void cancelEditMode() { // выполнятся при нажатии кнопки Отмена в режиме редактирования на странице man.xhtml
        editModeView = false;
        for (Man man : pager.getList()) {
            man.setEdit(false);
        }
    }
//</editor-fold>

    public void searchStringChanged(ValueChangeEvent e) { //сохраняет символы, введенные в поисковую строку, при переключении языка
        currentSearchString = (String) e.getNewValue();
    }

    public void searchTypeChanged(ValueChangeEvent e) { // сохраняет выбранный тип поиска при переключении языка
        selectedSearchType = (SearchType) e.getNewValue();
    }

    public void changeManCountOnPage(ValueChangeEvent e) { //выполняется при выборе (из выпадающего списка) пользователем количества отображаемых на одной странице сотрудников 
        cancelEditMode();
        pager.setManCountOnPage(Integer.valueOf(e.getNewValue().toString()).intValue());
        pager.setSelectedPageNumber(1);
        DataHelper.getInstance().runManListCriteria();
    }
    
    public void selectPage() { // отрабатывает после нажатия на к-л страницу в постраничности
        cancelEditMode();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        pager.setSelectedPageNumber(Integer.valueOf(params.get("page_number")));
        DataHelper.getInstance().runManListCriteria();
    }
    
    //<editor-fold defaultstate="collapsed" desc="геттеры/сеттеры">
    public SearchType getSelectedSearchType() {
        return selectedSearchType;
    }

    public String getSelectedSearchString() {
        return currentSearchString;
    }

    public void setSelectedSearchString(String currentSearchString) {
        this.currentSearchString = currentSearchString;
    }

    public void setSelectedSearchType(SearchType selectedSearchType) {
        this.selectedSearchType = selectedSearchType;
    }

    public boolean isEditModeView() {
        return editModeView;
    }

    public void setEditModeView(boolean editModeView) {
        this.editModeView = editModeView;
    }
    
        public Pager<Man> getPager() {
        return pager;
    }
//</editor-fold>
}
