package com.unicornwings.unicornwings.Utill;


public class Constants {
    public static final boolean DEBUG = true;
    public static final String FACEBOOK_SENDER_ID = "858510350947335";
    public static final String[] FB_PERMISSIONS = new String[]{"email", "publish_actions"};
    public static final String REQUEST_METHOD_GET = "GET";
    public static final String REQUEST_METHOD_POST = "POST";
    public static final String REQUEST_METHOD_PUT = "PUT";
    public static final String ERROR_TYPE_VALUE = "API";
    public static final String APP_PREFS_ACCOUNT = "Unicornwings";
    public static final String HEADER_KEY_AUTH_PROJECT_ID = "X-Auth-Project-Id";
    public static final String HEADER_KEY_ACCESSTOKEN = "X-Auth-Token";
    public static final String APP_API_HEADER_KEY = "Api-Key";
    public static final String DEVICE_TYPE_HEADER_KEY = "Device-Type";
    public static final String DEVICE_TYPE_VALUE = "2";
    public static final String AUTH_TOKEN_HEADER_KEY = "Authentication-Token";
    public static final String AUTO_SIGNIN_HEADER_KEY = "Auto-SignIn";
    public static final String GOOGLE_PROJECT_ID = "1054236757897";
    public static final String NOTIFICATION_REGISTRATION_ID_KEY = "Notification_register_id";
    public static final CharSequence ERROR_MESSAGE_NO_INTERNET = "Please check your network connection!";

    public static final String Rideivoce = "Rideivoce";
    public static final String USER_NAME = "username";
    public static final String USER_ID = "user_id";
    public static final String REFERRALCODE = "REFERRALCODE";
    public static final String REFERRALLINK = "REFERRALLINK";
    public static final String USER_CAR_ID = "CarId";
    public static final String User_Activemode = "isactive";
    public static final String USER_CAR_TYPE = "CARTYPE";
    public static final String USER_TYPE = "2";
    public static final String USER_FULL_NAME = "user_name";
    public static final String CAR_TYPE = "CAR_TYPE";
    public static final String CAR_TYPEID = "CAR_TYPEId";
    public static final String CAR_TYPE_SWITCHING = "CAR_TYPE_SWITCHING";
    public static final String ACCESS_TOKEN = "accesstoken_";
    public static final String TEM_USERNAME = "TEM_USERNAME";
    public static final String TEM_PASSWORD = "TEM_PASSWORD";
    public static final String Profile_not_Completed = "Profile_not_Completed";
    public static final String Profile_not_Completed_name = "Name";
    public static final String Profile_not_Completed_email = "Email";
    public static final String Profile_not_Completed_phone = "Phone";
    public static final String Add_Vehicle_Not_Completed = "Add_Vehicle_Not_Completed";

    public static final String WAITING_TIME = "WAITING_TIME";
    public static final String Locationservicesecondclik = "locationpermission";
    public static final String Phonecallpermission = "phonecallpermission";
    public static final String APP_PREFS_USERKEY = "useremail";
    public static final String APP_PREFS_AUTH_TOKEN = "accesstoken";
    public static final String APP_PREFS_ISGUEST_USER = "isguestuser";
    public static final String User_type = "2";
    public static final String FacebookLogin = "1";
    public static final String TwitterLogin = "2";
    public static final String GooglePlusLogin = "3";
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int GALLERY_IMAGE_REQUEST_CODE = 101;
    public static final String Profile_pic_url = "Profile_pic_url";
    public static final String Profile_name = "Profile_name";
    public static final String Profile_mobilenumber = "Profile_mobilenumber";
    public static final String newridebundel = "Newridebundel";
    public static final String newridedata = "Newridedata";
    public static final String notificationbundel = "notificationbundel";
    public static final int IMAGETYPE_LOCAL = 0;
    public static final int IMAGETYPE_COLORCODE_LOCAL = 2;
    public static final int IMAGETYPE_URL = 1;
    public static String appName = "App Name";
    public static String[] menuList = {"My Coupons", "About Us", "Contact Us", "Terms & Conditions", "Privacy & Policy"};

    //Browse Tab items
    public static String[] HOMESCREEN_MAIN_CATEGORY_ITEMS = {"Market Place", "Market Feeds", "Quick Updates", "Me Coupons", "Business Point"};
    public static String[] DD_MARKETPLACE_CATEGORY_ITEMS = {"Nutrition", "Fitness", "Organic", "PersonalCare"};
    public static final String Google_map_key = "AIzaSyAkhiBRDxuGHZ4-ojtWBS8rTYpMvDZOQUs";
    public static final String Goolgle_Location_details = "https://maps.googleapis.com/maps/api/directions/json";

    //Development URl
    /*public static final String BASE_URL = "http://45.55.48.150:1347/";
    public static final String SOCKET_BASE_URL = "http://45.55.48.150:1347";*/


    //Stagging URl
    /*public static final String BASE_UR\L = "http://192.227.107.58:1320/";
    public static final String SOCKET_BASE_URL = "http://192.227.107.58:1320";*/

    //Client URl
    public static final String SOCKET_BASE_URL = "http://192.227.107.58:1347";
    public static final String BASE_URL = "http://192.227.107.58:1347/";
    public static final String REFER_LINK = BASE_URL + "listReferalLink";
    public static final String LOGIN_URL = BASE_URL + "login";
    public static final String REGISTRATION_URL = BASE_URL + "createUser";
    public static final String SOCIAL_LOGIN_URL = BASE_URL + "socialLoginForDriver";
    public static String OTP_GENERATION_email = BASE_URL + "otpForResetPasswordForMobile?email=";
    public static String OTP_GENERATION_MobileNO = BASE_URL + "otpForResetPasswordForMobile?mobile_number=";
    public static String OTP_Verification_email = BASE_URL + "otpCheckForResetPasswordForMobile?email=";
    public static String OTP_Verification_Mobileno = BASE_URL + "otpCheckForResetPasswordForMobile?mobile_number=";
    public static final String Image_upload = BASE_URL + "addImage";
    public static final String Document_upload = BASE_URL + "addDocument";
    public static final String UPdate_driver = BASE_URL + "updateUser?id=";
    public static final String UPdate_Addcar = BASE_URL + "addCar";
    public static final String REFERRAL_EARNINGS = BASE_URL + "listReferalearning";
    public static final String TODAY_RIDEDETAIL = BASE_URL + "earningPerDay?date=";
    public static final String Bredcrumbsdetails = BASE_URL + "referalBreadcrumbs?user=";
    public static final String FETCH_REFERRAL_EARNINGS = BASE_URL + "listUserLevelReferralCount?user_type=";
    public static final String REFERRAL_AGAINST_EARNINGS = BASE_URL + "listReferalAgainstLevelUser";
    public static final String FETCH_REFERRAL_AGAINST_EARNINGS = BASE_URL + "listUserLevelReferralDetails?user_type=";
    public static final String UPdate_Cardeatails = BASE_URL + "updateCar?id=";
    public static final String UploadCarimage = BASE_URL + "addCarImage";
    public static final String Fetch_User = BASE_URL + "listUser?id=";
    public static final String Image_display = BASE_URL + "images";
    public static final String Car_Image_display = BASE_URL + "images/carpicture";
    public static final String Driver_acceptRide = BASE_URL + "driverAcceptRide?id=";
    public static final String Share_Driver_acceptRide = BASE_URL + "driverShareAcceptRide?id=";
    public static final String DRIVER_REJECT_RIDE = BASE_URL + "cancelRide?id=";
    public static final String Documet_display = BASE_URL + "listDocument?user=";
    public static final String documentImage_display = BASE_URL + "images/documentpicture";
    public static final String Fetch_cardetail = BASE_URL + "listCar?user=";
    public static final String SET_AVILABLE_USER = BASE_URL + "setAvailabilityUser?id=";
    public static final String OTP_VERIFICATION = BASE_URL + "otpVerification?id=";
    public static final String DRIVER_REJECTRIDE = BASE_URL + "rejectRide?ride_id=";
    public static final String DRIVER_REJECT_MESSAGE = BASE_URL + "listCancelMessage?cancel_message_type=2";
    public static final String PRIMETIME = BASE_URL + "PrimeArea";
    public static final String ENDRIDE = BASE_URL + "completeRide?id=";
    public static final String END_SHARE_RIDE = BASE_URL + "completeShareRide?id=";
    public static final String UPDATE_RIDE = BASE_URL + "updateRide?id=";
    public static final String MASTERCARLIST = BASE_URL + "listMasterCarType";
    public static final String Cashpayment_accepted = BASE_URL + "cashPaymentcompleted?ride_id=";
    public static final String LISTRIDE = BASE_URL + "listMyRide";
    public static final String LOGOUT = BASE_URL + "logout";
    public static final String LIST_CITY = BASE_URL + "listCity";
    // public static final String Fetch_cartypes = BASE_URL + "listCarType";
    public static final String Fetch_cartypes = BASE_URL + "listCarTypeNew";
    public static final int NUMBER_OF_ITEMS_IN_LIST = 20;

    public static final String ERROR_MESSAGE_UNPUBLISHED_EPISODE = "Sorry, The episode you are looking out for is no longer available!";
    public static final String ERROR_MESSAGE_YAHOO_LOGIN = "Sorry, Try again later";

    public static final String TEXT_FONT = "Roboto-Regular.ttf";

    public static final String TYPE_FITNESS = "Fitness";

    public static final String GET_BRANDS = "brands";

    public static final String STATUS_CODE_SUCESS = "200";

    public static final String ERROR_MESSAGE_SEARCH_BRANDS = "The information for your city will be coming soon !";

    public static final String ERROR_MESSAGE_EMPTY_BRANS_LIST = "No result found!";

    public static final String ERROR_MESSAGE_COMMON = "Oops Try again later!";

    public static final String MESSAGE_ENTER_VALUE = "Please enter the ";


    public static final String ERROR_MESSAGE_SEARCH_FITNESS = "No Fitness Club Available!";

    public static final String ERROR_MESSAGE_EMPTY_CHANNEL_LIST = "No Fitness Club Available!";

    public static final String TYPE_EPISODE = "PersonalCare";


    public static final String ERROR_MESSAGE_SEARCH_EPISODES = "No Personal care Available!";

    public static final String ERROR_MESSAGE_EMPTY_EPISODE_LIST = "No Personal care Available!";

    public static final String GENERIC_ERROR_MESSAGE = "Oops Try again later!";


    public static final String ERROR_MESSAGE_SEARCH_SHOWS = "No organic shop Available!";

    public static final String ERROR_MESSAGE_EMPTY_SHOW_LIST = "No organic shop Available!";

    public static final String TYPE_NUTRITION = "nutrition";

    public static final String MAINSCREEN = "homescreen";

    public static final String NORMAL_USER = "1";

    public static final String SUPPLIER_USER = "2";

    public static final String ACCESS_KEY_ID = "AKIAIPGUIMAWRPG657OA";
    public static final String SECRET_KEY = "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";

    public static final String PICTURE_BUCKET = "marketnutrition";
    public static final String PICTURE_NAME = "";

    public static final int LOGIN_ACTION_REQUEST_CODE = 1001;

    public static final String STATUS_CODE_USERNAME_ALREADYEXIST = "11";
    public static final String STATUS_CODE_EMAIL_ALREADYEXIST = "12";
    public static final String STATUS_CODE_INVALID_USERNAME_PASSWORD = "13";
    public static final String STATUS_CODE_INVALID_PACKAGECODE = "14";
    public static final String STATUS_CODE_AUTHENTICATION_FAILURE = "15";
    public static final String STATUS_CODE_MISSING_AUTHENTICAION_TOKEN = "16";
    public static final String STATUS_CODE_ERROR_WHILE_PROCESSTOKEN = "17";
    public static final String STATUS_CODE_UNABLE_TO_REGISTER_USER = "18";
    public static final String STATUS_CODE_FAILD_TOSEND_EMAIL = "19";
    public static final String STATUS_CODE_INVALID_USERNAME_EMAIL = "20";
    public static final String STATUS_CODE_VALID_USERNAME_EMAIL = "21";
    public static final String STATUS_CODE_COUPON_ALREADY_AVAILABLE = "27";
    public static final String STATUS_CODE_RESTART_LOGIN = "28";

    public static final String IS_USER_EMAIL_EXISTS = null;

    public static final String TWITTER_API_KEY = "";

    public static final String TWITTER_SECRET_KEY = "";


    public static final String COMMON_ERROR_MESSAGE = "Invalid Credentials!.";

    public static final int Roboto_Black = 1;
    public static final int TEXTSTYEL_ROBOTOLIGHT = 2;
    public static final int Roboto_BlackItalic = 3;
    public static final int Roboto_Bold = 4;
    public static final int Roboto_BoldItalic = 5;
    public static final int RobotoCondensed_Bold = 6;
    public static final int RobotoCondensed_BoldItalic = 7;
    public static final int RobotoCondensed_Italic = 8;
    public static final int RobotoCondensed_Light = 9;
    public static final int RobotoCondensed_LightItalic = 10;
    public static final int RobotoCondensed_Regular = 11;
    public static final int Roboto_Italic = 12;
    public static final int Roboto_Light = 13;
    public static final int Roboto_LightItalic = 14;
    public static final int Roboto_Medium = 15;
    public static final int Roboto_MediumItalic = 16;
    public static final int Roboto_Regular = 17;
    public static final int Roboto_Thin = 18;
    public static final int Roboto_ThinItalic = 19;
    public static final int Quicksand_Bold = 20;
    public static final int Quicksand_Light = 21;
    public static final int Quicksand_Medium = 22;
    public static final int Quicksand_Regular = 23;


    public static final int Helvetica_Neu_Bold = 25;
    public static final int Helvetica_Neue_BlackCond = 26;
    public static final int Helvetica_Neue_Light = 27;
    public static final int Helvetica_Neue_Medium = 28;
    public static final int Helvetica_Neue_Thin = 29;
    public static final int Helvetica_Neue = 30;
    public static final int Helvetica_NeueBd = 31;
    public static final int Helvetica_NeueHv = 32;
    public static final int Helvetica_NeueIt = 33;
    public static final int Helvetica_NeueLt = 34;
    public static final int Helvetica_NeueMed = 35;

}
