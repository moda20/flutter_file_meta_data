package com.modatwenty.flutter_file_meta_data;

import android.media.MediaMetadataRetriever;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FlutterFileMetaDataPlugin */
public class FlutterFileMetaDataPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private static final MediaMetadataRetriever mmr = new MediaMetadataRetriever();
  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "flutter_file_meta_data");
    channel.setMethodCallHandler(this);
  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "flutter_file_meta_data");
    channel.setMethodCallHandler(new FlutterFileMetaDataPlugin());
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    Map<String, Object> arguments = call.arguments();

    switch(call.method){
      case "getPlatformVersion":{
        result.success("Android " + android.os.Build.VERSION.RELEASE);
        break;
      }

      case "getFileMetadata":{

        String filepath = (String) arguments.get("filepath");
        List l = new ArrayList();
        try{
          mmr.setDataSource(filepath);
          l.add(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
          l.add(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
          try {
            byte[] pic = mmr.getEmbeddedPicture();
            l.add(pic);
          } catch (Exception e) {
            l.add("");
          }

          l.add(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
          l.add(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_CD_TRACK_NUMBER));
          l.add(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
          l.add(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_GENRE));
          l.add(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));
          l.add(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR));

        } catch (Exception e) {
          String[] arraysl= {null, null, null, null, null, null, null, null};
          Collections.addAll(l,arraysl);
        }
        result.success(l);
        break;
      }


      default:{
        result.notImplemented();
      }
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
