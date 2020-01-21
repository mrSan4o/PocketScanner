# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-ignorewarnings

-keep class  com.san4o.just4fun.authenticjobs.** { *; }

##--------------- Begin: proguard configuration common for all Android apps ----------
-optimizationpasses 3
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontpreverify
-verbose
-dump proguard/class_files.txt
-printseeds proguard/seeds.txt
-printusage proguard/unused.txt
-printmapping proguard/mapping.txt
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-allowaccessmodification
-keepattributes *Annotation*
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable
-repackageclasses ''

#-encryptassetfiles assets/**

-keep public class * extends androidx.app.Activity
-keep public class * extends androidx.app.Application
-keep public class * extends androidx.app.Service
-keep public class * extends androidx.content.BroadcastReceiver
-keep public class * extends androidx.content.ContentProvider
-keep public class * extends androidx.app.backup.BackupAgentHelper
-keep public class * extends androidx.preference.Preference

# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Preserve all native method names and the names of their classes.
-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# Preserve static fields of inner classes of R classes that might be accessed
# through introspection.
-keepclassmembers class **.R$* {
  public static <fields>;
}

# Preserve the special static methods that are required in all enumeration classes.
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep public class * {
    public protected *;
}

-keep class * implements androidx.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
##--------------- End: proguard configuration common for all Android apps ----------



-assumenosideeffects class android.util.Log {
public static boolean isLoggable(java.lang.String, int);
public static int d(...);
public static int w(...);
public static int v(...);
public static int i(...);
public static int e(...);
}

-assumenosideeffects class timber.log.Timber* {
public static *** d(...);
public static *** w(...);
public static *** v(...);
public static *** i(...);
public static *** e(...);
}

