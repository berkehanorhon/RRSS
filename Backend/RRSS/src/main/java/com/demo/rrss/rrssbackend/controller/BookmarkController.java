package com.demo.rrss.rrssbackend.controller;

import com.demo.rrss.rrssbackend.entity.BookmarkList;
import com.demo.rrss.rrssbackend.rest.request.BookmarkListRequest;
import com.demo.rrss.rrssbackend.service.BookmarkListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookmark")
public class BookmarkController {
    private final BookmarkListService bookmarkListService;

    public BookmarkController(BookmarkListService bookmarkListService) {
        this.bookmarkListService = bookmarkListService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createBookmarkList(@RequestHeader("Authorization") String token){
        bookmarkListService.addBookmarkList(token);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateBookmarkList(@RequestBody BookmarkListRequest request, @RequestHeader("Authorization") String token){
        bookmarkListService.updateBookmarkList(request, token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteBookmarkList(@RequestBody BookmarkListRequest request, @RequestHeader("Authorization") String token){
        bookmarkListService.deleteBookmarkList(request, token);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list/getAll")
    public ResponseEntity<?> getAllBookmarkFromList(@RequestParam Long bookmarkListId){
        return new ResponseEntity<>(bookmarkListService.getAllBookmarksFromList(bookmarkListId), HttpStatus.OK);
    }

    @PostMapping("/list/add")
    public ResponseEntity<Void> addBookmark(@RequestParam Long bookmarkListId, @RequestParam Long productId) {
        bookmarkListService.addBookmarkToList(bookmarkListId, productId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/list/remove")
    public ResponseEntity<Void> removeBookmarkFromList(@RequestParam Long bookmarkId) {
        bookmarkListService.removeBookmarkFromList(bookmarkId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/list/removeAll")
    public ResponseEntity<Void> removeAllBookmarksFromList(@RequestParam Long bookmarkListId) {
        bookmarkListService.removeAllBookmarksFromList(bookmarkListId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
