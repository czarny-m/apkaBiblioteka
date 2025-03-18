package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // <- TO JEST KLUCZ!
    private Long id;

    private String title;

    private String author;

    private String isbn;

    private boolean available = true;
}

