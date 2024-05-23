package com.demo.rrss.rrssbackend.rest.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PermissionRequest {
    private Long userId;
    private boolean setAdmin;
    private boolean setMerchant;
    private boolean setModerator;
    private boolean banUser;

}
