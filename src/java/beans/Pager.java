package beans;

import java.util.ArrayList;
import java.util.List;

public class Pager<T> {

    private int selectedPageNumber = 1; // выбранный номер страницы в постраничной навигации
    private int manCountOnPage = 5; // количество сотрудников, отображаемое на одной странице
    private long totalManCount; // общее количество найденных сотрудников
    private List<T> list;

    public Pager() {
    
    }
    
    public int getFrom() {
        return selectedPageNumber * manCountOnPage - manCountOnPage; // с какого по счету сотрудника показывать из полученной коллекции
    }
    
    public int getTo() {
    return manCountOnPage;
    }
    
    public List <T> getList() {
    return list;
    }
    
    public void setList(List<T> list) {
    this.list = list;
    }

    public int getSelectedPageNumber() {
        return selectedPageNumber;
    }

    public void setSelectedPageNumber(int selectedPageNumber) {
        this.selectedPageNumber = selectedPageNumber;
    }

    public long getTotalManCount() {
        return totalManCount;
    }

    public void setTotalManCount(long totalManCount) {
        this.totalManCount = totalManCount;
    }

    public int getManCountOnPage() {
        return manCountOnPage;
    }

    public void setManCountOnPage(int manCountOnPage) {
        this.manCountOnPage = manCountOnPage;
    } 
    
    private List<Integer> pageNumbers = new ArrayList<Integer>(); //количество страниц для постраничности
    
    public List<Integer> getPageNumbers() { //определяет количество страниц и создает соответствующую коллекцию целых чисел
    int pageCount = 0; //количество страниц
        if (totalManCount == 0) {
            pageCount = 0;
        } else if (totalManCount % manCountOnPage == 0) {
            pageCount = (int) totalManCount / manCountOnPage;
        } else {
            pageCount = (int) totalManCount / manCountOnPage + 1;
        }

        pageNumbers.clear();
        for (int i = 1; i <= pageCount; i++) {
            pageNumbers.add(i);
        }
        return pageNumbers;
    }
    
    
    

}
