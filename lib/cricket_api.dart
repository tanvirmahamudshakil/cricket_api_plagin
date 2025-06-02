import 'package:flutter/services.dart';

class CricketApi {
  static final _channel = const MethodChannel('cricket_api');

  Future<String?> getPlatformVersion() async {
    final version = await _channel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  static Future<String?> getLiveMatch() => _channel.invokeMethod("getLiveMatch");

  static Future<String?> getUpcomingMatch(String date) => _channel.invokeMethod("getUpcomingMatch", {"date": date});

  static Future<String?> getMatchInfo(String matchid) => _channel.invokeMethod("getMatchInfo", {"matchid": matchid});

  static Future<String?> getMatchDetails(String matchid) =>
      _channel.invokeMethod("getMatchDetails", {"matchid": matchid});

  static Future<String?> getMatchIning(String matchid) => _channel.invokeMethod("getMatchIning", {"matchid": matchid});

  static Future<String?> getMatchLineUp(String matchid) =>
      _channel.invokeMethod("getMatchLineUp", {"matchid": matchid});

  static Future<String?> getCountryList() => _channel.invokeMethod("getCountryList");

  static Future<String?> getSeriesList(String country) => _channel.invokeMethod("getSeriesList", {"country": country});

  static Future<String?> getSeriesDetails(String country, String seriesSlug) =>
      _channel.invokeMethod("getSeriesDetails", {"country": country, "seriesSlug": seriesSlug});

  static Future<String?> getLeagueTable(String leaguename, String leagueslug) =>
      _channel.invokeMethod("getLeagueTable", {"leaguename": leaguename, "leagueslug": leagueslug});

  static Future<String?> getSomeDayMatch() => _channel.invokeMethod("getSomeDayMatch");

  static Future getBannerImageBytes(String slug) async {
    final bytes = await _channel.invokeMethod("getBannerImageBytes", {"slug": slug});
    return bytes;
  }
}
