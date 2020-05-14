package fr.gendarmerienationale.reseauprevention31.activity.messages;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.nogema.inventairecanal.R;
import com.nogema.inventairecanal.tools.Tools;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ProgrammationActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_programmation);

		ViewPager viewPager = findViewById(R.id.viewpager);
		setViewPager(viewPager);

		TabLayout tabLayout = findViewById(R.id.tablayout);
		tabLayout.setupWithViewPager(viewPager);
	}

	private void setViewPager(ViewPager viewPager) {
		ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
		adapter.addFragment(new UserFragment(), getString(R.string.utilisateur));
//		adapter.addFragment(new FTPFragment(), getString(R.string.ftp));
		adapter.addFragment(new WirelessFragment(), getString(R.string.wireless));
		viewPager.setAdapter(adapter);

		// Quand on change de page dans la fenêtre
		viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageSelected(int position) {
				// Si le clavier est ouvert, on le ferme
				Tools.hideKeyboardFromView(viewPager, ProgrammationActivity.this);
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
	}


	class ViewPagerAdapter extends FragmentPagerAdapter {
		private final List<Fragment> mFragmentList = new ArrayList<>();
		private final List<String> mFragmentTitleList = new ArrayList<>();

		ViewPagerAdapter(FragmentManager manager) {
			super(manager);
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
}