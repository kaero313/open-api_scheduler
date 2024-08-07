package api;

public class weather {

    private int coordX;
    private int coordY;
    private String baseYmd;
    private String baseTime;
    private String rainProbabl;
    private String rainType;
    private String humidity;
    private String curTemp;
    private String windType;
    private String wind;
    private String areaNm;

    private int deviceSnsrId;

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }

    public String getBaseYmd() {
        return baseYmd;
    }

    public void setBaseYmd(String baseYmd) {
        this.baseYmd = baseYmd;
    }

    public String getBaseTime() {
        return baseTime;
    }

    public void setBaseTime(String baseTime) {
        this.baseTime = baseTime;
    }

    public String getRainProbabl() {
        return rainProbabl;
    }

    public void setRainProbabl(String rainProbabl) {
        this.rainProbabl = rainProbabl;
    }

    public String getRainType() {
        return rainType;
    }

    public void setRainType(String rainType) {
        this.rainType = rainType;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getCurTemp() {
        return curTemp;
    }

    public void setCurTemp(String curTemp) {
        this.curTemp = curTemp;
    }

    public String getWindType() {
        return windType;
    }

    public void setWindType(String windType) {
        this.windType = windType;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public int getDeviceSnsrId() {
        return deviceSnsrId;
    }

    public void setDeviceSnsrId(int deviceSnsrId) {
        this.deviceSnsrId = deviceSnsrId;
    }

    public String getAreaNm() { return areaNm; }

    public void setAreaNm(String areaNm) { this.areaNm = areaNm; }
}
