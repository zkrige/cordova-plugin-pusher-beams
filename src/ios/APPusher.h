#import <Cordova/CDVPlugin.h>

@interface CDVPusher : CDVPlugin

- (void)registerUserId:(CDVInvokedUrlCommand*)command;
@end
