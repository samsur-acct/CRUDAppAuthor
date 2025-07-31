package com.example.CRUDApplication.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "Books")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String title;
  private String author;
  private String ISBN;

  @PrePersist
  @PreUpdate
  public void ensureIsbn() {
    if (this.ISBN == null || this.ISBN.trim().isEmpty() || "null".equalsIgnoreCase(this.ISBN.trim())) {
      this.ISBN = UUID.randomUUID().toString();
    }
  }
}
