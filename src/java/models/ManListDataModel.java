package models;

import beans.Pager;
import db.DataHelper;
import entity.Man;
import java.util.List;
import java.util.Map;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class ManListDataModel extends LazyDataModel<Man> {

    private List<Man> manList;
    private DataHelper dataHelper = DataHelper.getInstance();
    private Pager pager = Pager.getInstance();

    public ManListDataModel() {
    }

    @Override
    public Man getRowData(String rowKey) {
        for (Man man : manList) {
            if (man.getId() == Long.valueOf(rowKey).intValue()) {
                return man;
            }
        }
        return null;
    }

    @Override
    public Object getRowKey(Man man) {
        return man.getId();
    }

    @Override
    public List<Man> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
    
        pager.setFrom(first);
        pager.setTo(pageSize);
        
        dataHelper.populateList();
        
        this.setRowCount((int) pager.getTotalManCount());
        
        return pager.getList();
        
    }
}
