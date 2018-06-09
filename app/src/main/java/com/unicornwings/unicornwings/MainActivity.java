package com.unicornwings.unicornwings;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.unicornwings.unicornwings.Utill.CommonUtil;
import com.unicornwings.unicornwings.Utill.CustomTypefaceSpan;
import com.unicornwings.unicornwings.Utill.FontChangeCrawler;
import com.unicornwings.unicornwings.Utill.Constants;
import com.unicornwings.unicornwings.fragment.FirstFragment;
import com.unicornwings.unicornwings.fragment.SecondFragment;
import com.unicornwings.unicornwings.fragment.ThirdFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    RelativeLayout rl_hcp_fragmentContainer;
    //private FloatingActionButton fab;

    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "https://api.androidhive.info/images/nav-menu-header-bg.jpg";
    // private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";
    private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOp";

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_PROGRAM = "Programs Offered";
    private static final String TAG_FOLLOW_US = "Follow Us";
    /*private static final String TAG_ACADEMY = "English Academy";
    private static final String TAG_UNIROBO = "UniRobo";
    private static final String TAG_MATHS = "Maths + U";
    private static final String TAG_YOGA = "Unique yoga";
    private static final String TAG_SCIENCE = "Science Projects";*/
    private static final String TAG_ADDRESS = "Address";
    public static String CURRENT_TAG = TAG_PROGRAM;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    private AppCompatActivity mActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActivity = this;
        mHandler = new Handler();
        FontChangeCrawler fontChanger = new FontChangeCrawler(mActivity, Constants.Roboto_Italic);
        fontChanger.replaceFonts((ViewGroup) mActivity.findViewById(android.R.id.content));
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);


        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }


        rl_hcp_fragmentContainer = (RelativeLayout) findViewById(R.id.rl_hcp_fragmentContainer);
        // fab = (FloatingActionButton) findViewById(R.id.fab);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName = (TextView) navHeader.findViewById(R.id.name);
        txtWebsite = (TextView) navHeader.findViewById(R.id.website);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);
        FontChangeCrawler fontChangerview = new FontChangeCrawler(mActivity, Constants.Helvetica_Neu_Bold);
        fontChangerview.replaceFonts((ViewGroup) navHeader);
        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

       /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Please Add Your Details", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                fab.setVisibility(View.GONE);
                *//*AddMemberFragment photosFragment = new AddMemberFragment();
                switchFragment(photosFragment,R.id.rl_hcp_fragmentContainer,"AddMemberFragment");*//*
                navItemIndex = 1;
                CURRENT_TAG = TAG_PHOTOS;
                setCustomeToolbarTitle(TAG_PHOTOS);
                loadHomeFragment();
            }
        });*/

        // load nav menu header data
        //loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_PROGRAM;
            loadHomeFragment();
        }


    }



//start
    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
  /*  private void loadNavHeader() {
        // name, website
        txtName.setText("Welcome to SRMT");
        txtWebsite.setText("www.sriramanujamissiontrust.info");

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image
        Glide.with(this).load(urlProfileImg)
                .crossFade()
                .thumbnail(0.5f)
                .placeholder(R.drawable.srmt_logo)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);

        // showing dot next to notifications label
        //navigationView.getMenu().getItem(3).setActionView(R.layout.menu_dot);
    }*/

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            //toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.rl_hcp_fragmentContainer, fragment, CURRENT_TAG);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commitAllowingStateLoss();


            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        //toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                FirstFragment homeFragment = new FirstFragment();
                return homeFragment;

            case 1:
                SecondFragment secondFragment = new SecondFragment();
                return secondFragment;

            case 2:
                ThirdFragment thirdFragment = new ThirdFragment();
                return thirdFragment;

            default:
                return new FirstFragment();

        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void setCustomeToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_program:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_PROGRAM;
                        setCustomeToolbarTitle(TAG_PROGRAM);
                        setToolbarTitle();
                        navigationView.getMenu().getItem(0).setChecked(true);
                        drawer.closeDrawers();

                        //fab.show();
                        FirstFragment homeFragment = new FirstFragment();
                        switchFragment(homeFragment,R.id.rl_hcp_fragmentContainer,"FirstFragment");
                        loadHomeFragment();
                        break;
                    case R.id.nav_follow:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_FOLLOW_US;
                        setCustomeToolbarTitle(TAG_FOLLOW_US);
                        setToolbarTitle();
                        navigationView.getMenu().getItem(1).setChecked(true);
                        drawer.closeDrawers();

                        // fab.hide();
                        //loadHomeFragment();
                        SecondFragment addMemberFragment = new SecondFragment();
                        switchFragment(addMemberFragment,R.id.rl_hcp_fragmentContainer,"SecondFragment");
                        loadHomeFragment();
                        break;
                   /* case R.id.nav_unirobo:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_UNIROBO;
                        setCustomeToolbarTitle(TAG_UNIROBO);
                        setToolbarTitle();
                        navigationView.getMenu().getItem(2).setChecked(true);
                        drawer.closeDrawers();
                        //fab.hide();
                        //loadHomeFragment();
                       *//* Intent intent = new Intent(mActivity, ViewMemberActivity.class);

                       startActivity(intent);*//*
                        loadHomeFragment();
                        break;
                    case R.id.nav_maths:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_MATHS;
                        setCustomeToolbarTitle(TAG_MATHS);
                        setToolbarTitle();
                        navigationView.getMenu().getItem(3).setChecked(true);
                        drawer.closeDrawers();
                        //fab.hide();
                        //loadHomeFragment();
                       *//* Intent intent = new Intent(mActivity, ViewMemberActivity.class);
                        startActivity(intent);*//*
                        loadHomeFragment();
                        break;

                    case R.id.nav_uniqueyoga:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_YOGA;
                        setCustomeToolbarTitle(TAG_YOGA);
                        setToolbarTitle();
                        navigationView.getMenu().getItem(4).setChecked(true);
                        drawer.closeDrawers();
                        //fab.hide();
                        //loadHomeFragment();
                       *//* Intent intent = new Intent(mActivity, ViewMemberActivity.class);
                        startActivity(intent);*//*
                        loadHomeFragment();
                        break;
                    case R.id.nav_science:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_SCIENCE;
                        setCustomeToolbarTitle(TAG_SCIENCE);
                        setToolbarTitle();
                        navigationView.getMenu().getItem(5).setChecked(true);
                        drawer.closeDrawers();
                        //fab.hide();
                        //loadHomeFragment();
                       *//* Intent intent = new Intent(mActivity, ViewMemberActivity.class);
                        startActivity(intent);*//*
                        loadHomeFragment();
                        break;*/
                    case R.id.nav_address:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_ADDRESS;
                        setCustomeToolbarTitle(TAG_ADDRESS);
                        setToolbarTitle();
                        navigationView.getMenu().getItem(2).setChecked(true);
                        drawer.closeDrawers();

                        ThirdFragment thirdFragment = new ThirdFragment();
                        switchFragment(thirdFragment,R.id.rl_hcp_fragmentContainer,"ThirdFragment");
                        //fab.hide();
                        //loadHomeFragment();
                       /* Intent intent = new Intent(mActivity, ViewMemberActivity.class);
                        startActivity(intent);*/
                        loadHomeFragment();
                        break;
                    /*case R.id.nav_logout:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_MOVIES;
                        //logoutAlertFunctionalities();
                        break;*/

                    /*case R.id.nav_about_us:
                        // launch new intent instead of loading fragment
                        //startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    case R.id.nav_privacy_policy:
                        // launch new intent instead of loading fragment
                        //startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                        drawer.closeDrawers();
                        return true;*/
                    default:
                        navItemIndex = 0;
                        break;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                //loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

  /*  @Override
    public void onBackPressed() {
       *//* if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();*//*


        if (mActivity.getSupportFragmentManager().getBackStackEntryCount() > 0) {
            if (mActivity.getSupportFragmentManager().getBackStackEntryCount() == 1 || (getVisibleFragment() != null && getVisibleFragment() instanceof AddMemberFragment)) {
                fab.setVisibility(View.VISIBLE);
                //super.onBackPressed();
                Log.d(TAG," Add Member Fragmenr ");



                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                setToolbarTitle();
                navigationView.getMenu().getItem(0).setChecked(true);

                drawer.closeDrawers();
                // refresh toolbar menu

                fab.show();
                fab.setVisibility(View.VISIBLE);

                //super.onBackPressed();
            }else if (mActivity.getSupportFragmentManager().getBackStackEntryCount() == 1 || (getVisibleFragment() != null && getVisibleFragment() instanceof HomeFragment))
            {
                exitFunctionalities();
            }
        }else {
            super.onBackPressed();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        // when fragment is notifications, load the menu created for notifications
        if (navItemIndex == 3) {
            getMenuInflater().inflate(R.menu.notifications, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            Toast.makeText(getApplicationContext(), "Settings!", Toast.LENGTH_LONG).show();
            return true;
        }

        // user is in notifications fragment
        // and selected 'Mark all as Read'
        if (id == R.id.action_mark_all_read) {
            Toast.makeText(getApplicationContext(), "All notifications marked as read!", Toast.LENGTH_LONG).show();
        }

        // user is in notifications fragment
        // and selected 'Clear All'
        if (id == R.id.action_clear_notifications) {
            Toast.makeText(getApplicationContext(), "Clear all notifications!", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    // show or hide the fab
   /* private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }*/


    public void switchFragment(Fragment fr,
                               int containerViewID, String tagName) {
   /* if (fr == null && mActivity == null) {
            return;
        }
        FragmentManager fm = mActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(containerViewID, fr, tagName);
        transaction.addToBackStack(null);
        transaction.commit();*/

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.rl_hcp_fragmentContainer, fr, tagName);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }

    List<WeakReference<Fragment>> fragList = new ArrayList<WeakReference<Fragment>>();

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public void onAttachFragment(Fragment fragment) {
        fragList.add(new WeakReference(fragment));
        super.onAttachFragment(fragment);
    }

    private Fragment getVisibleFragment() {
        if (fragList != null) {
            for (WeakReference<Fragment> fr : fragList) {
                if (fr.get() != null && fr.get().isVisible()) {
                    return fr.get();
                }
            }
        }
        return null;
    }


    private void exitFunctionalities() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exit Alert.!");
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mActivity.finish();
                        return;
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
    }



   /* private void logoutAlertFunctionalities() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout Alert.!");
        builder.setMessage("Are you sure you want to logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(mActivity, LoginActivity.class);
                        startActivity(intent);
                        mActivity.finish();
                        return;
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE);
    }*/



    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = CommonUtil.getfont(mActivity,Constants.Roboto_BlackItalic);
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }


}
