package com.crai.platform.watchlist.service;

import com.crai.platform.watchlist.domain.Watchlist;
import com.crai.platform.watchlist.dto.WatchlistDto;
import com.crai.platform.watchlist.params.WatchlistParams;
import com.crai.platform.watchlist.repository.WatchlistRespository;
import com.crai.platform.watchlist.specifications.WatchlistSpecification;
import com.crai.starter.jpa.exception.NoDataFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Transactional
@Service
public class WatchlistService {
    @Autowired
    WatchlistRespository watchlistRepository;

    @Autowired
    ModelMapper modelmapper;

    public WatchlistDto save(WatchlistDto watchlistDto) {
        Watchlist watchlist = modelmapper.map(watchlistDto, Watchlist.class);
        return modelmapper.map(watchlistRepository.save(watchlist), WatchlistDto.class);
    }




    public Page<WatchlistDto> findAllWatchlist(){
        WatchlistParams params = null;
        WatchlistParams watchlistParam = params == null ? new WatchlistParams(): params;
        WatchlistSpecification watchlistSpecification = new WatchlistSpecification(watchlistParam);
        return watchlistRepository.findAll(watchlistSpecification, watchlistSpecification.getUpdatable())
                .map(u -> modelmapper.map(u,WatchlistDto.class));
    }

    public Page<WatchlistDto> findWatchListByRole(WatchlistParams params){
        WatchlistParams watchlistParam = params == null ? new WatchlistParams(): params;
        WatchlistSpecification watchlistSpecification = new WatchlistSpecification(watchlistParam);
        return watchlistRepository.findAll(watchlistSpecification, watchlistSpecification.getUpdatable())
                .map(u -> modelmapper.map(u,WatchlistDto.class));
    }

    public Page<WatchlistDto> findWatchListByRoleAndStatus(WatchlistParams params){
        WatchlistParams watchlistParam = params == null ? new WatchlistParams(): params;
        WatchlistSpecification watchlistSpecification = new WatchlistSpecification(watchlistParam);
        return watchlistRepository.findAll(watchlistSpecification, watchlistSpecification.getUpdatable())
                .map(u -> modelmapper.map(u,WatchlistDto.class));
    }

    public Page<WatchlistDto> findWatchListByStatus(WatchlistParams params){
        WatchlistParams watchlistParam = params == null ? new WatchlistParams(): params;
        WatchlistSpecification watchlistSpecification = new WatchlistSpecification(watchlistParam);
        return watchlistRepository.findAll(watchlistSpecification, watchlistSpecification.getUpdatable())
                .map(u -> modelmapper.map(u,WatchlistDto.class));
    }


    public WatchlistDto update(WatchlistDto watchlistDto, Long id) {
        Optional<Watchlist> watchlistOp = watchlistRepository.findById(id);
        if(watchlistOp.isPresent()){
            Watchlist watchlist = watchlistOp.get();
            modelmapper.map(watchlistDto, watchlist);
            watchlist.setId(id);
            return modelmapper.map(watchlistRepository.save(watchlist), WatchlistDto.class);
        } else {
            throw  new NoDataFoundException();
        }


    }

    public long findWatchlistByIdForRemoving(WatchlistParams params){
        WatchlistParams watchlistParam = params == null ? new WatchlistParams(): params;
        WatchlistSpecification watchlistSpecification = new WatchlistSpecification(watchlistParam);
        return watchlistRepository.delete(watchlistSpecification);
    }




}
