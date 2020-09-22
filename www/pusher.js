/*global cordova, module*/

module.exports = {
    registerUserId: function (tokenUrl, userId, authToken, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "PusherBeams", "registerUserId", [tokenUrl, userId, authToken]);
    },
    clear: function(successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "PusherBeams", "clear", []);
    }
};
