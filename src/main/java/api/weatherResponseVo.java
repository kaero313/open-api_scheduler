package api;

public class weatherResponseVo {
    weatherHeaderVo header;
    weatherBodyVo body;

    public weatherHeaderVo getHeader() {
        return header;
    }

    public void setHeader(weatherHeaderVo header) {
        this.header = header;
    }

    public weatherBodyVo getBody() {
        return body;
    }

    public void setBody(weatherBodyVo body) {
        this.body = body;
    }
}
