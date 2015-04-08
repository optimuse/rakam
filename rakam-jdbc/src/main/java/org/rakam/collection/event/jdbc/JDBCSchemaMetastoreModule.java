package org.rakam.collection.event.jdbc;

import com.google.auto.service.AutoService;
import com.google.inject.Binder;
import com.google.inject.name.Names;
import io.airlift.configuration.ConfigurationFactory;
import io.airlift.configuration.ConfigurationModule;
import org.rakam.JDBCConfig;
import org.rakam.collection.event.metastore.EventSchemaMetastore;
import org.rakam.plugin.ConditionalModule;
import org.rakam.plugin.RakamModule;

/**
 * Created by buremba <Burak Emre Kabakcı> on 24/03/15 03:26.
 */
@AutoService(RakamModule.class)
public class JDBCSchemaMetastoreModule extends RakamModule implements ConditionalModule {
    @Override
    public boolean shouldInstall(ConfigurationFactory config) {
        String s = config.getProperties().get("event.schema.store");
        return s!=null && s.toLowerCase().trim().equals("jdbc");
    }

    @Override
    protected void setup(Binder binder) {
        ConfigurationModule.bindConfig(binder)
                .annotatedWith(Names.named("event.schema.store.jdbc"))
                .prefixedWith("event.schema.store.jdbc")
                .to(JDBCConfig.class);

        binder.bind(EventSchemaMetastore.class).to(JDBCSchemaMetastore.class);
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public String description() {
        return null;
    }
}
