package com.demo.rrss.rrssbackend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.demo.rrss.rrssbackend.entity.Bookmark;
import com.demo.rrss.rrssbackend.repository.BookmarkRepository;
import com.demo.rrss.rrssbackend.repository.ProductRepository;
import com.demo.rrss.rrssbackend.rest.request.BookmarkListRequest;
import com.demo.rrss.rrssbackend.rest.request.BookmarkRequest;

import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.demo.rrss.rrssbackend.entity.BookmarkList;
import com.demo.rrss.rrssbackend.repository.BookmarkListRepository;

@Service
public class BookmarkListService {
    private final BookmarkListRepository bookmarkListRepository;
    private final BookmarkRepository bookmarkRepository;
    private final ProductRepository productRepository;

    public BookmarkListService(BookmarkListRepository BL, BookmarkRepository B,
                               ProductRepository P) {
        this.bookmarkListRepository = BL;
        this.bookmarkRepository = B;
        this.productRepository = P;
    }

    /**
     * Adds a bookmark list to a user.
     * @param model - JWT token of the user.
     */
    public void addBookmarkList(BookmarkListRequest request, Model model) {
        Long userId = (Long) model.getAttribute("userId");
        BookmarkList bookmarkList = new BookmarkList();
        bookmarkList.setTitle(request.getTitle());
        bookmarkList.setUserId(userId);
        bookmarkListRepository.save(bookmarkList);
    }

    /**
     * Updates a bookmark list.
     * @param request - Request object containing the ID of the bookmark list to be updated.
     * @param model - JWT token of the user.
     */
    public void updateBookmarkList(BookmarkListRequest request, Model model) {
        Long userId = (Long) model.getAttribute("userId");
        Long bookmarkListID = request.getBookmarkListId();
        Optional<BookmarkList> existingBookmarkList = bookmarkListRepository.findById(bookmarkListID);
        if (existingBookmarkList.isPresent()) {
            BookmarkList bookmarkList = existingBookmarkList.get();
            if (!Objects.equals(bookmarkList.getUserId(), userId))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update this bookmark list");
            bookmarkList.setTitle(request.getTitle());
            bookmarkListRepository.save(bookmarkList);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookmark list not found");
        }
    }

    /**
     * Deletes a bookmark list.
     * @param request - Request object containing the ID of the bookmark list to be deleted.
     * @param model - JWT token of the user.
     */
    public void deleteBookmarkList(BookmarkListRequest request, Model model) {
        Long userId = (Long) model.getAttribute("userId");
        Long bookmarkListID = request.getBookmarkListId();
        Optional<BookmarkList> existingBookmarkList = bookmarkListRepository.findById(bookmarkListID);

        if (existingBookmarkList.isPresent()) {
            BookmarkList bookmarkList = existingBookmarkList.get();
            if (!Objects.equals(bookmarkList.getUserId(), userId))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to delete this bookmark list");
            bookmarkListRepository.deleteById(bookmarkList.getBookmarkListId());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookmark list not found");
        }
    }

    // TODO Yukarıdaki fonksiyonlarda sadece kullanıcılara ait bookmark listeleri üzerinde işlem yapmalarına izin veriliyor.
    // Ancak aşağıdaki fonksiyonlar böyle bir kısıt yok ve bunun gerekliliği tartışılabilir.

    /**
     * Returns all bookmark lists of a user.
     * @param userId - ID of the user.
     * @return List of bookmark lists.
     */
    public List<BookmarkList> getUsersAllBookmarkLists(Long userId, Model model) {
        if (userId == -1)
            userId = (Long) model.getAttribute("userId");
        return bookmarkListRepository.findByUserId(userId);
    }

    /**
     * Returns all bookmarks of a bookmark list.
     * @param bookmarkListId - ID of the bookmark list.
     * @return List of bookmarks.
     */
    public List<BookmarkRequest> getAllBookmarksFromList(Long bookmarkListId) {
        List<BookmarkRequest> bookmarks = new ArrayList<>();
        for(Bookmark bookmark : bookmarkRepository.findByBookmarkListId(bookmarkListId))
        {
            BookmarkRequest bookmarkRequest = new BookmarkRequest();
            bookmarkRequest.setBookmarkId(bookmark.getBookmarkId());
            bookmarkRequest.setBookmarkListId(bookmark.getBookmarkListId());
            bookmarkRequest.setProductId(bookmark.getProductId());
            bookmarkRequest.setCreationDate(bookmark.getCreationDate());
            bookmarkRequest.setProduct(productRepository.findById(bookmark.getProductId()).get());
            bookmarks.add(bookmarkRequest);
        }
        return bookmarks;
    }

    /**
     * Adds a product to a bookmark list.
     * @param bookmarkListId - ID of the bookmark list.
     * @param productId - ID of the product.
     */
    public void addBookmarkToList(Long bookmarkListId, Long productId, Model model) {
        if (bookmarkListRepository.findById(bookmarkListId).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookmark list not found");
        if (bookmarkListRepository.findById(bookmarkListId).get().getUserId() != (Long) model.getAttribute("userId"))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "You do not have permission to add a bookmark to this list");
        if (productRepository.findById(productId).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        if (bookmarkRepository.findByBookmarkListIdAndProductId(bookmarkListId, productId) != null)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Product already in bookmark list");
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmarkListId(bookmarkListId);
        bookmark.setProductId(productId);
        bookmarkRepository.save(bookmark);
    }

    public void removeBookmarkFromList(Long bookmarkId) {
        if (bookmarkRepository.findById(bookmarkId).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookmark not found");
        bookmarkRepository.deleteById(bookmarkId);
    }

    /**
     * Removes all bookmarks from a bookmark list.
     * @param bookmarkListId - ID of the bookmark list.
     */
    public void removeAllBookmarksFromList(Long bookmarkListId) {
        if (bookmarkListRepository.findById(bookmarkListId).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookmark list not found");
        List<Bookmark> bookmarks = bookmarkRepository.findByBookmarkListId(bookmarkListId);
        for (Bookmark bookmark : bookmarks) {
            bookmarkRepository.deleteById(bookmark.getBookmarkId());
        }
    }

    /**
     * Removes all bookmarks of a user from all bookmark lists. Removes also all bookmark lists of user.
     * @param userId - ID of the user.
     */
    public void removeAllBookmarksOfUser(Long userId) {
        List<BookmarkList> bookmarkLists = bookmarkListRepository.findByUserId(userId);
        for (BookmarkList bookmarkList : bookmarkLists) {
            removeAllBookmarksFromList(bookmarkList.getBookmarkListId());
        }
    }
}