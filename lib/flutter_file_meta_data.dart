import 'dart:async';

import 'package:flutter/services.dart';

class FlutterFileMetaData {
  static const MethodChannel _channel =
      const MethodChannel('flutter_file_meta_data');

  static Future<List> getFileMetaData(String trackPath) async {
    final List version = await _channel.invokeMethod('getFileMetadata', <String, dynamic>{
      "filepath":trackPath
    });
    return version;
  }
}
