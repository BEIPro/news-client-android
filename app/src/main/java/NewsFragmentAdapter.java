import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by songsubei on 01/10/15.
 */
public class NewsFragmentAdapter extends TaggedFragmentStatePagerAdapter{
    public NewsFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new TopNewsFragment();
            case 1:
                return new TechNewsFragment();
            case 2:
                return new SportNewsFragment();
            case 3:
                return new LocalNewsFragment();
            case 4:
                return new EnterTainNewsFragment();
        }
        return null;
    }
}
