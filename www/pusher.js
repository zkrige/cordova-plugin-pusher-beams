/*global cordova, module*/

module.exports = {
    registerUserId: function (userId, authToken, successCallback, errorCallback) {
        cordova.exec(successCallback, errorCallback, "PusherBeams", "registerUserId", [userId, authToken]);
    }
};
