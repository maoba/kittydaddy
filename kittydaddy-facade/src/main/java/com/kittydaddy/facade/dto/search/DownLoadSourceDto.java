package com.kittydaddy.facade.dto.search;

import java.util.List;

/**
 * 下载资源DTO
 */
public class DownLoadSourceDto {
    private String title;

    private List<String> source;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getSource() {
        return source;
    }

    public void setSource(List<String> source) {
        this.source = source;
    }
}
