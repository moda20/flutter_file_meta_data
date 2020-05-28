# flutter_file_meta_data

A Flutter plugin that enables you to get fileMetadata on iOS and Android
#### This Plugin is available only on Android 

## Usage

To get Any File metadata :

```dart

import 'package:flutter_file_meta_data/flutter_file_meta_data.dart';

List metadata = await FlutterFileMetaData.getFileMetaData("filePath");

print(metadata); // Will print a list of metaData of that file 

//Output Definition 

metadata = [ Title, Artist, 8bit AlbumCover, Album Title, Track Number, Duration, Genre, Bitrate, Year ]

```




## Getting Started

This project is a starting point for a Flutter
[plug-in package](https://flutter.dev/developing-packages/),
a specialized package that includes platform-specific implementation code for
Android and/or iOS.

For help getting started with Flutter, view our 
[online documentation](https://flutter.dev/docs), which offers tutorials, 
samples, guidance on mobile development, and a full API reference.
