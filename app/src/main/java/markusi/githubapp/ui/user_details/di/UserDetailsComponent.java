package markusi.githubapp.ui.user_details.di;

import dagger.Subcomponent;
import markusi.githubapp.data.interactors.users.UserGetInteractorModule;
import markusi.githubapp.ui.user_details.UserDetailsActivity;

@Subcomponent(modules = {
        UserDetailsModule.class,
        UserGetInteractorModule.class
})
public interface UserDetailsComponent {

    void inject(UserDetailsActivity activity);
}
