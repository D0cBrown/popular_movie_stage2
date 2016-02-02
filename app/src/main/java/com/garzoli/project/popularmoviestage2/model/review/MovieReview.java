
package com.garzoli.project.popularmoviestage2.model.review;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieReview {
    public final static String MOVIE_REVIEW_ID = "id";
    public final static String MOVIE_REVIEW_AUTHOR = "author";
    public final static String MOVIE_REVIEW_CONTENT = "content";
    public final static String MOVIE_REVIEW_URL = "url";

    @SerializedName(MOVIE_REVIEW_ID)
    @Expose
    private String id;
    @SerializedName(MOVIE_REVIEW_AUTHOR)
    @Expose
    private String author;
    @SerializedName(MOVIE_REVIEW_CONTENT)
    @Expose
    private String content;
    @SerializedName(MOVIE_REVIEW_URL)
    @Expose
    private String url;

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
     *     The author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 
     * @param author
     *     The author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 
     * @return
     *     The content
     */
    public String getContent() {
        return content;
    }

    /**
     * 
     * @param content
     *     The content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
