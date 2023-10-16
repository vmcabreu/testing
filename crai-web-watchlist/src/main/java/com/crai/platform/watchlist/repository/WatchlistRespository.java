package com.crai.platform.watchlist.repository;

import com.crai.platform.watchlist.domain.Watchlist;
import com.crai.platform.watchlist.dto.WatchlistDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchlistRespository extends JpaRepository<Watchlist,Long>, JpaSpecificationExecutor<Watchlist> {

}
