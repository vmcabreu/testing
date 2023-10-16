package com.crai.platform.watchlist.controller;

import com.crai.platform.watchlist.dto.WatchlistDto;
import com.crai.platform.watchlist.params.WatchlistParams;
import com.crai.platform.watchlist.repository.WatchlistRespository;
import com.crai.platform.watchlist.service.WatchlistService;
import com.crai.platform.watchlist.specifications.WatchlistSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("watchlist")
public class WatchlistController {

    @Autowired
    WatchlistService watchlistService;

    @Autowired
    WatchlistRespository watchlistRepository;

    @Autowired
    ModelMapper modelMapper;

    @Operation(summary = "Find All WatchList", description = "Find all watchlist", tags = { "watchlist" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = WatchlistDto.class)))) ,
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping (value = "/all" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<WatchlistDto>> findWatchlist(){

        return ResponseEntity.ok(watchlistService.findAllWatchlist());
    }


    @Operation(summary = "Save watchlist", description = "Save new watchlist into dataBase", tags = { "watchlist" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = WatchlistDto.class)))) ,
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody()
    public ResponseEntity<WatchlistDto> save(@RequestBody WatchlistDto watchlistDto){
        return ResponseEntity.ok(watchlistService.save(watchlistDto));
    }



    @Operation(summary = "Find watchlist by role", description = "Find all by role", tags = { "watchlist" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = WatchlistDto.class)))) ,
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping (value = "/role" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<WatchlistDto>> findWatchlistByRole(
            @RequestBody WatchlistParams params

    ){
        return ResponseEntity.ok(watchlistService.findWatchListByRole(params));
    }

    @Operation(summary = "Find wathclist by status", description = "Find wathclist by  status", tags = { "watchlist" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = WatchlistDto.class)))) ,
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping (value = "/status" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<WatchlistDto>> findWatchlistByStatus(
            @RequestBody WatchlistParams params
    ){

        return ResponseEntity.ok(watchlistService.findWatchListByStatus(params));
    }

    @Operation(summary = "Find wathclist by role and status", description = "Find wathclist by role and status", tags = { "watchlist" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = WatchlistDto.class)))) ,
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping (value = "/role/status" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<WatchlistDto>> findWatchlistByStatusAndRole(
            @RequestBody WatchlistParams params
    ){

        return ResponseEntity.ok(watchlistService.findWatchListByRoleAndStatus(params));
    }

    @Operation(summary = "Save user", description = "Save new user into dataBase", tags = { "example" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = WatchlistDto.class)))) ,
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping(path = "{id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody()
    public  ResponseEntity<WatchlistDto> update(@RequestBody WatchlistDto watchlistDto, @PathVariable Long id){

        return ResponseEntity.ok(watchlistService.update(watchlistDto, id));
    }

    @Operation(summary = "Delete watchlist", description = "Delete watchlist", tags = { "watchlist" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = WatchlistDto.class)))) ,
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @DeleteMapping (value = "/delete" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public Long deleteWatchlistById(
            @RequestBody WatchlistParams params
    ){
        return watchlistService.findWatchlistByIdForRemoving(params);
    }

}
