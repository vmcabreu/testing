package com.crai.platform.example.specifications;

import com.crai.platform.example.domain.User;
import com.crai.platform.example.params.UserParams;
import com.crai.starter.jpa.data.specifications.BaseSpecification;
import com.crai.starter.jpa.data.specifications.ParamDef;
import com.crai.starter.jpa.data.specifications.ParamOperators;

public class UserSpecification extends BaseSpecification<User> {


    public UserSpecification (UserParams params)  {
        super(params);
    }

    @Override
    protected ParamDef[] defineParamDefSet() {
        return new ParamDef[]{
                ParamDef.build("lastName","lastName", ParamOperators.NOT_EQUALS),
                ParamDef.buildLike("userName")



        };
    }
}
