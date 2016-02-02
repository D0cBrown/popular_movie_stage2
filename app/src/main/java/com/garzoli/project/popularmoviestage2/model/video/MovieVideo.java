
package com.garzoli.project.popularmoviestage2.model.video;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieVideo {
    public static final String MOVIE_VIDEO_ID = "id";
    public static final String MOVIE_VIDEO_ISO = "iso_639_1";
    public static final String MOVIE_VIDEO_KEY = "key";
    public static final String MOVIE_VIDEO_NAME = "name";
    public static final String MOVIE_VIDEO_SITE = "site";
    public static final String MOVIE_VIDEO_SIZE = "size";
    public static final String MOVIE_VIDEO_TYPE = "type";

    @SerializedName(MOVIE_VIDEO_ID)
    @Expose
    private String id;
    @SerializedName(MOVIE_VIDEO_ISO)
    @Expose
    private String iso6391;
    @SerializedName(MOVIE_VIDEO_KEY)
    @Expose
    private String key;
    @SerializedName(MOVIE_VIDEO_NAME)
    @Expose
    private String name;
    @SerializedName(MOVIE_VIDEO_SITE)
    @Expose
    private String site;
    @SerializedName(MOVIE_VIDEO_SIZE)
    @Expose
    private Integer size;
    @SerializedName(MOVIE_VIDEO_TYPE)
    @Expose
    private String type;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The iso6391
     */
    public String getIso6391() {
        return iso6391;
    }

    /**
     * 
     * @param iso6391
     *     The iso_639_1
     */
    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }

    /**
     * 
     * @return
     *     The key
     */
    public String getKey() {
        return key;
    }

    /**
     * 
     * @param key
     *     The key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The site
     */
    public String getSite() {
        return site;
    }

    /**
     * 
     * @param site
     *     The site
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * 
     * @return
     *     The size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * 
     * @param size
     *     The size
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

}
