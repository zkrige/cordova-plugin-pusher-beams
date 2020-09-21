#import <Cordova/CDVPlugin.h>

@interface APPusher : CDVPlugin

- (void)registerUserId:(CDVInvokedUrlCommand*)command;
@end
