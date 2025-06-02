import 'package:flutter_test/flutter_test.dart';
import 'package:cricket_api/cricket_api.dart';
import 'package:cricket_api/cricket_api_platform_interface.dart';
import 'package:cricket_api/cricket_api_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockCricketApiPlatform
    with MockPlatformInterfaceMixin
    implements CricketApiPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final CricketApiPlatform initialPlatform = CricketApiPlatform.instance;

  test('$MethodChannelCricketApi is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelCricketApi>());
  });

  test('getPlatformVersion', () async {
    CricketApi cricketApiPlugin = CricketApi();
    MockCricketApiPlatform fakePlatform = MockCricketApiPlatform();
    CricketApiPlatform.instance = fakePlatform;

    expect(await cricketApiPlugin.getPlatformVersion(), '42');
  });
}
