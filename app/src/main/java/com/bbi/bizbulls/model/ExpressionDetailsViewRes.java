package com.bbi.bizbulls.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExpressionDetailsViewRes {
    @SerializedName("data")
    @Expose
    private List<Data> data = null;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("project_name")
        @Expose
        private String projectName;
        @SerializedName("industry")
        @Expose
        private String industry;
        @SerializedName("location_interest")
        @Expose
        private String locationInterest;
        @SerializedName("financial_assistance")
        @Expose
        private String financialAssistance;
        @SerializedName("registration_fee")
        @Expose
        private String registrationFee;
        @SerializedName("franchisee_planning_for")
        @Expose
        private String franchiseePlanningFor;
        @SerializedName("franchisee_planning_as")
        @Expose
        private String franchiseePlanningAs;
        @SerializedName("business_place_type")
        @Expose
        private String businessPlaceType;
        @SerializedName("business_place_size")
        @Expose
        private String businessPlaceSize;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }

        public String getIndustry() {
            return industry;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public String getLocationInterest() {
            return locationInterest;
        }

        public void setLocationInterest(String locationInterest) {
            this.locationInterest = locationInterest;
        }

        public String getFinancialAssistance() {
            return financialAssistance;
        }

        public void setFinancialAssistance(String financialAssistance) {
            this.financialAssistance = financialAssistance;
        }

        public String getRegistrationFee() {
            return registrationFee;
        }

        public void setRegistrationFee(String registrationFee) {
            this.registrationFee = registrationFee;
        }

        public String getFranchiseePlanningFor() {
            return franchiseePlanningFor;
        }

        public void setFranchiseePlanningFor(String franchiseePlanningFor) {
            this.franchiseePlanningFor = franchiseePlanningFor;
        }

        public String getFranchiseePlanningAs() {
            return franchiseePlanningAs;
        }

        public void setFranchiseePlanningAs(String franchiseePlanningAs) {
            this.franchiseePlanningAs = franchiseePlanningAs;
        }

        public String getBusinessPlaceType() {
            return businessPlaceType;
        }

        public void setBusinessPlaceType(String businessPlaceType) {
            this.businessPlaceType = businessPlaceType;
        }

        public String getBusinessPlaceSize() {
            return businessPlaceSize;
        }

        public void setBusinessPlaceSize(String businessPlaceSize) {
            this.businessPlaceSize = businessPlaceSize;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
}
