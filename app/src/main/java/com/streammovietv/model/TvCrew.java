package com.streammovietv.model;

import com.google.gson.annotations.SerializedName;

public class TvCrew {
    @SerializedName("credit_id")
    private String creditId;

    @SerializedName("department")
    private String department;

    @SerializedName("gender")
    private int gender;

    @SerializedName("id")
    private int id;

    @SerializedName("job")
    private String job;

    @SerializedName("name")
    private String crewName;

    @SerializedName("profile_path")
    private String profilePath;


    public String getCreditId() { return creditId; }
    public void setCreditId(String creditId) {  this.creditId = creditId; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public int getGender() { return gender; }
    public void setGender(int gender) { this.gender = gender; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getJob() { return job; }
    public void setJob(String job) { this.job = job; }

    public String getCrewName() { return crewName; }
    public void setCrewName(String crewName) { this.crewName = crewName; }

    public String getProfilePath() { return profilePath; }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
