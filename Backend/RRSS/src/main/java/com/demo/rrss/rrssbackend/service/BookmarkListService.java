package com.demo.rrss.rrssbackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.ui.Model;
import com.demo.rrss.rrssbackend.entity.BookmarkList;
import com.demo.rrss.rrssbackend.repository.BookmarkListRepository;
// import com.demo.rrss.rrssbackend.rest.request.BookmarkListRequest;

@Service
public class BookmarkListService {
    @Autowired
    BookmarkListRepository repository;

    // public BookmarkList getBookmarkList(Long bookmarkListId) {
    //     return repository.findById(bookmarkListId)
    //             .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookmark list not found"));
    // }

    // public void addBookmarkList(BookmarkListRequest request, Model model) { // TODO yetki kontrolü eklenecek
    //     Long userId = (Long) model.getAttribute("userId"); 
    //     BookmarkList bookmarkList = new BookmarkList();
    //     bookmarkList.setCategoryId(request.getCategoryId());
    //     bookmarkList.setDescription(request.getDescription());
    //     bookmarkList.setPublishDate(new java.sql.Timestamp(new java.util.Date().getTime()));

    //     bookmarkList.setTitle(request.getTitle());
    //     bookmarkList.setUserId(userId);
    //     repository.save(bookmarkList);
    // }

    // public void updateBookmarkList(Long bookmarkListId, BookmarkListRequest request) { // TODO yetki kontrolü eklenecek
    //     Optional<BookmarkList> existingBookmarkList = repository.findById(bookmarkListId);
    //     if (existingBookmarkList.isPresent()) {
    //         BookmarkList bookmarkList = existingBookmarkList.get();
    //         bookmarkList.setCategoryId(request.getCategoryId());
    //         bookmarkList.setDescription(request.getDescription());
    //         bookmarkList.setPublishDate(new java.sql.Timestamp(new java.util.Date().getTime()));
    //         bookmarkList.setTitle(request.getTitle());
    //         bookmarkList.setUserId(request.getUserId());
    //         repository.save(bookmarkList);
    //     } else {
    //         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookmark list not found");
    //     }
    // }

    // public void deleteBookmarkList(Long bookmarkListId) { // TODO yetki kontrolü eklenecek
    //     if (repository.existsById(bookmarkListId))
    //         repository.deleteById(bookmarkListId);
    //     else
    //         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookmark list not found");

    // }

    // public List<BookmarkList> getAllBookmarkLists(Long categoryId) { // TODO yetki kontrolü eklenecek
    //     if (categoryId == -1)
    //         return repository.findAllMax50();
    //     else {
    //         List<BookmarkList> bookmarkLists = repository.findBookmarkListsByCategoryId(categoryId);
    //         return bookmarkLists;
    //     }
    // }

    public List<BookmarkList> getUsersAllBookmarkLists(Long userId) {
        return repository.findByUserId(userId);
    }
}