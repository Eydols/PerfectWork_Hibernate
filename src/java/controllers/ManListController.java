package controllers;

import beans.Pager;
import entity.Man;
import db.DataHelper;
import enums.SearchType;
import java.io.Serializable;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import models.ManListDataModel;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;

@ManagedBean(eager = true)
@SessionScoped
public class ManListController implements Serializable {

    private Man selectedMan;
    private DataTable dataTable;

    private DataHelper dataHelper = DataHelper.getInstance();
    private LazyDataModel<Man> manListModel;

    // критерии поиска
    private String currentSearchString; // хранит поисковую строку
    private SearchType selectedSearchType = SearchType.ALL_FIRM; // хранит выбранный тип поиска, по умолчанию - все организации
    private Pager pager = Pager.getInstance();

    private boolean editModeView = false; // отображение режима редактирования

    public ManListController() {
        manListModel = new ManListDataModel();
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
        dataHelper.updateMan(selectedMan);
        cancelEditMode();
        dataHelper.populateList();

        ResourceBundle bundle = ResourceBundle.getBundle("nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(bundle.getString("updated")));

        dataTable.setFirst(calcSelectedPage());
    }

    public void deleteMan() {
        dataHelper.deleteMan(selectedMan);
        dataHelper.populateList();

        ResourceBundle bundle = ResourceBundle.getBundle("nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(bundle.getString("deleted")));
        
        dataTable.setFirst(calcSelectedPage());
    }
    
    private int calcSelectedPage() {
    int page = dataTable.getPage();// текущий номер страницы (индексация с нуля)
    
    int leftBound = pager.getTo()*(page-1);
    int rightBound = pager.getTo()*page;
    boolean goPrevPage = pager.getTotalManCount()>leftBound & pager.getTotalManCount() <= rightBound;
    if (goPrevPage)
    {
    page--;
    }
    return (page>0) ? page*pager.getTo() : 0;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="режим редактирования">
    public void switchEditMode() {
        editModeView = true;
    }

    public void cancelEditMode() { // выполнятся при нажатии кнопки Отмена в режиме редактирования на странице man.xhtml
        editModeView = false;
        RequestContext.getCurrentInstance().execute("PF('dlgEditMan').hide()");
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
        //pager.setManCountOnPage(Integer.valueOf(e.getNewValue().toString()).intValue());
        // pager.setSelectedPageNumber(1);
        DataHelper.getInstance().runManListCriteria();
    }

    public void selectPage() { // отрабатывает после нажатия на к-л страницу в постраничности
        cancelEditMode();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        //pager.setSelectedPageNumber(Integer.valueOf(params.get("page_number")));
        DataHelper.getInstance().runManListCriteria();
    }

    //<editor-fold defaultstate="collapsed" desc="геттеры/сеттеры">
    public DataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(DataTable dataTable) {
        this.dataTable = dataTable;
    }
    
    public Man getSelectedMan() {
        return selectedMan;
    }

    public void setSelectedMan(Man selectedMan) {
        this.selectedMan = selectedMan;
    }

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

    public LazyDataModel<Man> getManListModel() {
        return manListModel;
    }

    public void setManListModel(LazyDataModel<Man> manListModel) {
        this.manListModel = manListModel;
    }

    public Pager getPager() {
        return pager;
    }
//</editor-fold>
}
