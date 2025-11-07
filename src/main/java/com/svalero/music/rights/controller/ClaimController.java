package com.svalero.music.rights.controller;

import com.svalero.music.rights.service.ClaimService;
import org.springframework.stereotype.Controller;

@Controller
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
}


}
