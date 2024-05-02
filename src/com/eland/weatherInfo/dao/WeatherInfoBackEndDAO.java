package com.eland.weatherInfo.dao;

import com.eland.weatherInfo.formbean.WeatherInfoForm;
import com.eland.weatherInfo.model.WeatherInfoEntity;
import com.eland.weatherInfo.util.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WeatherInfoBackEndDAO {
    private static WeatherInfoBackEndDAO weatherInfoBackEndDAO = new WeatherInfoBackEndDAO();
    private SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
    private Session session;
    private Transaction tx;

    public static WeatherInfoBackEndDAO getInstance() {
        return weatherInfoBackEndDAO;
    }

    public List<WeatherInfoEntity> queryWeatherInfos(WeatherInfoForm InfoForm, String sortColumn, String sorted) {
        session = sessionFactory.getCurrentSession();
        tx = session.beginTransaction();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<WeatherInfoEntity> query = criteriaBuilder.createQuery(WeatherInfoEntity.class);
        Root<WeatherInfoEntity> root = query.from(WeatherInfoEntity.class);
        List<Predicate> conditions = new ArrayList<>();
        if (InfoForm.getCity() != ""&&null!=InfoForm.getCity()) {
            Predicate city = criteriaBuilder.equal(root.get("city"), InfoForm.getCity());
            conditions.add(city);
        }
        if (null != InfoForm.getDate()) {
            Predicate date = criteriaBuilder.equal(root.get("date"), InfoForm.getDate());
            conditions.add(date);
        }
        if (0 != InfoForm.getHigh()) {
            Predicate high = criteriaBuilder.greaterThan(root.get("high"), InfoForm.getHigh());

            conditions.add(high);
        }
        if (0 != InfoForm.getLow()) {
            Predicate low = criteriaBuilder.lessThan(root.get("low"), InfoForm.getLow());
            conditions.add(low);
        }
        if (0 != InfoForm.getFl()) {
            Predicate fl = criteriaBuilder.equal(root.get("fl"), InfoForm.getFl());
            conditions.add(fl);
        }
        if ("" != InfoForm.getAqi()&&null!=InfoForm.getAqi()) {
            Predicate aqi = criteriaBuilder.greaterThan(root.get("aqi"), InfoForm.getAqi());
            conditions.add(aqi);
        }
        if (conditions.size() > 0) {
            Predicate restriction = null;
            switch (conditions.size()) {
                case 1: {
                    restriction = conditions.get(0);
                    break;
                }
                case 2: {
                    restriction = criteriaBuilder.and(conditions.get(0), conditions.get(1));
                    break;
                }
                case 3: {
                    restriction = criteriaBuilder.and(conditions.get(0), conditions.get(1), conditions.get(2));
                    break;
                }
                case 4: {
                    restriction = criteriaBuilder.and(conditions.get(0), conditions.get(1), conditions.get(2), conditions.get(3));
                    break;
                }
                case 5: {
                    restriction = criteriaBuilder.and(conditions.get(0), conditions.get(1), conditions.get(2), conditions.get(3), conditions.get(4));
                    break;
                }
                case 6: {
                    restriction = criteriaBuilder.and(conditions.get(0), conditions.get(1), conditions.get(2), conditions.get(3), conditions.get(4), conditions.get(5));
                    break;
                }
            }
            query.where(restriction);
        }
        if (null == sortColumn || "" == sortColumn) {
            sortColumn = "id";
        }
        List<Order> orderList = new ArrayList<>();
        if (null == sorted || "asc".equals(sorted)) {
            orderList.add(criteriaBuilder.asc(root.get(sortColumn)));
        } else if (sorted.equals("desc")) {
            orderList.add(criteriaBuilder.desc(root.get(sortColumn)));
        }
        query.orderBy(orderList);
        List<WeatherInfoEntity> dbWeatherInfo = session.createQuery(query).getResultList();
        tx.commit();
        session.close();
        return dbWeatherInfo;
    }
    public  List<WeatherInfoEntity>  queryAllWeather(){
        session = sessionFactory.getCurrentSession();
        tx = session.beginTransaction();
        Query queryData = null;
        queryData = session.createQuery("from WeatherInfoEntity");
        List<Query> queryWeatherInfo = queryData.getResultList();
        List<WeatherInfoEntity> weatherInfoList=new ArrayList<>();
        Iterator iterator = queryWeatherInfo.iterator();
        while (iterator.hasNext()) {
            WeatherInfoEntity weatherInfo = (WeatherInfoEntity) iterator.next();
            weatherInfoList.add(weatherInfo);
        }

        tx.commit();
        session.close();
        return weatherInfoList;
    }
}
