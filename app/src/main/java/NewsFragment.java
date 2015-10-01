import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

import com.example.songsubei.mynewsclient.R;

/**
 * Created by songsubei on 27/09/15.
 */
public class NewsFragment extends Fragment implements AppCompatCallback{

    private AppCompatDelegate appCompatDelegate;
    private Toolbar toolbar;
    private Activity activity;
    private ViewPager viewPager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        toolbar = (Toolbar)activity.findViewById(R.id.toolbar);
        viewPager = (ViewPager) activity.findViewById(R.id.viewPager);

        appCompatDelegate = AppCompatDelegate.create(activity, this);
        appCompatDelegate.setSupportActionBar(toolbar);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {

    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {

    }

    @Nullable
    @Override
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }
}
