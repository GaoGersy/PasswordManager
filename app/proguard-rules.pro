# Add project specific ProGuard rules here.
# By defaulticon, the flags in this file are appended to flags specified
# in D:\Androide/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}


-optimizationpasses 5          # 指定代码的压缩级别
-dontusemixedcaseclassnames   # 是否使用大小写混合
-dontpreverify           # 混淆时是否做预校验
-verbose                # 混淆时是否记录日志

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法

-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆

-keepclasseswithmembernames class * {  # 保持 native 方法不被混淆
    native <methods>;
}
-keepclasseswithmembers class * {   # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {# 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
    public void *(android.view.View);
}
-keepclassmembers enum * {     # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}

-keepattributes Signature
-ignorewarnings

#butterknife
-dontwarn butterknife.**
-keep class butterknife.** { *;}

#rxjava
-dontwarn rx.**
-keep class rx.** { *;}

#dialog
-dontwarn com.hss01248.dialog.**
-keep class com.hss01248.dialog.** { *;}

#sqlcipher
-dontwarn net.sqlcipher.**
-keep class net.sqlcipher.** { *;}

#calligraphy
-dontwarn uk.co.chrisjenx.calligraphy.**
-keep class uk.co.chrisjenx.calligraphy.** { *;}

#transforms
-dontwarn com.ToxicBakery.viewpager.transforms.**
-keep class com.ToxicBakery.viewpager.transforms.** { *;}

#eventbus
-dontwarn org.greenrobot.eventbus.**
-keep class org.greenrobot.eventbus.** { *;}

#logger
-dontwarn com.orhanobut.logger.**
-keep class com.orhanobut.logger.** { *;}

#materialsheetfab
-dontwarn com.gordonwong.materialsheetfab.**
-keep class com.gordonwong.materialsheetfab.** { *;}

#recyclerview
-dontwarn jp.wasabeef.recyclerview.**
-keep class jp.wasabeef.recyclerview.** { *;}

#rxandroid
-dontwarn rx.android.**
-keep class rx.android.** { *;}

#rebound
-dontwarn com.facebook.rebound.**
-keep class com.facebook.rebound.** { *;}

#rebound
-dontwarn com.facebook.rebound.**
-keep class com.facebook.rebound.** { *;}

#v4
-dontwarn android.support.v4.**
-keep class android.support.v4.** { *;}

#compat
-dontwarn android.support.compat.**
-keep class android.support.compat.** { *;}

#v7
-dontwarn android.support.v7.**
-keep class android.support.v7.** { *;}

#annotation
-dontwarn android.support.annotation.**
-keep class android.support.annotation.** { *;}

-keep class com.gersion.superlock.**{*;}