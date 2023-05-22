package com.gdu.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
  private int no;
  private String title;
  private String genre;
  private String description;
  private double star;
}
