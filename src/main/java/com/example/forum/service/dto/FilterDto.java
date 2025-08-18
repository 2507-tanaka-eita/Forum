package com.example.forum.service.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

// FilterFormで受け取った日付情報をLocalDate→LocalDateTime型に変換するのに使用。
public class FilterDto {

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    //---------
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    //---------
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
}
