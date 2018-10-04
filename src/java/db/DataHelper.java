package db;

import entity.HibernateUtil;
import entity.LeftMenu;
import entity.Man;
import enums.SearchType;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DataHelper {

    private SessionFactory sessionFactory = null;
    private static DataHelper dataHelper;

    private DataHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static DataHelper getInstance() {
        return dataHelper == null ? new DataHelper() : dataHelper;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<Man> getAllMan() {

        List<Man> man = getSession().createCriteria(Man.class).list();

        return man;
    }

    public List<LeftMenu> getLeftMenu() {

        List<LeftMenu> leftMenu = getSession().createCriteria(LeftMenu.class).list();

        return leftMenu;
    }

    public List<Man> getManBySurname(String manSurname) {

        List<Man> man = getSession().createCriteria(Man.class).add(Restrictions.ilike("surname", manSurname, MatchMode.ANYWHERE)).list();

        return man;
    }

    public List<Man> getManByString(String currentSearchString, SearchType selectedSearchType) {

        List<Man> man = getSession().createCriteria(Man.class).add(Restrictions.and(Restrictions.ilike("surname", currentSearchString, MatchMode.ANYWHERE), Restrictions.eq("sprFirmByFirmId.id", selectedSearchType.getId()))).list();

        return man;
    }

    public String getContent(String table, Integer id) throws ClassNotFoundException {

        Class classTable = Class.forName(table);
        String content = (String) getSession().createCriteria(classTable).setProjection(Projections.property("content")).add(Restrictions.eq("manId", id)).uniqueResult();

        return content;
    }

}
