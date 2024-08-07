package api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("WeatherController")
public class weatherController {

    @Value("${service.url}")
    private String serviceUrl;

    @Value("${service.key}")
    private String serviceKey;

    public void getWeather() {

        Calendar cal = Calendar.getInstance();

        String baseDate = getBaseDate(cal.getTime(), "yyyyMMdd");
        String baseTime = getBaseTime(cal, "HH", "WX_CURRENT");

        List<weather> coordXyList = new ArrayList<>();
        weather weatherLocationSet = new weather();
        weatherLocationSet.setCoordX(60);     // 서울시 중구
        weatherLocationSet.setCoordY(127);    // 서울시 중구
        coordXyList.add(weatherLocationSet);
        weatherLocationSet.setCoordX(61);     // 서울시 강남
        weatherLocationSet.setCoordY(126);    // 서울시 강남
        coordXyList.add(weatherLocationSet);
        weatherLocationSet.setCoordX(99);     // 부산 해운대
        weatherLocationSet.setCoordY(75);     // 부산 해운대
        coordXyList.add(weatherLocationSet);

        for(int i=0; i<coordXyList.size(); i++){

            weatherVo weatherVo = getWeatherDataToVo(serviceUrl, serviceKey, baseDate, baseTime,
                    coordXyList.get(i).getCoordX(), coordXyList.get(i).getCoordY());

            if(weatherVo != null){

                weather weather = parsingWeather(weatherVo);

                if(weather != null) {

                    System.out.println("지역명    : " + weather.getAreaNm() + "\n" +
                                       "예보 시간 : " + weather.getBaseYmd() + " " + weather.getBaseTime() + "\n" +
                                       "기온      : " + weather.getCurTemp() + "°C" + "\n" +
                                       "습도      : " + weather.getHumidity() + "%" + "\n" +
                                       "강수형태  : " + weather.getRainType() + "\n" +
                                       "강수량    : " + weather.getRainProbabl() + "mm" + "\n" +
                                       "풍속      : " + weather.getWind() + "m/s" + "\n" +
                                       "풍향      : " + weather.getWindType() + "\n");

                }

            }

        }

    }

    public weatherVo getWeatherDataToVo(String serviceUrl, String serviceKey, String baseDate, String baseTime, int coordX, int coordY) {

        weatherVo weatherVo = null;

        try {

            String reqUri = serviceUrl;
            reqUri += "?serviceKey=" + serviceKey;
            reqUri += "&base_date=" + baseDate;
            reqUri += "&base_time=" + baseTime;
            reqUri += "&nx=" + coordX;
            reqUri += "&ny=" + coordY;
            reqUri += "&numOfRows=100";
            reqUri += "&pageNo=1";
            reqUri += "&dataType=JSON";

            String urlConResult = getUrlConResult(reqUri);

            if (urlConResult != null) {
                ObjectMapper objMapper = new ObjectMapper();
                weatherVo = objMapper.readValue(urlConResult, weatherVo.class);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return weatherVo;
    }

    public String getUrlConResult(String reqUri) {

        String urlConResult = "";

        try {

            URL url = new URL(reqUri);
            URLConnection urlConnection = url.openConnection();

            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(2000);

            HttpURLConnection connection = null;
            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            } else {
                System.out.println("URL error");
                return null;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));

            String current;
            while ((current = br.readLine()) != null) {

                urlConResult += current + "\n";
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return urlConResult;
    }


    public weather parsingWeather(weatherVo weatherVo) {

        weather weather = null;

        if ("00".equals(weatherVo.getResponse().getHeader().getResultCode())) {
            weather = new weather();
            List<weatherItemVo> item = weatherVo.getResponse().getBody().getItems().getItem();

            if (item.size() > 0) {
                if(60 == item.get(0).getNx() && 127 == item.get(0).getNy()){
                    weather.setAreaNm("서울시 중구");
                }else if(61 == item.get(0).getNx() && 126 == item.get(0).getNy()){
                    weather.setAreaNm("서울시 강남");
                }else if(99 == item.get(0).getNx() && 75 == item.get(0).getNy()){
                    weather.setAreaNm("부산시 해운대");
                }

                weather.setBaseYmd(item.get(0).getBaseDate());
                weather.setBaseTime(item.get(0).getBaseTime());

                for(int i=0; i<item.size(); i++){

                    if("T1H".equals(item.get(i).getCategory())){                // 기온
                        weather.setCurTemp(item.get(i).getObsrValue());
                    }else if("REH".equals(item.get(i).getCategory())){          // 습도
                        weather.setHumidity(item.get(i).getObsrValue());
                    }else if("PTY".equals(item.get(i).getCategory())){          // 강수형태
                        if("0".equals(item.get(i).getObsrValue())){
                            weather.setRainType("강수 없음");
                        }else if("1".equals(item.get(i).getObsrValue())){
                            weather.setRainType("비");
                        }else if("2".equals(item.get(i).getObsrValue())){
                            weather.setRainType("비/눈");
                        }else if("3".equals(item.get(i).getObsrValue())){
                            weather.setRainType("눈");
                        }else if("4".equals(item.get(i).getObsrValue())){
                            weather.setRainType("소나기");
                        }
                    }else if("RN1".equals(item.get(i).getCategory())){          // 1시간 강수량
                        weather.setRainProbabl(item.get(i).getObsrValue());
                    }else if("WSD".equals(item.get(i).getCategory())){          // 풍속
                        weather.setWind(item.get(i).getObsrValue());
                    }else if("VEC".equals(item.get(i).getCategory())){          // 풍향
                        int windValue = Integer.parseInt(item.get(i).getObsrValue());
                        String windType = "";
                        // 범위별 구간 계산해서 표현 단위별 저장
                        if(windValue >= 0 && windValue <= 44){
                            windType = "N";
                        }else if(windValue >= 45 && windValue <= 89){
                            windType = "NE";
                        }else if(windValue >= 90 && windValue <= 134){
                            windType = "E";
                        }else if(windValue >= 135 && windValue <= 179){
                            windType = "SE";
                        }else if(windValue >= 180 && windValue <= 224){
                            windType = "S";
                        }else if(windValue >= 225 && windValue <= 269){
                            windType = "SW";
                        }else if(windValue >= 270 && windValue <= 314){
                            windType = "W";
                        }else if(windValue >= 315 && windValue <= 360){
                            windType = "NW";
                        }else {
                            windType = "-";
                        }
                        weather.setWindType(windType);
                    }

                }

            }

        }

        return weather;
    }

    public String getBaseDate(Date date, String format) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String baseDate = simpleDateFormat.format(date);

        return baseDate;
    }

    public String getBaseTime(Calendar cal, String format, String type) {

        Date date = cal.getTime();
        String baseTime = "";

        int baseTimeMM = Integer.valueOf(getBaseDate(date, "mm"));
        if(baseTimeMM < 40){
            cal.add(Calendar.HOUR_OF_DAY, -1);
            date = cal.getTime();
        }
        baseTime = getBaseDate(date, format) + "00";

        return baseTime;
    }




}