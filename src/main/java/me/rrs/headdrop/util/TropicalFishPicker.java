package me.rrs.headdrop.util;

import me.rrs.headdrop.database.EntityHead;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static me.rrs.headdrop.database.EntityHead.*;
import static me.rrs.headdrop.database.EntityHead.TROPICAL_FISH_LIGHT_GRAY;

public class TropicalFishPicker {

    private static List<EntityHead> headList = Arrays.asList(
        TROPICAL_FISH_ORANGE,
        TROPICAL_FISH_MAGENTA,
        TROPICAL_FISH_LIGHT_BLUE,
        TROPICAL_FISH_YELLOW,
        TROPICAL_FISH_PINK,
        TROPICAL_FISH_GRAY,
        TROPICAL_FISH_CYAN,
        TROPICAL_FISH_BLUE,
        TROPICAL_FISH_GREEN,
        TROPICAL_FISH_RED,
        TROPICAL_FISH_BLACK,
        TROPICAL_FISH_LIGHT_GRAY
    );
    private static int headListSize = headList.size();
    private static Random rng = new Random();
    public static EntityHead getRandomEntityHead() {
        int tropicalFishListIndex = rng.nextInt(headListSize);

        EntityHead entityHead = headList.get(tropicalFishListIndex);
        return entityHead;
    }
}
