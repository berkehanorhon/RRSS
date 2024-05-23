package com.demo.rrss.rrssbackend.service;

import com.demo.rrss.rrssbackend.entity.Product;
import com.demo.rrss.rrssbackend.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {
    private final ProductRatingRepository productRatingRepository;
    private final ReviewRepository reviewRepository;
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkListRepository bookmarkListRepository;
    private final ProductRepository productRepository;

    public RecommendationService(ProductRatingRepository productRatingRepository, ReviewRepository reviewRepository,
                                 BookmarkRepository bookmarkRepository, ProductRepository productRepository,
                                 BookmarkListRepository bookmarkListRepository) {
        this.productRatingRepository = productRatingRepository;
        this.reviewRepository = reviewRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.bookmarkListRepository = bookmarkListRepository;
        this.productRepository = productRepository;
    }

    private static final int RECOMMENDATION_LIMIT_PER_CATEGORY = 5;

    public Set<Product> getRecommendation(Long userId) {

        // long startTime = System.nanoTime();
        // Kullanıcının rating verdiği ürünleri çek.
        Set<Long> highRatingProductIds = productRatingRepository.findProductIdsByUserIdAndStarRatingGreaterThanEqual(userId, (short) 4);
        List<Product> highRatingProducts = productRepository.findAllByIdsSet(highRatingProductIds);
        Set <Product> interestedProducts= new HashSet<>(highRatingProducts);

        // long afterHighRatingsFetchTime = System.nanoTime();
        // System.out.println("Time to fetch high ratings: " + (afterHighRatingsFetchTime - startTime) / 1000000 + " ms");
        // Kullanıcının review yaptığı ürünleri çek.
        List<Long> reviewProductIds = reviewRepository.findProductIdsByUserId(userId);
        List<Product> reviewProducts = productRepository.findAllByIdsList(reviewProductIds);
        interestedProducts.addAll(reviewProducts);

        // long afterReviewFetchTime = System.nanoTime();
        // System.out.println("Time to fetch reviews: " + (afterReviewFetchTime - afterHighRatingsFetchTime) / 1000000 + " ms");
        // Kullanıcının bookmarkladığı ürünleri her listeden çek.
        List<Long> bookmarkListIds = bookmarkListRepository.findBookmarkListIdByUserId(userId);
        Set<Long> bookmarkProductIds = bookmarkRepository.findProductIdsByBookmarkListIds(bookmarkListIds);
        List<Product> products = productRepository.findAllByIdsSet(bookmarkProductIds);
        interestedProducts.addAll(products);

        //long afterBookmarkFetchTime = System.nanoTime();
        //System.out.println("Time to fetch bookmarks: " + (afterBookmarkFetchTime - afterReviewFetchTime) / 1000000 + " ms");
        // Kullanıcıdan çekilen ürünlerin kategorilerini çek.
        Set<Long> categoryIds = interestedProducts.stream().map(Product::getCategoryId).collect(Collectors.toSet());
        List<Long> productIds = productRepository.findProductIdsByCategoryIds(categoryIds);

        // long afterProductByCategoryFetchTime = System.nanoTime();
        // System.out.println("Time to fetch products by category: " + (afterProductByCategoryFetchTime - afterBookmarkFetchTime) / 1000000 + " ms");
        // Bütün ortalama rating ve review sayılarını çek.
        List<Object[]> averageRatingsData = productRatingRepository.findAverageRatingsByProductIds(productIds);
        if (averageRatingsData == null) {
            throw new RuntimeException("No products found in the categories of the interested products. Average ratings could not be calculated.");
        }
        Map<Long, Double> averageRatings = averageRatingsData.stream()
                .collect(Collectors.toMap(data -> (Long) data[0], data -> (Double) data[1]));

        // long afterAverageRatingsRepoFetchTime = System.nanoTime();
        // System.out.println("Time to fetch average ratings: " + (afterAverageRatingsRepoFetchTime - afterProductByCategoryFetchTime) / 1000000 + " ms");
        List<Object[]> reviewCountsData = reviewRepository.countReviewsByProductIds(productIds);
        if (reviewCountsData == null) {
            throw new RuntimeException("No products found in the categories of the interested products. Review counts could not be calculated.");
        }
        Map<Long, Long> reviewCounts = reviewCountsData.stream()
                .collect(Collectors.toMap(data -> (Long) data[0], data -> (Long) data[1]));

        // long afterReviewCountsRepoFetchTime = System.nanoTime();
        // System.out.println("Time to fetch review counts: " + (afterReviewCountsRepoFetchTime - afterAverageRatingsRepoFetchTime) / 1000000 + " ms");
        // Şimdi önermeye sıra geldi.
        Set<Product> newRecommendedProducts = new HashSet<>();
        for (Long categoryId : categoryIds) {
            // Kategorideki ürünleri çek.
            List<Product> productsInCategory = productRepository.findProductsByCategoryId(categoryId);
            // long afterProductsInCategoryFetchTime = System.nanoTime();
            // System.out.println("Time to fetch products in some category: " + (afterProductsInCategoryFetchTime - afterReviewCountsRepoFetchTime) / 1000000 + " ms");
            // Önce ortalama rating ve sonra review sayısına göre sırala.
            productsInCategory.sort((p1, p2) -> {
                Double avgRating1 = averageRatings.getOrDefault(p1.getProductId(), Double.NEGATIVE_INFINITY);
                Double avgRating2 = averageRatings.getOrDefault(p2.getProductId(), Double.NEGATIVE_INFINITY);
                Long reviewCount1 = reviewCounts.getOrDefault(p1.getProductId(), 0L);
                Long reviewCount2 = reviewCounts.getOrDefault(p2.getProductId(), 0L);

                // Önce average rating'e göre sırala, eğer ortalamaları aynıysa review sayısına göre sırala.
                // (ortalamaları yoksa da eşit olacak ve ratingComparison 0 olacak)
                int ratingComparison = avgRating2.compareTo(avgRating1);
                return ratingComparison != 0 ? ratingComparison : reviewCount2.compareTo(reviewCount1);
            });

            // long afterSortingTime = System.nanoTime();
            //System.out.println("Time to sort products in some category: " + (afterSortingTime - afterProductsInCategoryFetchTime) / 1000000 + " ms");

            for (Product product : productsInCategory) {
                // long afterProduct = System.nanoTime();
                // System.out.println("Time to check a product: " + (afterProduct - afterSortingTime) / 1000000 + " ms");

                // Eğer kullanıcı zaten daha önce bir şekilde bu ürünü beğenmişse, listesine eklemişse önüne çıkarma.
                if (!interestedProducts.contains(product)) {
                    newRecommendedProducts.add(product);
                    // Belli bir sayıda öneriden sonra önerme, bunu yukarıdaki global değişken ile değiştirebiliriz.
                    if (newRecommendedProducts.size() >= RECOMMENDATION_LIMIT_PER_CATEGORY) {
                        break;
                    }
                }
            }
            // System.out.println("---------");
        }

        return newRecommendedProducts;
    }
}
