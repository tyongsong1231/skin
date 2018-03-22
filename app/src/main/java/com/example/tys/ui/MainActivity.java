package com.example.tys.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.skin.SkinConfig.ResUtil;
import com.example.skin.SkinCore.ResPluginImpl;

import java.io.File;

public class MainActivity extends AppCompatActivity  {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout tb;
    private PopupWindow pop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ResPluginImpl.getsInstance().load(
                Environment.getExternalStorageDirectory() + File.separator + "plugin.apk",
                "com.example.tys.skinpluginapk");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tb = findViewById(R.id.tl);
        tb.setupWithViewPager(mViewPager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        for (int i = 0; i < tb.getTabCount(); i++) {
            TabLayout.Tab tab = tb.getTabAt(i);
            if (tab != null) {
                switch (i) {
                    case 0:
                        tab.setCustomView(R.layout.table_item_one);
                        break;
                    case 1:
                        tab.setCustomView(R.layout.table_item_two);
                        break;
                    case 2:
                        tab.setCustomView(R.layout.table_item_three);
                        break;
                    case 3:
                        tab.setCustomView(R.layout.table_item_foure);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void set(final View view) {
        if (pop == null) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.pop, null);
            contentView.findViewById(R.id.pf1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ResPluginImpl.getsInstance().load(
                            Environment.getExternalStorageDirectory() + File.separator + "plugin.apk",
                            "com.example.tys.skinpluginapk");
                    ResPluginImpl.getsInstance().setTypeFace(view.getContext(), "fzxz.TTF");
                    ResPluginImpl.getsInstance().setStatusBarColor(MainActivity.this, "status_bar_color_pf1");
                    ResPluginImpl.getsInstance().setNavigateBarColor(MainActivity.this, "navigation_bar_color_pf1");
                    ResPluginImpl.getsInstance().setRes(MainActivity.this, "pf1");
                    pop.dismiss();
                }
            });
            contentView.findViewById(R.id.pf2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ResPluginImpl.getsInstance().load(
                            Environment.getExternalStorageDirectory() + File.separator + "plugin.apk",
                            "com.example.tys.skinpluginapk");
                    ResPluginImpl.getsInstance().setTypeFace(view.getContext(), "kt.ttf");
                    ResPluginImpl.getsInstance().setStatusBarColor(MainActivity.this, "status_bar_color_pf2");
                    ResPluginImpl.getsInstance().setNavigateBarColor(MainActivity.this, "navigation_bar_color_pf2");
                    ResPluginImpl.getsInstance().setRes(MainActivity.this, "pf2");
                    pop.dismiss();
                }
            });
            contentView.findViewById(R.id.pf3).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ResPluginImpl.getsInstance().load(
                            Environment.getExternalStorageDirectory() + File.separator + "plugin.apk",
                            "com.example.tys.skinpluginapk");
                    ResPluginImpl.getsInstance().setTypeFace(view.getContext(), "pop.ttf");
                    ResPluginImpl.getsInstance().setStatusBarColor(MainActivity.this, "status_bar_color_pf3");
                    ResPluginImpl.getsInstance().setNavigateBarColor(MainActivity.this, "navigation_bar_color_pf3");
                    ResPluginImpl.getsInstance().setRes(MainActivity.this, "pf3");
                    pop.dismiss();
                }
            });

            contentView.findViewById(R.id.pf4).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ResPluginImpl.getsInstance().load(
                            Environment.getExternalStorageDirectory() + File.separator + "plugin.apk",
                            "com.example.tys.skinpluginapk");
                    ResPluginImpl.getsInstance().setTypeFace(view.getContext(), "");
                    ResPluginImpl.getsInstance().setStatusBarColor(MainActivity.this, "");
                    ResPluginImpl.getsInstance().setNavigateBarColor(MainActivity.this, "");
                    ResPluginImpl.getsInstance().setRes(MainActivity.this, "");
                    pop.dismiss();
                }
            });

            pop = new PopupWindow(this);
            pop.setContentView(contentView);
            pop.setOutsideTouchable(true);
        }
        pop.showAsDropDown(view);
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
            RecyclerView rv= rootView.findViewById(R.id.rv);
            rv.setLayoutManager(new LinearLayoutManager(getContext()));
            rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
            rv.setAdapter(new MyAdapter(getContext()));
            return rootView;
        }

        class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
            private final Context context;

            public MyAdapter(Context context) {
                this.context = context;
            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(context).inflate(R.layout.item_rv, null);
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                holder.tv.setText(position +" 第 ");
            }

            @Override
            public int getItemCount() {
                return 24;
            }
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private final TextView tv;

            public MyViewHolder(View view) {
                super(view);
                tv=view.findViewById(R.id.item_tv);
            }
        }

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }
    }
}
