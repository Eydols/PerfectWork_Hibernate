package controllers;

import comparators.ListComparator;
import db.DataHelper;
import entity.SprFirm;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

@ManagedBean(eager = false)
@ApplicationScoped
public class FirmController implements Serializable, Converter {

    private List<SelectItem> selectItems = new ArrayList<SelectItem>();
    private Map<Long, SprFirm> map;
    private List<SprFirm> list;

    public FirmController() {
        map = new HashMap<Long, SprFirm>();
        list = DataHelper.getInstance().getAllFirm();
        Collections.sort(list, ListComparator.getInstance());

        for (SprFirm firm : list) {
            map.put(firm.getId(), firm);
            selectItems.add(new SelectItem(firm, firm.getFirm()));
            
        }
    }
// мы заполняем выпадающий список (ВС) объектами SelectItem, в которых соотносим сам объект(SprFirm firm) и его отображение в ВС (firm.getFirm());
// при построении страницы из каждого объекта SelectItem автоматически вытягивается firm.getFirm() и отображается на странице при просмотре ВС
// а из объекта (SprFirm firm) вытягивается его id (с помощью метода getAsString()), чтобы при выборе пользователем определнного пункта из ВС, 
// по этому id достать из map объект SprFirm firm (с помощью метода getAsObject()) и записать его в переменную sprFirmByFirmId управляемого бина Man

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    public List<SprFirm> getFirmList() {
        return list;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return map.get(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return Long.toString(((SprFirm) value).getId());
    }
}
