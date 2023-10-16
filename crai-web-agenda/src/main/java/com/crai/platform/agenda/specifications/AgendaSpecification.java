package com.crai.platform.agenda.specifications;

import com.crai.platform.agenda.domain.Agenda;
import com.crai.platform.agenda.params.AgendaParams;
import com.crai.starter.jpa.data.specifications.BaseSpecification;
import com.crai.starter.jpa.data.specifications.ParamDef;
import com.crai.starter.jpa.data.specifications.ParamOperators;


public class AgendaSpecification extends BaseSpecification<Agenda> {

    public AgendaSpecification (AgendaParams params){ super(params);}
    @Override
    protected ParamDef[] defineParamDefSet() {
        return new ParamDef[]{
                ParamDef.build("departments", "departments", ParamOperators.EQUALS),
                ParamDef.buildLike("provinces", "provinces")

        };
    }
}
