package com.eland.weatherInfo.controller;

import com.eland.weatherInfo.model.WeatherInfoEntity;
import com.eland.weatherInfo.service.WeatherInfoBackEndService;
import com.eland.weatherInfo.util.APILimiter;
import com.eland.weatherInfo.util.HibernateUtils;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@MultipartConfig
public class WeatherInfoBackEndController extends HttpServlet {
    private static Logger logger = LogManager.getLogger(WeatherInfoBackEndController.class.getName());
    private SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
    private WeatherInfoBackEndService weatherInfoBackEndService = WeatherInfoBackEndService.getInstance();
    private APILimiter apiLimiter = new APILimiter();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGetAndPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGetAndPost(req, resp);
    }

    protected void doGetAndPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        logger.debug(action);
        switch (action) {
            case "queryWeatherInfoByKey":
                // 使用者新增
                queryWeatherInfoByKey(req, resp);
                break;
            case "queryWeatherInfo":
                // 帳戶新增
                queryWeatherInfo(req, resp);
                break;
        }
    }

    protected void queryWeatherInfoByKey(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            apiLimiter.checkLimit(); // 检查API限制
            String city = req.getParameter("queryCity");
            String date = req.getParameter("queryDate");
            String high = req.getParameter("queryHigh");
            String low = req.getParameter("queryLow");
            String aqi = req.getParameter("queryAqi");
            String fl = req.getParameter("queryFl");
            String sortColumn = req.getParameter("column");
            String sorted = req.getParameter("sorted");
            List<WeatherInfoEntity> weatherInfoList = weatherInfoBackEndService.queryWeatherInfos(city, date, high, low, aqi, fl, sortColumn, sorted);
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            PrintWriter out = resp.getWriter();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("weatherInfo", weatherInfoList);
            out.println(jsonObject.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "API请求被限制");
            PrintWriter out = resp.getWriter();
            out.println("API请求被限制");
        }
    }

    protected void queryWeatherInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<WeatherInfoEntity> weatherInfoList = weatherInfoBackEndService.queryAllWeather();
        Set<String> citiesSet = new HashSet<>();
        for (WeatherInfoEntity weatherInfo : weatherInfoList) {
            String cityName = weatherInfo.getCity();
            citiesSet.add(cityName);
        }
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("weatherInfo", weatherInfoList);
        jsonObject.put("citiesSet", citiesSet);
        out.println(jsonObject.toString());
    }
}
