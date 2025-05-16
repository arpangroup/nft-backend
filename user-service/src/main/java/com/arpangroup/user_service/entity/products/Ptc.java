package com.arpangroup.user_service.entity.products;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ptc")
@Getter
@NoArgsConstructor
public class Ptc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String adType; // DEFAULT '0' '1 = link | 2 = image | 3 = script'
    private String title;
    private String adsBody;
    private double amount;
    private int duration;
    private int maxShow;
    private int showed;
    private int remain;
    private int status; // DEFAULT '1' 'Status 0 = ptc inactive, 1 = ptc active'

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

}
