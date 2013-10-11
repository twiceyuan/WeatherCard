package info.twiceyuan.weather.domain;

import java.io.Serializable;

public class Weather implements Serializable{


	private String city; // 城市名字
	private String date_y; // 日期
	private String week; // 星期
	private String cityid; // 城市ID
	private String temp[] = new String[6]; // 城市温度6天
	private String tempF[] = new String[6]; // 城市温度6天华氏
	private String weather[] = new String[6]; // 城市天气6天
    private int weathericon[] = new int[6]; // 城市天气图标6天
	private String wind[] = new String[6]; // 城市风向 6天
	private String msg; // 天气详细信息
	private String meg_advice; // 天气建议
	private String msg48; // 48小时天气详细信息
	private String meg48_advice; // 48小时天气详细信息建议
	private String uv_info; // 当前紫外线信息
	private String uv48_info; // 48小时紫外线信息
	private String cleancar; // 洗车指数
	private String travel; // 旅行指数
	private String comfort; // 舒适指数
	private String morningexer; // 晨练指数
	private String drying; // 
	private String cold; //

	/**
	 * 
	 * */
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDate_y() {
		return date_y;
	}

	public void setDate_y(String date_y) {
		this.date_y = date_y;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getCityid() {
		return cityid;
	}

	public void setCityid(String cityid) {
		this.cityid = cityid;
	}

	public String[] getTemp() {
		return temp;
	}

	public void setTemp(String temp[]) {
		this.temp = temp;
	}

	public String[] getTempF() {
		return tempF;
	}

	public void setTempF(String tempF[]) {
		this.tempF = tempF;
	}

	public String[] getWeather() {
		return weather;
	}

	public void setWeather(String weather[]) {
		this.weather = weather;
	}

	public String[] getWind() {
		return wind;
	}

	public void setWind(String wind[]) {
		this.wind = wind;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMeg_advice() {
		return meg_advice;
	}

	public void setMeg_advice(String meg_advice) {
		this.meg_advice = meg_advice;
	}

	public String getMsg48() {
		return msg48;
	}

	public void setMsg48(String msg48) {
		this.msg48 = msg48;
	}

	public String getMeg48_advice() {
		return meg48_advice;
	}

	public void setMeg48_advice(String meg48_advice) {
		this.meg48_advice = meg48_advice;
	}

	public String getUv_info() {
		return uv_info;
	}

	public void setUv_info(String uv_info) {
		this.uv_info = uv_info;
	}

	public String getUv48_info() {
		return uv48_info;
	}

	public void setUv48_info(String uv48_info) {
		this.uv48_info = uv48_info;
	}

	public String getCleancar() {
		return cleancar;
	}

	public void setCleancar(String cleancar) {
		this.cleancar = cleancar;
	}

	public String getTravel() {
		return travel;
	}

	public void setTravel(String travel) {
		this.travel = travel;
	}

	public String getComfort() {
		return comfort;
	}

	public void setComfort(String comfort) {
		this.comfort = comfort;
	}

	public String getMorningexer() {
		return morningexer;
	}

	public void setMorningexer(String morningexer) {
		this.morningexer = morningexer;
	}

	public String getDrying() {
		return drying;
	}

	public void setDrying(String drying) {
		this.drying = drying;
	}

	public String getCold() {
		return cold;
	}

	public void setCold(String cold) {
		this.cold = cold;
	}

    public int[] getWeathericon() {
        return weathericon;
    }

    public void setWeathericon(int[] weathericon) {
        this.weathericon = weathericon;
    }
}
