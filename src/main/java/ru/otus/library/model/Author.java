package ru.otus.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private long id;

   @Column(name = "name", nullable = false, unique = true)
   private String name;

   public Author(String name) {
      this.name = name;
   }
}
