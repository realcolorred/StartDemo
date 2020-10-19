package com.example.task.util;

import com.example.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by new on 2020/10/1.
 */
@Slf4j
public class CityUtil {

    private static List<Map<String, String>> cityList = new ArrayList<>();

    static {
        addCity("001", "中国");

        addCity("001001", "湖北省");
        addCity("001002", "广东省");

        addCity("001001001", "武汉市");
        addCity("001001002", "孝感市");
        addCity("001002003", "广州市");
        addCity("001002004", "深圳市");

        addCity("001001001001", "汉阳区");
        addCity("001001001002", "武昌区");
        addCity("001001001003", "汉口区");
        addCity("001001002001", "孝东区");
        addCity("001001002002", "孝西区");
        addCity("001002003001", "天河区");
        addCity("001002003002", "越秀区");
        addCity("001002004001", "深圳东区");
        addCity("001002004002", "深圳西区");
    }

    private static void addCity(String cityId, String cityName) {
        Map<String, String> map = new HashMap<>();
        map.put("cityId", cityId);
        map.put("cityName", cityName);
        cityList.add(map);
    }

    /**
     * 获取所有省级地区名称
     * @return result
     */
    public static List<String> getCity() {
        return getCityByLevel(2);
    }

    /**
     * 获取某一级别的所有地区名称
     * @param level 2省级 3市级 4区级
     * @return result
     */
    public static List<String> getCityByLevel(int level) {
        List<String> result = new ArrayList<>();
        cityList.forEach(cityMap -> {
            if (cityMap.get("cityId").length() == (level * 3)) {
                result.add(cityMap.get("cityName"));
            }
        });
        return result;
    }

    /**
     * 获取某一地区下所有子地区的名称
     * @param parCityName 上级地区名称
     * @return result
     */
    public static List<String> getCityByParName(String parCityName) {
        String parCityId = "";
        List<String> result = new ArrayList<>();
        for (Map<String, String> cityMap : cityList) {
            if (cityMap.get("cityName").equals(parCityName)) {
                parCityId = cityMap.get("cityId");
                break;
            }
        }
        if (StringUtil.isBlank(parCityId)) {
            return result;
        }
        for (Map<String, String> cityMap : cityList) {
            if ((cityMap.get("cityId").length() == (parCityId.length() + 3)) && cityMap.get("cityId").startsWith(parCityId)) {
                result.add(cityMap.get("cityName"));
            }
        }
        return result;
    }

    /**
     * 获取某一级别的所有地区名称以及与上级对应关系
     * @param level 2省级 3市级 4区级
     * @return result
     */
    public static Map<String, List<String>> getCityWithPar(int level) {
        Map<String, List<String>> result = new HashMap<>();
        List<String> cityList = getCityByLevel(level - 1);
        cityList.forEach(city -> result.put(city, getCityByParName(city)));
        return result;
    }
}
