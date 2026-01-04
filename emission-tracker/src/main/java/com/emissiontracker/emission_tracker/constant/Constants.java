package com.emissiontracker.emission_tracker.constant;

import lombok.Getter;

/*
source:     https://www.co2everything.com/categories
 */
public class Constants {

    // ---------------- Clothes ----------------
    // emission per 2 square meter
    public static class ClothesEmission {
        public static final float WOOL_CO2 = 13.89f;
        public static final float COTTON_CO2 = 8.3f;
        public static final float SILK_CO2 = 7.63f;
        public static final float POLYESTER_OR_NYLON_CO2 = 7f;
    }

    // ---------------- Food ----------------
    // emission per 100g
    public static class FoodEmission {
        public static final float BEEF_CO2 = 15.5f;
        public static final float MUTTON_CO2 = 4.55f;
        public static final float PORK_OR_CHICKEN_CO2 = 2.2f;
        public static final float FISH_CO2 = 1.4f;
        public static final float OTHER_NONVEG_FOOD_CO2 = 4.0f;

        public static final float CHEESE_CO2 = 2.79f;
        public static final float RICE_CO2 = .16f;
        public static final float EGG_CO2 = .53f;
        public static final float OTHER_VEG_FOOD_CO2 = 0.3f;
    }

    // ---------------- Household ----------------
    // emission of co2 by uses of following appliances per hour
    public static class HouseholdEmission {
        public static final float HEATING_OR_COOLING_CO2 = 1.23f;
        public static final float LED_LIGHTS_CO2 = 0.007f;
        public static final float TUBE_LIGHTS_CO2 = 0.035f;
        public static final float WASHING_WITH_DRYER_CO2= 2.4f;
        public static final float WASHING_WITHOUT_DRYER_CO2= 0.7f;
        public static final float HAIR_DRYER_CO2 = 2.0f;
        public static final float WATER_HEATING_CO2 = 0.0095f;


    }

    // ---------------- Transportation ----------------

    public static class TransportationEmission {
        public static final float PLANE_CO2 = .146f;
        public static final float BUS_CO2 = 0.11f;
        public static final float CAR_CO2 = .12f;
        public static final float BIKE_CO2= 0.113f;
        public static final float EV_CO2=0.081f;
    }

    // ---------------- Waste ----------------
    // emission by waste of both types of waste by 5,10,20 kg bag
    public static class WasteGenerationEmission {

        public static final float NON_BIO_SMALL_CO2 = 12.5f;    // 5kg
        public static final float NON_BIO_MEDIUM_CO2 = 25f;     // 10kg
        public static final float NON_BIO_LARGE_CO2 = 50f;      //20kg

        public static final float BIO_SMALL_CO2 = 2f;       // 5kg
        public static final float BIO_MEDIUM_CO2 = 4f;      // 10kg
        public static final float BIO_LARGE_CO2 = 8f;       // 20kg
    }
}
