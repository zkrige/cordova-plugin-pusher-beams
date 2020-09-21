#import "CDVPusher.h"
#import "AppDelegate.h"
@import PushNotifications;

#pragma mark -
#pragma mark CDVPusher

@implementation CDVPusher

- (void)registerUserId:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSString* userId = [[command argumentAtIndex:0] stringValue];
        NSString* authToken= [[command argumentAtIndex:1] stringValue];
        NSString* bearerToken = [NSString stringWithFormat:@"Bearer %@", authToken];
        AppDelegate *appDelegate = (AppDelegate *)[[UIApplication sharedApplication] delegate];
        BeamsTokenProvider *tokenProvider = [[BeamsTokenProvider alloc] initWithAuthURL:@"http://pager.testing.medicom.care/api/v1/user/beams-token" getAuthData:^AuthData * _Nonnull{
            NSDictionary *headers = @{
                @"Authorization" : bearerToken
            }
            AuthData *toRet = [[AuthData alloc] initWithHeaders:headers queryParams:@{}];
            return toRet;
        }];
        [[appDelegate beamsClient] setUserId:userId tokenProvider:tokenProvider completion:^(NSError *error) {
            //user id has been set
        }];
    }];
    CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

@end
