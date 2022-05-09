package com.getir.keycloakmock.document;

//@Document("groceryitems")
//public class GroceryItem {
//
//        @Id
//        private String id;
//
//        private String name;
//        private int quantity;
//        private String category;
//
//        public GroceryItem(String id, String name, int quantity, String category) {
//            super();
//            this.id = id;
//            this.name = name;
//            this.quantity = quantity;
//            this.category = category;
//        }
//}

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document("user")
@AllArgsConstructor
public class User {

    @Id
    private UUID id;

    private String name;
    private String password;
    private String email;
}
