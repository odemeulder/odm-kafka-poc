package us.odm.producer;

import lombok.Data;

@Data
public class Subscription {
    private int id;
    private int user_id;
    private int product_id;
}
