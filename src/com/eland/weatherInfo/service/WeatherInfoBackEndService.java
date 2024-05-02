package com.eland.weatherInfo.service;

import com.eland.weatherInfo.dao.WeatherInfoBackEndDAO;
import com.eland.weatherInfo.formbean.WeatherInfoForm;
import com.eland.weatherInfo.model.WeatherInfoEntity;
import org.hibernate.query.Query;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WeatherInfoBackEndService {
    private static WeatherInfoBackEndService weatherInfoBackEndService = new WeatherInfoBackEndService();
    private WeatherInfoBackEndDAO weatherInfoBackEndDAO = WeatherInfoBackEndDAO.getInstance();

    public static WeatherInfoBackEndService getInstance() {
        return weatherInfoBackEndService;
    }

    public List<WeatherInfoEntity> queryWeatherInfos(String city,String date, String high, String low, String aqi, String fl, String sortColumn, String sorted) {
        WeatherInfoForm weatherInfoForm = new WeatherInfoForm();
        if ("" != city) {
            weatherInfoForm.setCity(city);
        }
        if ("" != date) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date dateObj = sf.parse(date);
                weatherInfoForm.setDate(dateObj);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (""!=high) {
            weatherInfoForm.setHigh(Integer.parseInt(high));
        }
        if (""!=low) {
            weatherInfoForm.setLow(Integer.parseInt(low));
        }
        if ("" != aqi) {
            weatherInfoForm.setAqi(aqi);
        }
        if ("" != fl) {
            weatherInfoForm.setFl(Integer.parseInt(fl));
        }

        List<WeatherInfoEntity> weatherInfo = weatherInfoBackEndDAO.queryWeatherInfos(weatherInfoForm, sortColumn, sorted);

        return weatherInfo;
    }
    public List<WeatherInfoEntity> queryAllWeather(){

        return weatherInfoBackEndDAO.queryAllWeather();
    }
}
