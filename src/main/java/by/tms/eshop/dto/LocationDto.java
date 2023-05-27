package by.tms.eshop.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LocationDto {

    private boolean cart;
    private boolean favorite;
}