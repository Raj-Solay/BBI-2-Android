package com.bbi.bizbulls.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HealthDetailsViewRes {

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
        @SerializedName("birthidentificationmarks")
        @Expose
        private String birthidentificationmarks;
        @SerializedName("birthidentificationmarks2")
        @Expose
        private String birthidentificationmarks2;
        @SerializedName("handuse")
        @Expose
        private String handuse;
        @SerializedName("weight")
        @Expose
        private String weight;
        @SerializedName("bloodgroup")
        @Expose
        private String bloodgroup;
        @SerializedName("willingtodonate")
        @Expose
        private String willingtodonate;
        @SerializedName("typeofph")
        @Expose
        private Object typeofph;
        @SerializedName("surgelesstreatmentundergo")
        @Expose
        private String surgelesstreatmentundergo;
        @SerializedName("typeofsurgery")
        @Expose
        private String typeofsurgery;
        @SerializedName("anyotherhealthissue")
        @Expose
        private String anyotherhealthissue;
        @SerializedName("otherissuesdetail")
        @Expose
        private String otherissuesdetail;
        @SerializedName("anyunhealthyhabit")
        @Expose
        private String anyunhealthyhabit;
        @SerializedName("habbitdetails")
        @Expose
        private String habbitdetails;
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

        public String getBirthidentificationmarks() {
            return birthidentificationmarks;
        }

        public void setBirthidentificationmarks(String birthidentificationmarks) {
            this.birthidentificationmarks = birthidentificationmarks;
        }

        public String getBirthidentificationmarks2() {
            return birthidentificationmarks2;
        }

        public void setBirthidentificationmarks2(String birthidentificationmarks2) {
            this.birthidentificationmarks2 = birthidentificationmarks2;
        }

        public String getHanduse() {
            return handuse;
        }

        public void setHanduse(String handuse) {
            this.handuse = handuse;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getBloodgroup() {
            return bloodgroup;
        }

        public void setBloodgroup(String bloodgroup) {
            this.bloodgroup = bloodgroup;
        }

        public String getWillingtodonate() {
            return willingtodonate;
        }

        public void setWillingtodonate(String willingtodonate) {
            this.willingtodonate = willingtodonate;
        }

        public Object getTypeofph() {
            return typeofph;
        }

        public void setTypeofph(Object typeofph) {
            this.typeofph = typeofph;
        }

        public String getSurgelesstreatmentundergo() {
            return surgelesstreatmentundergo;
        }

        public void setSurgelesstreatmentundergo(String surgelesstreatmentundergo) {
            this.surgelesstreatmentundergo = surgelesstreatmentundergo;
        }

        public String getTypeofsurgery() {
            return typeofsurgery;
        }

        public void setTypeofsurgery(String typeofsurgery) {
            this.typeofsurgery = typeofsurgery;
        }

        public String getAnyotherhealthissue() {
            return anyotherhealthissue;
        }

        public void setAnyotherhealthissue(String anyotherhealthissue) {
            this.anyotherhealthissue = anyotherhealthissue;
        }

        public String getOtherissuesdetail() {
            return otherissuesdetail;
        }

        public void setOtherissuesdetail(String otherissuesdetail) {
            this.otherissuesdetail = otherissuesdetail;
        }

        public String getAnyunhealthyhabit() {
            return anyunhealthyhabit;
        }

        public void setAnyunhealthyhabit(String anyunhealthyhabit) {
            this.anyunhealthyhabit = anyunhealthyhabit;
        }

        public String getHabbitdetails() {
            return habbitdetails;
        }

        public void setHabbitdetails(String habbitdetails) {
            this.habbitdetails = habbitdetails;
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
