package com.example.StandHealthMonitor.dto;

import java.util.List;

public class DataResponse {
    private List<DayData> data;

    public DataResponse() {}

    public DataResponse(List<DayData> data) {
        this.data = data;
    }

    public List<DayData> getData() {
        return data;
    }

    public void setData(List<DayData> data) {
        this.data = data;
    }

    public static class DayData {
        private String date;
        private List<SystemStat> stat;

        public DayData() {}

        public DayData(String date, List<SystemStat> stat) {
            this.date = date;
            this.stat = stat;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<SystemStat> getStat() {
            return stat;
        }

        public void setStat(List<SystemStat> stat) {
            this.stat = stat;
        }
    }

    public static class SystemStat {
        private String system;
        private String status;
        private Integer count;
        private Integer httpCode;
        private Boolean success;

        public SystemStat() {}

        public SystemStat(String system, String status) {
            this.system = system;
            this.status = status;
            this.count = 1;
        }

        public SystemStat(String system, String status, Integer count) {
            this.system = system;
            this.status = status;
            this.count = count;
        }

        public SystemStat(String system, String status, Integer count, Integer httpCode, Boolean success) {
            this.system = system;
            this.status = status;
            this.count = count;
            this.httpCode = httpCode;
            this.success = success;
        }

        public String getSystem() {
            return system;
        }

        public void setSystem(String system) {
            this.system = system;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Integer getHttpCode() {
            return httpCode;
        }

        public void setHttpCode(Integer httpCode) {
            this.httpCode = httpCode;
        }

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }
    }
}

