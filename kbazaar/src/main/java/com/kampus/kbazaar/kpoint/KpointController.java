package com.kampus.kbazaar.kpoint;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class KpointController {

    private final KpointService kpointService;

    public KpointController(KpointService kpointService) {
        this.kpointService = kpointService;
    }

    //    @ApiResponse(
//            responseCode = "200",
//            description = "Update kpoint success",
//            content = {
//                @Content(
//                        mediaType = "application/json",
//                        schema = @Schema(implementation = KpointResponse.class))
//            })
//    @ApiResponse(
//            responseCode = "404",
//            description = "product not found",
//            content =
//                    @Content(
//                            mediaType = "application/json",
//                            schema = @Schema(implementation = NotFoundException.class)))
    @PostMapping("/kpoints/{username}/earn")
    public KpointResponse getKpoint(@PathVariable String username , @RequestBody KpointRequest kpointRequest) {
        return kpointService.getKpoint(username,kpointRequest);
    }
}
