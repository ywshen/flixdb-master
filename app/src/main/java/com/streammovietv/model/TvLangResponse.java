package com.streammovietv.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvLangResponse {
    @SerializedName("JSON")
    private List<TvLang> languages;

    public List<TvLang> getLanguages() {
        return languages;
    }

    public void setLanguages(List<TvLang> languages) {
        this.languages = languages;
    }
}
