package com.crai.platform.watchlist.controller;

import com.crai.platform.watchlist.dto.WatchlistDto;
import com.crai.platform.watchlist.params.WatchlistParams;
import com.crai.platform.watchlist.repository.WatchlistRespository;
import com.crai.platform.watchlist.service.WatchlistService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WatchlistController.class)
public class WatchlistControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private WatchlistRespository watchlistRespository;
    @MockBean
    private WatchlistService watchlistService;
/*
    @Test
    void findWatchlistByRole() throws Exception {
        //Data test
        WatchlistParams params = new WatchlistParams();
        params.setUserRoles("ADMINISTRADOR");
        List<WatchlistDto> watchlistDtos = new ArrayList<>();
        Page<WatchlistDto> pagedResponse = new PageImpl<>(watchlistDtos);
        //Simulated service behavior
        Mockito.when(watchlistService.findWatchListByRole(params)).thenReturn(pagedResponse);
        //Checking controller method
        mvc.perform(MockMvcRequestBuilders.get("/watchlist/role"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
    }

    @Test
    void findAllWatchlist() throws Exception{
        //Data test
        List<WatchlistDto> watchlistDtos = new ArrayList<>();
        Page<WatchlistDto> pagedResponse = new PageImpl<>(watchlistDtos);
        //Simulated service behavior
        Mockito.when(watchlistService.findAllWatchlist()).thenReturn(pagedResponse);
        //Checking controller method
        mvc.perform(MockMvcRequestBuilders.get("/watchlist/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
    }
*/
}
