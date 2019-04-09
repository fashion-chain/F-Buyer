package com.hottop.core.utils.database;

import org.hibernate.boot.model.naming.EntityNaming;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.internal.util.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ImplicitNamingStrategyImpl extends ImplicitNamingStrategyJpaCompliantImpl {

    private static Logger logger = LoggerFactory.getLogger(ImplicitNamingStrategyImpl.class);

    @Override
    public String transformEntityName(EntityNaming entityNaming) {
        return customize(defaultTableEntityName(entityNaming));
    }

    public String defaultTableEntityName(EntityNaming entityNaming) {
        return new StringBuilder().append(getPrefix())
                .append(StringHelper.isNotEmpty(entityNaming.getJpaEntityName()) ?
                        entityNaming.getJpaEntityName() :
                        StringHelper.unqualify(entityNaming.getEntityName())).toString();
    }

    public abstract String getPrefix();

    public abstract String customize(String defaultTableEntityName);
}
