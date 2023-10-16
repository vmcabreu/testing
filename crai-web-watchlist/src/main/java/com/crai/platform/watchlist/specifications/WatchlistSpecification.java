package com.crai.platform.watchlist.specifications;

import com.crai.platform.watchlist.domain.Watchlist;
import com.crai.platform.watchlist.params.WatchlistParams;
import com.crai.starter.jpa.data.specifications.BaseSpecification;
import com.crai.starter.jpa.data.specifications.ParamDef;
import com.crai.starter.jpa.data.specifications.ParamOperators;
import org.springframework.data.repository.query.Param;

public class WatchlistSpecification extends BaseSpecification<Watchlist> {
    public WatchlistSpecification (WatchlistParams params)  {
        super(params);
    }

    @Override
    protected ParamDef[] defineParamDefSet() {
        return new ParamDef[]{
                ParamDef.buildLike("userRoles"),
                ParamDef.build("status", "status",ParamOperators.EQUALS),
                ParamDef.build("id","id",ParamOperators.EQUALS)
        };
    }
}
