package ISA.Team54.drugAndRecipe.service.impl;

import ISA.Team54.drugAndRecipe.service.interfaces.IClock;

import java.util.Date;

public class ClockImpl implements IClock {

    @Override
    public Date getDate() {
        return new Date();
    }
}
