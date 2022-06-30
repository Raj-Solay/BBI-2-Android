package com.bbi.bizbulls.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonalDetailsViewRes {
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
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("present_acco_since")
        @Expose
        private String presentAccoSince;
        @SerializedName("fathersname")
        @Expose
        private String fathersname;
        @SerializedName("permanent_emergency_no")
        @Expose
        private String permanentEmergencyNo;
        @SerializedName("present_emergency_no")
        @Expose
        private String presentEmergencyNo;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("mothersname")
        @Expose
        private String mothersname;
        @SerializedName("permanent_acco_since")
        @Expose
        private String permanentAccoSince;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("permanent_acco_type")
        @Expose
        private String permanentAccoType;
        @SerializedName("mothertongue")
        @Expose
        private String mothertongue;
        @SerializedName("present_acco_type")
        @Expose
        private String presentAccoType;
        @SerializedName("maritalstatus")
        @Expose
        private String maritalstatus;
        @SerializedName("present_add")
        @Expose
        private String presentAdd;
        @SerializedName("whatsappnumber")
        @Expose
        private String whatsappnumber;
        @SerializedName("pob")
        @Expose
        private String pob;
        @SerializedName("alternatenumber")
        @Expose
        private String alternatenumber;
        @SerializedName("zip")
        @Expose
        private String zip;
        @SerializedName("permanent_add")
        @Expose
        private String permanentAdd;
        @SerializedName("fullname")
        @Expose
        private String fullname;
        @SerializedName("emailid")
        @Expose
        private String emailid;
        @SerializedName("age")
        @Expose
        private String age;
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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPresentAccoSince() {
            return presentAccoSince;
        }

        public void setPresentAccoSince(String presentAccoSince) {
            this.presentAccoSince = presentAccoSince;
        }

        public String getFathersname() {
            return fathersname;
        }

        public void setFathersname(String fathersname) {
            this.fathersname = fathersname;
        }

        public String getPermanentEmergencyNo() {
            return permanentEmergencyNo;
        }

        public void setPermanentEmergencyNo(String permanentEmergencyNo) {
            this.permanentEmergencyNo = permanentEmergencyNo;
        }

        public String getPresentEmergencyNo() {
            return presentEmergencyNo;
        }

        public void setPresentEmergencyNo(String presentEmergencyNo) {
            this.presentEmergencyNo = presentEmergencyNo;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getMothersname() {
            return mothersname;
        }

        public void setMothersname(String mothersname) {
            this.mothersname = mothersname;
        }

        public String getPermanentAccoSince() {
            return permanentAccoSince;
        }

        public void setPermanentAccoSince(String permanentAccoSince) {
            this.permanentAccoSince = permanentAccoSince;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPermanentAccoType() {
            return permanentAccoType;
        }

        public void setPermanentAccoType(String permanentAccoType) {
            this.permanentAccoType = permanentAccoType;
        }

        public String getMothertongue() {
            return mothertongue;
        }

        public void setMothertongue(String mothertongue) {
            this.mothertongue = mothertongue;
        }

        public String getPresentAccoType() {
            return presentAccoType;
        }

        public void setPresentAccoType(String presentAccoType) {
            this.presentAccoType = presentAccoType;
        }

        public String getMaritalstatus() {
            return maritalstatus;
        }

        public void setMaritalstatus(String maritalstatus) {
            this.maritalstatus = maritalstatus;
        }

        public String getPresentAdd() {
            return presentAdd;
        }

        public void setPresentAdd(String presentAdd) {
            this.presentAdd = presentAdd;
        }

        public String getWhatsappnumber() {
            return whatsappnumber;
        }

        public void setWhatsappnumber(String whatsappnumber) {
            this.whatsappnumber = whatsappnumber;
        }

        public String getPob() {
            return pob;
        }

        public void setPob(String pob) {
            this.pob = pob;
        }

        public String getAlternatenumber() {
            return alternatenumber;
        }

        public void setAlternatenumber(String alternatenumber) {
            this.alternatenumber = alternatenumber;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getPermanentAdd() {
            return permanentAdd;
        }

        public void setPermanentAdd(String permanentAdd) {
            this.permanentAdd = permanentAdd;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getEmailid() {
            return emailid;
        }

        public void setEmailid(String emailid) {
            this.emailid = emailid;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
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
