package com.unicornwings.unicornwings.Utill;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import com.unicornwings.unicornwings.R;
import com.unicornwings.unicornwings.Unicornwings;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;



import static com.unicornwings.unicornwings.Utill.Constants.Image_upload;


public class CommonUtil {
    //public static GoogleApiClient googleApiClient;
    private static final String TAG = "CommonUtil";
    static boolean serverTimeOut = true;
    private InetAddress serverAddress = null;

    final static int REQUEST_LOCATION = 199;
    public static Typeface comfortaa = null;
    /**
     * check whether user is logged in or not.
     *
     * @return true if user logged in.
     */
    public static Boolean isUserLoggedIn(SharedPreferences appPreferences) {
        if (appPreferences != null) {
            if (appPreferences.contains(Constants.APP_PREFS_USERKEY)
                    && appPreferences.contains(Constants.APP_PREFS_AUTH_TOKEN)) {
                if (CommonUtil.isHavingValue(appPreferences.getString(
                        Constants.APP_PREFS_USERKEY, ""))
                        && CommonUtil.isHavingValue(appPreferences.getString(
                        Constants.APP_PREFS_AUTH_TOKEN, ""))) {

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Adding Headervalues using Webservice Calling
     *
     * @param mActivity -
     *                  returns headerVal
     */
    public static HashMap<String, String> getHeaderValue(Activity mActivity) {
        SharedPreferences appPreferences = Unicornwings.getSharedPreferences();
        HashMap<String, String> headerVal = new HashMap<String, String>();
        boolean isUserLoggedIn = isUserLoggedIn(appPreferences);
        headerVal.put("GuestUser", isUserLoggedIn ? "0" : "1");
        headerVal.put("AccessToken", appPreferences.contains(Constants.APP_PREFS_AUTH_TOKEN) ? appPreferences.getString(Constants.APP_PREFS_AUTH_TOKEN, "") : "0");
        return headerVal;
    }
    /**
     * check whether user is logout
     *
     * @return false if user logout.
     */
    public static boolean removeUserFromApp(SharedPreferences appPreferences) {
        if (appPreferences != null) {
            if (appPreferences.contains(Constants.APP_PREFS_USERKEY)
                    && appPreferences.contains(Constants.APP_PREFS_AUTH_TOKEN)) {
                if (CommonUtil.isHavingValue(appPreferences.getString(
                        Constants.APP_PREFS_USERKEY, ""))
                        && CommonUtil.isHavingValue(appPreferences.getString(
                        Constants.APP_PREFS_AUTH_TOKEN, ""))) {
                    SharedPreferences.Editor editor = appPreferences.edit();
                    editor.remove(Constants.APP_PREFS_AUTH_TOKEN);
                    editor.remove(Constants.APP_PREFS_USERKEY);
                    editor.commit();
                    return true;
                }
            }
        }
        return false;
    }

    /*Get Data from server with token*/
    public static String getDataFromServerwithtoken(String stringURL, HashMap<String, String> headerValueMap, String accesstoken) {
        InputStream inputStream = null;
        String response = "";
        if (stringURL == null || stringURL.equalsIgnoreCase("")) {
            return response;
        }
        try {

            CommonUtil.log(TAG, " <URL> " + stringURL);
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // make POST request to the given URL
            HttpGet httpGet = new HttpGet(stringURL);

            // Set some headers to inform server about the type of the content
            Log.d(TAG,"Authorization Bearer " + accesstoken);
            httpGet.setHeader("Authorization", "Bearer " + accesstoken);
            String mHeaderKey = null;
            String mHeaderVal = null;
            if (headerValueMap != null && headerValueMap.size() > 0) {
                Iterator it = headerValueMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pairs = (Map.Entry) it.next();
                    mHeaderKey = pairs.getKey().toString().trim();
                    mHeaderVal = pairs.getValue().toString().trim();
                    CommonUtil.log(TAG, pairs.getKey() + " == " + pairs.getValue());
                    if (isHavingValue(mHeaderKey) && isHavingValue(mHeaderVal)) {
                        httpGet.setHeader(mHeaderKey, mHeaderVal);
                    }
                    // it.remove(); // to avoids a
                    // ConcurrentModificationException
                }
            }
            // Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpGet);

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            String res = null;
            // convert inputstream to string
            if (inputStream != null) {
                res = convertInputStreamToString(inputStream);
            } else {
                res = "";
            }
            response = res;
            inputStream.close();
            CommonUtil.log(TAG, " the fnal response " + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String getfacebookDataFromServer(String stringURL, String requestMethod, HashMap<String, String> headerValueMap) {
        InputStream inputStream = null;
        String response = "";
        if (stringURL == null || stringURL.equalsIgnoreCase("")) {
            return response;
        }
        try {

            CommonUtil.log(TAG, " <URL> " + stringURL);
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // make POST request to the given URL
            HttpGet httpGet = new HttpGet(stringURL);

            // Set some headers to inform server about the type of the content

            String mHeaderKey = null;
            String mHeaderVal = null;
            if (headerValueMap != null && headerValueMap.size() > 0) {
                Iterator it = headerValueMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pairs = (Map.Entry) it.next();
                    mHeaderKey = pairs.getKey().toString().trim();
                    mHeaderVal = pairs.getValue().toString().trim();
                    CommonUtil.log(TAG, pairs.getKey() + " == " + pairs.getValue());
                    if (isHavingValue(mHeaderKey) && isHavingValue(mHeaderVal)) {
                        httpGet.setHeader(mHeaderKey, mHeaderVal);
                    }
                    // it.remove(); // to avoids a
                    // ConcurrentModificationException
                }
            }
            // Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpGet);

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            String res = null;
            // convert inputstream to string
            if (inputStream != null) {
                res = convertInputStreamToString(inputStream);
            } else {
                res = "";
            }
            response = res;
            inputStream.close();
            CommonUtil.log(TAG, " the fnal response " + response);
        } catch (Exception e) {

        }
        return response;
    }


    /**
     * Getting Youtube video id From youtube URL
     *
     * @return Channel_id.
     */
    public static String getYoutubeChannelId(String youtubeUrl) {
        String channel_id = "";
        try {
            if (CommonUtil.isHavingValue(youtubeUrl) && youtubeUrl.indexOf("/") != -1) {
                channel_id = youtubeUrl.substring(youtubeUrl.lastIndexOf("/") + 1).trim();
            }
        } catch (Exception e) {

        }
        return channel_id;
    }

    /**
     * Getting Youtube video id From youtube URL
     *
     * @return video_id.
     */

    public static String getYoutubeVideoId(String youtubeUrl) {
        String video_id = "";
        try {
            if (youtubeUrl != null && youtubeUrl.trim().length() > 0
                    && (youtubeUrl.startsWith("http") || youtubeUrl.startsWith("www"))) {
                String expression = "^.*((youtu.be"
                        + "\\/)"
                        + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*";
                CharSequence input = youtubeUrl;
                Pattern pattern = Pattern.compile(expression,
                        Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(input);
                if (matcher.matches()) {
                    String groupIndex1 = matcher.group(7);
                    if (groupIndex1 != null && groupIndex1.length() == 11)
                        video_id = groupIndex1;
                }
            }
        } catch (PatternSyntaxException e) {

        }
        return video_id;
    }

    /**
     * Getting Timeset 24 Hour into 12 Hour
     *
     * @return Timeset.
     */
    public static String convert24HourTimeTo12Hour(String time) {
        String newTimeToSet = "";
        if (CommonUtil.isHavingValue(time)) {
            try {
                CommonUtil.log(TAG, "Time is : " + time);
                int mSubscriptionHours = 0;
                int mSubscriptionMinutes = 0;
                if (time.indexOf(":") != -1) {
                    String[] split = time.split("\\:");
                    if (split.length > 0) {
                        mSubscriptionHours = Integer.parseInt(split[0].trim());
                    }
                    if (split.length > 1) {
                        mSubscriptionMinutes = Integer.parseInt(split[1].trim());
                    }
                }
                CommonUtil.log(TAG, "Hour : " + mSubscriptionHours + "   Minute : " + mSubscriptionMinutes);
                if (mSubscriptionHours >= 12) {
                    mSubscriptionHours = mSubscriptionHours - 12;
                    if (mSubscriptionHours == 0) {
                        mSubscriptionHours = 12;
                    }
                    newTimeToSet = padZero(mSubscriptionHours) + ":"
                            + padZero(mSubscriptionMinutes) + " PM";
                } else {
                    if (mSubscriptionHours == 0) {
                        mSubscriptionHours = 12;
                    }
                    newTimeToSet = padZero(mSubscriptionHours) + ":"
                            + padZero(mSubscriptionMinutes) + " AM";
                }
            } catch (Exception e) {

            }
        }
        return newTimeToSet;
    }

    private static String padZero(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    /**
     * Getting BroadcastAddress
     *
     * @return InetAddress.getByAddress(quads);.
     */
    public static InetAddress getBroadcastAddress(Context mActivity) throws IOException {
        WifiManager wifi = (WifiManager) mActivity.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcp = wifi.getDhcpInfo();
        // handle null somehow
        int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
        byte[] quads = new byte[4];
        for (int k = 0; k < 4; k++)
            quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
        CommonUtil.log(TAG, "BradCast Add : " + InetAddress.getByAddress(quads));
        return InetAddress.getByAddress(quads);
    }


    /**
     * Seting ActionBarBackToNormal View
     */
    public static void setActionBarBackToNormal(FragmentActivity mActivity) {
        if (mActivity == null && mActivity.getActionBar() == null) {
            return;
        }
        else {
           /* mActivity.getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE,
                    ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME
                            | ActionBar.DISPLAY_SHOW_TITLE);
            mActivity.getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);*/
        }
    }


    /**
     * Sets Home As Up Enabled
     */
    public static void SetHomeAsUp(Activity activity) {
        activity.getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Getting JSONValid or not
     *
     * @return true or false.
     */

    public static boolean isJSONValid(String string) {
        try {
            new JSONObject(string);
        } catch (JSONException ex) {
            return false;
        }
        return true;
    }

    /**
     * Sets Home As Up Enabled
     */
    @SuppressLint("NewApi")
    public static void RemoveHomeAsUp(Activity activity) {
        activity.getActionBar().setDisplayHomeAsUpEnabled(false);
        activity.getActionBar().setHomeButtonEnabled(false);
    }

    /*
     * Function Takes a List of String as Input and returns a comma separated
     * string.
     */
    public static String ListToCommaSeparatedString(List<String> list) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (sb.length() > 0 && i != (list.size() - 1))
                sb.append(",");
        }

        return sb.toString();
    }

    /*
     * This Function is supposed to take a comma separated string and convert
     * that to a list.
     */
    public static List<String> CommaSeparatedStringToList(String string) {
        return Arrays.asList(string.split("\\s*,\\s*"));
    }


    /*
     * Checks if a string is null or epmty,
     *
     * @Param, String SReturn Boolean.
     */
    public static boolean checkNullAndEmpty(String s) {
        boolean result = false;
        try {
            if (s != null && !s.equalsIgnoreCase("")) {
                result = true;
            }
        } catch (Exception e) {

        }
        return result;
    }

    /**
     * switch the fragment in the screen.
     *
     * @param mActivity
     * @param fr
     */
    public static void switchFragment(FragmentActivity mActivity, Fragment fr,
                                      int containerViewID, String tagName) {
        if (fr == null && mActivity == null) {
            return;
        }

        FragmentManager fm = mActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(containerViewID, fr, tagName);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    /**
     * Get response from the server
     *
     * @return server response.
     */
    public static String getServerResponse(String stringURL,
                                           String contentType, String requestMethod, HashMap<String, String> headerValueMap,
                                           String json) {
        String response = "";
        if (stringURL == null || stringURL.equalsIgnoreCase("")) {
            return response;
        }
        try {
            if (Constants.DEBUG)
                CommonUtil.log(TAG, "final URL is  : " + stringURL);
            URL url = new URL(stringURL);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setUseCaches(false);
            if (CommonUtil.isHavingValue(requestMethod))
                connection.setRequestMethod(requestMethod);
            String defaultContentVal = "application/x-www-form-urlencoded";
            if (isHavingValue(contentType)) {
                defaultContentVal = contentType;
            }
            String mHeaderKey;
            String mHeaderVal;
            if (headerValueMap != null && headerValueMap.size() > 0) {
                Iterator it = headerValueMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pairs = (Map.Entry) it.next();
                    mHeaderKey = pairs.getKey().toString().trim();
                    mHeaderVal = pairs.getValue().toString().trim();
                    CommonUtil.log(TAG, pairs.getKey() + " = "
                            + pairs.getValue());
                    if (isHavingValue(mHeaderKey) && isHavingValue(mHeaderVal)) {
                        connection.setRequestProperty(mHeaderKey, mHeaderVal);
                    }
                }
            }

            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 ( compatible ) ");
            connection.setRequestProperty("Accept", "*/*");
            DataInputStream dataIn = new DataInputStream(
                    connection.getInputStream());
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(dataIn));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response += inputLine;
            }
            if (Constants.DEBUG)
                CommonUtil.log(TAG, "final response is  : " + response);
            br.close();
            dataIn.close();

        } catch (Exception e) {

        }
        return response;
    }


    /**
     * Get String data from jsonData
     *
     * @param jsonData - json object
     * @param info     - search keyword
     * @return - value of the searched object, return null if keyword not
     * available
     * @throws JSONException parser exception.
     */
    public static String getDataFromJSON(final JSONObject jsonData, String info) {
        try {
            if (jsonData.has(info)) {
                return jsonData.getString(info);
            } else {
                return "";
            }
        } catch (JSONException e) {

            return "";
        }
    }

    /**
     * Get Boolean data from jsonData
     *
     * @param jsonData - json object
     * @param info     - search keyword
     * @return - value of the searched object, return null if keyword not
     * available
     * @throws JSONException parser exception.
     */
    public static boolean getBooleanFromJSON(final JSONObject jsonData,
                                             String info) {
        try {
            if (jsonData.has(info)) {

                return jsonData.getBoolean(info);
            } else {
                return false;
            }
        } catch (JSONException e) {

            return false;
        }
    }

    /**
     * Get Integer data from jsonData
     *
     * @param jsonData - json object
     * @param info     - search keyword
     * @return - value of the searched object, return null if keyword not
     * available
     * @throws JSONException parser exception.
     */
    public static int getIntFromJSON(final JSONObject jsonData, String info) {
        try {
            if (jsonData.has(info)) {
                if (!(JSONObject.NULL.equals(jsonData.get(info)))) {
                    return jsonData.getInt(info);
                } else {
                    return -1;
                }
            } else {
                return -1;
            }
        } catch (JSONException e) {

            return -1;
        }
    }

    /**
     * get JSON Array from jsonData
     *
     * @param jsonData
     * @param info
     * @return
     * @throws JSONException
     */
    public static JSONArray getDataArrayFromJSON(final JSONObject jsonData,
                                                 String info) {
        try {
            if (jsonData.has(info)
                    && !(JSONObject.NULL.equals(jsonData.get(info)))) {
                return jsonData.getJSONArray(info);
            } else {
                return null;
            }
        } catch (JSONException e) {

            return null;
        }
    }

    /**
     * get Object data from jsonData
     *
     * @param jsonData
     * @param info
     * @return
     * @throws JSONException
     */
    public static JSONObject getDataObjectFromJSON(final JSONObject jsonData,
                                                   String info) {
        try {
            if (jsonData.has(info)
                    && !(JSONObject.NULL.equals(jsonData.get(info)))) {
                return jsonData.getJSONObject(info);
            } else {
                return null;
            }
        } catch (JSONException e) {

            return null;
        }
    }

    /**
     * get Date from jsonData
     *
     * @param jsonData
     * @param info
     * @return
     * @throws JSONException
     */
    public static Date getDateObjectFromJSON(final JSONObject jsonData,
                                             String info) throws java.text.ParseException {
        try {
            if (jsonData.has(info)) {
                // NOTE: SimpleDateFormat uses GMT[-+]hh:mm for the TZ which
                // breaks
                // things a bit. Before we go on we have to repair this.
                SimpleDateFormat df = new SimpleDateFormat(
                        "yyyy-MM-dd'T'HH:mm:ssz");
                // this is zero time so we need to add that TZ indicator for
                String date = jsonData.getString(info);
                if (date.endsWith("Z")) {
                    date = date.substring(0, date.length() - 1) + "GMT-00:00";
                } else {
                    int inset = 6;

                    String s0 = date.substring(0, date.length() - inset);
                    String s1 = date.substring(date.length() - inset,
                            date.length());

                    date = s0 + "GMT" + s1;
                }
                Date temp = df.parse(date);
                return temp;
                // return jsonData.getJSONObject(info);
            } else {
                return null;
            }
        } catch (JSONException e) {

            return null;
        }
    }

    /**
     * Checking valid email Address
     *
     * @param emailID
     * @return
     */
    public static boolean isValidEmail(String emailID) {
        try {
            Pattern pattern;
            Matcher matcher;
            String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(emailID);
            return matcher.matches();
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * Checking valid password
     *
     * @param password
     * @return true if password match else return false
     */
    public static boolean isValidPassword(String password) {
        boolean sReturnValue = false;
        try {
            String special = "!@#$%^&*()_";
            String pattern = ".*[" + Pattern.quote(special) + "].*";
            if (password.matches(pattern)) {
                sReturnValue = true;
            } else {
                sReturnValue = false;
            }

        } catch (Exception e) {

        }
        return sReturnValue;
    }

    /**
     * Checking whether the string is null or not.
     *
     * @param response
     * @return true if string not empty else return false
     */
    public static boolean isHavingValue(String response) {
        if (response != null && !response.equalsIgnoreCase("")) {
            return true;
        }
        return false;
    }

    /**
     * Shows a Toast Message in the Activity.
     *
     * @param mActivity
     * @param message
     */
    public static void showToastMessage(final Activity mActivity,
                                        final String message) {
        if (mActivity != null) {
            mActivity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast toast = Toast.makeText(mActivity.getApplicationContext(), message,
                            Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            });
        }
    }

    /**
     * Show alert dialog with positive and negative button both.
     *
     * @param posButtonText
     * @param negButtonText
     * @param message
     * @param context
     * @param positiveClick
     * @param negativeClick
     * @return
     */
    public static Builder showDialog(String posButtonText,
                                     String negButtonText, String message, final Context context,
                                     DialogInterface.OnClickListener positiveClick,
                                     DialogInterface.OnClickListener negativeClick) {

        Builder alertDialog = new Builder(context);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        alertDialog.setPositiveButton(posButtonText, positiveClick);
        alertDialog.setNegativeButton(negButtonText, negativeClick);

        // Showing Alert Dialog
        alertDialog.show();

        return alertDialog;

    }

    /**
     * Show alert dialog To Ask People To Sign In.
     *
     * @param posButtonText
     * @param negButtonText
     * @param message
     * @param context
     * @return
     */
    public static Builder showDialog(String posButtonText,
                                     String negButtonText, String message, final Context context, final String targetFragment, final Bundle selectedItem) {

        Builder alertDialog = new Builder(context);
        alertDialog
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(posButtonText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                .setNegativeButton(negButtonText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });

        // show it
        alertDialog.show();

        return alertDialog;

    }

    /**
     * Show alert dialog To Ask People To Sign In.
     *
     * @param context
     * @return
     */
    public static Builder showNoNetworkDialog(final Context context) {
        Builder alertDialog = new Builder(context);
        alertDialog
                .setMessage(Constants.ERROR_MESSAGE_NO_INTERNET)
                .setCancelable(false)
                .setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                ((Activity) context)
                                        .startActivity(new Intent(
                                                Settings.ACTION_SETTINGS));

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // show it
        alertDialog.show();

        return alertDialog;

    }

    /**
     * Show alert dialog To for Error Message.
     *
     * @param message
     * @param context
     * @return
     */
    public static Builder showDialog(String neutralButtonText, String message,
                                     final Context context) {
        Builder alertDialog = new Builder(context);
        alertDialog
                .setMessage(message)
                .setCancelable(false)
                .setNeutralButton(neutralButtonText,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        alertDialog.show();
        return alertDialog;
    }

    /**
     * Show alert dialog with positive and negative button both.
     *
     * @param posButtonText
     * @param negButtonText
     * @param message
     * @param title
     * @param context
     * @param positiveClick
     * @param negativeClick
     * @return
     */
    public static Builder showDialog(String posButtonText,
                                     String negButtonText, String message, String title,
                                     final Context context,
                                     DialogInterface.OnClickListener positiveClick,
                                     DialogInterface.OnClickListener negativeClick) {

        Builder alertDialog = new Builder(context);

        // Setting Dialog Message
        alertDialog.setMessage(message);
        alertDialog.setTitle(title);
        alertDialog.setPositiveButton(posButtonText, positiveClick);
        alertDialog.setNegativeButton(negButtonText, negativeClick);

        // Showing Alert Dialog
        alertDialog.show();

        return alertDialog;

    }

    /**
     * Show alert dialog with Ok Button
     *
     * @param message
     * @param context
     * @param positiveClick
     * @return
     */
    public static Builder showErrorDialog(String message,
                                          final Context context, DialogInterface.OnClickListener positiveClick) {
        Builder alertDialog = new Builder(context);
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("OK", positiveClick);
        alertDialog.show();
        return alertDialog;

    }

    /**
     * Check whether 2 string is equal.
     *
     * @param value
     * @param tocompare
     * @return
     */
    public static boolean cheakEqualStringValues(String value, String tocompare) {
        if (!checkNullAndEmpty(value.trim())
                || !checkNullAndEmpty(tocompare.trim())) {
            return false;
        }
        if (value.trim().equalsIgnoreCase(tocompare.trim())) {
            return true;
        }
        return false;
    }

    public static String getDeviceId(Context mActivity) {
        String android_id = "";
        try {
            android_id = Settings.Secure.getString(mActivity.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
        }
        return android_id;
    }

    /**
     * Function to get Progress percentage
     *
     * @param currentDuration
     * @param totalDuration
     */
    public static int getProgressPercentage(long currentDuration,
                                            long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);
        percentage = (((double) currentSeconds) / totalSeconds) * 100;
        return percentage.intValue();
    }

    /**
     * Function to change progress to timer
     *
     * @param progress      -
     * @param totalDuration returns current duration in milliseconds
     */
    public static int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = totalDuration / 1000;
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }

    /**
     * Converts MillitSecond to Time.
     *
     * @param millsec -
     *                <p>
     *                returns current duration in milliseconds
     */
    public static String convertMillisecToTime(String millsec) {
        String time = "";
        try {
            int milSec = 0;
            try {
                milSec = Integer.parseInt(millsec);
            } catch (NumberFormatException e) {
                milSec = 0;

            }
            long second = (milSec / 1000) % 60;
            long minute = (milSec / (1000 * 60)) % 60;
            long hour = (milSec / (1000 * 60 * 60)) % 24;

            time = String.format("%02d:%02d:%02d", hour, minute, second);
        } catch (Exception e) {

        }
        return time;
    }

    /**
     * Adding Headervalues using Webservice Calling
     *
     * @param mActivity -
     *                  returns headerVal
     */
  /*  public static HashMap<String, String> getHeaderValue(Activity mActivity)
    {
        SharedPreferences appPreferences = YummyChefApp.getSharedPreferences();
        HashMap<String, String> headerVal = new HashMap<String, String>();
        boolean isUserLoggedIn = isUserLoggedIn(appPreferences);
        headerVal.put("GuestUser", isUserLoggedIn ? "0" : "1");
        headerVal.put("AccessToken", appPreferences.contains(Constants.APP_PREFS_AUTH_TOKEN) ? appPreferences.getString(Constants.APP_PREFS_AUTH_TOKEN, "") : "0");
        return headerVal;
    }*/

    /**
     * Webservice Calling GET Method
     *
     * @param stringURL
     * @param requestMethod
     * @param headerValueMap -
     *                       returns response from given Webservice URL.
     */
    public static String getDataFromServer(String stringURL,
                                           String requestMethod, HashMap<String, String> headerValueMap) {
        InputStream inputStream = null;
        String response = "";
        if (stringURL == null || stringURL.equalsIgnoreCase("")) {
            return response;
        }
        try {

            CommonUtil.log(TAG, " <URL> " + stringURL);
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // make POST request to the given URL
            HttpGet httpGet = new HttpGet(stringURL);

            // Set some headers to inform server about the type of the content
            httpGet.setHeader("Accept", "application/json");
            httpGet.setHeader("Content-type", "application/json");
            String mHeaderKey = null;
            String mHeaderVal = null;
            if (headerValueMap != null && headerValueMap.size() > 0) {
                Iterator it = headerValueMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pairs = (Map.Entry) it.next();
                    mHeaderKey = pairs.getKey().toString().trim();
                    mHeaderVal = pairs.getValue().toString().trim();
                    CommonUtil.log(TAG, pairs.getKey() + " == " + pairs.getValue());
                    if (isHavingValue(mHeaderKey) && isHavingValue(mHeaderVal)) {
                        httpGet.setHeader(mHeaderKey, mHeaderVal);
                    }
                    // it.remove(); // to avoids a
                    // ConcurrentModificationException
                }
            }
            // Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpGet);

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            String res = null;
            // convert inputstream to string
            if (inputStream != null) {
                res = convertInputStreamToString(inputStream);
            } else {
                res = "";
            }
            response = res;
            inputStream.close();

        } catch (Exception e) {

        }
        return response;
    }

    /**
     * Webservice Calling POST Method
     *
     * @param stringURL
     * @param contentType
     * @param requestMethod
     * @param headerValueMap -
     *                       returns response from given Webservice URL.
     */
    public static String postDataToServer(String stringURL, String contentType,
                                          String requestMethod, HashMap<String, String> headerValueMap,
                                          String json) {

        InputStream inputStream = null;
        String response = "";
        if (stringURL == null || stringURL.equalsIgnoreCase("")) {
            return response;
        }
        try {
            CommonUtil.log(TAG, "URL is  : " + stringURL);
            CommonUtil.log(TAG, "json is  : " + json);
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // make POST request to the given URL
            HttpPost httpPost = new HttpPost(stringURL);

            // Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            String mHeaderKey = null;
            String mHeaderVal = null;
            if (headerValueMap != null && headerValueMap.size() > 0)
            {
                Iterator it = headerValueMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pairs = (Map.Entry) it.next();
                    mHeaderKey = pairs.getKey().toString().trim();
                    mHeaderVal = pairs.getValue().toString().trim();
                    CommonUtil.log(TAG, pairs.getKey() + " = "
                            + pairs.getValue());
                    if (isHavingValue(mHeaderKey) && isHavingValue(mHeaderVal)) {
                        httpPost.setHeader(mHeaderKey, mHeaderVal);
                    }
                }
            }
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);


            // Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // receive response as inputStream
            //			inputStream = httpResponse.getEntity().getContent();
            DataInputStream dataIn = new DataInputStream(
                    httpResponse.getEntity().getContent());
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(dataIn));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response += inputLine;
            }
            if (Constants.DEBUG)
                CommonUtil.log(TAG, "final response is  : " + response);
            br.close();
            dataIn.close();

        } catch (Exception e) {

        }
        return response;
    }





    /**
     * Webservice Calling POST Method with authorization
     *
     * @param stringURL
     * @param contentType
     * @param requestMethod
     * @param headerValueMap -
     *                       returns response from given Webservice URL.
     */
    public static String postDataToServerwithautho(String stringURL, String contentType,
                                                   String requestMethod, HashMap<String, String> headerValueMap,
                                                   String json, String accesstoken) {

        InputStream inputStream = null;
        String response = "";
        if (stringURL == null || stringURL.equalsIgnoreCase("")) {
            return response;
        }
        try {
            CommonUtil.log(TAG, "URL is  : " + stringURL);
            CommonUtil.log(TAG, "json is  : " + json);
            CommonUtil.log(TAG, "json accesstoken  : " + accesstoken);
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // make POST request to the given URL
            HttpPost httpPost = new HttpPost(stringURL);

            // Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            if(accesstoken!=null) {
                httpPost.setHeader("Authorization", "Bearer " + accesstoken);
            }
            String mHeaderKey = null;
            String mHeaderVal = null;
            if (headerValueMap != null && headerValueMap.size() > 0)
            {
                Iterator it = headerValueMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pairs = (Map.Entry) it.next();
                    mHeaderKey = pairs.getKey().toString().trim();
                    mHeaderVal = pairs.getValue().toString().trim();
                    CommonUtil.log(TAG, pairs.getKey() + " = "
                            + pairs.getValue());
                    if (isHavingValue(mHeaderKey) && isHavingValue(mHeaderVal)) {
                        httpPost.setHeader(mHeaderKey, mHeaderVal);
                    }
                }
            }
            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);


            // Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // receive response as inputStream
            //			inputStream = httpResponse.getEntity().getContent();
            DataInputStream dataIn = new DataInputStream(
                    httpResponse.getEntity().getContent());
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(dataIn));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response += inputLine;
            }
            if (Constants.DEBUG)
                CommonUtil.log(TAG, "final response is  : " + response);
            br.close();
            dataIn.close();

        } catch (Exception e) {

        }
        return response;
    }

    /**
     * Webservice Calling POST Method
     *
     * @param stringURL
     *
     *@param headerValueMap -with token
     *                       returns response from given Webservice URL.
     */
    public static String postDataToServerwithtoken(String stringURL, HashMap<String, String> headerValueMap,
                                                   String json, String accesstoken) {

        InputStream inputStream = null;
        String response = "";
        if (stringURL == null || stringURL.equalsIgnoreCase("")) {
            return response;
        }
        try {
            CommonUtil.log(TAG, "URL is  : " + stringURL);


            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // make POST request to the given URL
            HttpPost httpPost = new HttpPost(stringURL);

            // Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + accesstoken);
            String mHeaderKey = null;
            String mHeaderVal = null;
            if (headerValueMap != null && headerValueMap.size() > 0) {
                Iterator it = headerValueMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pairs = (Map.Entry) it.next();
                    mHeaderKey = pairs.getKey().toString().trim();
                    mHeaderVal = pairs.getValue().toString().trim();
                    CommonUtil.log(TAG, pairs.getKey() + " = "
                            + pairs.getValue());
                    if (isHavingValue(mHeaderKey) && isHavingValue(mHeaderVal)) {
                        httpPost.setHeader(mHeaderKey, mHeaderVal);
                    }
                }
            }
            if(isHavingValue(json)) {
                StringEntity entity = new StringEntity(json);
                httpPost.setEntity(entity);
            }

            // Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // receive response as inputStream
            //			inputStream = httpResponse.getEntity().getContent();
            DataInputStream dataIn = new DataInputStream(
                    httpResponse.getEntity().getContent());
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(dataIn));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response += inputLine;
            }
            if (Constants.DEBUG)
                CommonUtil.log(TAG, "final response is  : " + response);
            br.close();
            dataIn.close();

        } catch (Exception e) {

        }
        return response;
    }

    /*Calll Http Put method*/

    public static String PutDataToServer(String stringURL, String contentType,
                                         String requestMethod, HashMap<String, String> headerValueMap,
                                         String json, String accesstoken) {

        InputStream inputStream = null;
        String response = "";
        if (stringURL == null || stringURL.equalsIgnoreCase("")) {
            return response;
        }
        try {
            CommonUtil.log(TAG, "URL is  : " + stringURL);
            CommonUtil.log(TAG, "json is  : " + json);
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // make POST request to the given URL
            HttpPut httpput = new HttpPut(stringURL);

            // Set some headers to inform server about the type of the content
            httpput.setHeader("Accept", "application/json");
            httpput.setHeader("Content-type", "application/json");
            if(accesstoken!=null) {
                httpput.setHeader("Authorization", "Bearer " + accesstoken);
            }
            String mHeaderKey = null;
            String mHeaderVal = null;
            if (headerValueMap != null && headerValueMap.size() > 0)
            {
                Iterator it = headerValueMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pairs = (Map.Entry) it.next();
                    mHeaderKey = pairs.getKey().toString().trim();
                    mHeaderVal = pairs.getValue().toString().trim();
                    CommonUtil.log(TAG, pairs.getKey() + " = "
                            + pairs.getValue());
                    if (isHavingValue(mHeaderKey) && isHavingValue(mHeaderVal)) {
                        httpput.setHeader(mHeaderKey, mHeaderVal);
                    }
                }
            }
            StringEntity entity = new StringEntity(json);
            httpput.setEntity(entity);


            // Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpput);

            // receive response as inputStream
            //			inputStream = httpResponse.getEntity().getContent();
            DataInputStream dataIn = new DataInputStream(
                    httpResponse.getEntity().getContent());
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(dataIn));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response += inputLine;
            }
            if (Constants.DEBUG)
                CommonUtil.log(TAG, "final response is  : " + response);
            br.close();
            dataIn.close();

        } catch (Exception e) {

        }
        return response;
    }


    /**
     * Convert InputStream To String
     *
     * @param inputStream -
     *                    returns response string.
     */
    public static String convertInputStreamToString(InputStream inputStream) {
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(
                    inputStream));
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;

            CommonUtil.log(TAG, "Input Stream to String : " + result);
            return result;
        } catch (IllegalStateException e) {

        } catch (IOException e) {

        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {

            }
        }

        return null;

    }

    /**
     * Get Key HashValue
     *
     * @param mActivity -
     *                  returns keyHash.
     */
    public static String getKeyHashValue(Activity mActivity) {
        // Add code to print out the key hash
        String keyHash = "";
        try {

            PackageInfo info = mActivity.getPackageManager().getPackageInfo(
                    "packagename",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);
            }
        } catch (NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        return keyHash;
    }

    /**
     * Sets the Header Style and Title of the ActionBar.
     *
     * @param mActivity
     * @param title
     * @param Color     .
     */
    public static void setHeaderStyleAndTitle(Activity mActivity,
                                              CharSequence title, int Color) {
        mActivity.getActionBar()
                .setBackgroundDrawable(new ColorDrawable(Color));
        mActivity.getActionBar().setTitle(title);
    }

    /**
     * Sets the Title of the ActionBar.
     *
     * @param title
     */
    public static void setHeaderTitle(Activity mActivity, CharSequence title) {
        mActivity.getActionBar().setTitle(title);
    }

    /**
     * Gets the JSON from Resource.
     *
     * @param is Stream
     */
    public static String GetJSONFromResource(InputStream is) {
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is,
                    "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block

        } catch (IOException e) {
            // TODO Auto-generated catch block

        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block

            }
        }

        return writer.toString();
    }

    /**
     * check whether connected network or not.
     *
     * @param _context
     * @return
     */
    public static boolean isConnectedToInternet(Context _context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) _context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }

            }
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * Post a Log.
     *
     * @param tag
     * @param string
     */
    public static void log(String tag, String string) {
        if (Constants.DEBUG) {
            Log.d(tag, string);
        }
    }

    /**
     * hide KeyBoard in Activity Loading time
     *
     * @param mActivity
     */
    public static void hideKeyBoard(Activity mActivity) {
        if (mActivity != null) {
            try {
                InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mActivity.getWindow().getDecorView().getWindowToken(), 0);
            } catch (Exception e) {

            }
        }


    }

    /**
     * check whether the entering ip(String) is valid or not
     *
     * @param s - ip entered
     * @return ture/false valid ip
     */
    public static boolean checkValidIp(String s) {
        try {
            log(TAG, "Is Valid IP " + s + "   " + InetAddressUtils.isIPv4Address(s));
            return InetAddressUtils.isIPv4Address(s);
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Get FileName From URL
     *
     * @param url
     * @return filename
     */
    public static String getFileNameFromURL(String url) {
        String fileName = "";
        try {
            fileName = url.substring(url.lastIndexOf('/') + 1).trim();
        } catch (Exception e) {
            CommonUtil.log(TAG, e.getMessage());
        }
        return fileName;
    }


    /**
     * Webservice Calling PUT Method
     *
     * @param stringURL
     * @param contentType
     * @param requestMethod
     * @param headerValueMap -
     *                       returns response from given Webservice URL.
     */
    public static String putDataToServer(String stringURL, String contentType,
                                         String requestMethod, HashMap<String, String> headerValueMap,
                                         String json, String accesstoken) {

        InputStream inputStream = null;
        String response = "";
        if (stringURL == null || stringURL.equalsIgnoreCase("")) {
            return response;
        }
        try {
            CommonUtil.log(TAG, "URL is  : " + stringURL);


            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // make POST request to the given URL
            HttpPut httpPut = new HttpPut(stringURL);

            // Set some headers to inform server about the type of the content
            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json");
            httpPut.setHeader("Authorization", "Bearer " + accesstoken);
            String mHeaderKey = null;
            String mHeaderVal = null;
            if (headerValueMap != null && headerValueMap.size() > 0) {
                Iterator it = headerValueMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pairs = (Map.Entry) it.next();
                    mHeaderKey = pairs.getKey().toString().trim();
                    mHeaderVal = pairs.getValue().toString().trim();
                    CommonUtil.log(TAG, pairs.getKey() + " = "
                            + pairs.getValue());
                    if (isHavingValue(mHeaderKey) && isHavingValue(mHeaderVal)) {
                        httpPut.setHeader(mHeaderKey, mHeaderVal);
                    }
                }
            }
            if(json!=null) {
                CommonUtil.log(TAG, "json is  : " + json);
                StringEntity entity = new StringEntity(json);
                httpPut.setEntity(entity);
            }



            // Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPut);

            // receive response as inputStream
            //			inputStream = httpResponse.getEntity().getContent();
            DataInputStream dataIn = new DataInputStream(
                    httpResponse.getEntity().getContent());
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(dataIn));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response += inputLine;
            }
            if (Constants.DEBUG)
                CommonUtil.log(TAG, "final response is  : " + response);
            br.close();
            dataIn.close();

        } catch (Exception e) {

        }
        return response;
    }

    /*Upload Image*/
    public static String executeMultipartPost(Bitmap bm, String accesstoken, String userid) throws Exception {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());
            String filename = "IMG_" + timeStamp + ".jpg";
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] data = bos.toByteArray();

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(Image_upload);
            String tokent = accesstoken;
            CommonUtil.log(TAG," >>>>>>>>Image UURRLL <<<<<<<<<< " + Image_upload);
            postRequest.setHeader("Authorization", "Bearer " + accesstoken);
            Log.d(TAG, userid + " >>>>" + tokent);
            org.apache.http.entity.mime.MultipartEntity reqEntity = new org.apache.http.entity.mime.MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("userId", new StringBody("" + userid));
            reqEntity.addPart("profile_picture", new ByteArrayBody(data, "image/jpeg", "" + filename));



            postRequest.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(postRequest);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent(), "UTF-8"));
            String sResponse;
            StringBuilder s = new StringBuilder();

            while ((sResponse = reader.readLine()) != null) {
                s = s.append(sResponse);
            }
            System.out.println("Response: " + s);
            return "" + s;
        } catch (Exception e) {

            // handle exception here
            Log.d(TAG, " the exception " + e.getMessage());
            Log.e(e.getClass().getName(), e.getMessage());
            return "faild " + e.getMessage();
        }

    }
    public static String Uploadcarimage(Bitmap bm, String accesstoken, String carid) throws Exception {
        try {
            Log.d(TAG, accesstoken+" the data>>> " + carid);
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());
            String filename = "IMG_" + timeStamp + ".jpg";
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] data = bos.toByteArray();

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(Constants.UploadCarimage);
            String tokent = accesstoken;
            postRequest.setHeader("Authorization", "Bearer " + accesstoken);

            org.apache.http.entity.mime.MultipartEntity reqEntity = new org.apache.http.entity.mime.MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("car_id", new StringBody(""+carid));
            reqEntity.addPart("car_image", new ByteArrayBody(data, "image/jpeg", "" + filename));



            postRequest.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(postRequest);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent(), "UTF-8"));
            String sResponse;
            StringBuilder s = new StringBuilder();

            while ((sResponse = reader.readLine()) != null) {
                s = s.append(sResponse);
            }
            System.out.println("Response: " + s);
            return "" + s;
        } catch (Exception e) {

            // handle exception here
            Log.d(TAG, " the exception " + e.getMessage());
            Log.e(e.getClass().getName(), e.getMessage());
            return "faild " + e.getMessage();
        }

    }



    public static String documentupdate(Bitmap bm, String accesstoken, String documentname, String userid) throws Exception {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.getDefault()).format(new Date());
            String filename = "IMG_" + timeStamp + ".jpg";
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] data = bos.toByteArray();

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(Constants.Document_upload);
            String tokent = accesstoken;
            postRequest.setHeader("Authorization", "Bearer " + accesstoken);
            Log.d(TAG, documentname + " >>>>" + tokent+" User id "+userid);
            org.apache.http.entity.mime.MultipartEntity reqEntity = new org.apache.http.entity.mime.MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addPart("user", new StringBody(""+userid));
            reqEntity.addPart("document_type ", new StringBody("1"));
            reqEntity.addPart("document_name", new StringBody("" + documentname));
            reqEntity.addPart("document_image", new ByteArrayBody(data, "image/jpeg", "" + filename));



            postRequest.setEntity(reqEntity);
            HttpResponse response = httpClient.execute(postRequest);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent(), "UTF-8"));
            String sResponse;
            StringBuilder s = new StringBuilder();

            while ((sResponse = reader.readLine()) != null) {
                s = s.append(sResponse);
            }
            System.out.println("Response: " + s);
            return "" + s;
        } catch (Exception e) {

            // handle exception here
            Log.d(TAG, " the exception " + e.getMessage());
            Log.e(e.getClass().getName(), e.getMessage());
            return "faild " + e.getMessage();
        }

    }

    private Typeface typeface;

    public static Typeface getfont(Context mContext, int id) {
        //  comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");
        switch (id) {
            case Constants.Roboto_Light:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");

                break;
            case Constants.Roboto_Thin:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Thin.ttf");
                break;
            case Constants.Roboto_Regular:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Regular.ttf");
                break;

            case Constants.Roboto_Bold:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Bold.ttf");
                break;
            case Constants.Roboto_BlackItalic:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-BlackItalic.ttf");
                break;
            case Constants.RobotoCondensed_Bold:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/RobotoCondensed-Bold.ttf");
                break;
            case Constants.RobotoCondensed_BoldItalic:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/RobotoCondensed-BoldItalic.ttf");
                break;
            case Constants.RobotoCondensed_Italic:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/HelveticaNeueBd.ttf");
                break;
            case Constants.RobotoCondensed_Light:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/RobotoCondensed-Italic.ttf");
                break;
            case Constants.RobotoCondensed_LightItalic:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/RobotoCondensed-LightItalic.ttf");
                break;
            case Constants.RobotoCondensed_Regular:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/RobotoCondensed-Regular.ttf");
                break;
            case Constants.Roboto_Black:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Black.ttf");
                break;

            case Constants.Roboto_LightItalic:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-LightItalic.ttf");
                break;
            case Constants.Roboto_Medium:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Medium.ttf");
                break;
            case Constants.Roboto_ThinItalic:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-ThinItalic.ttf");
                break;
            case Constants.Quicksand_Bold:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/Quicksand-Bold.ttf");
                break;
            case Constants.Quicksand_Light:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/Quicksand-Light.ttf");
                break;
            case Constants.Quicksand_Medium:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/Quicksand-Medium.ttf");
                break;
            case Constants.Quicksand_Regular:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/Quicksand-Regular.ttf");
                break;


            case Constants.Helvetica_Neu_Bold:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/Helvetica Neu Bold.ttf");
                break;
            case Constants.Helvetica_Neue_BlackCond:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/HelveticaNeue BlackCond.ttf");
                break;

            case Constants.Helvetica_Neue_Light:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/HelveticaNeue Light.ttf.ttf");
                break;
            case Constants.Helvetica_Neue_Medium:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/HelveticaNeue Medium.ttf");
                break;
            case Constants.Helvetica_Neue_Thin:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/HelveticaNeue Thin.ttf");
                break;
            case Constants.Helvetica_Neue:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/HelveticaNeue.ttf");
                break;
            case Constants.Helvetica_NeueBd:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/HelveticaNeueBd.ttf");
                break;
            case Constants.Helvetica_NeueHv:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/HelveticaNeueHv.ttf");
                break;
            case Constants.Helvetica_NeueIt:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/HelveticaNeueIt.ttf");
                break;
            case Constants.Helvetica_NeueLt:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/HelveticaNeueLt.ttf");
                break;
            case Constants.Helvetica_NeueMed:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/HelveticaNeueMed.ttf");
                break;


            default:
                comfortaa = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");
                break;
        }


        return comfortaa;
    }


    /*
  *To get a Bitmap image from the URL received
  * */
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }


   /* public static void EnableGPS(final FragmentActivity mActivity) {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(mActivity)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {

                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                            Log.d("Location error", "Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            builder.setAlwaysShow(true);

            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(mActivity, REQUEST_LOCATION);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                    }
                }
            });
        }

    }*/

    /*Make Google URL*/
    public static String makeGoogleURL (double sourcelat, double sourcelog, double destlat, double destlog ){
        StringBuilder urlString = new StringBuilder();
        urlString.append(""+ Constants.Goolgle_Location_details);
        urlString.append("?origin=");// from
        urlString.append(Double.toString(sourcelat));
        urlString.append(",");
        urlString
                .append(Double.toString( sourcelog));
        urlString.append("&destination=");// to
        urlString
                .append(Double.toString( destlat));
        urlString.append(",");
        urlString.append(Double.toString( destlog));
        urlString.append("&sensor=false&mode=driving&alternatives=true");
        urlString.append("&key="+Constants.Google_map_key);
        Log.d(TAG,">>>>>>>>>>>>>>make url "+urlString);
        return urlString.toString();
    }
    /*Get Distance detail from google URL*/



    public static boolean validatedistance(String result)
    {
        if(result!=null){
            final JSONObject json;
            try {
                json = new JSONObject(result);
                JSONArray routeArray = json.getJSONArray("routes");
                JSONObject routes = routeArray.getJSONObject(0);
                JSONArray distancearray = routes.getJSONArray("legs");
                JSONObject distanceobj = distancearray.getJSONObject(0);
                JSONObject distance = distanceobj.getJSONObject("distance");
                Log.d(TAG,"????????????json object "+distance);
                String numberOnly = distance.getString("text");
                Log.d(TAG,">>>>>>>>>>the number only "+numberOnly);
if(!numberOnly.contains("km"))
{
    return true;
}else {
    return false;
}
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }

        }
        return false;
    }


    public static String convertdate(String conversiondate)
    {
        String dateStr = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date date = null;//You will get date object relative to server/client timezone wherever it is parsed
            date = dateFormat.parse(conversiondate);
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy, HH:mm.a");
             dateStr = formatter.format(date);

        } catch (ParseException e) {

            e.printStackTrace();
            return dateStr;

        }
        return dateStr;
    }


    public static String Ridehistoryconvertdatetostring(String datestring, String dateformate)
    {
        String dateStr=null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
            Date date = null;//You will get date object relative to server/client timezone wherever it is parsed
            date = dateFormat.parse(datestring);
            Log.d(TAG,">>>>>>>>>>date time>>>>>>>>>>>>>>>"+datestring);
            //DateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
            DateFormat formatter = new SimpleDateFormat(""+dateformate);
            dateStr = formatter.format(date);

        } catch (Exception e) {

            e.printStackTrace();
            return dateStr;
        }
        return dateStr;
    }
    public static String saprateimageurlurl(String result) {
        String url = "www.googl.com";
        if (CommonUtil.isHavingValue(result)) {
            try {
                JSONArray imagearray = new JSONArray(result);
                if(imagearray!=null&&imagearray.length()>0)
                {
                    if (result.contains("image_name")) {

                        JSONObject reponseimage = new JSONObject();
                        JSONObject imageobj = new JSONObject(imagearray.getString(imagearray.length() - 1));
                        Log.d(TAG, "   URL  " + Constants.Image_display + "" + imageobj.getString("image_name"));
                        url = Constants.Image_display + "" + imageobj.getString("image_name");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return url;
            }

        }
        return url;
    }

    public static ProgressDialog createProgressDialog(Context mContext) {
        ProgressDialog dialog = new ProgressDialog(mContext);
        try {
            dialog.show();
        } catch (Exception e) {
            CommonUtil.log(TAG, "Custom Progress Loading Failed.. " + e.getMessage());
        }
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_progrss_layout);
        return dialog;
    }

}
