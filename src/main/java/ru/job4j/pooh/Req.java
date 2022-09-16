package ru.job4j.pooh;

public class Req {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    public static Req of(String content) {
        String[] result = new String[4];
        String[] stringsSplit = content.split(System.lineSeparator());
        String[] stringsOne = stringsSplit[0].split("/");
        result[0] = stringsOne[0].replaceAll("\\s+", "");
        result[1] = stringsOne[1];
        if (stringsOne.length == 4) {
            result[2] = stringsOne[2].split(" ")[0];
            if (stringsSplit.length < 8) {
                result[3] = "";
            } else {
                result[3] = stringsSplit[7];
            }
        }
        if (stringsOne.length == 5 && stringsSplit.length < 8) {
            result[2] = stringsOne[2];
            result[3] = stringsOne[3].split(" ")[0];
        }
        return new Req(result[0], result[1], result[2], result[3]);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }

    @Override
    public String toString() {
        return "Req{" + "httpRequestType='" + httpRequestType + '\''
                + ", poohMode='" + poohMode + '\''
                + ", sourceName='" + sourceName + '\''
                + ", param='" + param + '\'' + '}';
    }
}
