package db;

import beans.Pager;
import entity.HibernateUtil;
import entity.LeftMenu;
import entity.Man;
import entity.SprDoljnost;
import entity.SprFirm;
import enums.SearchType;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DataHelper {

    private SessionFactory sessionFactory = null;
    private static DataHelper dataHelper;
    private DetachedCriteria manCountCriteria;
    private DetachedCriteria manListCriteria;
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
        sessionFactory.getCurrentSession().beginTransaction();
        List<LeftMenu> leftMenu = getSession().createCriteria(LeftMenu.class).list();
        sessionFactory.getCurrentSession().getTransaction().commit();
        return leftMenu;
    }

    public List<SprFirm> getAllFirm() {
        return getSession().createCriteria(SprFirm.class).list();
    }

    public List<SprDoljnost> getAllDoljnost() {
        return getSession().createCriteria(SprDoljnost.class).list();
    }

    public void getAllMan(Pager pager) {
        currentPager = pager;

        createManCountCriteria();
        runCountCriteria();

        createManListCriteria();
        runManListCriteria();
    }

    public void getManBySurname(String manSurname, Pager pager) {
        currentPager = pager;

        Criterion criterion = Restrictions.ilike("surname", manSurname, MatchMode.ANYWHERE);

        createManCountCriteria(criterion);
        runCountCriteria();

        createManListCriteria(criterion);
        runManListCriteria();
    }

    public void getManByString(String currentSearchString, SearchType selectedSearchType, Pager pager) {
        currentPager = pager;

        Criterion criterion = Restrictions.or(Restrictions.and(Restrictions.ilike("surname", currentSearchString, MatchMode.ANYWHERE), Restrictions.eq("sprFirmByFirmId.id", selectedSearchType.getId())), Restrictions.and(Restrictions.ilike("surname", currentSearchString, MatchMode.ANYWHERE), Restrictions.eq("sprFirmByFirm2Id.id", selectedSearchType.getId())));

        createManCountCriteria(criterion);
        runCountCriteria();

        createManListCriteria(criterion);
        runManListCriteria();

        // до этого было так
        //Criteria criteria = getSession().createCriteria(Man.class, "man").createAlias("man.sprFirmByFirmId", "sprFirmByFirmId");
        //Long total = (Long) criteria.add(criterion).setProjection(Projections.rowCount()).uniqueResult();
        //currentPager.setTotalManCount(total);
        //currentCriteria = DetachedCriteria.forClass(Man.class, "man").createAlias("man.sprFirmByFirmId", "sprFirmByFirmId");
        //currentCriteria.add(criterion);
        //runCurrentCriteria();
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

    private void runCountCriteria() {
        //sessionFactory.getCurrentSession().beginTransaction();
        Criteria criteria = manCountCriteria.getExecutableCriteria(getSession());
        Long total = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        currentPager.setTotalManCount(total);
        //sessionFactory.getCurrentSession().getTransaction().commit();
    }

    public void runManListCriteria() {
        Criteria criteria = manListCriteria.addOrder(Order.asc("surname")).getExecutableCriteria(getSession());
        List<Man> list = criteria.setFirstResult(currentPager.getFrom()).setMaxResults(currentPager.getTo()).list();
        currentPager.setList(list);
    }

    public void update() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        for (Object object : currentPager.getList()) {
            Man man = (Man) object;
            if (man.getEdit()) {
                session.update(man);
            }
        }
        transaction.commit();
        session.flush();
        session.close();
    }
    
    private void createManCountCriteria(Criterion criterion) {
    manCountCriteria = DetachedCriteria.forClass(Man.class, "m");
    manCountCriteria.add(criterion);
    }
    
    private void createManCountCriteria() {
    manCountCriteria = DetachedCriteria.forClass(Man.class, "m");
    }
    
    private void createManListCriteria(Criterion criterion) {
    manListCriteria = DetachedCriteria.forClass(Man.class, "m");
    manListCriteria.add(criterion);
    createAliases();
    }
    
    private void createManListCriteria() {
    manListCriteria = DetachedCriteria.forClass(Man.class, "m");
    createAliases();
    }
    
    private void createAliases() {
    manListCriteria.createAlias("m.sprFirmByFirmId", "sprFirmByFirmId");
    manListCriteria.createAlias("m.sprFirmByFirm2Id", "sprFirmByFirm2Id");
    manListCriteria.createAlias("m.sprDoljnostByDoljnostId", "sprDoljnostByDoljnostId");
    manListCriteria.createAlias("m.sprDoljnostByDoljnost2Id", "sprDoljnostByDoljnost2Id");
    }
    
    public void refreshList() {
    runCountCriteria();
    runManListCriteria();
    }

}
