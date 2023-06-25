package by.tms.eshop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Location {

    CART(true, false),
    FAVORITE(false, true);

    private final boolean cart;
    private final boolean favorite;
}
