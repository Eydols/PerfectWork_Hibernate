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
        sessionFactory.getCurrentSession().beginTransaction();
        List<Man> man = getSession().createCriteria(Man.class).list();
        sessionFactory.getCurrentSession().getTransaction().commit();
        return man;
    }

    public List<LeftMenu> getLeftMenu() {
        sessionFactory.getCurrentSession().beginTransaction();
        List<LeftMenu> leftMenu = getSession().createCriteria(LeftMenu.class).list();
        sessionFactory.getCurrentSession().getTransaction().commit();
        return leftMenu;
    }

    public List<Man> getManBySurname(String manSurname) {
        sessionFactory.getCurrentSession().beginTransaction();
        List<Man> man = getSession().createCriteria(Man.class).add(Restrictions.ilike("surname", manSurname, MatchMode.ANYWHERE)).list();
        sessionFactory.getCurrentSession().getTransaction().commit();
        return man;
    }

    public List<Man> getManByString(String currentSearchString, SearchType selectedSearchType) {
        sessionFactory.getCurrentSession().beginTransaction();
        List<Man> man = getSession().createCriteria(Man.class).add(Restrictions.and(Restrictions.ilike("surname", currentSearchString, MatchMode.ANYWHERE), Restrictions.eq("sprFirmByFirmId.id", selectedSearchType.getId()))).list();
        sessionFactory.getCurrentSession().getTransaction().commit();
        return man;
    }

    public String getContent(String table, Integer id) throws ClassNotFoundException {
        sessionFactory.getCurrentSession().beginTransaction();
        Class classTable = Class.forName(table);
        String content = (String) getSession().createCriteria(classTable).setProjection(Projections.property("content")).add(Restrictions.eq("manId", id)).uniqueResult();
        sessionFactory.getCurrentSession().getTransaction().commit();
        return content;
    }

}
