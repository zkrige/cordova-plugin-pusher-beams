#import "APPusher.h"
@import PushNotifications;

#pragma mark -
#pragma mark APPusher

@implementation APPusher

- (void)registerUserId:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSString *tokenUrl = [[command argumentAtIndex:0] stringValue];
        NSString *userId = [[command argumentAtIndex:1] stringValue];
        NSString *authToken= [[command argumentAtIndex:2] stringValue];
        NSString *bearerToken = [NSString stringWithFormat:@"Bearer %@", authToken];
        BeamsTokenProvider *tokenProvider = [[BeamsTokenProvider alloc] initWithAuthURL:tokenUrl getAuthData:^AuthData * _Nonnull{
            NSDictionary *headers = @{
                @"Authorization" : bearerToken
            };
            AuthData *toRet = [[AuthData alloc] initWithHeaders:headers queryParams:@{}];
            return toRet;
        }];
        [PushNotificationsStatic setUserId:userId tokenProvider:tokenProvider completion:^(NSError *error) {
            //user id has been set
        }];
    }];
    CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

@end
