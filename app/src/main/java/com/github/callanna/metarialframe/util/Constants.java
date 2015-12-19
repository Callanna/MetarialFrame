package com.github.callanna.metarialframe.util;

/**
 * Created by Callanna on 2015/12/17.
 */
public class Constants {

    public static final String CHAR_SET_UTF8 = "UTF-8";

    public static String Device_ID = "DEVICE_ID";
    public static String FactroyID = "";
    public static String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String MAC_ADDRESS ="MAC_ADDRESS" ;
    //获取Token 参数
    public static String APP_ID = "534Glh2zxyyfvSiThU";
    public static String APP_SECRET = "p5lwRGEgUqH1BHgHf1XAn2vRlUamrvqf";
    public static String GRANT_TYPE = "client_credential";
    //============Mqtt param
    public static final String TOPIC = "TOPIC";
    public static final String CLIENT_ID = "CLIENT_ID";
    public static final String HOSTNAME = "HOSTNAME";
    public static final String HOSTPORT = "HOSTPORT";
    public static final String USER_NAME = "USER_NAME";
    public static final String PASSWD = "PASSWD";

    /**
     * =========mqtt===url======
     **/
    //register Device
    public static final String  URL_GET_DEVICEID = "http://smart.56iq.net:8002/device";
    //access_token
    public static final String URL_GET_TOKEN = "http://api.53iq.com/token";
    // URL-topic
    public static final String URL_MQTT = "https://api.53iq.com/message";


    //获取天气的接口
    public static final String WEATHRER_URL = "http://api.53iq.com/component/weather";
}
