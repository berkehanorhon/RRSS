package com.demo.rrss.rrssbackend.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.demo.rrss.rrssbackend.entity.Bookmark;
import com.demo.rrss.rrssbackend.repository.BookmarkRepository;
import com.demo.rrss.rrssbackend.repository.ProductRepository;
import com.demo.rrss.rrssbackend.rest.request.BookmarkListRequest;
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
    private final JwtUtil jwtUtil; // TODO JWTyi böyle çağırmak ne kadar makul?

    public BookmarkListService(BookmarkListRepository BL, BookmarkRepository B,
                               ProductRepository P, JwtUtil jwtUtil) {
        this.bookmarkListRepository = BL;
        this.bookmarkRepository = B;
        this.productRepository = P;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Adds a bookmark list to a user.
     * @param token - JWT token of the user.
     */
    public void addBookmarkList(String token) {
        Long userId = jwtUtil.extractUserId(token);
        BookmarkList bookmarkList = new BookmarkList();
        bookmarkList.setUserId(userId);
        bookmarkListRepository.save(bookmarkList);
    }

    /**
     * Updates a bookmark list.
     * @param request - Request object containing the ID of the bookmark list to be updated.
     * @param token - JWT token of the user.
     */
    public void updateBookmarkList(BookmarkListRequest request, String token) {
        Long userId = jwtUtil.extractUserId(token);
        Long bookmarkListID = request.getBookmarkListId();
        Optional<BookmarkList> existingBookmarkList = bookmarkListRepository.findById(bookmarkListID);
        if (existingBookmarkList.isPresent()) {
            BookmarkList bookmarkList = existingBookmarkList.get();
            if (!Objects.equals(bookmarkList.getUserId(), userId))
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to update this bookmark list");
            // TODO Burada BookmarkList editlenecek ama neden bunu yapmak isteyeceğimizi anlayamadım. Editlenecek bir field yok?
            bookmarkList.setCreationDate(new java.sql.Timestamp(new java.util.Date().getTime())); //Gerçekten update olduğunu anlayabilmek için ekledim.
            bookmarkListRepository.save(bookmarkList);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookmark list not found");
        }
    }

    /**
     * Deletes a bookmark list.
     * @param request - Request object containing the ID of the bookmark list to be deleted.
     * @param token - JWT token of the user.
     */
    public void deleteBookmarkList(BookmarkListRequest request, String token) {
        Long userId = jwtUtil.extractUserId(token);
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

    /**
     * Returns all bookmark lists of a user.
     * @param userId - ID of the user. //TODO (taken from JWT token)
     * @return List of bookmark lists.
     */
    public List<BookmarkList> getUsersAllBookmarkLists(Long userId) {
        return bookmarkListRepository.findByUserId(userId);
    }

    /**
     * Returns all bookmarks of a bookmark list.
     * @param bookmarkListId - ID of the bookmark list.
     * @return List of bookmarks.
     */
    public List<Bookmark> getAllBookmarksFromList(Long bookmarkListId) {
        return bookmarkRepository.findByBookmarkListId(bookmarkListId);
    }

    /**
     * Adds a product to a bookmark list.
     * @param bookmarkListId - ID of the bookmark list.
     * @param productId - ID of the product.
     */
    public void addBookmarkToList(Long bookmarkListId, Long productId) {
        // TODO Burada her listeye bir şey eklendiğinde creationDate güncelleyebiliriz.
        // TODO Aynı şekilde bu özellik aşağıdaki delete fonksiyonuna da eklenebilir.
        Bookmark bookmark = new Bookmark();
        if (bookmarkListRepository.findById(bookmarkListId).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bookmark list not found");
        bookmark.setBookmarkListId(bookmarkListId);
        if (productRepository.findById(productId).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        bookmark.setProductId(productId);
        bookmarkRepository.save(bookmark);
    }

    public void removeBookmarkFromList(Long bookmarkId) {
        bookmarkRepository.deleteById(bookmarkId);
    }

    /**
     * Removes all bookmarks from a bookmark list.
     * @param bookmarkListId - ID of the bookmark list.
     */
    public void removeAllBookmarksFromList(Long bookmarkListId) {
        List<Bookmark> bookmarks = bookmarkRepository.findByBookmarkListId(bookmarkListId);
        for (Bookmark bookmark : bookmarks) {
            bookmarkRepository.deleteById(bookmark.getBookmarkId());
        }
    }

    /**
     * Removes all bookmarks of a user from all bookmark lists. Removes also all bookmark lists of user.
     * @param userId - ID of the user. //TODO (taken from JWT token)
     */
    public void removeAllBookmarksOfUser(Long userId) {
        List<BookmarkList> bookmarkLists = bookmarkListRepository.findByUserId(userId);
        for (BookmarkList bookmarkList : bookmarkLists) {
            removeAllBookmarksFromList(bookmarkList.getBookmarkListId());
        }
    }
}