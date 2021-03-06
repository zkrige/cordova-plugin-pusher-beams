#import "PusherBeams.h"
@import PushNotifications;

#pragma mark -
#pragma mark PusherBeams

@implementation PusherBeams

- (void)registerUserId:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{
        NSString *tokenUrl = [command argumentAtIndex:0];
        NSString *userId = [command argumentAtIndex:1];
        if ([userId isEqualToString:@"null"]) {
            //dont register yet - there is no userid
            return;
        }
        NSString *authToken= [command argumentAtIndex:2];
        NSString *bearerToken = [NSString stringWithFormat:@"Bearer %@", authToken];
        BeamsTokenProvider *tokenProvider = [[BeamsTokenProvider alloc] initWithAuthURL:tokenUrl getAuthData:^AuthData *{
            NSDictionary *headers = @{
                @"Authorization" : bearerToken
            };
            AuthData *toRet = [[AuthData alloc] initWithHeaders:headers queryParams:@{}];
            return toRet;
        }];
        [PushNotificationsStatic setUserId:userId tokenProvider:tokenProvider completion:^(NSError *error) {
            if (error != nil) {
                NSLog(@"%@", error);
            }
        }];
    }];
    CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

- (void)clear:(CDVInvokedUrlCommand*)command {
    [self.commandDelegate runInBackground:^{

        [PushNotificationsStatic clearAllStateWithCompletion:^{}];
        CDVPluginResult *result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
        [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
    }];
}

@end

