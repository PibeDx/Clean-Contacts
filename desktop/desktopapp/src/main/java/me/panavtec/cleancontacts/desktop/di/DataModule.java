package me.panavtec.cleancontacts.desktop.di;

import dagger.Module;
import dagger.Provides;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.inject.Singleton;
import me.panavtec.cleancontacts.desktop.data.RetrofitLog;
import me.panavtec.cleancontacts.desktop.data.UserAgent;
import me.panavtec.cleancontacts.desktop.domain.InteractorInvokerImp;
import me.panavtec.cleancontacts.desktop.domain.SameThreadSpec;
import me.panavtec.cleancontacts.domain.entities.Contact;
import me.panavtec.cleancontacts.domain.interactors.base.InteractorInvoker;
import me.panavtec.cleancontacts.domain.interactors.base.ThreadSpec;
import me.panavtec.cleancontacts.presentation.model.PresentationContact;
import me.panavtec.cleancontacts.presentation.model.mapper.PresentationContactMapper;
import me.panavtec.cleancontacts.presentation.model.mapper.base.ListMapper;
import retrofit.Endpoint;
import retrofit.Endpoints;

@Module(
        includes = {
                InteractorsModule.class, RepositoryModule.class,
        },
        complete = false,
        library = true)
public class DataModule {

    @Provides @Singleton Endpoint provideEndpoint() {
        return Endpoints.newFixedEndpoint("http://api.randomuser.me/");
    }

    @Provides @Singleton @RetrofitLog boolean provideRetrofitLog() {
        return true;
    }

    @Provides @Singleton @UserAgent String provideUserAgent() {
        return String.format("Sample-JavaFX-1.0");
    }

    @Provides @Singleton InteractorInvoker provideInteractorInvoker(ExecutorService executorService) {
        return new InteractorInvokerImp(executorService);
    }

    @Provides @Singleton ExecutorService provideExecutorService() {
        return Executors.newFixedThreadPool(5);
    }

    @Provides @Singleton PresentationContactMapper providePresentationContactMapper() {
        return new PresentationContactMapper();
    }

    @Provides @Singleton ListMapper<Contact, PresentationContact> providePresentationContactMapper(
            PresentationContactMapper presentationContactMapper) {
        return new ListMapper<>(presentationContactMapper);
    }

    @Provides @Singleton ThreadSpec threadSpec() {
      return new SameThreadSpec();
    }

}
