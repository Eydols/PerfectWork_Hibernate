package db;

import beans.Pager;
import entity.HibernateUtil;
import entity.LeftMenu;
import entity.Man;
import enums.SearchType;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DataHelper {

    private SessionFactory sessionFactory = null;
    private static DataHelper dataHelper;
    private DetachedCriteria currentCriteria;
    private Pager currentPager;

    private DataHelper() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public static DataHelper getInstance() {
        if (dataHelper == null) {
            dataHelper = new DataHelper();
        }
        return dataHelper;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public List<LeftMenu> getLeftMenu() {

        List<LeftMenu> leftMenu = getSession().createCriteria(LeftMenu.class).list();

        return leftMenu;
    }
    
    public void getAllMan(Pager pager) {
        currentPager = pager;
        
        Criteria criteria = getSession().createCriteria(Man.class);
        Long total = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        currentPager.setTotalManCount(total);
        
        currentCriteria = DetachedCriteria.forClass(Man.class);
        runCurrentCriteria();
    }

    public void getManBySurname(String manSurname, Pager pager) {
        currentPager = pager;
        
        Criterion criterion = Restrictions.ilike("surname", manSurname, MatchMode.ANYWHERE);
        Criteria criteria = getSession().createCriteria(Man.class);
        Long total = (Long) criteria.add(criterion).setProjection(Projections.rowCount()).uniqueResult();
        currentPager.setTotalManCount(total);
        currentCriteria = DetachedCriteria.forClass(Man.class);
        currentCriteria.add(criterion);
        
        runCurrentCriteria();
    }

    public void getManByString(String currentSearchString, SearchType selectedSearchType, Pager pager) {
        currentPager = pager;
        
        Criterion criterion = Restrictions.and(Restrictions.ilike("surname", currentSearchString, MatchMode.ANYWHERE), Restrictions.eq("sprFirmByFirmId.id", selectedSearchType.getId()));
        
        Criteria criteria = getSession().createCriteria(Man.class, "man").createAlias("man.sprFirmByFirmId", "sprFirmByFirmId");
        Long total = (Long) criteria.add(criterion).setProjection(Projections.rowCount()).uniqueResult();
        currentPager.setTotalManCount(total);
        
        currentCriteria = DetachedCriteria.forClass(Man.class, "man").createAlias("man.sprFirmByFirmId", "sprFirmByFirmId");
        currentCriteria.add(criterion);
        
        runCurrentCriteria();
    }

    public String getContent(String table, Integer id) throws ClassNotFoundException {
        Class classTable = Class.forName(table);
        
        Criteria criteria = getSession().createCriteria(classTable);
        criteria.setProjection(Projections.property("content"));
        criteria.add(Restrictions.eq("manId", id));
        return (String) criteria.uniqueResult();
        // до этого все было в одной строке
        // String content = (String) getSession().createCriteria(classTable).setProjection(Projections.property("content")).add(Restrictions.eq("manId", id)).uniqueResult();
    }
    
    public void runCurrentCriteria() {
    Criteria criteria = currentCriteria.addOrder(Order.asc("surname")).getExecutableCriteria(getSession());
    List<Man> list = criteria.setFirstResult(currentPager.getFrom()).setMaxResults(currentPager.getTo()).list();
    currentPager.setList(list);
    }

}
