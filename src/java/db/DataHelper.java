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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class DataHelper {

    private Pager pager = Pager.getInstance();
    private SessionFactory sessionFactory = null;
    private static DataHelper dataHelper;
    private DetachedCriteria manCountCriteria;
    private DetachedCriteria manListCriteria;

    private DataHelper() {

        prepareCriterias();
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
        prepareCriterias();
        populateList();
    }

    public void getManBySurname(String manSurname, Pager pager) {

        Criterion criterion = Restrictions.ilike("surname", manSurname, MatchMode.ANYWHERE);

        prepareCriterias(criterion);
        populateList();
    }

    public void getManByString(String currentSearchString, SearchType selectedSearchType, Pager pager) {

        Criterion criterion = Restrictions.or(
                Restrictions.and(
                        Restrictions.ilike("surname", currentSearchString, MatchMode.ANYWHERE),
                        Restrictions.eq("sprFirmByFirmId.id", selectedSearchType.getId())),
                Restrictions.and(
                        Restrictions.ilike("surname", currentSearchString, MatchMode.ANYWHERE),
                        Restrictions.eq("sprFirmByFirm2Id.id", selectedSearchType.getId()))
        );
        prepareCriterias(criterion);
        populateList();

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
        Criteria criteria = manCountCriteria.getExecutableCriteria(getSession());
        Long total = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
        pager.setTotalManCount(total);
    }

    public void runManListCriteria() {
        Criteria criteria = manListCriteria.addOrder(Order.asc("surname")).getExecutableCriteria(getSession());
        criteria.setFirstResult(pager.getFrom()).setMaxResults(pager.getTo()).list();
        List<Man> list = criteria.list();
        pager.setList(list);
    }

    public void updateMan(Man man) {
        Query query = getSession().createQuery("update Man set id = :id, "
                + " surname = :surname, "
                + " name = :name, "
                + " otchestvo = :otchestvo, "
                + " birthDate = :birthDate, "
                + " photo = :photo, "
                + " sprDoljnostByDoljnostId = :sprDoljnostByDoljnostId, "
                + " sprDoljnostByDoljnost2Id = :sprDoljnostByDoljnost2Id, "
                + " sprFirmByFirmId = :sprFirmByFirmId, "
                + " sprFirmByFirm2Id = :sprFirmByFirm2Id "
                + " where id = :id");

        query.setParameter("id", man.getId());
        query.setParameter("surname", man.getSurname());
        query.setParameter("name", man.getName());
        query.setParameter("otchestvo", man.getOtchestvo());
        query.setParameter("birthDate", man.getBirthDate());
        query.setParameter("photo", man.getPhoto());
        query.setParameter("sprDoljnostByDoljnostId", man.getSprDoljnostByDoljnostId());
        query.setParameter("sprDoljnostByDoljnost2Id", man.getSprDoljnostByDoljnost2Id());
        query.setParameter("sprFirmByFirmId", man.getSprFirmByFirmId());
        query.setParameter("sprFirmByFirm2Id", man.getSprFirmByFirm2Id());

        int result = query.executeUpdate();
    }

    public void deleteMan(Man man) {
        Session session = getSession();
        session.delete(man);
    }

    public void addMan(Man man) {
        Session session = getSession();
        session.save(man);
    }

    private void prepareCriterias(Criterion criterion) {
        manListCriteria = DetachedCriteria.forClass(Man.class, "m");
        createAliases(manListCriteria);
        manListCriteria.add(criterion);

        manCountCriteria = DetachedCriteria.forClass(Man.class, "m");
        createAliases(manCountCriteria);
        manCountCriteria.add(criterion);

    }

    private void prepareCriterias() {
        manListCriteria = DetachedCriteria.forClass(Man.class, "m");
        createAliases(manListCriteria);

        manCountCriteria = DetachedCriteria.forClass(Man.class, "m");
        createAliases(manCountCriteria);
    }

    private void createAliases(DetachedCriteria criteria) {
        criteria.createAlias("m.sprFirmByFirmId", "sprFirmByFirmId");
        criteria.createAlias("m.sprFirmByFirm2Id", "sprFirmByFirm2Id");
        criteria.createAlias("m.sprDoljnostByDoljnostId", "sprDoljnostByDoljnostId");
        criteria.createAlias("m.sprDoljnostByDoljnost2Id", "sprDoljnostByDoljnost2Id");
    }

    public void populateList() {
        runCountCriteria();
        runManListCriteria();
    }

}
