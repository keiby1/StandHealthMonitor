package com.example.StandHealthMonitor.dto;

import java.util.List;

public class CriteriaResponse {
    private List<CriteriaItem> criterias;

    public CriteriaResponse() {}

    public CriteriaResponse(List<CriteriaItem> criterias) {
        this.criterias = criterias;
    }

    public List<CriteriaItem> getCriterias() {
        return criterias;
    }

    public void setCriterias(List<CriteriaItem> criterias) {
        this.criterias = criterias;
    }

    public static class CriteriaItem {
        private Integer count;
        private String color;

        public CriteriaItem() {}

        public CriteriaItem(Integer count, String color) {
            this.count = count;
            this.color = color;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }
}

