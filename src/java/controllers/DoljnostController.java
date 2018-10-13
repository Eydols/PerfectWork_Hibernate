package controllers;

import comparators.ListComparator;
import db.DataHelper;
import entity.SprDoljnost;
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
public class DoljnostController implements Serializable, Converter {

    private List<SelectItem> selectItems = new ArrayList<SelectItem>();
    private Map<Long, SprDoljnost> map;
    private List<SprDoljnost> list;
    

    public DoljnostController() {
        map = new HashMap <Long, SprDoljnost>();
        list = DataHelper.getInstance().getAllDoljnost();
        Collections.sort(list, ListComparator.getInstance());

        for (SprDoljnost doljnost : list) {
            map.put(doljnost.getId(), doljnost);
            selectItems.add(new SelectItem(doljnost, doljnost.getDoljnost()));
        }
    }

    public List<SprDoljnost> getDoljnostList() {
        return list;
    }

    public List<SelectItem> getSelectItems() {
        return selectItems;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return map.get(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return Long.toString(((SprDoljnost) value).getId());
    }
}
