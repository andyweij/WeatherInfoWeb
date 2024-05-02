<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:url value="/" var="WEB_PATH"/>
<c:url value="/js" var="JS_PATH"/>
<c:url value="/css" var="CSS_PATH"/>
<html>
<head>
    <title>weatherInfoView</title>
    <link rel="stylesheet" href="${CSS_PATH}/bootstrap.min.css">
    <script src="${JS_PATH}/jquery-3.2.1.min.js"></script>
    <script src="${JS_PATH}/popper.min.js"></script>
    <script src="${JS_PATH}/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/moment.min.js"></script>
    <script>

        function weatherList(page) {
            if (page == null) {
                page = 1;
            }
            const city = document.getElementById('CityList').value;
            const date = document.getElementById('inputDate').value;
            const high = document.getElementById('inputHigh').value;
            const low = document.getElementById('inputLow').value;
            const aqi = document.getElementById('inputAqi').value;
            const fl = document.getElementById('inputFl').value;
            console.log(city, date, high, low, aqi, fl);
            var queryData = {
                queryCity: city,
                queryDate: date,
                queryHigh: high,
                queryLow: low,
                queryAqi: aqi,
                queryFl: fl,
            }
            // console.log(queryData.queryDate);
            $.ajax({
                url: 'WeatherInfoBackEndController?action=queryWeatherInfoByKey', // 指定要進行呼叫的位址
                method: "GET", // 請求方式 POST/GET
                data: queryData, // 傳送至 Server的請求資料(物件型式則為 Key/Value pairs)
                dataType: 'JSON', // Server回傳的資料類型
                success: function (data) { // 請求成功時執行函式
                    var weatherInfoList = $("#weatherInfo");
                    weatherInfoList.empty(); // 清空现有列表
                    var weatherInfo = data.weatherInfo;
                    // var pages = data.pagination;
                    console.log(data)
                    // 遍历数据并添加到列表中
                    $.each(weatherInfo, function (index, info) {
                        const formattedDate = moment(info.date.time).format('YYYY-MM-DD'); // 移除多餘的引號
                        weatherInfoList.append("<tr>")
                        weatherInfoList.append("<td>" + info.id + "</td>" + "<td>" + info.city + "</td>" + "<td>" + formattedDate + "</td>" +
                            "<td>" + info.sunrise + "</td>" + "<td>" + info.high + "</td>" + "<td>" + info.low + "</td>" + "<td>" + info.sunset + "</td>"
                            + "<td>" + info.aqi + "</td>"
                            + "<td>" + info.fx + "</td>" + "<td>" + info.fl + "</td>" + "<td>" + info.type + "</td>" + "<td>" + info.notice + "</td>");
                        weatherInfoList.append("</tr>");
                    });

                },
                error: function (error) { // 請求發生錯誤時執行函式
                    alert("Ajax Error!");
                    console.log("Error Message:", error);
                }
            });
        }
        function init() {

            $.ajax({
                url: 'WeatherInfoBackEndController?action=queryWeatherInfo', // 指定要進行呼叫的位址
                method: "GET", // 請求方式 POST/GET
                dataType: 'JSON', // Server回傳的資料類型
                success: function (data) { // 請求成功時執行函式
                    var weatherInfoList = $("#weatherInfo");
                    weatherInfoList.empty(); // 清空现有列表
                    var weatherInfo = data.weatherInfo;
                    var citiesList = $("#CityList");
                    citiesList.empty();
                    citiesList.append("<option value=''>ALL" + "</option>");
                    var cities = data.citiesSet;
                    // var pages = data.pagination;
                    console.log(data)
                    // 遍历数据并添加到列表中
                    $.each(cities, function (index, city) {
                        console.log(city);
                        citiesList.append("<option value='" + city + "'>" + city);
                        citiesList.append("</option>");
                    });
                    $.each(weatherInfo, function (index, info) {
                        const formattedDate = moment(info.date.time).format('YYYY-MM-DD'); // 移除多餘的引號
                        weatherInfoList.append("<tr>")
                        weatherInfoList.append("<td>" + info.id + "</td>" + "<td>" + info.city + "</td>" + "<td>" + formattedDate + "</td>" +
                            "<td>" + info.sunrise + "</td>" + "<td>" + info.high + "</td>" + "<td>" + info.low + "</td>" + "<td>" + info.sunset + "</td>"
                            + "<td>" + info.aqi + "</td>"
                            + "<td>" + info.fx + "</td>" + "<td>" + info.fl + "</td>" + "<td>" + info.type + "</td>" + "<td>" + info.notice + "</td>");
                        weatherInfoList.append("</tr>");
                    });

                },
                error: function (error) { // 請求發生錯誤時執行函式
                    alert("Ajax Error!");
                    console.log("Error Message:", error);
                }
            });
        }
    </script>
</head>
<body onload="init()">
<div class="container-fluid">

    <form action="javascript:weatherList()">

        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="CityList">City</label>
                <select id="CityList" class="form-control"></select>
            </div>
            <div class="form-group col-md-6">
                <label for="inputDate">Date</label>
                <input type="date" class="form-control" id="inputDate" placeholder="日期">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="inputHigh">High</label>
                <input type="number" class="form-control" id="inputHigh" placeholder="當日最高溫">
            </div>
            <div class="form-group col-md-6">
                <label for="inputLow">low</label>
                <input type="number" class="form-control" id="inputLow" placeholder="當日最低溫">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="inputAqi">AQI</label>
                <input type="text" class="form-control" id="inputAqi" placeholder="空氣品質指數">
            </div>
            <div class="form-group col-md-6">
                <label for="inputFl">FL</label>
                <input type="text" class="form-control" id="inputFl" placeholder="風速">
            </div>
        </div>
        <button type="submit" class="btn btn-primary">查詢</button>
    </form>
    <div class="portlet" id="memberList">
        <div class="top clearfix">
            <h1 class="pull-left">天氣資訊</h1>
        </div>
        <!--top-->
        <div class="content">

            <div class="channelList" id="list">
                <table class="table channelTable">
                    <thead>
                    <tr>
                        <th scope="col" class="sortable" data-column="id">#</th>
                        <th scope="col" class="sortable" data-column="city">城市名稱</th>
                        <th scope="col" class="sortable" data-column="date">日期</th>
                        <th scope="col" class="sortable" data-column="sunrise">日出時間</th>
                        <th scope="col" class="sortable" data-column="high">當日最高溫</th>
                        <th scope="col" class="sortable" data-column="low">當日最低溫</th>
                        <th scope="col" class="sortable" data-column="sunset">日落時間</th>
                        <th scope="col" class="sortable" data-column="aqi">空氣品質指數</th>
                        <th scope="col" class="sortable" data-column="fx">風向</th>
                        <th scope="col" class="sortable" data-column="fl">風速</th>
                        <th scope="col" class="sortable" data-column="type">天氣</th>
                        <th scope="col" class="sortable" data-column="notice">提醒</th>
                    </tr>
                    </thead>
                    <tbody id="weatherInfo">
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
