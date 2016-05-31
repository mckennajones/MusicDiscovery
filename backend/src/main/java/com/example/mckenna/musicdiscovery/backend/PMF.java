package com.example.mckenna.musicdiscovery.backend;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {
    private static final PersistenceManagerFactory pmfInstance = JDOHelper
            .getPersistenceManagerFactory("transactions-optional");

    public static PersistenceManagerFactory getPMF() {
        return pmfInstance;
    }

    private PMF() {
    }
}
