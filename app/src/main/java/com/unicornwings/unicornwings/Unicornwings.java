package com.unicornwings.unicornwings;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;

import com.unicornwings.unicornwings.Utill.CommonUtil;
import com.unicornwings.unicornwings.Utill.Constants;


/**
 * Created by hai on 5/19/2017.
 */

public class Unicornwings extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "zMdPbhoZIapovlLtXocAqnzwy";
    private static final String TWITTER_SECRET = "eGqEolASAeEMly9URCtEKM2YMpQXL9l7xgJdTcrFPaMSTMNBTi";

    static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor editor = null;
    public static final String CURRENT_THEME = "CurrentTheme";
    public static final int DARK_THEME = 0;
    public static final int LIGHT_THEME = 1;
    public static Context mContext = null;

    //Device orientation constants.
    public static final int ORIENTATION_PORTRAIT = 0;
    public static final int ORIENTATION_LANDSCAPE = 1;

    //Device screen size/orientation identifiers.
    public static final String REGULAR = "regular";
    public static final String SMALL_TABLET = "small_tablet";
    public static final String LARGE_TABLET = "large_tablet";
    public static final String XLARGE_TABLET = "xlarge_tablet";
    public static final int REGULAR_SCREEN_PORTRAIT = 0;
    public static final int REGULAR_SCREEN_LANDSCAPE = 1;
    public static final int SMALL_TABLET_PORTRAIT = 2;
    public static final int SMALL_TABLET_LANDSCAPE = 3;
    public static final int LARGE_TABLET_PORTRAIT = 4;
    public static final int LARGE_TABLET_LANDSCAPE = 5;
    public static final int XLARGE_TABLET_PORTRAIT = 6;
    public static final int XLARGE_TABLET_LANDSCAPE = 7;

    //SharedPreferences keys.
    public static final String CROSSFADE_ENABLED = "CrossfadeEnabled";
    public static final String CROSSFADE_DURATION = "CrossfadeDuration";
    public static final String REPEAT_MODE = "RepeatMode";
    public static final String MUSIC_PLAYING = "MusicPlaying";
    public static final String SERVICE_RUNNING = "ServiceRunning";
    public static final String CURRENT_LIBRARY = "CurrentLibrary";
    public static final String CURRENT_LIBRARY_POSITION = "CurrentLibraryPosition";
    public static final String SHUFFLE_ON = "ShuffleOn";
    public static final String FIRST_RUN = "FirstRun";
    public static final String STARTUP_BROWSER = "StartupBrowser";
    public static final String SHOW_LOCKSCREEN_CONTROLS = "ShowLockscreenControls";
    public static final String ARTISTS_LAYOUT = "ArtistsLayout";
    public static final String ALBUM_ARTISTS_LAYOUT = "AlbumArtistsLayout";
    public static final String ALBUMS_LAYOUT = "AlbumsLayout";
    public static final String PLAYLISTS_LAYOUT = "PlaylistsLayout";
    public static final String GENRES_LAYOUT = "GenresLayout";
    public static final String FOLDERS_LAYOUT = "FoldersLayout";
    private static final String TAG = "MarketNutritionApp";

    private static TelephonyManager telephonyManager = null;
    private static String deviceId = "";

    public static SharedPreferences getSharedPreferences() {
        return mSharedPreferences;
    }

    public static SharedPreferences.Editor getPreferencesEditor() {
        return editor;
    }

    public int getCurrentTheme() {
        return getSharedPreferences().getInt(CURRENT_THEME, LIGHT_THEME);
    }


    @Override
    public void onCreate() {
        mSharedPreferences = this.getApplicationContext().getSharedPreferences(Constants.APP_PREFS_ACCOUNT, Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
        mContext = this;

        super.onCreate();
        //TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        //Fabric.with(this, new Twitter(authConfig));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
    }

    /*
     * Returns the status bar height for the current layout configuration.
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

    /*
     * Returns the navigation bar height for the current layout configuration.
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }

        return 0;
    }

    /**
     * Returns the view container for the ActionBar.
     *
     * @return
     */
    public View getActionBarView(Activity activity) {
        Window window = activity.getWindow();
        View view = window.getDecorView();
        int resId = getResources().getIdentifier("action_bar_container", "id", "android");

        return view.findViewById(resId);
    }


    /**
     * Converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public float convertDpToPixels(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * Converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    /**
     * Returns the orientation of the device.
     */
    public int getOrientation() {
        if (getResources().getDisplayMetrics().widthPixels >
                getResources().getDisplayMetrics().heightPixels) {
            return ORIENTATION_LANDSCAPE;
        } else {
            return ORIENTATION_PORTRAIT;
        }

    }

    /**
     * Returns the current screen configuration of the device.
     */
    public int getDeviceScreenConfiguration() {
        String screenSize = getResources().getString(R.string.size_of_screen);
        boolean landscape = false;

        if (screenSize.equals(REGULAR) && !landscape)
            return REGULAR_SCREEN_PORTRAIT;
        else if (screenSize.equals(REGULAR) && landscape)
            return REGULAR_SCREEN_LANDSCAPE;
        else if (screenSize.equals(SMALL_TABLET) && !landscape)
            return SMALL_TABLET_PORTRAIT;
        else if (screenSize.equals(SMALL_TABLET) && landscape)
            return SMALL_TABLET_LANDSCAPE;
        else if (screenSize.equals(LARGE_TABLET) && !landscape)
            return LARGE_TABLET_PORTRAIT;
        else if (screenSize.equals(LARGE_TABLET) && landscape)
            return LARGE_TABLET_LANDSCAPE;
        else if (screenSize.equals(XLARGE_TABLET) && !landscape)
            return XLARGE_TABLET_PORTRAIT;
        else if (screenSize.equals(XLARGE_TABLET) && landscape)
            return XLARGE_TABLET_LANDSCAPE;
        else
            return REGULAR_SCREEN_PORTRAIT;
    }

    public boolean isTabletInLandscape() {
        int screenConfig = getDeviceScreenConfiguration();
        if (screenConfig == SMALL_TABLET_LANDSCAPE ||
                screenConfig == LARGE_TABLET_LANDSCAPE ||
                screenConfig == XLARGE_TABLET_LANDSCAPE)
            return true;
        else
            return false;

    }

    public boolean isTabletInPortrait() {
        int screenConfig = getDeviceScreenConfiguration();
        if (screenConfig == SMALL_TABLET_PORTRAIT ||
                screenConfig == LARGE_TABLET_PORTRAIT ||
                screenConfig == XLARGE_TABLET_PORTRAIT)
            return true;
        else
            return false;

    }

    public boolean isPhoneInLandscape() {
        int screenConfig = getDeviceScreenConfiguration();
        if (screenConfig == REGULAR_SCREEN_LANDSCAPE)
            return true;
        else
            return false;
    }

    public boolean isPhoneInPortrait() {
        int screenConfig = getDeviceScreenConfiguration();
        if (screenConfig == REGULAR_SCREEN_PORTRAIT)
            return true;
        else
            return false;
    }

    public static String getDeviceId() {
        if (!CommonUtil.isHavingValue(deviceId)) {
            deviceId = generateDeviceUniqueID();
        }
        CommonUtil.log(TAG, "Device Unique ID " + deviceId);
        return deviceId;
    }

    /**
     * generate the device uniqueId.
     *
     * @return -device unique id
     */
    public static String generateDeviceUniqueID() {
        String deviceUniqueId = "";
        deviceUniqueId = CommonUtil.getDeviceId(mContext);
        /*try {
            String deviceIMEINo = "";
            if (telephonyManager != null) {
                deviceIMEINo = telephonyManager.getDeviceId();
            }
            String deviceMac = "";
            try {
                CommonUtil.log(TAG, "Device Id IMEI NO " + deviceIMEINo);
                deviceMac = NetworkUtils.getMACAddress(mContext, "wlan0");
                CommonUtil.log(TAG, "Device Id LAN MAC ADDRESS " + deviceMac);
                if (!CommonUtil.isHavingValue(deviceMac)) {
                    deviceMac = NetworkUtils.getMACAddress(mContext, "eth0");
                }
                if (deviceMac != null && deviceMac.indexOf(":") != -1) {
                    deviceMac = deviceMac.replaceAll(":", "");
                }

            } catch (Exception e) {

            }
            if (CommonUtil.isHavingValue(deviceIMEINo)) {
                deviceUniqueId = deviceIMEINo;
            }
            if (CommonUtil.isHavingValue(deviceMac)) {
                deviceUniqueId = deviceUniqueId + deviceMac;
            }
            CommonUtil.log(TAG, "Device Id IMEI NO " + deviceIMEINo);
        } catch (Exception e) {

        }*/
        return deviceUniqueId.trim();
    }
}
