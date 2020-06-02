package fr.gendarmerienationale.reseauprevention31.activity.messages;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import fr.gendarmerienationale.reseauprevention31.R;
import fr.gendarmerienationale.reseauprevention31.activity.MainActivity;
import fr.gendarmerienationale.reseauprevention31.util.Tools;
import java.util.ArrayList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    private final static Integer[] PAGES_NAME = new Integer[]{R.string.nouvelles_annonces, R.string.anciennes_annonces,
            R.string.all_messages};

    private ViewPagerFragment[] mFragments;

    private NouvellesAnnoncesFragment mFragment1;
    private AnciennesAnnoncesFragment mFragment2;
    private AllMessagesFragment       mFragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        ViewPager viewPager = findViewById(R.id.viewpager);
        setViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(mFragment1 = new NouvellesAnnoncesFragment(), getString(R.string.nouvelles_annonces));
        adapter.addFragment(mFragment2 = new AnciennesAnnoncesFragment(), getString(R.string.anciennes_annonces));
        adapter.addFragment(mFragment3 = new AllMessagesFragment(), getString(R.string.all_messages));

        mFragments = new ViewPagerFragment[]{mFragment1, mFragment2, mFragment3};

        viewPager.setAdapter(adapter);

        // Quand on change de page dans la fenêtre
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // Si le clavier est ouvert, on le ferme
                Tools.hideKeyboardFromView(viewPager, MessagesActivity.this);

                if (position < 0 || position > 2 || getSupportActionBar() == null)
                    return;

                getSupportActionBar().setSubtitle(PAGES_NAME[position]);

                mFragments[position].refreshRecycler();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList      = new ArrayList<>();
        private final List<String>   mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager, ViewPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    void disconnectUser() {
        if (MainActivity.sDatabaseHelper.deleteUser()) {
            Toast.makeText(this, R.string.deconnexion_reussie, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MessagesActivity.this, MainActivity.class);

            finish();
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}