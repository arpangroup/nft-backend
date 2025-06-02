package com.arpangroup.nft_core.appconfig.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppConfigUpdateRequest {
    private String key;
    private String value;
}
