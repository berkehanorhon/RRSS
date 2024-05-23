package com.demo.rrss.rrssbackend.controller;

import com.demo.rrss.rrssbackend.entity.BookmarkList;
import com.demo.rrss.rrssbackend.rest.request.BookmarkListRequest;
import com.demo.rrss.rrssbackend.service.BookmarkListService;
import com.demo.rrss.rrssbackend.service.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookmark")
public class BookmarkController {
    private final BookmarkListService bookmarkListService;
    private final JwtUtil jwtUtil;

    public BookmarkController(BookmarkListService bookmarkListService, JwtUtil jwtUtil) {
        this.bookmarkListService = bookmarkListService;
        this.jwtUtil = jwtUtil;
    }

    @ModelAttribute // TODO Herhangi bir hata durumunda 403 döndürülecek
    public void addUserIdToModel(@RequestHeader(value="Authorization") String bearerToken, Model model) {
        String token = bearerToken.substring(7);
        Long userId = jwtUtil.extractUserId(token);
        model.addAttribute("userId", userId);
    }

    @GetMapping("/get-users-lists")
    public ResponseEntity<?> getBookmarkListFromUser(@RequestParam Long userId, Model model){
        return new ResponseEntity<>(bookmarkListService.getUsersAllBookmarkLists(userId, model), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createBookmarkList(@RequestBody BookmarkListRequest request, Model model){
        bookmarkListService.addBookmarkList(request, model);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateBookmarkList(@RequestBody BookmarkListRequest request, Model model){
        bookmarkListService.updateBookmarkList(request, model);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteBookmarkList(@RequestBody BookmarkListRequest request, Model model ){
        bookmarkListService.deleteBookmarkList(request, model);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list/getAll")
    public ResponseEntity<?> getAllBookmarkFromList(@RequestParam Long bookmarkListId){
        return new ResponseEntity<>(bookmarkListService.getAllBookmarksFromList(bookmarkListId), HttpStatus.OK);
    }

    @PostMapping("/list/add")
    public ResponseEntity<Void> addBookmark(@RequestParam Long bookmarkListId, @RequestParam Long productId, Model model) {
        bookmarkListService.addBookmarkToList(bookmarkListId, productId, model);
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
