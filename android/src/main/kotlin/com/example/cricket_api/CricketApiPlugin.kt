package com.example.cricket_api

import android.content.Context
import com.example.secure_api.NativeLib
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/** CricketApiPlugin */
class CricketApiPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  private lateinit var context: Context
  private lateinit var nativeLib: NativeLib

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    context = flutterPluginBinding.applicationContext
    nativeLib = NativeLib(context)
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "cricket_api")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    val args = call.arguments as? Map<*, *> ?: emptyMap<String, String>()
    CoroutineScope(Dispatchers.IO).launch {
      try {
        val response = when (call.method) {
          "getLiveMatch" -> nativeLib.getliveMatch()
          "getUpcomingMatch" -> nativeLib.getupCommingMatch(args["date"] as? String ?: "")
          "getMatchInfo" -> nativeLib.getmatchInfo(args["matchid"] as? String ?: "")
          "getMatchDetails" -> nativeLib.getmatchDetails(args["matchid"] as? String ?: "")
          "getMatchIning" -> nativeLib.getmatchIning(args["matchid"] as? String ?: "")
          "getMatchLineUp" -> nativeLib.getmatchLineUp(args["matchid"] as? String ?: "")
          "getCountryList" -> nativeLib.getcountryList()
          "getSeriesList" -> nativeLib.getseriesList(args["country"] as? String ?: "")
          "getSeriesDetails" -> nativeLib.getseriesDetails(
            args["country"] as? String ?: "",
            args["seriesSlug"] as? String ?: ""
          )
          "getLeagueTable" -> nativeLib.GetLeagueTable(
            args["leaguename"] as? String ?: "",
            args["leagueslug"] as? String ?: ""
          )
          "getSomeDayMatch" -> nativeLib.getsomeDayMatch()
          else -> null
        }
        withContext(Dispatchers.Main) {
          result.success(response)
        }
      } catch (e: Exception) {
        withContext(Dispatchers.Main) {
          result.error("ERROR", e.message, null)
        }
      }
    }
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}
