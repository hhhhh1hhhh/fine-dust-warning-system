package com.example.finedust;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JsonData {

    @JsonProperty("날짜")
    private String date;

    @JsonProperty("측정소명")
    private String stationName;

    @JsonProperty("측정소코드")
    private String stationCode;

    @JsonProperty("PM10")
    private int pm10;

    @JsonProperty("PM2.5")
    private int pm25;
}
