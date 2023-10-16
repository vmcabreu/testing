package com.crai.platform.watchlist.service;

import com.crai.platform.watchlist.domain.Watchlist;
import com.crai.platform.watchlist.dto.WatchlistDto;
import com.crai.platform.watchlist.enums.AlertStatus;
import com.crai.platform.watchlist.params.WatchlistParams;
import com.crai.platform.watchlist.repository.WatchlistRespository;
import com.crai.platform.watchlist.specifications.WatchlistSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WatchlistServiceTest {

    @Mock
    private WatchlistRespository watchlistRespository;

    @Spy
    ModelMapper modelMapper=new ModelMapper();

    @InjectMocks
    private WatchlistService watchlistService;


    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void save() {

        when(watchlistRespository.save(any())).thenReturn(Watchlist.builder().id(1L).name("Nombre")
                        .title("titulo")
                        .userRoles("ADMINISTRADOR,OPERADOR")
                .build());
        WatchlistDto dto = watchlistService.save(WatchlistDto.builder().name("Nombre")
                .title("titulo")
                .userRoles("ADMINISTRADOR,OPERADOR").build());
        assertEquals("Nombre",dto.getName());
        assertEquals("titulo",dto.getTitle());
        assertEquals("ADMINISTRADOR,OPERADOR",dto.getUserRoles());
    }

    @Test
    void modify(){
    when(watchlistRespository.findById(any())).thenReturn(Optional.of(Watchlist.builder().id(1L).title("titulo").build()));
    when(watchlistRespository.save(any())).thenReturn(Watchlist.builder().id(1L).title("Titulo2").name("Nombre").build());
    WatchlistDto watchlistDto = watchlistService.update(WatchlistDto.builder().name("Nombre").build(), Long.valueOf("1"));
    assertEquals("Titulo2",watchlistDto.getTitle());
    }

    @Test
    void findWatchlistByIdForRemoving(){
        when(watchlistRespository.delete(any(WatchlistSpecification.class))).thenReturn(Watchlist.builder().id(1L).build().getId());
        WatchlistParams watchlistParam = new WatchlistParams();
        watchlistParam.setId(1L);
        Long numberLong = watchlistService.findWatchlistByIdForRemoving(watchlistParam);
        assertEquals(1L,numberLong);

    }

    @Test
    void findWatchListByRole() {
        List<Watchlist> watchlists = Arrays.asList(
                Watchlist.builder().id(1L).name("Nombre1").title("titulo1").userRoles("ADMINISTRADOR").build(),
                Watchlist.builder().id(3L).name("Nombre3").title("titulo3").userRoles("ADMINISTRADOR").build()
        );


        List<WatchlistDto> expectedDtos = watchlists.stream()
                .map(watchlist -> modelMapper.map(watchlist,WatchlistDto.class))
                .collect(Collectors.toList());
        Page<Watchlist> pageData = new PageImpl<>(watchlists);
        Page<WatchlistDto> pagedResponse = new PageImpl<>(expectedDtos);

        when(watchlistRespository.findAll(any(WatchlistSpecification.class),any(Pageable.class))).thenReturn(pageData);

        WatchlistParams watchlistParam = new WatchlistParams();
        watchlistParam.setUserRoles("ADMINISTRADOR");

        Page<WatchlistDto> result = watchlistService.findWatchListByRole(watchlistParam);
        assertEquals(pagedResponse,result);
    }

@Test
void findWathclistByRoleAndStatus(){
    List<Watchlist> watchlists = Arrays.asList(
            Watchlist.builder().id(1L).name("Nombre1").title("titulo1").userRoles("ADMINISTRADOR").status(AlertStatus.ABIERTO).build(),
            Watchlist.builder().id(3L).name("Nombre3").title("titulo3").userRoles("ADMINISTRADOR").status(AlertStatus.ABIERTO).build()
    );


    List<WatchlistDto> expectedDtos = watchlists.stream()
            .map(watchlist -> modelMapper.map(watchlist,WatchlistDto.class))
            .collect(Collectors.toList());
    Page<Watchlist> pageData = new PageImpl<>(watchlists);
    Page<WatchlistDto> pagedResponse = new PageImpl<>(expectedDtos);
    


    when(watchlistRespository.findAll(any(WatchlistSpecification.class),any(Pageable.class))).thenReturn(pageData);

    WatchlistParams watchlistParam = new WatchlistParams();
    watchlistParam.setUserRoles("ADMINISTRADOR");
    watchlistParam.setStatus(AlertStatus.ABIERTO);

    Page<WatchlistDto> result = watchlistService.findWatchListByRoleAndStatus(watchlistParam);
    assertEquals(pagedResponse,result);
}
    @Test
    void findWathclistByStatus(){
        List<Watchlist> watchlists = Arrays.asList(
                Watchlist.builder().id(1L).name("Nombre1").title("titulo1").status(AlertStatus.ABIERTO).build(),
                Watchlist.builder().id(3L).name("Nombre3").title("titulo3").status(AlertStatus.ABIERTO).build()
        );


        List<WatchlistDto> expectedDtos = watchlists.stream()
                .map(watchlist -> modelMapper.map(watchlist,WatchlistDto.class))
                .collect(Collectors.toList());
        Page<Watchlist> pageData = new PageImpl<>(watchlists);
        Page<WatchlistDto> pagedResponse = new PageImpl<>(expectedDtos);



        when(watchlistRespository.findAll(any(WatchlistSpecification.class),any(Pageable.class))).thenReturn(pageData);

        WatchlistParams watchlistParam = new WatchlistParams();
        watchlistParam.setStatus(AlertStatus.ABIERTO);

        Page<WatchlistDto> result = watchlistService.findWatchListByStatus(watchlistParam);
        assertEquals(pagedResponse,result);
    }

}
