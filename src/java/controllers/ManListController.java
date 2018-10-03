package controllers;

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

    // коллекции
    private List<Man> currentManList; // текущий список сотрудников для отображения
    private ArrayList<Integer> pageNumbersList = new ArrayList<Integer>(); //количество страниц для постраничности

    // критерии поиска
    private String currentSearchString; // хранит поисковую строку
    private SearchType selectedSearchType = SearchType.ALL_FIRM; // хранит выбранный тип поиска, по умолчанию - все организации
    private String currentSqlNoLimit; // последний выполненный SQL запрос без оператора limit

    // для постраничности
    private int totalManCount; // общее количество найденных сотрудников (необходи)
    private int manCountOnPage = 2; // количество сотрудников, отображаемое на одной странице
    private long selectedPageNumber = 1; // выбранный номер страницы в постраничной навигации
    private int pageCount; // количество страниц

    private boolean editModeView = false; // отображение режима редактирования

    public ManListController() {
        fillManAll();
    }

//<editor-fold defaultstate="collapsed" desc="запросы в БД">
    private void fillManAll() {
        currentManList = DataHelper.getInstance().getAllMan();
    }

    public void fillManBySurname() {
        currentManList = DataHelper.getInstance().getManBySurname(currentSearchString);
    }

    public String fillManBySearch() {

        if (currentSearchString.trim().length() == 0) {
            fillManAll();
            return "man";
        }

        if (selectedSearchType == SearchType.KSR || selectedSearchType == SearchType.PERFECT) { // потом можно попробовать использовать оператор switch
            currentManList = DataHelper.getInstance().getManByString(currentSearchString, selectedSearchType);
        } else {
            currentManList = DataHelper.getInstance().getManBySurname(currentSearchString);
        }
        return "man";
    }

    public void updateMan() { // обновляет измененные данные сотудников после редактирования
        cancelEditMode();
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="для постраничности">
    // методы для постраничности
    private void fillPageNumbers(long totalBooksCount, int booksOnPage) { //определяет количество страниц и создает соответствующую коллекцию целых чисел

        pageCount = 0; //количество страниц
        if (totalBooksCount == 0) {
            pageCount = 0;
        } else if (totalBooksCount % booksOnPage == 0) {
            pageCount = (int) totalBooksCount / booksOnPage;
        } else {
            pageCount = (int) totalBooksCount / booksOnPage + 1;
        }

        pageNumbersList.clear();
        for (int i = 1; i <= pageCount; i++) {
            pageNumbersList.add(i);
        }
    }

    public void selectPage() { // отрабатывает после нажатия на к-л страницу в постраничности
        cancelEditMode();
        imitateLoading();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selectedPageNumber = Integer.valueOf(params.get("page_number"));
        //fillManBySQL(currentSqlNoLimit);
    }
//</editor-fold>

    private void imitateLoading() { // имитация загрузки процесса
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ManListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void switchEditMode() {
        editModeView = !editModeView;
    }

    public void cancelEditMode() { // выполнятся при нажатии кнопки Отмена в режиме редактирования на странице man.xhtml
        editModeView = false;
        for (Man man : currentManList) {
            // man.setEdit(false);
        }
    }

    public void searchTypeChanged(ValueChangeEvent e) { // сохраняет выбранный тип поиска при переключении языка
        selectedSearchType = (SearchType) e.getNewValue();
    }

    public void searchStringChanged(ValueChangeEvent e) { //сохраняет символы, введенные в поисковую строку, при переключении языка
        currentSearchString = (String) e.getNewValue();
    }

    public void changeManCountOnPage(ValueChangeEvent e) { //выполняется при выборе (из выпадающего списка) пользователем количества отображаемых на одной странице сотрудников 
        imitateLoading();
        cancelEditMode();
        manCountOnPage = Integer.valueOf(e.getNewValue().toString()).intValue();
        selectedPageNumber = 1;
        //fillManBySQL(currentSqlNoLimit);

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

    public void setCurrentManList(ArrayList<Man> currentManList) {
        this.currentManList = currentManList;
    }

    public List<Man> getCurrentManList() {
        return currentManList;
    }

    public int getTotalManCount() {
        return totalManCount;
    }

    public void setTotalManCount(int totalManCount) {
        this.totalManCount = totalManCount;
    }

    public int getManCountOnPage() {
        return manCountOnPage;
    }

    public void setManCountOnPage(int manCountOnPage) {
        this.manCountOnPage = manCountOnPage;
    }

    public long getSelectedPageNumber() {
        return selectedPageNumber;
    }

    public void setSelectedPageNumber(long selectedPageNumber) {
        this.selectedPageNumber = selectedPageNumber;
    }

    public ArrayList<Integer> getPageNumbersList() {
        return pageNumbersList;
    }

    public void setPageNumbersList(ArrayList<Integer> pageNumbersList) {
        this.pageNumbersList = pageNumbersList;
    }

    public boolean isEditModeView() {
        return editModeView;
    }

    public void setEditModeView(boolean editModeView) {
        this.editModeView = editModeView;
    }
//</editor-fold>
}
