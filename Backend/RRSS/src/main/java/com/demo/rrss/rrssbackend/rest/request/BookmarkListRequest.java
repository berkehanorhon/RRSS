package com.demo.rrss.rrssbackend.rest.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class BookmarkListRequest {
    private Long bookmarkListId;
    private Long userId;
    private String title;
    private String creationDate;

}
