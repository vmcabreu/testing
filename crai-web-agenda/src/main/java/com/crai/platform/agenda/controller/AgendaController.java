package com.crai.platform.agenda.controller;

import com.crai.platform.agenda.dto.AgendaDto;
import com.crai.platform.agenda.params.AgendaParams;
import com.crai.platform.agenda.service.AgendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("agenda")
public class AgendaController {
    @Autowired
    AgendaService agendaService;

    @Operation(summary = "All Agenda", description = "print all contacts on agenda", tags = { "All Agenda" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Page<AgendaDto>>  search(@RequestBody AgendaParams params) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok(agendaService.search(params));
    }
}
