package com.turkcell.product_service.domain.model.category;

//aggregate root -> has own lifecycle
public class Category {

    private final CategoryId id;

    private String categoryName;


    private Category(CategoryId id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public static Category create(String categoryName) {
        validateCategoryName(categoryName);
        return new Category(
                CategoryId.generate(),
                categoryName
        );
    }

    public static Category rehydrate(CategoryId id, String categoryName) {
        return new Category(
                id,
                categoryName
        );
    }

    public static void validateCategoryName(String categoryName) {
        if (categoryName == null || categoryName.isBlank()) {
            throw new IllegalArgumentException("categoryName cannot be null or empty");
        }
        if (categoryName.length() < 5) {
            throw new IllegalArgumentException("categoryName cannot be less than 5");
        }
    }

    public void updateCategoryName(String newCategoryName) {
        validateCategoryName(newCategoryName);
        this.categoryName = newCategoryName.trim();
    }

    public CategoryId id() {
        return id;
    }

    public String categoryName() {
        return categoryName;
    }
}
